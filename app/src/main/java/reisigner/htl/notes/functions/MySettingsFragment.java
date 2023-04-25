package reisigner.htl.notes.functions;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import reisigner.htl.notes.R;

public class MySettingsFragment extends
        PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle
                                            savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences,
                rootKey);
    }
}
