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
    public Mono<ToDo> createToDo(@PathVariable("task") String task){
        return service.addTask(task);
    }

    @GetMapping("/get/all")
    public Flux<ToDo> getAllTasks(){
        return service.getTasks();
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
}
