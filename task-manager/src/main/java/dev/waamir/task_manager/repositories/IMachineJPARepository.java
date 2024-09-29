package dev.waamir.task_manager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.waamir.task_manager.models.Machine;


public interface IMachineJPARepository extends JpaRepository<Machine, Long> {

    public Optional<Machine> findByUuid(String uuid);
    public boolean existsByUuid(String uuid);
}
