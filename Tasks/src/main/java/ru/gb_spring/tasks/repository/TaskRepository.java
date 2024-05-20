package ru.gb_spring.tasks.repository;

import org.springframework.data.repository.CrudRepository;
import ru.gb_spring.tasks.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {
    /**
     * получить список задач по их статусу
     */
    Optional<Task> findByStatus(String status);
}
