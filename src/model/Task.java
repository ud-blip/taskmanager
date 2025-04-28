package model;

import java.time.LocalDate;

public class Task {
    private final String title;
    private final Priority priority;
    private final LocalDate deadline;

    public Task(String title, Priority priority, LocalDate deadline) {
        this.title = title;
        this.priority = priority;
        this.deadline = deadline;
    }

    public Priority getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return String.format("Задача: %s | Приоритет: %s | Дедлайн: %s", title, priority, deadline);
    }
}
