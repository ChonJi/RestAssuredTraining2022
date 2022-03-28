package helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

    private final Properties properties;

    public ReadProperties() {
        properties = new Properties();
        try (final InputStream is = ReadProperties.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readBaseUri() {
        return properties.getProperty("baseUri");
    }

    public String readKey() {
        return properties.getProperty("key");
    }

    public String readToken() {
        return properties.getProperty("token");
    }

}
