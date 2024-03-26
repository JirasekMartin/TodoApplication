package org.example;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Serializable {
    private String description;
    private boolean isCompleted;
    private Date dueDate;

    public Task(String description) {
        this.description = description;
        this.isCompleted = false;
        this.dueDate = null;
    }

    public String getDescription() {
        return description;
    }
    // Přidáme metodu pro nastavení popisu
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dueDateString = (dueDate != null) ? dateFormat.format(dueDate) : "N/A";
        return "[" + (isCompleted ? "X" : " ") + "] " + description + " (Due: " + dueDateString + ")";
    }
}