package reisigner.htl.notes.functions;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import reisigner.htl.notes.ToDo;

public class FileHandler {
    public static void saveFile(List<ToDo> notes, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("save.csv", Context.MODE_PRIVATE));
            for (ToDo n:
                 notes) {
                outputStreamWriter.write(n.serialize());
                outputStreamWriter.write("\n");
            }
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<ToDo> readFile(Context context)  {
        List<ToDo> notes = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = context.openFileInput("save.csv");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    notes.add(ToDo.deserialize(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notes;
    }
}
