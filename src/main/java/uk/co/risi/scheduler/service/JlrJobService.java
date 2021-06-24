package uk.co.risi.scheduler.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.co.risi.scheduler.domain.JlrSingleJob;
import uk.co.risi.scheduler.domain.JlrWeekJob;
import uk.co.risi.scheduler.repository.JlrSingleJobRepository;
import uk.co.risi.scheduler.repository.JlrWeekJobRepository;

@Service
@AllArgsConstructor
public class JlrJobService {

    private final JlrSingleJobRepository singleJobRepository;
    private final JlrWeekJobRepository weekJobRepository;

    public Mono<JlrSingleJob> addSingleJob(JlrSingleJob job) {
        return singleJobRepository.save(job);
    }

    public Mono<JlrWeekJob> addJob(JlrWeekJob job) {
        return weekJobRepository
                .save(job)
                .doOnNext(jlrWeekJob -> {

                });
    }

    public Mono<Void> deleteJob(String id) {
        return singleJobRepository.deleteById(id);
    }

    public Mono<JlrSingleJob> returnJob(String id) {
        return singleJobRepository.findById(id);
    }

    public Flux<JlrSingleJob> returnAllJobs() {
        return singleJobRepository.findAll();
    }

    public Mono<JlrWeekJob> addWeekJob(JlrWeekJob job) {
        return weekJobRepository.save(job);
    }
}
