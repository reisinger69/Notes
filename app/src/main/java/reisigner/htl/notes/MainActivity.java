package reisigner.htl.notes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import reisigner.htl.notes.functions.FileHandler;
import reisigner.htl.notes.functions.NoteAdapter;

public class MainActivity extends AppCompatActivity {

    int positionOfEditedNote;
    public static List<ToDo> notes = null;
    NoteAdapter adapter;
    ListView listView;

    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getData() != null) {
                        Intent i = result.getData();
                        Bundle b = i.getExtras();
                        notes.add((ToDo) b.get("note"));
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
                        notes.remove(positionOfEditedNote);
                        notes.add( (ToDo) b.get("note"));
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
        switch (id) {
            case idCreate:
                Intent t = new Intent(this, CreateNoteActivity.class);
                startActivityIntent.launch(t);
                break;
            case idSave:
                FileHandler.saveFile(notes, getApplicationContext());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.kontextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getItemId() == R.id.detailNote) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle(notes.get(info.position).getTitle()  + " - " + notes.get(info.position).getDate());
            alert.setMessage(notes.get(info.position).getDetails());
            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.show();
        } else if (item.getItemId() == R.id.deleteNote) {
            notes.remove(info.position);
            adapter.notifyDataSetChanged();
        } else if(item.getItemId() == R.id.editNote) {
            Intent t = new Intent(this, CreateNoteActivity.class);
            positionOfEditedNote = info.position;
            t.putExtra("note",notes.get(info.position));
            startActivityIntentForEdit.launch(t);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(notes == null) {
            notes = FileHandler.readFile(getApplicationContext());
            if (notes.size() == 0) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Keine gespeicherten Notizen").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        }

        listView = findViewById(R.id.list);
        adapter = new NoteAdapter(getApplicationContext(), notes);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

    }

}