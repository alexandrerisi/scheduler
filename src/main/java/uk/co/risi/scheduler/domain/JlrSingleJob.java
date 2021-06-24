package uk.co.risi.scheduler.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.time.*;

@Data
public class JlrSingleJob implements Serializable {

    @Id
    private String id;
    @Indexed(unique=true)
    private String name;
    private String description;
    private JlrTask task;

    // One Time Schedule
    private Instant scheduleTrigger;

    public void setScheduleTrigger(ZonedDateTime zonedTrigger) {
        scheduleTrigger = zonedTrigger.toInstant();
    }
}
