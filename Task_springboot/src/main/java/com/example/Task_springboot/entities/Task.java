package com.example.Task_springboot.entities;


import com.example.Task_springboot.dto.TaskDto;
import com.example.Task_springboot.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Data
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private  String description;
    private String dueDate;
    private String priority;
    private TaskStatus taskStatus;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private  User user;

    public TaskDto getTaskDto(){
        TaskDto taskDto= new TaskDto();
        taskDto.setId(id);
        taskDto.setTitle(title);
        taskDto.setDescription(description);
        taskDto.setEmployeeName(user.getName());
        taskDto.setEmployeeId(user.getId());
        taskDto.setDueDate(dueDate);
        taskDto.setPriority(priority);
        taskDto.setTaskStatus(taskStatus);
        return taskDto;
    }
}
