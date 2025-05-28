package com.udblip.taskmanager.storage;

import com.udblip.taskmanager.model.Priority;
import com.udblip.taskmanager.model.Task;
import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageTest {

    private Path tempFile;
    private FileStorage fileStorage;

    @BeforeEach
    void setup() throws IOException {
        tempFile = Files.createTempFile("tasks_test", ".txt");
        fileStorage = new FileStorage(tempFile.toString());
    }

    @AfterEach
    void cleanup() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testSaveAndLoad() {
        List<Task> tasksToSave = new ArrayList<>();
        tasksToSave.add(new Task("Test Task 1", Priority.HIGH, LocalDate.of(2025, 5, 22)));
        tasksToSave.add(new Task("Test Task 2", Priority.MEDIUM, LocalDate.of(2025, 5, 23)));

        fileStorage.save(tasksToSave);

        List<Task> loadedTasks = new ArrayList<>();
        fileStorage.load(loadedTasks);

        // Проверяем, что список загруженных задач совпадает с сохранённым
        assertEquals(tasksToSave.size(), loadedTasks.size());
        for (int i = 0; i < tasksToSave.size(); i++) {
            assertEquals(tasksToSave.get(i).getTitle(), loadedTasks.get(i).getTitle());
            assertEquals(tasksToSave.get(i).getDeadline(), loadedTasks.get(i).getDeadline());
            assertEquals(tasksToSave.get(i).getPriority(), loadedTasks.get(i).getPriority());
        }
    }

    @Test
    void testLoadWhenFileDoesNotExist() throws IOException {
        Files.deleteIfExists(tempFile);

        List<Task> tasks = new ArrayList<>();
        fileStorage.load(tasks);

        assertTrue(tasks.isEmpty());
    }
}
