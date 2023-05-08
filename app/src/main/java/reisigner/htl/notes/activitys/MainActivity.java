package reisigner.htl.notes.activitys;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import reisigner.htl.notes.R;
import reisigner.htl.notes.data.ToDo;
import reisigner.htl.notes.functions.FileHandler;
import reisigner.htl.notes.functions.NoteAdapter;

public class MainActivity extends AppCompatActivity {

    ToDo ToDoToEdit;

    public static List<ToDo> ShownToDos = null;

    NoteAdapter adapter;
    ListView listView;
    LinearLayout linearLayout;

    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener preferencesChangeListener;

    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getData() != null) {
                        Intent i = result.getData();
                        Bundle b = i.getExtras();
                        ShownToDos.add((ToDo) b.get("note"));
                        adapter.notifyDataSetChanged();
                    }
                }
            });

    ActivityResultLauncher<Intent> startActivityIntentForEdit = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getData() != null) {
                        Intent i = result.getData();
                        Bundle b = i.getExtras();
                        ShownToDos.remove(ToDoToEdit);
                        ShownToDos.add( (ToDo) b.get("note"));
                        adapter.notifyDataSetChanged();
                    }
                }
            });

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
           return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        final int idCreate = R.id.createNewNote;
        final int idSave = R.id.saveNotes;
        final int idSettings = R.id.menu_preferences;
        switch (id) {
            case idCreate:
                Intent t = new Intent(this, CreateNoteActivity.class);
                startActivityIntent.launch(t);
                break;
            case idSave:
                FileHandler.saveFile(ShownToDos, getApplicationContext());
                break;
            case idSettings:
                Intent intent = new Intent(this,
                        MySettingsActivity.class);
                startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        System.out.println("OnCreateContextMenu ");;
        getMenuInflater().inflate(R.menu.kontextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.detailNote) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(ShownToDos.get(info.position).getTitle()  + " - " + ShownToDos.get(info.position).getDate());
            alert.setMessage(ShownToDos.get(info.position).getDetails());
            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
        } else if (item.getItemId() == R.id.deleteNote) {
            ShownToDos.remove(ShownToDos.remove(info.position));
            adapter.notifyDataSetChanged();
        } else if(item.getItemId() == R.id.editNote) {
            Intent t = new Intent(this, CreateNoteActivity.class);
            ToDoToEdit = ShownToDos.get(info.position);
            t.putExtra("note", ShownToDos.get(info.position));
            startActivityIntentForEdit.launch(t);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this );
        preferencesChangeListener = (sharedPrefs , key ) -> preferenceChanged(sharedPrefs, key);
        prefs.registerOnSharedPreferenceChangeListener( preferencesChangeListener );


        if(ShownToDos == null) {
            ShownToDos = FileHandler.readFile(getApplicationContext());
            if (ShownToDos.size() == 0) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Keine gespeicherten Notizen").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        }

        listView = findViewById(R.id.list);
        adapter = new NoteAdapter(getApplicationContext(), ShownToDos);
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