package reisigner.htl.notes.functions;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import reisigner.htl.notes.data.ToDo;
import reisigner.htl.notes.data.ToDoList;
import reisigner.htl.notes.data.ToDoListData;

public class FileHandler {

    private static Gson gson = new GsonBuilder().create();

    public void addNewList(String name, Context context) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(name+".json", Context.MODE_PRIVATE));
        OutputStreamWriter outputStreamWriter2 = new OutputStreamWriter(context.openFileOutput("lists.txt", Context.MODE_PRIVATE | Context.MODE_APPEND));
        outputStreamWriter2.write(name+"\n");
        outputStreamWriter.write("");
        outputStreamWriter.close();
        outputStreamWriter2.close();
    }

    public List<String> readAllLists(Context context) throws FileNotFoundException {
        List<String> names = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = context.openFileInput("lists.txt");
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    names.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return names;
    }

    public static void saveFile(List<ToDo> notes, Context context, String name) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(name+".json", Context.MODE_PRIVATE));
            String stringToWrite = gson.toJson(notes);
            outputStreamWriter.write(stringToWrite);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<ToDo> readFile(Context context, String name)  {
        List<ToDo> notes = new ArrayList<>();
        InputStream inputStream = null;
        try {
            inputStream = context.openFileInput(name+".json");
            if ( inputStream != null ) {
                TypeToken<List<ToDo>> token = new TypeToken<List<ToDo>>(){};
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                if (line!=null) {
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
