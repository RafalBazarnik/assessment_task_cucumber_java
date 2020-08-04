package ratesapi;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class Helper {
    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Instant now = Instant.now(); //current date
        Instant before = now.minus(Duration.ofDays(1));
        Date dateBefore = Date.from(before); // TODO: remove
        String dateString = dateFormat.format(dateBefore);
        return dateString;
    }
}
