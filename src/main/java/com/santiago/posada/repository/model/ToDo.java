package com.santiago.posada.repository.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ToDo {

    @Id
    public String id;
    public String task;

    public ToDo(){

    }

    public ToDo(String task){
        this.task = task;
    }

    public ToDo(String id, String task){
        this.id = id;
        this.task = task;
    }

    public static ToDo from(String task, String id){
        return new ToDo(id, task);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
