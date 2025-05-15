package com.udblip.taskmanager.model;

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

    public String toDataString() {
        return String.format("%s;%s;%s", title, priority, deadline);
    }

    public static Task fromDataString(String data) {
        String[] parts = data.split(";");
        return new Task(parts[0], Priority.valueOf(parts[1]), LocalDate.parse(parts[2]));
    }

    @Override
    public String toString() {
        return String.format("Задача: %s | Приоритет: %s | Дедлайн: %s", title, priority, deadline);
    }
}
