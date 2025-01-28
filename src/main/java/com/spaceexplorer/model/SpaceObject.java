package main.java.com.spaceexplorer.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a celestial object in the game such as a planet, asteroid, or space station.
 * Space objects are the primary entities that players can explore and interact with.
 * Each object can contain resources for collection and interesting facts for discovery.
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public class SpaceObject {
    /** The type of space object (PLANET, ASTEROID, or SPACE_STATION) */
    private ObjectType type;

    /** The unique name of the space object */
    private String name;

    /** A detailed description of the space object */
    private String description;

    /** List of resources available for collection from this object */
    private List<ResourceType> resources;

    /** List of interesting facts about this space object */
    private List<String> facts;

    /**
     * Creates a new SpaceObject with empty resource and fact lists.
     */
    public SpaceObject() {
        this.resources = new ArrayList<>();
        this.facts = new ArrayList<>();
    }

    /**
     * Gets the type of this space object.
     *
     * @return The object's type
     */
    public ObjectType getType() {
        return type;
    }

    /**
     * Sets the type of this space object.
     *
     * @param type The new object type
     * @throws NullPointerException if type is null
     */
    public void setType(ObjectType type) {
        this.type = Objects.requireNonNull(type, "Object type cannot be null");
    }

    /**
     * Gets the name of this space object.
     *
     * @return The object's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this space object.
     *
     * @param name The new object name
     * @throws IllegalArgumentException if name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Space object name cannot be empty");
        }
        this.name = name.trim();
    }

    /**
     * Gets the description of this space object.
     *
     * @return The object's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of this space object.
     *
     * @param description The new description
     */
    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
    }

    /**
     * Gets a copy of the list of resources available from this object.
     *
     * @return A new ArrayList containing all available resources
     */
    public List<ResourceType> getResources() {
        return new ArrayList<>(resources);
    }

    /**
     * Sets the list of resources available from this object.
     *
     * @param resources The new list of resources
     * @throws NullPointerException if resources is null
     */
    public void setResources(List<ResourceType> resources) {
        this.resources = new ArrayList<>(Objects.requireNonNull(resources, "Resources list cannot be null"));
    }

    /**
     * Gets a copy of the list of interesting facts about this object.
     *
     * @return A new ArrayList containing all facts
     */
    public List<String> getFacts() {
        return new ArrayList<>(facts);
    }

    /**
     * Sets the list of interesting facts about this object.
     *
     * @param facts The new list of facts
     * @throws NullPointerException if facts is null
     */
    public void setFacts(List<String> facts) {
        this.facts = new ArrayList<>(Objects.requireNonNull(facts, "Facts list cannot be null"));
    }

    /**
     * Checks if this SpaceObject is equal to another object.
     * Space objects are considered equal if they have the same name and type.
     *
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpaceObject)) return false;
        SpaceObject that = (SpaceObject) o;
        return Objects.equals(name, that.name) && type == that.type;
    }

    /**
     * Generates a hash code for this SpaceObject.
     *
     * @return The hash code value for this space object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}