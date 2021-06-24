package uk.co.risi.scheduler.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.co.risi.scheduler.domain.JlrTask;
import uk.co.risi.scheduler.service.JlrTaskService;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class JlrTaskController {

    private final JlrTaskService service;

    public Mono<JlrTask> addTask(JlrTask task) {
        return service.addTask(task);
    }

    public Mono<Void> deleteTask(String id) {
        return service.deleteTask(id);
    }

    public Mono<JlrTask> returnTask(String id) {
        return service.returnTask(id);
    }

    @GetMapping
    public Flux<JlrTask> returnAllTasks() {
        return service.returnAllTasks();
    }
}
