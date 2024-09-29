package dev.waamir.task_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.waamir.task_manager.models.Person;

public interface IPersonJPARepository extends JpaRepository<Person, Long> {

}
