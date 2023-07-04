package com.santiago.posada.service;

import com.santiago.posada.repository.ToDoRepository;
import com.santiago.posada.repository.model.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository repository;
    private String helloWorld = "Hello Bancolombia's team";

    public String saludar(){
        return helloWorld;
    }

    public Mono<ToDo> addTask(String task){
        return repository.save(new ToDo(task));
    }

    public Flux<ToDo> getTasks(){
        return repository.findAll();
    }

    public Mono<ToDo> updateTask(String id, String newTask){
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El registro no esta en la base de datos")))
                .flatMap(task -> repository.save(ToDo.from(newTask, id)));
    }
}
