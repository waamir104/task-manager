package dev.waamir.task_manager.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonRequest {

    private Long id;
    private String name;
    private Integer age;
    private List<UpdateSkillRequest> skills;
}
