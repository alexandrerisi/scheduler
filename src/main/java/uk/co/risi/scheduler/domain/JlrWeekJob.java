package uk.co.risi.scheduler.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.*;
import java.util.HashSet;
import java.util.Set;

@Data
public class JlrWeekJob {

    private static ZoneId systemZoneId = ZonedDateTime.now().getZone();

    @Id
    private String id;
    private Set<DayOfWeek> weekDays;
    private LocalTime time;
    private JlrTask task;

    public void adjustTimeZone(ZoneId zoneId) {
        var userZdt = ZonedDateTime.of(LocalDate.now(), time, zoneId);
        var systemZdt = userZdt.withZoneSameInstant(systemZoneId);

        var delta = Math.abs(userZdt.getHour() - systemZdt.getHour());
        if (userZdt.getHour() > systemZdt.getHour()) {
            if (userZdt.getHour() - delta < 0) {
                Set<DayOfWeek> updatedWeekDays = new HashSet<>();
                for (DayOfWeek dow : weekDays)
                    updatedWeekDays.add(dow.minus(1));

                weekDays = updatedWeekDays;
            }
        } else if (userZdt.getHour() < systemZdt.getHour()) {
            if (userZdt.getHour() + delta > 23) {
                Set<DayOfWeek> updatedWeekDays = new HashSet<>();
                for (DayOfWeek dow : weekDays)
                    updatedWeekDays.add(dow.plus(1));

                weekDays = updatedWeekDays;
            }
        }
    }
}
