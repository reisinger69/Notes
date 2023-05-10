package reisigner.htl.notes.functions.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import reisigner.htl.notes.R;
import reisigner.htl.notes.data.ToDo;
import reisigner.htl.notes.data.ToDoList;

public class ToDoListsAdapter extends ArrayAdapter<String> {

    List<String> toDoLists;


    public ToDoListsAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, 0, objects);
        toDoLists = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.lists_listelement, parent, false);
        }

        String name = toDoLists.get(position);

            TextView textView = currentItemView.findViewById(R.id.listName);
            textView.setText(name);


        return currentItemView;
    }
}
