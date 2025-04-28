package storage;

import model.Task;

import java.io.*;
import java.util.List;

public class FileStorage {
    private final String FILE_NAME = "tasks.txt";

    public void save(List<Task> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.println(task.toDataString());
            }
            System.out.println("Задачи сохранены в файл.");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    public void load(List<Task> tasks) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            tasks.clear();
            while ((line = reader.readLine()) != null) {
                tasks.add(Task.fromDataString(line));
            }
            System.out.println("Задачи загружены из файла.");
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке: " + e.getMessage());
        }
    }
}