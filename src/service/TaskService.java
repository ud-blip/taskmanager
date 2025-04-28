package service;

import model.Priority;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskService {
    private final List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Задача добавлена.");
    }

    public void printAll() {
        if (tasks.isEmpty()) {
            System.out.println("Список задач пуст.");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    public void printByPriority(Priority priority) {
        boolean found = false;
        for (Task task : tasks) {
            if (task.getPriority() == priority) {
                System.out.println(task);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Нет задач с таким приоритетом.");
        }
    }
}
