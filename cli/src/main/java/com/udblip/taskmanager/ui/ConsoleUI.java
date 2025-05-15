package com.udblip.taskmanager.ui;


import com.udblip.taskmanager.model.Priority;
import com.udblip.taskmanager.model.Task;
import com.udblip.taskmanager.service.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleUI {
    private final TaskService service = new TaskService();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        boolean running = true;

        while (running) {
            printMenu();
            switch (scanner.nextLine()) {
                case "1" -> addTask();
                case "2" -> service.printAll();
                case "3" -> filterByPriority();
                case "4" -> service.saveToFile();
                case "5" -> service.loadFromFile();
                case "0" -> running = false;
                default -> System.out.println("Неверный ввод. Попробуйте снова.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Менеджер задач ===");
        System.out.println("1. Добавить задачу");
        System.out.println("2. Показать все задачи");
        System.out.println("3. Фильтровать по приоритету");
        System.out.println("4. Сохранить задачи");
        System.out.println("5. Загрузить задачи");
        System.out.println("0. Выход");
        System.out.print("Ваш выбор: ");
    }

    private void addTask() {
        try {
            System.out.print("Введите название задачи: ");
            String title = scanner.nextLine();

            System.out.print("Приоритет (LOW, MEDIUM, HIGH): ");
            Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Дедлайн (ГГГГ-ММ-ДД): ");
            LocalDate deadline = LocalDate.parse(scanner.nextLine());

            service.addTask(new Task(title, priority, deadline));
        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println("Ошибка ввода. Убедитесь, что данные введены корректно.");
        }
    }

    private void filterByPriority() {
        System.out.print("Введите приоритет для фильтрации (LOW, MEDIUM, HIGH): ");
        try {
            Priority p = Priority.valueOf(scanner.nextLine().toUpperCase());
            service.printByPriority(p);
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный приоритет.");
        }
    }
}