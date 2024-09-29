package dev.waamir.task_manager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.waamir.task_manager.models.Skill;

public interface ISkillJPARepository extends JpaRepository<Skill, Long> {

}
