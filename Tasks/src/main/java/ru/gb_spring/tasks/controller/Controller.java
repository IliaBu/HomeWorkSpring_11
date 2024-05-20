package ru.gb_spring.tasks.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb_spring.tasks.model.Task;
import ru.gb_spring.tasks.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

/**
 * Класс контролер обработки запросов к WEB серверу
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class Controller {

    /**
     * Репозиторий хранения записей информации о заметках
     */
    public final TaskRepository repository;

    /**
     * Счётчик количества запросов для метрики
     */
    private final Counter requestCounter = Metrics.counter("request_count_total");

    /**
     * Обработка запроса на добавление новой записи в заметку
     * @param task заметка
     * @return добавленную запись в базу данных
     */
    @PostMapping("/add")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        repository.save(task);
        requestCounter.increment();
        return new ResponseEntity<>("Заметка добавлена", HttpStatus.CREATED);
    }

    /**
     * Обработка запроса вывода всех записей из базы данных
     * @return список заметок
     */
    @GetMapping
    public ResponseEntity<?> findAllTasks() {
        List<Task> allTasks;
        try {
            allTasks = (List<Task>) repository.findAll();
            requestCounter.increment();
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка: " + e.getMessage(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allTasks, HttpStatus.OK);
    }


    /**
     * Получить список задач по их статусу
     * @param status - статус задач для отбора
     * @return список полученных задач
     */
    @GetMapping("/status")
    public ResponseEntity<?> findByStatus(@RequestBody String status) {
        Optional<Task> tS = repository.findByStatus(status);
        requestCounter.increment();
        if (tS != null) {
            return new ResponseEntity<>(tS, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Список задач по статусу не получен", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Найти заметку по id
     * @param id идентификатор заметки
     * @return заметка
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Task task = repository.findById(id).orElse(null);
        requestCounter.increment();
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.FOUND);
        } else {
            return new ResponseEntity<>("Заметка не найдена", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Изменить заметку по id
     * @param id   идентификатор заметки
     * @param task заметка
     * @return обновлённая заметка
     */
    @PutMapping("/upd/{id}")
    public ResponseEntity<?> updateById(@PathVariable Long id, @RequestBody Task task) {
        Task old = repository.findById(id).orElse(null);
        requestCounter.increment();
        if (old != null) {
            task.setCreatedate(old.getCreatedate());
            task.setId(old.getId());
            return new ResponseEntity<>(repository.save(task), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Заметка не изменена", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Удалить заметку по id
     * @param id идентификатор заметки
     * @return пустой ответ
     */
    @DeleteMapping("/del/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Task old = repository.findById(id).orElse(null);
        requestCounter.increment();
        if (old != null) {
            repository.deleteById(id);
            return findAllTasks();
        } else {
            return new ResponseEntity<>("Заметка не удалена", HttpStatus.NOT_FOUND);
        }
    }
}
