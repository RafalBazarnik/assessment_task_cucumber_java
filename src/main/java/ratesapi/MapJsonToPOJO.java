package ratesapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class MapJsonToPOJO {
    private static Logger LOGGER = LogManager.getLogger(MapJsonToPOJO.class);

    public static ResponsePOJO map(String jsonString) {
        LOGGER.debug(jsonString);
        ResponsePOJO responsePOJO = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            responsePOJO = mapper.readValue(jsonString, ResponsePOJO.class);
        } catch (IOException exception) {
            LOGGER.error(exception);
        }
        return responsePOJO;
    }

}
