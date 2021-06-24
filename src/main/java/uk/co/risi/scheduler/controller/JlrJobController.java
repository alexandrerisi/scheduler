package uk.co.risi.scheduler.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import uk.co.risi.scheduler.domain.JlrSingleJob;
import uk.co.risi.scheduler.domain.JlrWeekJob;
import uk.co.risi.scheduler.service.JlrJobService;

@RestController
@AllArgsConstructor
@RequestMapping("/jobs")
public class JlrJobController {

    private final JlrJobService service;

    @PostMapping("/single")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<JlrSingleJob> addSingleJob(@RequestBody JlrSingleJob job) {
        return service.addSingleJob(job);
    }

    @PostMapping("/week")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<JlrWeekJob> addSingleJob(@RequestBody JlrWeekJob job) {
        return service.addWeekJob(job);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteJob(@PathVariable String id) {
        return service.deleteJob(id);
    }

    @GetMapping("/{id}")
    public Mono<JlrSingleJob> returnJob(@PathVariable String id) {
        return service.returnJob(id);
    }

    @GetMapping
    public Flux<JlrSingleJob> returnAllJobs() {
        return service.returnAllJobs();
    }
}
