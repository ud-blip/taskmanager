package com.udblip.taskmanager.storage;

import com.udblip.taskmanager.model.Task;

import java.io.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileStorage {
    private final String FILE_NAME = "tasks.txt";

    private static final Logger logger = LoggerFactory.getLogger(FileStorage.class);


    public void save(List<Task> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.println(task.toDataString());
            }
            System.out.println("Задачи сохранены в файл.");
            logger.info("Задачи успешно сохранены в файл: {}", FILE_NAME);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
            logger.error("Ошибка при сохранении задач в файл: {}", FILE_NAME, e);
        }
    }

    public void load(List<Task> tasks) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            logger.warn("Файл {} не существует. Загрузка пропущена.", FILE_NAME);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            tasks.clear();
            while ((line = reader.readLine()) != null) {
                tasks.add(Task.fromDataString(line));
            }
            System.out.println("Задачи загружены из файла.");
            logger.info("Задачи успешно загружены из файла: {}", FILE_NAME);
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
            logger.error("Ошибка при загрузке задач из файла: {}", FILE_NAME, e);
        }
    }
}