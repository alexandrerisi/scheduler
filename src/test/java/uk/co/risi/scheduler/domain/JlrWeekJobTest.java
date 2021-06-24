package uk.co.risi.scheduler.domain;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class JlrWeekJobTest {

    @Test
    void setTime() {

        var instant = Instant.parse("2019-06-11T22:30:00Z");
        var londonZdt = ZonedDateTime.ofInstant(instant, ZoneId.of("Europe/London"));
        var parisZdt = londonZdt.withZoneSameInstant(ZoneId.of("Europe/Paris"));

        var weeJob = new JlrWeekJob();
        var localTime = LocalTime.now();
        System.out.println(localTime);
        weeJob.setTime(localTime, ZoneId.of("Europe/Paris"));
        System.out.println(weeJob.getTime());

        //System.out.println(londonZdt.toLocalTime());
        //System.out.println(parisZdt.toLocalTime());
        assertEquals(londonZdt.toLocalTime().plusHours(1), parisZdt.toLocalTime());
    }
}