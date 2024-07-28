package com.hamitmizrak.todolistandroid;

// TodoList
public class TaskModel {

    // Variable
    private String id;
    private String name;
    private boolean completed;

    // Constructor(parametresiz)
    public TaskModel() {
    }

    // Constructor(parametreli)
    public TaskModel(String id, String name, boolean completed) {
        this.id = id;
        this.name = name;
        this.completed = completed;
    }

    // Method
    // Getter And Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
} //end TaskModel
