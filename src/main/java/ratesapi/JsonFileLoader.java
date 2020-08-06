package ratesapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
public abstract class JsonFileLoader {
    public static ResponsePOJO getJsonFile(String date, String base, String symbol) {
        ObjectMapper mapper = new ObjectMapper();
        String dir = System.getProperty("user.dir");
        String filename = getFilename(date, base, symbol);
        Path path = Paths.get(dir, "src", "main", "resources", "jsonResponsesForDate", filename);
        ResponsePOJO response = null;

        try {
            response = mapper.readValue(new File(String.valueOf(path)), ResponsePOJO.class);
        } catch (IOException exception) {
            log.error(exception);
        }

        log.debug(response);
        return response;
    }

    private static String getFilename(String date, String base, String symbol) {
        String filename;
        if (!base.isEmpty() && !symbol.isEmpty()) {
            filename = String.format("%s_base=%s&symbols=%s.json", date, base, symbol);
        } else if (!base.isEmpty()) {
            filename = String.format("%s_base=%s.json", date, base);
        } else if (!symbol.isEmpty()) {
            filename = String.format("%s_symbols=%s.json", date, symbol);
        } else {
            filename = String.format("%s.json", date);
        }

        log.debug(filename);
        return filename;
    }

    private JsonFileLoader() {
        // private constructor
    }
}
