package main.java.com.spaceexplorer.util;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.com.spaceexplorer.model.GameMap;

import java.io.File;
import java.io.IOException;

public class JsonReader {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static GameMap loadMap(String path) {
        try {
            return mapper.readValue(new File(path), GameMap.class);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.toString());
            return null;
        }
    }
}
