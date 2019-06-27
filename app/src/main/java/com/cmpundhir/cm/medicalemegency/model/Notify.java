package com.cmpundhir.cm.medicalemegency.model;

public class Notify {
    String title;
    String description;

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


    public Notify(){}
    public Notify(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
