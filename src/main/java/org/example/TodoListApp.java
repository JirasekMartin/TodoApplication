package org.example;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class TodoListApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();

        boolean exit = false;

        while (!exit) {
            System.out.println("\nTODO List Menu:");
            System.out.println("1. Add Task");
            System.out.println("2. Edit Task");
            System.out.println("3. Delete Task");
            System.out.println("4. Display Tasks");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter task description: ");
                        String description = scanner.nextLine();
                        Task newTask = new Task(description);

                        System.out.print("Set due date (yyyy-MM-dd, press Enter to skip): ");
                        String dueDateString = scanner.nextLine();
                        if (!dueDateString.isEmpty()) {
                            try {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                LocalDate dueDate = LocalDate.parse(dueDateString, formatter);
                                ZonedDateTime zonedDateTime = dueDate.atStartOfDay(ZoneId.systemDefault());
                                newTask.setDueDate(Date.from(zonedDateTime.toInstant()));
                            } catch (Exception e) {
                                System.out.println("Invalid date format. Task added without due date.");
                            }
                        }

                        taskManager.addTask(newTask);
                        break;

                    case 2:
                        System.out.print("Enter the index of the task to edit: ");
                        try {
                            int editIndex = Integer.parseInt(scanner.nextLine());
                            if (editIndex >= 1 && editIndex <= taskManager.size()) {
                                Task taskToEdit = taskManager.getTask(editIndex - 1);

                                System.out.println("Current task description: " + taskToEdit.getDescription());
                                System.out.print("Enter the new task description: ");
                                String newDescription = scanner.nextLine();
                                taskToEdit.setDescription(newDescription);

                                System.out.println("Task edited successfully.");
                            } else {
                                System.out.println("Invalid index. No task edited.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid index.");
                        }
                        break;

                    case 3:
                        System.out.print("Enter the index of the task to delete: ");
                        try {
                            int deleteIndex = Integer.parseInt(scanner.nextLine());
                            if (deleteIndex >= 1 && deleteIndex <= taskManager.size()) {
                                taskManager.deleteTask(deleteIndex - 1);
                                System.out.println("Task deleted successfully.");
                            } else {
                                System.out.println("Invalid index. No task deleted.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid index.");
                        }
                        break;

                    case 4:
                        taskManager.displayTasks();
                        break;

                    case 5:
                        exit = true;  // Nastavíme exit na true místo volání System.exit(0)
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
            }
        }

        // Ukončení programu
        taskManager.saveTasksToFile("tasks.ser");
        System.out.println("Exiting the TODO List application. Goodbye!");
    }
}