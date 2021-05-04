package com.m27lab.m27labnotemvvm.repository.models;


import java.io.Serializable;
import java.util.Date;

public class NoteModel implements Serializable {
    private String id;
    private String title;
    private String description;
    private Date crated_time;

    public NoteModel(String id, String title, String description, Date crated_time) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.crated_time = crated_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCrated_time() {
        return crated_time;
    }

    public void setCrated_time(Date crated_time) {
        this.crated_time = crated_time;
    }
}
