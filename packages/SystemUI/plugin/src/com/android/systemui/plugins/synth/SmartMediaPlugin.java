/*
 * Copyright (C) 2018 The Android Open Source Project
 * Copyright (C) 2021 SynthOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.android.systemui.plugins;

import android.graphics.Bitmap;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.view.View;

import com.android.systemui.plugins.annotations.ProvidesInterface;

import java.util.TimeZone;

/**
 * Plugin used to replace smart media in keyguard.
 */
@ProvidesInterface(action = SmartMediaPlugin.ACTION, version = SmartMediaPlugin.VERSION)
public interface SmartMediaPlugin extends Plugin {

    String ACTION = "com.android.systemui.action.PLUGIN_SMART_MEDIA";
    int VERSION = 1;

    String getName();

    String getTitle();

    Bitmap getThumbnail();

    Bitmap getPreview(int width, int height);

    View getMediaView();

    void setState(boolean state);

    default void setHasVisibleNotifications(boolean hasVisibleNotifications) {}

    default void onDestroyView() {}

    default void setShowRunnable(Runnable run) {}

    default void setHideRunnable(Runnable run) {}

    default void setOnClickRunnable(Runnable run) {}

    default void setTitleTypeface(Typeface tf) {}

    default void setArtistTypeface(Typeface tf) {}
    
    default void setAppTypeface(Typeface tf) {}

    default void setDarkAmount(float darkAmount) {}

    default void setArtworkBlur(boolean blur, float blurRadius) {}
}
