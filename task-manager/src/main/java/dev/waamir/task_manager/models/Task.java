package dev.waamir.task_manager.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

import dev.waamir.task_manager.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
@Entity(name = "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "deadLine", nullable = false)
    private LocalDateTime deadLine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToMany
    @JoinTable(
        name = "task_person", 
        joinColumns = @JoinColumn(name = "task_id"), 
        inverseJoinColumns = @JoinColumn(name = "person_id") 
    )
    private ArrayList<Person> people;
}
