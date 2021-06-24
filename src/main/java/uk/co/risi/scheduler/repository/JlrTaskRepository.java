package uk.co.risi.scheduler.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import uk.co.risi.scheduler.domain.JlrTask;

public interface JlrTaskRepository extends ReactiveCrudRepository<JlrTask, String> {
}
