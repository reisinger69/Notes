package reisigner.htl.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {

    private LayoutInflater inflater;
    private int layoutID;

    public NoteAdapter(@NonNull Context context, @NonNull List<Note> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.listelement, parent, false);
        }

        Note n = getItem(position);

        TextView textView1 = currentItemView.findViewById(R.id.noteTitle);
        textView1.setText(n.getTitle());

        TextView textView2 = currentItemView.findViewById(R.id.noteDate);
        textView2.setText(n.getDate());

        return currentItemView;
    }
}
