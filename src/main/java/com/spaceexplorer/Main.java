package main.java.com.spaceexplorer;

import main.java.com.spaceexplorer.model.GameMap;
import main.java.com.spaceexplorer.util.JsonReader;

public class Main {
    public static void main(String[] args) {
        GameMap map = JsonReader.loadMap("src/main/resources/maps/game_map.json");
        if(map != null) {
            System.out.println("Map loaded successfully");
        }
    }
}
