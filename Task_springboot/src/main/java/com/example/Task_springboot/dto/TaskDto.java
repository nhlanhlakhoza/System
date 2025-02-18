package com.example.Task_springboot.dto;

import com.example.Task_springboot.enums.TaskStatus;
import lombok.Data;

@Data
public class TaskDto {

    private Long id;
    private  String description;
    private String dueDate;
    private String priority;
    private TaskStatus taskStatus;
    private String title;
    private  Long employeeId;
    private String employeeName;
}
