package com.santiago.posada.controller;

import com.santiago.posada.repository.ToDoRepository;
import com.santiago.posada.repository.model.ToDo;
import com.santiago.posada.routes.ErrorResponse;
import com.santiago.posada.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/toDo")
public class ToDoController {

    /**
     * 1 - Server response
     * 2 - Router functions
     * 3 - Handle business errors
     * */

    @Autowired
    private ToDoService service;


    @GetMapping("/helloWorld")
    public String helloWorld(){
        return service.saludar();
    }

    @PostMapping("/create/task/{task}")
    public Mono<ResponseEntity<ToDo>> createToDo(@PathVariable("task") String task) {
        return service.addTask(task)
                .map(todo -> ResponseEntity.status(HttpStatus.CREATED).body(todo))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
    }
    @GetMapping("/get/all")
    public Flux<ResponseEntity<ToDo>> getAllTasks(){
        return service.getTasks()
                .map(ResponseEntity::ok)
                .onErrorResume(error -> Flux.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @PutMapping("update/task/{id}/{newTask}")
    public Mono<ResponseEntity<ToDo>> updateTask(@PathVariable("id") String id, @PathVariable("newTask") String newTask){
        return service.updateTask(id, newTask)
                .map(todo -> new ResponseEntity<>(todo, HttpStatus.OK))
                .onErrorResume(error -> {
                    System.out.println(error.getMessage());
                    return Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
                });
    }
    @DeleteMapping("/delete/task/{id}")
    public Mono<ResponseEntity<String>> deleteTask(@PathVariable("id") String id) {
        return service.deleteTask(id)
                .map(deleted -> {
                    if (deleted) {
                        return ResponseEntity.ok("Task deleted successfully");
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
                    }
                })
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + error.getMessage())));
    }

}
