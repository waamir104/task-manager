package dev.waamir.task_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.waamir.task_manager.models.Machine;

public interface IMachineJPARepository extends JpaRepository<Machine, Long> {

}
