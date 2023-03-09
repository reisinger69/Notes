package reisigner.htl.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.time.LocalDate;
import java.util.List;

import reisigner.htl.notes.functions.NoteAdapter;

public class CreateNoteActivity extends AppCompatActivity implements View.OnClickListener {

    Button date;
    DatePickerDialog datePicker;
    List<Note> notes = MainActivity.notes;
    EditText title;
    EditText details;

    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        title = findViewById(R.id.nodeTitle);
        details = findViewById(R.id.noteDetails);
        date = findViewById(R.id.date);

        ListView lv = findViewById(R.id.list);
        adapter = new NoteAdapter(getApplicationContext(), notes);
        lv.setAdapter(adapter);

        Button cancel = findViewById(R.id.cancel);
        Button ok = findViewById(R.id.ok);
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);
        date.setOnClickListener(this);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b!= null) {
            Note note = (Note) b.get("note");
            title.setText(note.getTitle());
            details.setText(note.getDetails());
            date.setText(note.getDate());
        } else {
            LocalDate today = LocalDate.now();
            date.setText(today.getDayOfMonth() + "." + today.getMonthValue() + "." + today.getYear());
        }
        String dateButtonText = String.valueOf(date.getText());
        String[] parts = dateButtonText.split("\\.");

        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(dayOfMonth + "." + month + "." + year);
            }
        }, Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel) {
            finish();
        } else if(v.getId() == R.id.ok) {
            if (!title.getText().toString().isEmpty()) {
                Intent i = getIntent();
                Note n = new Note(title.getText().toString(), details.getText().toString(), date.getText().toString());
                i.putExtra("note", n);
                setResult(1, i);
                finish();
            }
        } else if (v.getId() == R.id.date) {
            datePicker.show();
        }
    }
}