package main.java.com.spaceexplorer.model;

import java.util.List;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a celestial object in the game such as a planet, asteroid, or space station.
 */
public class SpaceObject {
    private ObjectType type;
    private String name;
    private String description;
    private List<ResourceType> resources;
    private List<String> facts;

    public SpaceObject() {
        this.resources = new ArrayList<>();
        this.facts = new ArrayList<>();
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = Objects.requireNonNull(type, "Object type cannot be null");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Space object name cannot be empty");
        }
        this.name = name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
    }

    public List<ResourceType> getResources() {
        return new ArrayList<>(resources);
    }

    public void setResources(List<ResourceType> resources) {
        this.resources = new ArrayList<>(Objects.requireNonNull(resources, "Resources list cannot be null"));
    }

    public List<String> getFacts() {
        return new ArrayList<>(facts);
    }

    public void setFacts(List<String> facts) {
        this.facts = new ArrayList<>(Objects.requireNonNull(facts, "Facts list cannot be null"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpaceObject)) return false;
        SpaceObject that = (SpaceObject) o;
        return Objects.equals(name, that.name) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}