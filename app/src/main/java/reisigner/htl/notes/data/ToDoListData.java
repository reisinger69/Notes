package reisigner.htl.notes.data;

import java.io.Serializable;

public class ToDoListData implements Serializable {
    private String name;
    private String todos;

    public ToDoListData(ToDoList toDoList){
        this.name = toDoList.getName();
        this.todos = toDoList.getTodos();
    }


}
