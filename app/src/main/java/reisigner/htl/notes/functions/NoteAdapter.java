package reisigner.htl.notes.functions;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import reisigner.htl.notes.Note;
import reisigner.htl.notes.R;

public class NoteAdapter extends ArrayAdapter<Note> {

    private LayoutInflater inflater;
    private int layoutID;

    public NoteAdapter(@NonNull Context context, @NonNull List<Note> objects) {
        super(context, 0, objects);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        System.out.println("get view aufgerufen");
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.listelement, parent, false);
        }

        Note n = getItem(position);

        TextView textView1 = currentItemView.findViewById(R.id.noteTitle);
        textView1.setText(n.getTitle());

        TextView textView2 = currentItemView.findViewById(R.id.noteDate);
        textView2.setText(n.getDate());

        String[] parts = n.getDate().split("\\.");

        LocalDate date = LocalDate.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
        if (date.isBefore(LocalDate.now())) {
            textView1.setBackgroundColor(Color.argb(162, 227, 34, 89));
            textView2.setBackgroundColor(Color.argb(162, 227, 34, 89));
        } else {
            textView1.setBackgroundColor(Color.argb(255,255,255,255));
            textView2.setBackgroundColor(Color.argb(255,255,255,255));
        }

        return currentItemView;
    }
}
