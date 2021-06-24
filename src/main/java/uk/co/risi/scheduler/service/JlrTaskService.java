package uk.co.risi.scheduler.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.co.risi.scheduler.domain.JlrTask;
import uk.co.risi.scheduler.repository.JlrTaskRepository;

@AllArgsConstructor
@Service
public class JlrTaskService {

    private final JlrTaskRepository repository;

    public Mono<JlrTask> addTask(JlrTask task) {
        return repository.save(task);
    }

    public Mono<Void> deleteTask(String id) {
        return repository.deleteById(id);
    }

    public Mono<JlrTask> returnTask(String id) {
        return repository.findById(id);
    }

    public Flux<JlrTask> returnAllTasks() {
        return repository.findAll();
    }
}
