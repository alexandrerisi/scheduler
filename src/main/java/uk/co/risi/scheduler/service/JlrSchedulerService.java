package uk.co.risi.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uk.co.risi.scheduler.repository.JlrSingleJobRepository;
import uk.co.risi.scheduler.repository.JlrWeekJobRepository;

import java.time.Instant;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@EnableAsync
@EnableScheduling
@Service
public class JlrSchedulerService {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Value("${queue.job}")
    private String jlrJobQueue;
    @Value("${polling.rate}")
    private long pollingRate;
    private final JlrSingleJobRepository singleJobRepository;
    private final JlrWeekJobRepository weekJobRepository;
    private Instant lastSingleTimeRun = Instant.now();
    private final AsyncRabbitTemplate template;
    private LocalTime lastWeekRun = LocalTime.now();

    @Async
    @Scheduled(fixedRateString = "${polling.rate}", initialDelay = 1000)
    public void scheduleSingleTimeJobs() {

        var nextMinute = lastSingleTimeRun.plusMillis(pollingRate);
        var jobs = singleJobRepository
                .getNextScheduledJobs(lastSingleTimeRun, nextMinute)
                .doOnTerminate(() -> lastSingleTimeRun = nextMinute);

        jobs
                //.log()
                .doOnNext(jlrJob -> template.convertSendAndReceive(jlrJobQueue, jlrJob))
                .switchIfEmpty(empty -> logger.log(Level.INFO, "No Single jobs at " + lastSingleTimeRun))
                //.subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }

    @Async
    @Scheduled(fixedRateString = "${polling.rate}", initialDelay = 1000)
    public void scheduleWeekJobs() {

        var nextMinute = lastWeekRun.plusSeconds(pollingRate / 1000);
        var jobs = weekJobRepository
                .getNextScheduledJobs(lastWeekRun, nextMinute)
                .doOnTerminate(() -> lastWeekRun = nextMinute);

        jobs
                //.log()
                .doOnNext(jlrJob -> template.convertSendAndReceive(jlrJobQueue, jlrJob))
                .switchIfEmpty(empty -> logger.log(Level.INFO, "No Week jobs at " + lastWeekRun))
                //.subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }
}
