package main.java.com.spaceexplorer.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents the game map containing all sectors in the Space Explorer game.
 * The map is organized into sectors, each containing various space objects
 * that players can explore.
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public class GameMap {
    /** List of all sectors in the game map */
    private List<Sector> sectors;

    /**
     * Constructs a new empty GameMap with no sectors.
     */
    public GameMap() {
        this.sectors = new ArrayList<>();
    }

    /**
     * Returns a defensive copy of the sectors list.
     *
     * @return A new ArrayList containing all sectors in the map
     */
    public List<Sector> getSectors() {
        return new ArrayList<>(sectors);
    }

    /**
     * Sets the list of sectors for this game map.
     * Creates a defensive copy of the provided list.
     *
     * @param sectors The new list of sectors
     * @throws NullPointerException if sectors is null
     */
    public void setSectors(List<Sector> sectors) {
        this.sectors = new ArrayList<>(Objects.requireNonNull(sectors, "Sectors list cannot be null"));
    }

    /**
     * Adds a new sector to the game map.
     *
     * @param sector The sector to add
     * @throws NullPointerException if sector is null
     */
    public void addSector(Sector sector) {
        Objects.requireNonNull(sector, "Sector cannot be null");
        sectors.add(sector);
    }

    /**
     * Removes a sector from the game map.
     *
     * @param sector The sector to remove
     */
    public void removeSector(Sector sector) {
        sectors.remove(sector);
    }

    /**
     * Checks if this GameMap is equal to another object.
     * Two GameMaps are considered equal if they have the same sectors.
     *
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameMap)) return false;
        GameMap gameMap = (GameMap) o;
        return Objects.equals(sectors, gameMap.sectors);
    }

    /**
     * Generates a hash code for this GameMap.
     *
     * @return The hash code value for this game map
     */
    @Override
    public int hashCode() {
        return Objects.hash(sectors);
    }
}