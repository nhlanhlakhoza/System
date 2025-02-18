package com.example.Task_springboot.services.employee;

import com.example.Task_springboot.dto.CommentDTO;
import com.example.Task_springboot.dto.TaskDto;

import java.util.List;

public interface EmployeeService {

    List<TaskDto> getTasksByUserId();
    TaskDto updateTask(Long id,String status);
    TaskDto getTaskById(Long id);
    CommentDTO createComment(long taskId, String content);
    List<CommentDTO>getCommentedByTaskId(Long taskId);
}
