package reisigner.htl.notes.functions;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import reisigner.htl.notes.data.ToDoList;

public class ToDoListsAdapter extends ArrayAdapter<ToDoList> {


    public ToDoListsAdapter(@NonNull Context context, @NonNull List<ToDoList> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
