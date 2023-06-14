package com.example.notebook;

import java.time.LocalDate;

public class TaskClass {

    private final String event2;
    private final String description;
    private final String place;
    private LocalDate time;

    private String status;
    private Boolean isDone;
    private String comment;



    public String getEvent2() {
        return event2;
    }

    public String getDescription() {
        return description;
    }
    public LocalDate getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public String getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public Boolean isDone() {
        return isDone;
    }

    public void setDone() {
        this.isDone = true;
    }

    public void setNotDone() {
        this.isDone = false;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public TaskClass(String event2, String description, String place, LocalDate time, String status, Boolean isDone, String comment) {
        this.event2 = event2;
        this.description = description;
        this.time = time;
        this.place = place;
        this.status = status;
        this.isDone = isDone;
        this.comment = comment;
    }
}
