package reisigner.htl.notes.functions;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import reisigner.htl.notes.data.ToDo;

public class FileHandler {

    private static Gson gson = new GsonBuilder().create();

    public static void saveFile(List<ToDo> notes, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("save.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(gson.toJson(notes));
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<ToDo> readFile(Context context)  {
        List<ToDo> notes = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = context.openFileInput("save.json");
            if ( inputStream != null ) {
                TypeToken<List<ToDo>> token = new TypeToken<List<ToDo>>(){};
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                if (!line.isEmpty()) {
                    System.out.println(line);
                    notes = gson.fromJson(line, token.getType());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notes;
    }
}
