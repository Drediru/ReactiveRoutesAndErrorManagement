package com.santiago.posada.repository;

import com.santiago.posada.repository.model.ToDo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface ToDoRepository extends ReactiveMongoRepository<ToDo, String> {

}
