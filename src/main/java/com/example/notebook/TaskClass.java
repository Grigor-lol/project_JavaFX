package com.example.notebook;

import java.time.LocalDate;

public class TaskClass {

    private final String task;
    private final String description;
    private final String executor;
    private LocalDate deadline;

    private String status;
    private Boolean isDone;
    private Boolean inDevelop;
    private String comment;



    public String getTask() {
        return task;
    }

    public String getDescription() {
        return description;
    }
    public String getExecutor() {
        return executor;
    }

    public LocalDate getDeadline() {
        return deadline;
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
    public Boolean inDevelop() {
        return inDevelop;
    }


    public void setDone() {
        this.isDone = true;
    }

    public void setNotInDevelope() {
        this.inDevelop = false;
    }

    public void setNotDone() {
        this.isDone = false;
    }

    public void addWeek() {
       this.deadline = this.deadline.plusWeeks(1);
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public TaskClass(String task, String description, String executor, LocalDate deadline, String status, Boolean isDone, String comment, Boolean inDevelop) {
        this.task = task;
        this.description = description;
        this.executor = executor;
        this.deadline = deadline;
        this.status = status;
        this.isDone = isDone;
        this.comment = comment;
        this.inDevelop = inDevelop;
    }
}
