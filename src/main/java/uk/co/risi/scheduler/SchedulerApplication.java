package uk.co.risi.scheduler;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;
import uk.co.risi.scheduler.domain.JlrSingleJob;
import uk.co.risi.scheduler.domain.JlrTask;
import uk.co.risi.scheduler.domain.JlrWeekJob;
import uk.co.risi.scheduler.service.JlrJobService;
import uk.co.risi.scheduler.service.JlrTaskService;

import java.net.URI;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.HashSet;

@SpringBootApplication
public class SchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerApplication.class, args);
    }

    @RabbitListener(queues = "${queue.job}")
    public void listen(JlrSingleJob job) {
        System.out.println("Job received: " + job);
    }

    @Bean
    public CommandLineRunner runner(JlrTaskService taskService, JlrJobService jobService) {
        return args -> {
            var task = new JlrTask();
            task.setBody("Body");
            var headers = new HashMap<String, String>();
            headers.put("AUTHORIZATION", "auth_token");
            task.setHeaders(headers);
            task.setHttpMethod(HttpMethod.POST);
            task.setUri(new URI("http://127.0.0.1:8080"));
            taskService
                    .addTask(task)
                    .flatMap(jlrTask -> {
                        var job = new JlrSingleJob();
                        job.setDescription("Test job description!");
                        job.setName("Test job name");
                        job.setTask(jlrTask);
                        job.setScheduleTrigger(ZonedDateTime.now(ZoneId.of("Europe/Paris")).plusSeconds(15));
                        System.out.println(job);
                        return jobService.addSingleJob(job);
                    })
                    .flatMap(jlrSingleJob -> {
                        //ZonedDateTime.
                        //var weekJob = new JlrWeekJob();
                        var weekDays = new HashSet<DayOfWeek>();
                        //weekDays.add(ZonedDateTime.now().getDayOfWeek())
                        return Mono.empty();
                    })
                    .block();
        };
    }
}
