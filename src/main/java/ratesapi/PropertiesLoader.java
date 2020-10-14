package ratesapi;

import lombok.extern.log4j.Log4j2;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Log4j2
public final class PropertiesLoader {
    private static Properties properties = new Properties();

    private static Properties getProperties() {
        if (properties.isEmpty()) {
            loadProperties();
        }
        return properties;
    }

    private static void loadProperties() {
        String dir = System.getProperty("user.dir");
        Path path = Paths.get(dir, "src", "main", "resources", "config.properties");

        try (InputStream input = new FileInputStream(path.toString());) {
            properties.load(input);
        } catch (IOException exception) {
            log.error(exception.getMessage());
        }
    }

    public static String get(String key) {
        String property = getProperties().getProperty(key);
        if (property == null || property.length() == 0) {
            throw new IllegalArgumentException("Empty property or no property with key " + key);
        }
        return property;
    }

    private PropertiesLoader() {
        // private constructor
    }
}
