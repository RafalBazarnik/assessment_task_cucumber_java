package ratesapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public final class MapJsonToPOJO {
    public static <T> T map(String jsonString, Class<T> pojoClass) {
        log.debug(jsonString);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString, pojoClass);
        } catch (IOException exception) {
            log.error(exception);
        }
        return null;
    }

    private MapJsonToPOJO() {
        // private constructor
    }
}
