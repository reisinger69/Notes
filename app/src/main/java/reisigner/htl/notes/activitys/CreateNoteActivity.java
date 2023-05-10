package reisigner.htl.notes.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import reisigner.htl.notes.R;
import reisigner.htl.notes.data.ToDo;
import reisigner.htl.notes.functions.adapters.NoteAdapter;

public class CreateNoteActivity extends AppCompatActivity implements View.OnClickListener {

    Button date;
    DatePickerDialog datePicker;
    List<ToDo> notes = MainActivity.ShownToDos;
    EditText title;
    EditText details;

    LocalDateTime setDateTime = LocalDateTime.now();

    NoteAdapter adapter;

    LinearLayout linearLayout;

    private SharedPreferences prefs;
    private SharedPreferences.OnSharedPreferenceChangeListener preferencesChangeListener;

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
        LocalDateTime dateToUse;
        if (b!= null) {
            ToDo note = (ToDo) b.get("note");
            title.setText(note.getTitle());
            details.setText(note.getDetails());
            date.setText(note.getDate().toString());
            dateToUse=note.getDate();
        } else {
            LocalDate today = LocalDate.now();
            date.setText(today.toString());
            dateToUse= LocalDateTime.now();
        }
        String dateButtonText = String.valueOf(date.getText());
        String[] parts = dateButtonText.split("\\.");


        Context context = this;

        datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setDateTime = LocalDateTime.of(year, monthOfYear, dayOfMonth, hourOfDay, minute, 0);
                        date.setText(setDateTime.toLocalDate().toString());
                    }
                }, dateToUse.getHour(), dateToUse.getMinute(),false).show();
            }
        }, dateToUse.getYear(), dateToUse.getMonth().getValue(), dateToUse.getDayOfMonth());

        prefs = PreferenceManager.getDefaultSharedPreferences(this );
        preferencesChangeListener = this::preferenceChanged;
        prefs.registerOnSharedPreferenceChangeListener( preferencesChangeListener );

        linearLayout=findViewById(R.id.linearLayout);

        changeBackground(prefs.getBoolean("darkMode", true));
    }


    private void preferenceChanged(SharedPreferences sharedPrefs , String key) {
        changeBackground(sharedPrefs.getBoolean(key, false));
    }

    private void changeBackground(boolean isDarkmode){
        if (isDarkmode){
            title.setBackgroundColor(Color.WHITE);
            details.setBackgroundColor(Color.WHITE);
            linearLayout.setBackgroundColor(Color.BLACK);
        } else {
            linearLayout.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel) {
            finish();
        } else if(v.getId() == R.id.ok) {
            if (!title.getText().toString().isEmpty()) {
                Intent i = getIntent();
                ToDo n = new ToDo(title.getText().toString(), details.getText().toString(), setDateTime, false);
                i.putExtra("note", (Serializable) n);
                setResult(1, i);
                finish();
            }
        } else if (v.getId() == R.id.date) {
            datePicker.show();
        }
    }
}