package reisigner.htl.notes.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ToDoList implements Serializable, Parcelable{
    private String name;
    private String todos;

    TypeToken<List<ToDo>> token = new TypeToken<List<ToDo>>(){};
    private static Gson gson = new GsonBuilder().create();


    public ToDoList(String name, ArrayList<ToDo> toDosList){
        this.name = name;
        this.todos = gson.toJson(toDosList, token.getType());
        System.out.println(todos);
    }

    protected ToDoList(Parcel in) {
        name = in.readString();
        todos = in.readString();
    }

    public void listChanged(List<ToDo> toDosList){
        this.todos = gson.toJson(toDosList, token.getType());
    }

    public static final Creator<ToDoList> CREATOR = new Creator<ToDoList>() {
        @Override
        public ToDoList createFromParcel(Parcel in) {
            return new ToDoList(in);
        }

        @Override
        public ToDoList[] newArray(int size) {
            return new ToDoList[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTodos() {
        return todos;
    }

    public List<ToDo> getToDosList() {
        System.out.println(todos);
        return gson.fromJson(this.todos, token.getType());
    }

    @Override
    public String toString() {
        return "ToDoList{" +
                "name='" + name + '\'' +
                ", toDos=" + todos +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(todos);
    }
}
