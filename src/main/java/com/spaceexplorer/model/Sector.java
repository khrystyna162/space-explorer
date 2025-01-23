package main.java.com.spaceexplorer.model;

import java.util.List;

public class Sector {
    private String id;
    private String name;
    private List<SpaceObject> objects;

    public Sector() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SpaceObject> getObjects() {
        return objects;
    }

    public void setObjects(List<SpaceObject> objects) {
        this.objects = objects;
    }
}
