package ratesapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public abstract class MapJsonToPOJO {
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

    public static ErrorResponsePOJO mapError(String jsonString) {
        LOGGER.debug(jsonString);
        ErrorResponsePOJO errorResponsePOJO = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            errorResponsePOJO = mapper.readValue(jsonString, ErrorResponsePOJO.class);
        } catch (IOException exception) {
            LOGGER.error(exception);
        }

        return errorResponsePOJO;
    }
}
