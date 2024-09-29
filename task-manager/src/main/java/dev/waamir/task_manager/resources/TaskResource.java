package dev.waamir.task_manager.resources;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.waamir.task_manager.repositories.IMachineJPARepository;
import dev.waamir.task_manager.repositories.IPersonJPARepository;
import dev.waamir.task_manager.repositories.ISkillJPARepository;
import dev.waamir.task_manager.repositories.ITaskJPARepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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


    @GetMapping("/{id}")
    public ResponseEntity getTask(@RequestParam Long id) {
        return new ResponseEntity();
    }
    
    @GetMapping("/list/{uuid}")
    public ResponseEntity listTasks(@RequestParam String uuid) {
        return new ResponseEntity();
    }
    
}
