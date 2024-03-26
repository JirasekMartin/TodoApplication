package org.example;

import java.io.*;
import java.util.ArrayList;

public class TaskManager implements Serializable {
    private ArrayList<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
        loadTasksFromFile("tasks.ser"); // Načte úkoly při inicializaci
        addShutdownHook();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void editTask(int index, Task newTask) {
        tasks.set(index, newTask);
    }

    public void deleteTask(int index) {
        tasks.remove(index);
    }

    public int size() {
        return tasks.size();
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("TODO list is empty.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    public void saveTasksToFile(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(tasks);
            System.out.println("Tasks saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving tasks to file: " + e.getMessage());
        }
    }

    public void loadTasksFromFile(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            tasks = (ArrayList<Task>) inputStream.readObject();
            System.out.println("Tasks loaded from file successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading tasks from file: " + e.getMessage());
        }
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveTasksToFile("tasks.ser");
            System.out.println("Tasks saved before exit.");
        }));
    }
}