package com.udblip.taskmanager.service;


import com.udblip.taskmanager.model.Priority;
import com.udblip.taskmanager.model.Task;
import com.udblip.taskmanager.storage.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {
    private final List<Task> tasks = new ArrayList<>();

    private final FileStorage storage;

    public TaskService(FileStorage storage) {
        this.storage = storage;
    }

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);


    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Задача добавлена.");
        logger.info("Добавлена задача: {}", task);
    }

    public void printAll() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
            logger.info("Попытка вывода: список задач пуст.");
        } else {
            logger.info("Вывод всех задач. Кол-во: {}", tasks.size());
            tasks.forEach(System.out::println);
        }
    }

    public void printByPriority(Priority priority) {
        logger.info("Фильтрация задач по приоритету: {}", priority);
        boolean found = false;
        for (Task task : tasks) {
            if (task.getPriority() == priority) {
                System.out.println(task);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Нет задач с таким приоритетом.");
            logger.warn("Нет задач с приоритетом {}", priority);
        }
    }

    public void saveToFile() {
        storage.save(tasks);
    }

    public void loadFromFile() {
        storage.load(tasks);
    }

    public List<Task> searchByKeyword(String keyword) {
        return tasks.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Task> filterByDate(LocalDate from, LocalDate to) {
        return tasks.stream()
                .filter(t -> !t.getDeadline().isBefore(from)
                        && !t.getDeadline().isAfter(to))
                .collect(Collectors.toList());
    }

    public List<Task> getAllTasks() {
        return tasks;
    }
}

