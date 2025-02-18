package com.example.Task_springboot.services.admin;


import com.example.Task_springboot.dto.CommentDTO;
import com.example.Task_springboot.dto.TaskDto;
import com.example.Task_springboot.dto.UserDto;
import com.example.Task_springboot.entities.Comment;
import com.example.Task_springboot.entities.Task;
import com.example.Task_springboot.entities.User;
import com.example.Task_springboot.enums.TaskStatus;
import com.example.Task_springboot.enums.UserRole;
import com.example.Task_springboot.repositories.CommentRepository;
import com.example.Task_springboot.repositories.TaskRepository;
import com.example.Task_springboot.repositories.UserRepository;
import com.example.Task_springboot.utils.Jwtutil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements   AdminService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private  final Jwtutil jwtutil;
  private  final CommentRepository commentRepository;
    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getUserRole() == UserRole.EMPLOYEE)
                .map(User::getUserDto)
                .collect(Collectors.toList());
    }

    @Override

    public TaskDto createTask(TaskDto taskDto) {
        Optional<User> optionalUser = userRepository.findById(taskDto.getEmployeeId());
        System.out.println(optionalUser);
        if (optionalUser.isPresent()) {
            Task task = new Task();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setPriority(taskDto.getPriority());
            task.setDueDate(taskDto.getDueDate());
            task.setTaskStatus(TaskStatus.INPROGRESS);
            task.setUser(optionalUser.get());
            return taskRepository.save(task).getTaskDto();
        }
        return null;
    }


    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.reverseOrder())))
                .map(Task::getTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto getTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(Task::getTaskDto).orElse(null);

    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {

        Optional<Task> optionalTask = taskRepository.findById(id);
        Optional<User> optionalUser=userRepository.findById(taskDto.getEmployeeId());
        if (optionalTask.isPresent() && optionalUser.isPresent()) {
            Task existingTask = optionalTask.get();
            existingTask.setTitle(taskDto.getTitle());
            existingTask.setDescription(taskDto.getDescription());
            existingTask.setDueDate(taskDto.getDueDate());
            existingTask.setPriority(taskDto.getPriority());
            existingTask.setTaskStatus(mapStringToTaskStatus(String.valueOf(taskDto.getTaskStatus())));

            existingTask.setUser(optionalUser.get());
            return taskRepository.save(existingTask).getTaskDto();
        }
        return  null;
    }

    @Override
    public List<TaskDto> searchTaskByTittle(String title) {
        return taskRepository.findAllByTitleContaining(title)
                .stream()
                .sorted(Comparator.comparing(Task::getDueDate).reversed())
                .map(Task::getTaskDto )
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO createComment(long taskId, String content) {
      Optional<Task> optionalTask=  taskRepository.findById(taskId);
      User user=jwtutil.getLoggedInUser();
      if((optionalTask.isPresent()) &&user !=null){
          Comment comment = new Comment();
          comment.setCreatedAt(new Date());
          comment.setContent(content);
          comment.setTask(optionalTask.get());
          comment.setUser(user);
          return commentRepository.save(comment).getCommentDTO();
      }

        throw  new EntityNotFoundException("user or Task not found");
    }

    @Override
    public List<CommentDTO> getCommentedByTaskId(Long taskId) {
        return commentRepository.findAllByTaskId(taskId)
                .stream().
                map(Comment::getCommentDTO)
                .collect(Collectors.toList());
    }

    private TaskStatus mapStringToTaskStatus(String status) {
        return switch (status) {

            case "PENDING" -> TaskStatus.PENDING;
            case "INPROGRESS" -> TaskStatus.INPROGRESS;
            case "COMPLETED" -> TaskStatus.COMPLETED;
            case "DEFERRED" -> TaskStatus.DEFERRED;
            default -> TaskStatus.CANCELLED;
        };
    }
}
