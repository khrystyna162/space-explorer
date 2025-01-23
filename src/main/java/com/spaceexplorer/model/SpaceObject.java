package main.java.com.spaceexplorer.model;

import java.util.List;

public class SpaceObject {
    private ObjectType type;
    private String name;
    private List<ResourceType> resources;

    public SpaceObject() {}

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ResourceType> getResources() {
        return resources;
    }

    public void setResources(List<ResourceType> resources) {
        this.resources = resources;
    }
}
