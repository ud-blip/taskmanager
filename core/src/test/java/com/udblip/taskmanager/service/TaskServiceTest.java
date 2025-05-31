package com.udblip.taskmanager.service;

import com.udblip.taskmanager.model.Priority;
import com.udblip.taskmanager.model.Task;
import com.udblip.taskmanager.storage.FileStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private FileStorage mockStorage;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService(mockStorage);
        taskService.addTask(new Task("Сдать отчёт", Priority.HIGH, LocalDate.of(2025, 5, 30)));
        taskService.addTask(new Task("Купить молоко", Priority.LOW, LocalDate.of(2025, 5, 25)));
        taskService.addTask(new Task("Отчёт для директора", Priority.HIGH, LocalDate.of(2025, 5, 28)));
    }

    @Test
    void testSearchByKeyword() {
        List<Task> results = taskService.searchByKeyword("отчёт");
        assertEquals(2, results.size());
    }

    @Test
    void testFilterByDate() {
        List<Task> results = taskService.filterByDate(LocalDate.of(2025, 5, 26), LocalDate.of(2025, 5, 30));
        assertEquals(2, results.size());
    }

    @Test
    void testSaveCallsStorageSave() {
        taskService.saveToFile();
        verify(mockStorage).save(anyList());
    }

    @Test
    void testLoadCallsStorageLoad() {
        taskService.loadFromFile();
        verify(mockStorage).load(anyList());
    }


    @Test
    void testAddTaskIncreasesSize() {
        int initialSize = taskService.getAllTasks().size();
        taskService.addTask(new Task("Новая задача", Priority.MEDIUM,  LocalDate.now()));
        assertEquals(initialSize + 1, taskService.getAllTasks().size());
    }


    @Test
    void testSearchByKeywordEmptyStringReturnsAll() {
        List<Task> results = taskService.searchByKeyword("");
        assertEquals(taskService.getAllTasks().size(), results.size());
    }

    @Test
    void testFilterByDateNoMatchesReturnsEmpty() {
        LocalDate from = LocalDate.of(2100, 1, 1);
        LocalDate to = LocalDate.of(2100, 12, 31);
        List<Task> results = taskService.filterByDate(from, to);
        assertTrue(results.isEmpty());
    }

    @Test
    void testFilterByDateNoMatchesReturnsEmpty2() {
        LocalDate from = LocalDate.of(2025, 1, 1);
        LocalDate to = LocalDate.of(2025, 12, 31);
        List<Task> results = taskService.filterByDate(from, to);
        assertTrue(!(results.isEmpty()));
    }


}


