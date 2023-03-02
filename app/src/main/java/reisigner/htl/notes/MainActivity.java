package reisigner.htl.notes;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getData() != null) {
                        Intent i = result.getData();
                        Bundle b = i.getExtras();
                        System.out.println(b.getString("test"));
                    }
                }
            });

    private List<Note> notes = new ArrayList<>();
    NoteAdapter adapter;
    ListView listView;

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
                //todo speichern
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notes.add(new Note("Hallo", "test", "1"));
        notes.add(new Note("Hallo1", "test2", "1"));
        listView = findViewById(R.id.list);
        adapter = new NoteAdapter(getApplicationContext(), notes);
        listView.setAdapter(adapter);
    }
}