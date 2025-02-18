package com.example.Task_springboot.controller.employee;

import com.example.Task_springboot.dto.CommentDTO;
import com.example.Task_springboot.dto.TaskDto;
import com.example.Task_springboot.services.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@CrossOrigin("*")
public class employeeController {

    private  final EmployeeService employeeService;

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getTasksByUserId(){
        return  ResponseEntity.ok(employeeService.getTasksByUserId());
    }

    @GetMapping("/task/{id}/{status}")
    public  ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @PathVariable String status){
        TaskDto updtedTaskDTO =employeeService.updateTask(id,status);
        if(updtedTaskDTO ==null)
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.ok(updtedTaskDTO);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDto>getTaskById(@PathVariable Long id){

        return  ResponseEntity.ok(employeeService.getTaskById(id));
    }


    @PostMapping("/task/comment/{taskId}")
    public  ResponseEntity<?> createComment(@PathVariable Long taskId, @RequestParam String content){
        CommentDTO createdCommentDTO=employeeService.createComment(taskId,content);
        if(createdCommentDTO==null)
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCommentDTO);
    }
    @GetMapping("/comments/{taskId}")
    public ResponseEntity<List<CommentDTO>> getCommentByTaskId(@PathVariable Long taskId){
        return  ResponseEntity.ok(employeeService.getCommentedByTaskId((taskId)));
    }
}


