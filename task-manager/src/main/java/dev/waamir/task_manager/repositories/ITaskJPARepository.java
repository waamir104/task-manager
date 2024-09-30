package dev.waamir.task_manager.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.waamir.task_manager.models.Machine;
import dev.waamir.task_manager.models.Task;

public interface ITaskJPARepository extends JpaRepository<Task, Long> {

    public Page<Task> findAllByMachine(Machine machine, Pageable pagination);
}
