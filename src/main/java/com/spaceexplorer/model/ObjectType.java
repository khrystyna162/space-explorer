package main.java.com.spaceexplorer.model;

/**
 * Enumeration of possible space object types in the game.
 * Defines the different kinds of celestial bodies and structures
 * that players can encounter and interact with in the game world.
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public enum ObjectType {
    /**
     * A celestial body orbiting a star.
     * Planets are major bodies that have cleared their orbital neighborhood.
     */
    PLANET("Planet"),

    /**
     * A small rocky body orbiting a star.
     * Asteroids are typically found in the asteroid belt or near planets.
     */
    ASTEROID("Asteroid"),

    /**
     * An artificial structure in space.
     * Space stations serve as hubs for players and can provide various services.
     */
    SPACE_STATION("Space Station");

    /** Human-readable description of the space object type */
    private final String description;

    /**
     * Constructs a new ObjectType with the specified description.
     *
     * @param description A human-readable description of the object type
     */
    ObjectType(String description) {
        this.description = description;
    }

    /**
     * Returns the human-readable description of the object type.
     *
     * @return The description of the object type
     */
    public String getDescription() {
        return description;
    }
}