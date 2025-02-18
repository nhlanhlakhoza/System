package com.example.Task_springboot.services.admin;

import com.example.Task_springboot.dto.CommentDTO;
import com.example.Task_springboot.dto.TaskDto;
import com.example.Task_springboot.dto.UserDto;

import java.util.List;

public interface AdminService {


    List<UserDto> getUsers();
    TaskDto createTask(TaskDto taskDto);
    List<TaskDto>getAllTasks();
    void  deleteTask(Long id);
    TaskDto getTaskById(Long id);
    TaskDto updateTask(Long id,TaskDto taskDto);
    List<TaskDto> searchTaskByTittle(String title);
    CommentDTO createComment(long taskId,String content);
    List<CommentDTO>getCommentedByTaskId(Long taskId);
}
