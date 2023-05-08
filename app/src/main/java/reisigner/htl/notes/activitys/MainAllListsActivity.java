package reisigner.htl.notes.activitys;

import static reisigner.htl.notes.activitys.MainActivity.ShownToDos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import reisigner.htl.notes.R;
import reisigner.htl.notes.data.ToDoList;
import reisigner.htl.notes.functions.FileHandler;
import reisigner.htl.notes.functions.NoteAdapter;
import reisigner.htl.notes.functions.ToDoListsAdapter;

public class MainAllListsActivity extends AppCompatActivity {

    ToDoListsAdapter adapter;
    ListView listView;
    LinearLayout linearLayout;

    private List<ToDoList> toDoLists;

    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener preferencesChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_all_lists);


        prefs = PreferenceManager.getDefaultSharedPreferences(this );
        preferencesChangeListener = (sharedPrefs , key ) -> preferenceChanged(sharedPrefs, key);
        prefs.registerOnSharedPreferenceChangeListener( preferencesChangeListener );

        //todo lesen fixen
        if(toDoLists == null) {
            toDoLists = FileHandler.readFile(getApplicationContext());
            if (toDoLists.size() == 0) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Keine gespeicherten Notizen").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        }

        listView = findViewById(R.id.list);
        adapter = new ToDoListsAdapter(getApplicationContext(),  toDoLists);
        listView.setAdapter(adapter);

        linearLayout = findViewById(R.id.background);

        changeBackground(prefs.getBoolean("darkMode", true));
        registerForContextMenu(listView);
    }

    private void preferenceChanged(SharedPreferences sharedPrefs , String key) {
        changeBackground(sharedPrefs.getBoolean(key, true));
    }

    private void changeBackground(boolean isDarkmode){
        if (isDarkmode){
            linearLayout.setBackgroundColor(Color.BLACK);
        } else {
            linearLayout.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}