package ratesapi;

import java.time.*;
import java.time.format.DateTimeFormatter;

public abstract class Helper {
    public static String getLatestDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        if(localDateTime.getHour() >= 16) { // see comment about dates in README.md
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            return localDateTime.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

}
