/*
 * Copyright (C) 2019 The Android Open Source Project
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
 */

package android.provider.settings.backup;

import android.compat.annotation.UnsupportedAppUsage;
import android.provider.Settings;

/** Information relating to the Secure settings which should be backed up */
public class SecureSettings {

    /**
     * NOTE: Settings are backed up and restored in the order they appear
     *       in this array. If you have one setting depending on another,
     *       make sure that they are ordered appropriately.
     */
    @UnsupportedAppUsage
    public static final String[] SETTINGS_TO_BACKUP = {
        Settings.Secure.BUGREPORT_IN_POWER_MENU,                            // moved to global
        Settings.Secure.ALLOW_MOCK_LOCATION,
        Settings.Secure.USB_MASS_STORAGE_ENABLED,                           // moved to global
        Settings.Secure.ACCESSIBILITY_DISPLAY_INVERSION_ENABLED,
        Settings.Secure.ACCESSIBILITY_DISPLAY_DALTONIZER,
        Settings.Secure.ACCESSIBILITY_DISPLAY_DALTONIZER_ENABLED,
        Settings.Secure.ACCESSIBILITY_DISPLAY_MAGNIFICATION_ENABLED,
        Settings.Secure.ACCESSIBILITY_DISPLAY_MAGNIFICATION_NAVBAR_ENABLED,
        Settings.Secure.ADAPTIVE_SLEEP,
        Settings.Secure.AUTOFILL_SERVICE,
        Settings.Secure.ACCESSIBILITY_DISPLAY_MAGNIFICATION_SCALE,
        Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES,
        Settings.Secure.ENABLED_VR_LISTENERS,
        Settings.Secure.TOUCH_EXPLORATION_GRANTED_ACCESSIBILITY_SERVICES,
        Settings.Secure.TOUCH_EXPLORATION_ENABLED,
        Settings.Secure.ACCESSIBILITY_ENABLED,
        Settings.Secure.ACCESSIBILITY_SHORTCUT_TARGET_SERVICE,
        Settings.Secure.ACCESSIBILITY_BUTTON_TARGET_COMPONENT,
        Settings.Secure.ACCESSIBILITY_SHORTCUT_DIALOG_SHOWN,
        Settings.Secure.ACCESSIBILITY_SHORTCUT_ON_LOCK_SCREEN,
        Settings.Secure.ACCESSIBILITY_HIGH_TEXT_CONTRAST_ENABLED,
        Settings.Secure.ACCESSIBILITY_CAPTIONING_PRESET,
        Settings.Secure.ACCESSIBILITY_CAPTIONING_ENABLED,
        Settings.Secure.ACCESSIBILITY_CAPTIONING_LOCALE,
        Settings.Secure.ACCESSIBILITY_CAPTIONING_BACKGROUND_COLOR,
        Settings.Secure.ACCESSIBILITY_CAPTIONING_FOREGROUND_COLOR,
        Settings.Secure.ACCESSIBILITY_CAPTIONING_EDGE_TYPE,
        Settings.Secure.ACCESSIBILITY_CAPTIONING_EDGE_COLOR,
        Settings.Secure.ACCESSIBILITY_CAPTIONING_TYPEFACE,
        Settings.Secure.ACCESSIBILITY_CAPTIONING_FONT_SCALE,
        Settings.Secure.ACCESSIBILITY_CAPTIONING_WINDOW_COLOR,
        Settings.Secure.TTS_DEFAULT_RATE,
        Settings.Secure.TTS_DEFAULT_PITCH,
        Settings.Secure.TTS_DEFAULT_SYNTH,
        Settings.Secure.TTS_ENABLED_PLUGINS,
        Settings.Secure.TTS_DEFAULT_LOCALE,
        Settings.Secure.SHOW_IME_WITH_HARD_KEYBOARD,
        Settings.Secure.WIFI_NETWORKS_AVAILABLE_NOTIFICATION_ON,            // moved to global
        Settings.Secure.WIFI_NETWORKS_AVAILABLE_REPEAT_DELAY,               // moved to global
        Settings.Secure.WIFI_NUM_OPEN_NETWORKS_KEPT,                        // moved to global
        Settings.Secure.MOUNT_PLAY_NOTIFICATION_SND,
        Settings.Secure.MOUNT_UMS_AUTOSTART,
        Settings.Secure.MOUNT_UMS_PROMPT,
        Settings.Secure.MOUNT_UMS_NOTIFY_ENABLED,
        Settings.Secure.DOUBLE_TAP_TO_WAKE,
        Settings.Secure.WAKE_GESTURE_ENABLED,
        Settings.Secure.LONG_PRESS_TIMEOUT,
        Settings.Secure.CAMERA_GESTURE_DISABLED,
        Settings.Secure.ACCESSIBILITY_AUTOCLICK_ENABLED,
        Settings.Secure.ACCESSIBILITY_AUTOCLICK_DELAY,
        Settings.Secure.ACCESSIBILITY_LARGE_POINTER_ICON,
        Settings.Secure.PREFERRED_TTY_MODE,
        Settings.Secure.ENHANCED_VOICE_PRIVACY_ENABLED,
        Settings.Secure.TTY_MODE_ENABLED,
        Settings.Secure.RTT_CALLING_MODE,
        Settings.Secure.INCALL_POWER_BUTTON_BEHAVIOR,
        Settings.Secure.MINIMAL_POST_PROCESSING_ALLOWED,
        Settings.Secure.NIGHT_DISPLAY_CUSTOM_START_TIME,
        Settings.Secure.NIGHT_DISPLAY_CUSTOM_END_TIME,
        Settings.Secure.NIGHT_DISPLAY_COLOR_TEMPERATURE,
        Settings.Secure.NIGHT_DISPLAY_AUTO_MODE,
        Settings.Secure.DISPLAY_WHITE_BALANCE_ENABLED,
        Settings.Secure.SYNC_PARENT_SOUNDS,
        Settings.Secure.CAMERA_DOUBLE_TWIST_TO_FLIP_ENABLED,
        Settings.Secure.CAMERA_DOUBLE_TAP_POWER_GESTURE_DISABLED,
        Settings.Secure.SYSTEM_NAVIGATION_KEYS_ENABLED,
        Settings.Secure.QS_TILES,
        Settings.Secure.CONTROLS_ENABLED,
        Settings.Secure.POWER_MENU_LOCKED_SHOW_CONTENT,
        Settings.Secure.DOZE_ENABLED,
        Settings.Secure.DOZE_ALWAYS_ON,
        Settings.Secure.DOZE_ALWAYS_ON_AUTO_MODE,
        Settings.Secure.DOZE_ALWAYS_ON_AUTO_TIME,
        Settings.Secure.DOZE_PICK_UP_GESTURE,
        Settings.Secure.DOZE_DOUBLE_TAP_GESTURE,
        Settings.Secure.DOZE_TAP_SCREEN_GESTURE,
        Settings.Secure.DOZE_ON_CHARGE_NOW,
        Settings.Secure.NFC_PAYMENT_DEFAULT_COMPONENT,
        Settings.Secure.AUTOMATIC_STORAGE_MANAGER_DAYS_TO_RETAIN,
        Settings.Secure.FACE_UNLOCK_KEYGUARD_ENABLED,
        Settings.Secure.SHOW_MEDIA_WHEN_BYPASSING,
        Settings.Secure.FACE_UNLOCK_DISMISSES_KEYGUARD,
        Settings.Secure.FACE_UNLOCK_APP_ENABLED,
        Settings.Secure.FACE_UNLOCK_ALWAYS_REQUIRE_CONFIRMATION,
        Settings.Secure.VR_DISPLAY_MODE,
        Settings.Secure.NOTIFICATION_BADGING,
        Settings.Secure.NOTIFICATION_DISMISS_RTL,
        Settings.Secure.QS_AUTO_ADDED_TILES,
        Settings.Secure.SCREENSAVER_ENABLED,
        Settings.Secure.SCREENSAVER_COMPONENTS,
        Settings.Secure.SCREENSAVER_ACTIVATE_ON_DOCK,
        Settings.Secure.SCREENSAVER_ACTIVATE_ON_SLEEP,
        Settings.Secure.LOCKDOWN_IN_POWER_MENU,
        Settings.Secure.SHOW_FIRST_CRASH_DIALOG_DEV_OPTION,
        Settings.Secure.VOLUME_HUSH_GESTURE,
        Settings.Secure.MANUAL_RINGER_TOGGLE_COUNT,
        Settings.Secure.HUSH_GESTURE_USED,
        Settings.Secure.IN_CALL_NOTIFICATION_ENABLED,
        Settings.Secure.LOCK_SCREEN_ALLOW_PRIVATE_NOTIFICATIONS,
        Settings.Secure.LOCK_SCREEN_CUSTOM_CLOCK_FACE,
        Settings.Secure.LOCK_SCREEN_SHOW_NOTIFICATIONS,
        Settings.Secure.LOCK_SCREEN_SHOW_SILENT_NOTIFICATIONS,
        Settings.Secure.SHOW_NOTIFICATION_SNOOZE,
        Settings.Secure.NOTIFICATION_HISTORY_ENABLED,
        Settings.Secure.ZEN_DURATION,
        Settings.Secure.SHOW_ZEN_UPGRADE_NOTIFICATION,
        Settings.Secure.SHOW_ZEN_SETTINGS_SUGGESTION,
        Settings.Secure.ZEN_SETTINGS_UPDATED,
        Settings.Secure.ZEN_SETTINGS_SUGGESTION_VIEWED,
        Settings.Secure.CHARGING_SOUNDS_ENABLED,
        Settings.Secure.CHARGING_VIBRATION_ENABLED,
        Settings.Secure.ACCESSIBILITY_NON_INTERACTIVE_UI_TIMEOUT_MS,
        Settings.Secure.ACCESSIBILITY_INTERACTIVE_UI_TIMEOUT_MS,
        Settings.Secure.NOTIFICATION_NEW_INTERRUPTION_MODEL,
        Settings.Secure.TRUST_AGENTS_EXTEND_UNLOCK,
        Settings.Secure.UI_NIGHT_MODE,
        Settings.Secure.DARK_THEME_CUSTOM_START_TIME,
        Settings.Secure.DARK_THEME_CUSTOM_END_TIME,
        Settings.Secure.LOCK_SCREEN_WHEN_TRUST_LOST,
        Settings.Secure.SKIP_DIRECTION,
        Settings.Secure.THEME_CUSTOMIZATION_OVERLAY_PACKAGES,
        Settings.Secure.BACK_GESTURE_INSET_SCALE_LEFT,
        Settings.Secure.BACK_GESTURE_INSET_SCALE_RIGHT,
        Settings.Secure.NAVIGATION_MODE,
        Settings.Secure.SKIP_GESTURE_COUNT,
        Settings.Secure.SKIP_TOUCH_COUNT,
        Settings.Secure.SILENCE_ALARMS_GESTURE_COUNT,
        Settings.Secure.SILENCE_CALL_GESTURE_COUNT,
        Settings.Secure.SILENCE_TIMER_GESTURE_COUNT,
        Settings.Secure.SILENCE_ALARMS_TOUCH_COUNT,
        Settings.Secure.SILENCE_CALL_TOUCH_COUNT,
        Settings.Secure.SILENCE_TIMER_TOUCH_COUNT,
        Settings.Secure.DARK_MODE_DIALOG_SEEN,
        Settings.Secure.GLOBAL_ACTIONS_PANEL_ENABLED,
        Settings.Secure.AWARE_LOCK_ENABLED,
        Settings.Secure.AWARE_TAP_PAUSE_GESTURE_COUNT,
        Settings.Secure.AWARE_TAP_PAUSE_TOUCH_COUNT,
        Settings.Secure.PEOPLE_STRIP,
        Settings.Secure.MEDIA_CONTROLS_RESUME,
        Settings.Secure.MEDIA_CONTROLS_RESUME_BLOCKED,
        Settings.Secure.ACCESSIBILITY_MAGNIFICATION_MODE,
        Settings.Secure.ACCESSIBILITY_BUTTON_TARGETS,
        Settings.Secure.ADAPTIVE_CONNECTIVITY_ENABLED,
        Settings.Secure.ASSIST_HANDLES_LEARNING_TIME_ELAPSED_MILLIS,
        Settings.Secure.ASSIST_HANDLES_LEARNING_EVENT_COUNT,
        Settings.Secure.VOLUME_LINK_NOTIFICATION
    };
}
