package dev.waamir.task_manager.mapper;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.waamir.task_manager.dtos.MachineDto;
import dev.waamir.task_manager.dtos.PersonDto;
import dev.waamir.task_manager.dtos.SkillDto;
import dev.waamir.task_manager.dtos.TaskDto;
import dev.waamir.task_manager.dtos.UpdatePersonRequest;
import dev.waamir.task_manager.dtos.UpdateSkillRequest;
import dev.waamir.task_manager.models.Machine;
import dev.waamir.task_manager.models.Person;
import dev.waamir.task_manager.models.Skill;
import dev.waamir.task_manager.models.Task;
import dev.waamir.task_manager.repositories.IPersonJPARepository;
import dev.waamir.task_manager.repositories.ISkillJPARepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Slf4j
public class MapperService {

    @Autowired
    private IPersonJPARepository personRepository;

    @Autowired
    private ISkillJPARepository skillRepository;

    public PersonDto personToDto(Person entity) {
        PersonDto dto = PersonDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .age(entity.getAge())
            .skills(
                entity.getSkills()
                .stream()
                .map(this::skillToDto)
                .toList()
                )
            .build();
        return dto;
    } 

    public SkillDto skillToDto(Skill entity) {
        SkillDto dto = SkillDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .build();
        return dto;
    }

    public TaskDto taskToDto(Task entity) {
        TaskDto dto = TaskDto.builder()
            .id(entity.getId())
            .name(entity.getName())
            .deadLine(entity.getDeadLine())
            .status(entity.getStatus().name())
            .people(
                entity.getPeople()
                .stream()
                .map(this::personToDto)
                .toList()
            )
            .build();
            return dto;
    }

    public MachineDto machineToDto(Machine entity) {
        MachineDto dto = MachineDto.builder()
            .id(entity.getId())
            .uuid(entity.getUuid())
            .build();
        return dto;
    }

    public Person updateToPerson(UpdatePersonRequest request) {
        Person entity;
        if (request.getId() == null) {
            entity = Person.builder()
                .age(request.getAge())
                .name(request.getName())
                .skills(
                    request.getSkills()
                        .stream()
                        .map(this::updateToSkill)
                        .toList()
                )
                .build();
        } else {
            entity = Person.builder()
            .id(request.getId())
            .age(request.getAge())
            .name(request.getName())
            .skills(
                request.getSkills()
                    .stream()
                    .map(this::updateToSkill)
                    .toList()
                    )
                    .build();
                }
        entity = personRepository.save(entity);
        return entity;
    }
    
    public Skill updateToSkill(UpdateSkillRequest request) {
        Skill entity;
        if (request.getId() == null) {
            log.info("Ingreso id null");
            entity = Skill.builder()
                .name(request.getName())
                .build();
            entity = skillRepository.save(entity);
        } else {
            entity = Skill.builder()
                .id(request.getId())
                .name(request.getName())
                .build();
        }
        return entity;
    }
}
