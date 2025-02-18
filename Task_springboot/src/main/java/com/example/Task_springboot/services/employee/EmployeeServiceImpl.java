package com.example.Task_springboot.services.employee;

import com.example.Task_springboot.dto.CommentDTO;
import com.example.Task_springboot.dto.TaskDto;
import com.example.Task_springboot.entities.Comment;
import com.example.Task_springboot.entities.Task;
import com.example.Task_springboot.entities.User;
import com.example.Task_springboot.enums.TaskStatus;
import com.example.Task_springboot.repositories.CommentRepository;
import com.example.Task_springboot.repositories.TaskRepository;
import com.example.Task_springboot.utils.Jwtutil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl  implements  EmployeeService{

    private  final TaskRepository taskRepository;
    private  final Jwtutil jwtutil;
    private   final CommentRepository commentRepository;
    @Override
    public List<TaskDto> getTasksByUserId() {
        User user = jwtutil.getLoggedInUser();

        if (user != null) {
            return taskRepository.findAllByUserId(user.getId())
                    .stream()
                    .sorted(Comparator.comparing(Task::getDueDate).reversed())
                    .map(Task::getTaskDto)
                    .collect(Collectors.toList());
        }

        // Throw an exception if the user is not found
        throw new EntityNotFoundException("User not found");
    }

    @Override
    public TaskDto updateTask(Long id, String status) {
        Optional<Task> optionalTask=taskRepository.findById(id);
        if(optionalTask.isPresent()){
            Task existingTask=optionalTask.get();
            existingTask.setTaskStatus(mapStringToTaskStatus(status));
            return  taskRepository.save(existingTask).getTaskDto();
        }
        throw  new EntityNotFoundException("Task not found");
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

    @Override
    public TaskDto getTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return optionalTask.map(Task::getTaskDto).orElse(null);

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
