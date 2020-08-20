package webDriver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Configuration {

    private static final String configPath = "src/test/resources/config.json";

    public static String getConfigValue(String key) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(new File(configPath));
            return root.path(key).asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root.path(key).asText();
    }
}
