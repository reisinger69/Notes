package reisigner.htl.notes.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import reisigner.htl.notes.R;
import reisigner.htl.notes.data.ToDo;
import reisigner.htl.notes.data.ToDoList;
import reisigner.htl.notes.functions.FileHandler;
import reisigner.htl.notes.functions.adapters.ToDoListsAdapter;

public class MainAllListsActivity extends AppCompatActivity {

    ToDoListsAdapter adapter;
    ListView listView;
    LinearLayout linearLayout;

    private List<String> toDoLists;

    private FileHandler fileHandler;

    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener preferencesChangeListener;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final View vDialog = getLayoutInflater () . inflate (R.layout.create_list_dialog, null );
            new AlertDialog.Builder(this)
                    .setTitle("Neue Liste")
                    .setView(vDialog)
                    .setPositiveButton("ok", (dialog, which) -> {
                        try {
                            handleDialog(vDialog);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    })
                    .setNegativeButton("cancel", null)
                    .show();
        return super.onOptionsItemSelected(item);
    }

    private void handleDialog( final View vDialog) throws IOException {
        EditText listName = vDialog.findViewById(R.id.name);
        fileHandler.addNewList(listName.getText().toString(), this);
        toDoLists.add(listName.getText().toString());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.createlistmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_all_lists);

        fileHandler = new FileHandler();

        prefs = PreferenceManager.getDefaultSharedPreferences(this );
        preferencesChangeListener = (sharedPrefs , key ) -> preferenceChanged(sharedPrefs, key);
        prefs.registerOnSharedPreferenceChangeListener(preferencesChangeListener);

        if(toDoLists == null) {
            try {
                toDoLists = fileHandler.readAllLists(this);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (toDoLists.size() == 0) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Keine gespeicherten ToDo-Listen").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        }

        listView = findViewById(R.id.list);
        adapter = new ToDoListsAdapter(getApplicationContext(),  toDoLists);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(position);
            }
        });

        linearLayout = findViewById(R.id.background);

        changeBackground(prefs.getBoolean("darkMode", true));
        registerForContextMenu(listView);
    }

    public void startActivity(int position) {
        Intent intent = new Intent(this,
                MainActivity.class);
        intent.putExtra("name", toDoLists.get(position));
        startActivity(intent);
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