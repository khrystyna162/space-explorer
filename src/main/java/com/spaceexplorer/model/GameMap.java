package main.java.com.spaceexplorer.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class GameMap {
    private List<Sector> sectors;

    public GameMap() {
        this.sectors = new ArrayList<>();
    }

    public List<Sector> getSectors() {
        return new ArrayList<>(sectors);
    }

    public void setSectors(List<Sector> sectors) {
        this.sectors = new ArrayList<>(Objects.requireNonNull(sectors, "Sectors list cannot be null"));
    }

    public void addSector(Sector sector) {
        Objects.requireNonNull(sector, "Sector cannot be null");
        sectors.add(sector);
    }

    public void removeSector(Sector sector) {
        sectors.remove(sector);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameMap)) return false;
        GameMap gameMap = (GameMap) o;
        return Objects.equals(sectors, gameMap.sectors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectors);
    }
}
