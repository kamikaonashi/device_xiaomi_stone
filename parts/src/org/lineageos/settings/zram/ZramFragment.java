/*
 * Copyright (C) 2015-2016 The CyanogenMod Project
 *               2017,2021-2022 The LineageOS Project
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

package org.lineageos.settings.zram;

import android.os.Bundle;
import android.os.SystemProperties;
import androidx.preference.PreferenceFragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;

import org.lineageos.settings.R;

public class ZramFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private static final String PREF_ZRAM_SIZE = "zram_size";
    private static final String PREF_ZRAM_COMPRESSION = "zram_compression";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.zram_settings);

        ListPreference zramSizePref = (ListPreference) findPreference(PREF_ZRAM_SIZE);
        if (zramSizePref != null) {
            String currentSize = SystemProperties.get("persist.sys.zram.size", "2");
            zramSizePref.setValue(currentSize);
            zramSizePref.setSummary(zramSizePref.getEntry());
            zramSizePref.setOnPreferenceChangeListener(this);
        }

        ListPreference zramCompressionPref = (ListPreference) findPreference(PREF_ZRAM_COMPRESSION);
        if (zramCompressionPref != null) {
            String currentCompression = SystemProperties.get("persist.sys.zram.compression", "lz4");
            zramCompressionPref.setValue(currentCompression);
            zramCompressionPref.setSummary(zramCompressionPref.getEntry());
            zramCompressionPref.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (PREF_ZRAM_SIZE.equals(preference.getKey())) {
            String newSize = (String) newValue;
            SystemProperties.set("persist.sys.zram.size", newSize);
            preference.setSummary(((ListPreference) preference).getEntries()[
                    ((ListPreference) preference).findIndexOfValue(newSize)]);
            return true;
        } else if (PREF_ZRAM_COMPRESSION.equals(preference.getKey())) {
            String newCompression = (String) newValue;
            SystemProperties.set("persist.sys.zram.compression", newCompression);
            preference.setSummary(((ListPreference) preference).getEntries()[
                    ((ListPreference) preference).findIndexOfValue(newCompression)]);
            return true;
        }
        return false;
    }
}
