package uk.co.risi.scheduler.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import uk.co.risi.scheduler.domain.JlrSingleJob;

import java.time.Instant;

public interface JlrSingleJobRepository extends ReactiveCrudRepository<JlrSingleJob, String> {

    @Query("{'scheduleTrigger' : { $gte: ?0, $lt: ?1 } }")
    Flux<JlrSingleJob> getNextScheduledJobs(Instant start, Instant end);

    //Flux<JlrJob> findByScheduleTriggerBetween(Instant start, Instant end);
}
