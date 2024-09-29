package dev.waamir.task_manager.resources;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.waamir.task_manager.dtos.MachineDto;
import dev.waamir.task_manager.mapper.MapperService;
import dev.waamir.task_manager.models.Machine;
import dev.waamir.task_manager.repositories.IMachineJPARepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/machine")
public class MachineResource {

    @Autowired
    private IMachineJPARepository machineRespository;

    @Autowired
    private MapperService mapper;

    @GetMapping("/{uuid}")
    public ResponseEntity<MachineDto> getMachineUUID(@PathVariable String uuid) {
        Machine machine = machineRespository.findByUuid(uuid).orElse(null);
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                mapper.machineToDto(machine)
            );
    }

    @GetMapping("/create")
    public ResponseEntity<MachineDto> createMachine() {
        UUID uuid = null;
        boolean keepValidating = true;
        while (keepValidating) {
            uuid = UUID.randomUUID();
            keepValidating = machineRespository.existsByUuid(uuid.toString());
        }
        Machine machine = Machine.builder()
            .uuid(uuid.toString())
            .build();
        machine = machineRespository.save(machine);
        return ResponseEntity.status(HttpStatus.OK)
            .body(
                mapper.machineToDto(machine)
            );
    }
    
    
}
