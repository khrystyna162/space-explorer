package main.java.com.spaceexplorer.model;

import java.util.List;

/**
 * Represents a sector in the game's space map.
 * A sector is a region of space that contains various space objects
 * such as planets, asteroids, and space stations that players can explore.
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public class Sector {
    /** Unique identifier for the sector */
    private String id;

    /** Display name of the sector */
    private String name;

    /** List of space objects contained within this sector */
    private List<SpaceObject> objects;

    /**
     * Creates a new empty Sector.
     * Required for JSON deserialization.
     */
    public Sector() {}

    /**
     * Gets the sector's unique identifier.
     *
     * @return The sector's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the sector's unique identifier.
     *
     * @param id The new sector ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the sector's display name.
     *
     * @return The sector's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the sector's display name.
     *
     * @param name The new sector name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of space objects in this sector.
     *
     * @return List of space objects in the sector
     */
    public List<SpaceObject> getObjects() {
        return objects;
    }

    /**
     * Sets the list of space objects in this sector.
     *
     * @param objects The new list of space objects
     */
    public void setObjects(List<SpaceObject> objects) {
        this.objects = objects;
    }
}