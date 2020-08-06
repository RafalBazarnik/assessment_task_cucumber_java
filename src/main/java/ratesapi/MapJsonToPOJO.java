package ratesapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class MapJsonToPOJO {
    public static ResponsePOJO map(String jsonString) {
        log.debug(jsonString);
        ResponsePOJO responsePOJO = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            responsePOJO = mapper.readValue(jsonString, ResponsePOJO.class);
        } catch (IOException exception) {
            log.error(exception);
        }

        return responsePOJO;
    }

    public static ErrorResponsePOJO mapError(String jsonString) {
        log.debug(jsonString);
        ErrorResponsePOJO errorResponsePOJO = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            errorResponsePOJO = mapper.readValue(jsonString, ErrorResponsePOJO.class);
        } catch (IOException exception) {
            log.error(exception);
        }

        return errorResponsePOJO;
    }

    private MapJsonToPOJO() {
        // private constructor
    }
}
