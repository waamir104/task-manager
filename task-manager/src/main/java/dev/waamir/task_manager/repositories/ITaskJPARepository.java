package dev.waamir.task_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.waamir.task_manager.models.Task;

public interface ITaskJPARepository extends JpaRepository<Task, Long> {

}
