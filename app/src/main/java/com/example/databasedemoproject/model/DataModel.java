package com.example.databasedemoproject.model;

/**
 * Created by sarabjjeet on 9/8/17.
 */

public class DataModel {

    String title;
    String description;
    String date;
    String time;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DataModel(String date, String time, String title, String description,String type) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time=time;
        this.type=type;
    }

    public DataModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
