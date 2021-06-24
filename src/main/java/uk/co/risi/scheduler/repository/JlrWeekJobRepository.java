package uk.co.risi.scheduler.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import uk.co.risi.scheduler.domain.JlrSingleJob;
import uk.co.risi.scheduler.domain.JlrWeekJob;

import java.time.LocalTime;

public interface JlrWeekJobRepository extends ReactiveCrudRepository<JlrWeekJob, String> {

    @Query("{'time' : { $gte: ?0, $lt: ?1 } }")
    Flux<JlrSingleJob> getNextScheduledJobs(LocalTime start, LocalTime end);
}
