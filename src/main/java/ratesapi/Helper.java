package ratesapi;

import java.time.*;
import java.time.format.DateTimeFormatter;

public abstract class Helper {
    public static String getLatestDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        if(localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY) { // see comment about dates in README.md
            return localDateTime.minusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else if(localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY || localDateTime.getHour() < 16) { // see comment about dates in README.md
            return localDateTime.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

}
