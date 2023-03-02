package reisigner.htl.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateNoteActivity extends AppCompatActivity implements View.OnClickListener {

    Button date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        Button cancel = findViewById(R.id.cancel);
        Button ok = findViewById(R.id.ok);
        date = findViewById(R.id.date);
        cancel.setOnClickListener(this);
        ok.setOnClickListener(this);
        date.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel) {
            finish();
        } else if(v.getId() == R.id.ok) {
            /*Intent i = getIntent();
        i.putExtra("test", "test");
        setResult(1, i);
        finish(); */
            //todo get Inputs and retunr them
        } else if (v.getId() == R.id.date) {
            //todo datumsspinner erstellen
        }
    }
}