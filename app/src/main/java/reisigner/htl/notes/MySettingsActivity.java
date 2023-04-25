package reisigner.htl.notes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import reisigner.htl.notes.functions.MySettingsFragment;

public class MySettingsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new MySettingsFragment())
                .commit();
    }
}
