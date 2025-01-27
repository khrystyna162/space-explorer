package main.java.com.spaceexplorer.model;

/**
 * Enumeration of possible space object types in the game.
 */
public enum ObjectType {
    /** A celestial body orbiting a star */
    PLANET("Planet"),

    /** A small rocky body orbiting a star */
    ASTEROID("Asteroid"),

    /** An artificial structure in space */
    SPACE_STATION("Space Station");

    private final String description;

    ObjectType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}