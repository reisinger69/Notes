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
import java.time.LocalDateTime;
import java.util.List;

import reisigner.htl.notes.ToDo;
import reisigner.htl.notes.R;

public class NoteAdapter extends ArrayAdapter<ToDo> {

    private LayoutInflater inflater;
    private int layoutID;

    public NoteAdapter(@NonNull Context context, @NonNull List<ToDo> objects) {
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

        ToDo n = getItem(position);

        TextView textView1 = currentItemView.findViewById(R.id.noteTitle);
        textView1.setText(n.getTitle());

        TextView textView2 = currentItemView.findViewById(R.id.noteDate);
        textView2.setText(n.getDate().toString());

        LocalDate date = n.getDate().toLocalDate();
        if (date.isBefore(LocalDate.now())) {
            currentItemView.setBackgroundColor(Color.argb(162, 227, 34, 89));
        } else {
            currentItemView.setBackgroundColor(Color.argb(255,255,255,255));
        }

        return currentItemView;
    }
}
