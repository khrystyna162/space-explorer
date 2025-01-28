package main.java.com.spaceexplorer.config;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

/**
 * Configuration loader for the Space Explorer game.
 * Loads and provides access to application configuration stored in YAML format.
 * Configuration is loaded once during class initialization and cached.
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public class ConfigLoader {
    /** Cached configuration data from the YAML file */
    private static final Map<String, Map<String, String>> config;

    /**
     * Static initializer that loads the configuration from config.yaml.
     * Configuration is loaded when the class is first accessed.
     *
     * @throws RuntimeException if the config file is not found or cannot be loaded
     */
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

    /**
     * Retrieves a path configuration value by key.
     * Paths are stored in the "paths" section of the configuration.
     *
     * @param key The configuration key to look up
     * @return The path value associated with the key
     * @throws RuntimeException if the paths configuration is not found
     * @throws RuntimeException if the specified key is not found
     */
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