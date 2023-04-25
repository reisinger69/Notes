package reisigner.htl.notes.functions;

import androidx.preference.PreferenceFragmentCompat;

public class MySettingsFragment extends
        PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle
                                            savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences,
                rootKey);
    }
}
