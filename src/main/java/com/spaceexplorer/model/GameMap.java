package main.java.com.spaceexplorer.model;

import java.util.List;

public class GameMap {
    private List<Sector> sectors;

    public GameMap() {}

    public List<Sector> getSectors() {
        return sectors;
    }

    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
    }
}
