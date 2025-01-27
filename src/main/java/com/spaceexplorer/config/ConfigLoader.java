package main.java.com.spaceexplorer.config;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

public class ConfigLoader {
    private static final Map<String, Map<String, String>> config;

    static {
        try {
            Yaml yaml = new Yaml();
            try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.yaml")) {
                if (input == null) {
                    throw new RuntimeException("config.yaml not found in resources");
                }
                config = yaml.load(input);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration", e);
        }
    }

    public static String getPath(String key) {
        Map<String, String> paths = config.get("paths");
        if (paths == null) {
            throw new RuntimeException("Paths configuration not found");
        }
        String path = paths.get(key);
        if (path == null) {
            throw new RuntimeException("Path not found for key: " + key);
        }
        return path;
    }
}