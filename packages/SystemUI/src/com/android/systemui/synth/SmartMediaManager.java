/**
 * Copyright (C) 2018 The Android Open Source Project
 * Copyright (C) 2019 ArrowOS
 * Copyright (C) 2020 Potato Open Source Project
 * Copyright (C) 2021 SynthOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Smart media manager for themes and more
 */

package com.android.systemui.synth;

import android.annotation.Nullable;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.Observer;

import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.SmartMediaPlugin;
import com.android.systemui.synth.smartmedia.DefaultSmartMedia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Manages custom clock faces for AOD and lock screen.
 */
@Singleton
public class SmartMediaManager {

    private static final String TAG = "SmartMediaProvider";

    private final AvailableSmartMedia mSmartMediaViews;

    private final Context mContext;
    private final ContentResolver mContentResolver;

    private final PluginManager mPluginManager;

    private final int mWidth;
    private final int mHeight;

    @VisibleForTesting
    SmartMediaManager(Context context, PluginManager pluginManager, SysuiColorExtractor colorExtractor) {
        mContext = context;
        mPluginManager = pluginManager;
        mContentResolver = context.getContentResolver();
        mSmartMediaViews = new AvailableSmartMedia();

        Resources res = context.getResources();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        addBuiltinSmartMedia(() -> new DefaultSmartMedia(res, layoutInflater, colorExtractor, context));

        // Store the size of the display for generation of clock preview.
        DisplayMetrics dm = res.getDisplayMetrics();
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
    }

    /**
     * Get the current clock.
     * @return current custom clock or null for default.
     */
    @Nullable
    SmartMediaPlugin getCurrentSmartMedia() {
        return mSmartMediaViews.getCurrentSmartMedia();
    }

    @VisibleForTesting
    void addBuiltinSmartMedia(Supplier<SmartMediaPlugin> pluginSupplier) {
        SmartMediaPlugin plugin = pluginSupplier.get();
        mSmartMediaViews.addSmartMediaPlugin(plugin);
    }

    private void register() {
        mPluginManager.addPluginListener(mSmartMediaViews, SmartMediaPlugin.class, true);
    }

    private void unregister() {
        mPluginManager.removePluginListener(mSmartMediaViews);
    }

    private void reload() {
        mSmartMediaViews.reloadCurrentSmartMedia();
    }

    /**
     * Collection of available smartMedia.
     */
    private final class AvailableSmartMedia implements PluginListener<SmartMediaPlugin> {

        /**
         * Map from expected value stored in settings to plugin for custom clock face.
         */
        private final Map<String, SmartMediaPlugin> mSmartMediaMap = new ArrayMap<>();

        /**
         * Active SmartMediaPlugin.
         */
        @Nullable private SmartMediaPlugin mCurrentSmartMedia;

        @Override
        public void onPluginConnected(SmartMediaPlugin plugin, Context pluginContext) {
            addSmartMediaPlugin(plugin);
            reloadIfNeeded(plugin);
        }

        @Override
        public void onPluginDisconnected(SmartMediaPlugin plugin) {
            removeSmartMediaPlugin(plugin);
            reloadIfNeeded(plugin);
        }

        @Nullable
        SmartMediaPlugin getCurrentSmartMedia() {
            return mCurrentSmartMedia;
        }

        void addSmartMediaPlugin(SmartMediaPlugin plugin) {
            final String id = plugin.getClass().getName();
            mSmartMediaMap.put(plugin.getClass().getName(), plugin);
        }

        private void removeSmartMediaPlugin(SmartMediaPlugin plugin) {
            final String id = plugin.getClass().getName();
            mSmartMediaMap.remove(id);
        }

        private void reloadIfNeeded(SmartMediaPlugin plugin) {
            final boolean wasCurrentClock = plugin == mCurrentSmartMedia;
            reloadCurrentSmartMedia();
            final boolean isCurrentClock = plugin == mCurrentSmartMedia;
        }

        /**
         * Update the current clock.
         */
        void reloadCurrentSmartMedia() {
            mCurrentSmartMedia = getSmartMediaPlugin();
        }

        private SmartMediaPlugin getSmartMediaPlugin() {
            SmartMediaPlugin plugin = null;
            final String name = Settings.System.getString(mContentResolver, Settings.System.SMART_MEDIA_THEME);
            if (name != null) {
                plugin = mSmartMediaMap.get(name);
                if (plugin != null) {
                    return plugin;
                }
            }
            return plugin;
        }
    }
}