package com.client.sound;

/**
 * @author ArkCane
 * @social Discord: ArkCane
 * Website: www.arkcane.net
 * @since 14/03/2024
 */

import com.client.features.settings.Preferences;

public enum SoundType {
    MUSIC, SOUND, AREA_SOUND
    ;

    public double getVolume() {
        switch (this) {
            case MUSIC:
                return Preferences.getPreferences().musicVolume;
            case SOUND:
                return Preferences.getPreferences().soundVolume;
            case AREA_SOUND:
                return Preferences.getPreferences().areaSoundVolume;
            default:
                throw new IllegalStateException("Didn't handle " + toString());
        }
    }
}
