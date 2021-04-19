/*
* Copyright (C) 2021 SynthOS
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package com.android.systemui.synth.smartmedia;

import android.app.PendingIntent;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.android.internal.colorextraction.ColorExtractor;
import com.android.settingslib.Utils;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.internal.util.ImageUtils;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.statusbar.MediaArtworkProcessor;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.plugins.SmartMediaPlugin;
import com.android.systemui.media.MediaData;
import com.android.systemui.media.MediaAction;

import java.util.List;

public class DefaultSmartMedia implements SmartMediaPlugin, MediaDataManager.Listener {
    private static final boolean DEBUG = true;
    private static final String TAG = "SynthMediaView";

    /**
     * Resources used to get title and thumbnail.
     */
    private final Resources mResources;

    /**
     * LayoutInflater used to inflate custom clock views.
     */
    private final LayoutInflater mLayoutInflater;

    /**
     * Extracts accent color from wallpaper.
     */
    private final SysuiColorExtractor mColorExtractor;

    // Button IDs for QS controls
    static final int[] ACTION_IDS = {
            R.id.action0,
            R.id.action1,
            R.id.action2,
            R.id.action3,
            R.id.action4
    };

    Context mContext;
    private NotificationMediaManager mMediaManager;
    private MediaDataManager mMediaDataManager;
    private MediaArtworkProcessor mMediaArtworkProcessor;
    private MediaSession.Token mToken;
    private MediaController mController;

    boolean mState = true;

    /**
     * Root view of clock.
     */
    private View mView;

    // Views
    private TextView mTitle;
    private TextView mArtist;
    private ImageView mArtwork;
    private View mBackground;
    private TextView mAppName;
    private ImageView mAppIcon;

    private int mBackgroundColor;
    private int mAlbumArtRadius = 20;

    // This will provide the corners for the album art.
    private ViewOutlineProvider mArtworkOutlineProvider;
    private ViewOutlineProvider mBackgroundOutlineProvider;

    private boolean mBlur;
    private float mBlurRadius;

    private Runnable mRunnable;

    /**
     * Create a DefaultClockController instance.
     *
     * @param res Resources contains title and thumbnail.
     * @param inflater Inflater used to inflate custom clock views.
     * @param colorExtractor Extracts accent color from wallpaper.
     */
    public DefaultSmartMedia(Resources res, LayoutInflater inflater,
            SysuiColorExtractor colorExtractor, Context context) {
        mResources = res;
        mLayoutInflater = inflater;
        mColorExtractor = colorExtractor;
        mContext = context;
        mMediaManager = Dependency.get(NotificationMediaManager.class);
        mMediaArtworkProcessor = mMediaManager.getMediaArtworkProcessor();
        mMediaDataManager = mMediaManager.getMediaDataManager();
        mMediaDataManager.addListener(this);
    }

    public void createViews() {
        mView = mLayoutInflater.inflate(R.layout.synth_media_view, null);

        mTitle = (TextView) mView.findViewById(R.id.title);
        mArtist = (TextView) mView.findViewById(R.id.artist);
        mArtwork = (ImageView) mView.findViewById(R.id.artwork);

        mAppName = (TextView) mView.findViewById(R.id.app_name);
        mAppIcon = (ImageView) mView.findViewById(R.id.app_icon);

        mBackground = mView.findViewById(R.id.background);
    }

    @Override
    public void onDestroyView() {
        mView = null;
        mTitle = null;
        mArtist = null;
        mArtwork = null;
        mAppName = null;
        mAppIcon = null;
        mBackground = null;
        mMediaDataManager.removeListener(this);
    }

    @Override
    public void onMediaDataLoaded(String key, String oldKey, MediaData data) {
        if (mState) {
            bind(data);
        }
    }

    /**
    * Is empty cause is unnecessary.
    * @param key key of notification removed.
    */
    @Override
    public void onMediaDataRemoved(String key) {
    }

    /**
     * Bind this view based on the data given
     */
    public void bind(@NonNull MediaData data) {

        MediaSession.Token token = data.getToken();
        mBackgroundColor = data.getBackgroundColor();
        if (mToken == null || !mToken.equals(token)) {
            mToken = token;
        }

        if (mToken != null) {
            mController = new MediaController(mContext, mToken);
        } else {
            mController = null;
        }

        // Background
        mBackground.setBackgroundTintList(
                ColorStateList.valueOf(mBackgroundColor));
        mBackgroundOutlineProvider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, mBackground.getWidth(), mBackground.getHeight(), mAlbumArtRadius);
            }
        };
        mBackground.setClipToOutline(true);
        mBackground.setOutlineProvider(mBackgroundOutlineProvider);

        // Artwork
        boolean hasArtwork = data.getArtwork() != null;
        if (hasArtwork) {
            Drawable artwork = ImageUtils.resize(mContext, data.getArtwork().loadDrawable(mContext), mArtwork.getWidth());
            Bitmap artworkBlur = mMediaArtworkProcessor.processArtwork(mContext, ImageUtils.buildScaledBitmap(artwork, artwork.getIntrinsicWidth(), artwork.getIntrinsicHeight()), mBlurRadius);
            if (mBlur) mArtwork.setImageBitmap(artworkBlur); else mArtwork.setImageDrawable(artwork);
            mArtworkOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, mArtwork.getWidth(), mArtwork.getHeight(), mAlbumArtRadius);
                }
            };
            mArtwork.setClipToOutline(true);
            mArtwork.setOutlineProvider(mArtworkOutlineProvider);
        }

        // App icon
        if (data.getAppIcon() != null) {
            mAppIcon.setImageDrawable(data.getAppIcon());
        } else {
            Drawable iconDrawable = mContext.getDrawable(R.drawable.ic_music_note);
            mAppIcon.setImageDrawable(iconDrawable);
        }

        // Song name
        mTitle.setText(data.getSong());

        // App title
        mAppName.setText(data.getApp());

        // Artist name
        mArtist.setText(data.getArtist());

        // Actions

        // Media controls
        int i = 0;
        List<MediaAction> actionIcons = data.getActions();
        for (; i < actionIcons.size() && i < ACTION_IDS.length; i++) {
            int actionId = ACTION_IDS[i];
            final ImageButton button = (ImageButton) mView.findViewById(actionId);
            MediaAction mediaAction = actionIcons.get(i);
            button.setImageDrawable(mediaAction.getDrawable());
            button.setContentDescription(mediaAction.getContentDescription());
            Runnable action = mediaAction.getAction();

            if (action == null) {
                button.setEnabled(false);
                button.setVisibility(View.GONE);
            } else {
                button.setEnabled(true);
                button.setOnClickListener(v -> {
                    action.run();
                    if (mRunnable != null) {
                        mRunnable.run();
                    }
                });
                button.setVisibility(View.VISIBLE);
            }
        }

        // Hide any unused buttons
        for (; i < ACTION_IDS.length; i++) {
            int actionId = ACTION_IDS[i];
            final ImageButton button = (ImageButton) mView.findViewById(actionId);
            button.setVisibility(View.GONE);
        }
    }

    @Override
    public void setState(boolean state) {
        if (mState != state) mState = state;
    }

    @Override
    public void setArtworkBlur(boolean blur, float blurRadius) {
        mBlur = blur;
        mBlurRadius = blurRadius;
    }

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public String getTitle() {
        return mResources.getString(R.string.default_smart_media);
    }

    @Override
    public Bitmap getThumbnail() {
        return null;
    }

    @Override
    public Bitmap getPreview(int width, int height) {
        return null;
    }

    @Override
    public View getMediaView() {
        if (mView == null) {
            createViews();
        }
        return mView;
    }

    @Override
    public void setHasVisibleNotifications(boolean hasVisibleNotifications) {}

    @Override
    public void setTitleTypeface(Typeface tf) {
        mTitle.setTypeface(tf);
    }

    @Override
    public void setArtistTypeface(Typeface tf) {
        mArtist.setTypeface(tf);
    }
    
    @Override
    public void setAppTypeface(Typeface tf) {
        mAppName.setTypeface(tf);
    }

    @Override
    public void setDarkAmount(float darkAmount) {}

    @Override
    public void setShowRunnable(Runnable run) {}

    @Override
    public void setHideRunnable(Runnable run) {}

    @Override
    public void setOnClickRunnable(Runnable run) {
        mRunnable = run;
    }

}
