package dev.waamir.task_manager.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {
    
    private String name;
    private LocalDateTime deadLine;
    private String status;
    private List<CreatePersonRequest> people;
    private String machineUUID;
}
