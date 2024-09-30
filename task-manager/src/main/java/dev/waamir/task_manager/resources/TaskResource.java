package dev.waamir.task_manager.resources;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.waamir.task_manager.dtos.CreatePersonRequest;
import dev.waamir.task_manager.dtos.CreateSkillRequest;
import dev.waamir.task_manager.dtos.CreateTaskRequest;
import dev.waamir.task_manager.dtos.TaskDto;
import dev.waamir.task_manager.dtos.UpdateTaskRequest;
import dev.waamir.task_manager.enums.TaskStatus;
import dev.waamir.task_manager.mapper.MapperService;
import dev.waamir.task_manager.models.Machine;
import dev.waamir.task_manager.models.Person;
import dev.waamir.task_manager.models.Skill;
import dev.waamir.task_manager.models.Task;
import dev.waamir.task_manager.repositories.IMachineJPARepository;
import dev.waamir.task_manager.repositories.IPersonJPARepository;
import dev.waamir.task_manager.repositories.ISkillJPARepository;
import dev.waamir.task_manager.repositories.ITaskJPARepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/v1/task")
public class TaskResource {

    @Autowired
    private IPersonJPARepository personRepository;

    @Autowired
    private ISkillJPARepository skillRepository;

    @Autowired
    private ITaskJPARepository taskRepository;

    @Autowired
    private IMachineJPARepository machineRepository;

    @Autowired
    private MapperService mapper;


    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                mapper.taskToDto(task.get())
            );
    }
    
    @GetMapping("/list/{uuid}")
    public ResponseEntity<Page<TaskDto>> listTasks(
    @PathVariable String uuid,
    @PageableDefault Pageable pagination) {
        Machine machine = machineRepository.findByUuid(uuid).orElse(null);
        Page<Task> entities = taskRepository.findAllByMachine(machine, pagination);
        Page<TaskDto> dtos = entities.map(mapper::taskToDto);
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                dtos
            );
    }
    
    @PostMapping("/")
    public ResponseEntity<TaskDto> createTaskEntity(@RequestBody CreateTaskRequest request) {
        ArrayList<Person> createdPeople = new ArrayList<Person>();
        ArrayList<Skill> createdSkills;
        Machine machine = machineRepository.findByUuid(request.getMachineUUID()).orElse(null);
        Task task;
        for (CreatePersonRequest personRequest : request.getPeople()) {
            Person createdPerson;
            createdSkills = new ArrayList<Skill>();
            for (CreateSkillRequest skillRequest : personRequest.getSkills()) {

                Skill createdSkill = Skill.builder()
                    .name(skillRequest.getName())
                    .build();
                createdSkill = skillRepository.save(createdSkill);
                createdSkills.add(createdSkill);
            }
            createdPerson = Person.builder()
                .name(personRequest.getName())
                .age(personRequest.getAge())
                .skills(createdSkills)
                .build();
            createdPerson = personRepository.save(createdPerson);
            createdPeople.add(createdPerson);
        }
        task = Task.builder()
            .name(request.getName())
            .deadLine(request.getDeadLine())
            .machine(machine)
            .people(createdPeople)
            .status(TaskStatus.PENDING)
            .build();
        task = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                mapper.taskToDto(task)
            );
    }

    @PutMapping("/")
    public ResponseEntity<TaskDto> updateTask(@RequestBody UpdateTaskRequest request) {
        Task taskToUpdate = taskRepository.findById(request.getId()).orElse(null);
        taskToUpdate.setName(request.getName());
        taskToUpdate.setDeadLine(request.getDeadLine());
        taskToUpdate.setStatus(TaskStatus.valueOf(request.getStatus()));
        taskToUpdate.setPeople(
            new ArrayList<Person>(
                request.getPeople()
                    .stream()
                    .map(mapper::updateToPerson)
                    .toList()
            )
        );
        taskToUpdate = taskRepository.save(taskToUpdate);
        return ResponseEntity.status(HttpStatus.OK)
            .body( 
                mapper.taskToDto(taskToUpdate)
            );
    }
    
}
