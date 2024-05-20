package ru.gb_spring.tasks.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Сущность "Задача"
 * @id - идентификатор
 * @description - описание
 * @status - статус
 * @createdDate - дата создания
 */
@Data
@Entity
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String status;
    private LocalDateTime createdate;

    public Task() {
    }

}
