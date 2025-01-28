package main.java.com.spaceexplorer.model;

import java.util.Objects;

/**
 * Represents an item in the Space Explorer game.
 * Items can be collected, stored in inventory, and used by players.
 * Each item has a name, type, and optional description.
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public class Item {
    /** The unique name of the item */
    private String name;

    /** The type of the item (e.g., "RESOURCE", "TOOL", "ARTIFACT") */
    private String type;

    /** Optional description providing more details about the item */
    private String description;

    /**
     * Default constructor for Item.
     * Required for JSON deserialization.
     */
    public Item() {}

    /**
     * Constructs a new Item with the specified name and type.
     *
     * @param name The name of the item
     * @param type The type of the item
     * @throws IllegalArgumentException if name or type is null or empty
     */
    public Item(String name, String type) {
        setName(name);
        setType(type);
    }

    /**
     * Gets the name of the item.
     *
     * @return The item's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     *
     * @param name The new name for the item
     * @throws IllegalArgumentException if name is null or empty
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty");
        }
        this.name = name.trim();
    }

    /**
     * Gets the type of the item.
     *
     * @return The item's type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the item.
     *
     * @param type The new type for the item
     * @throws IllegalArgumentException if type is null or empty
     */
    public void setType(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Item type cannot be empty");
        }
        this.type = type.trim();
    }

    /**
     * Gets the description of the item.
     *
     * @return The item's description, may be null
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the item.
     * If the description is null, it will be stored as null.
     * If non-null, the description will be trimmed.
     *
     * @param description The new description for the item
     */
    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
    }

    /**
     * Checks if this Item is equal to another object.
     * Items are considered equal if they have the same name.
     *
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

    /**
     * Generates a hash code for this Item.
     *
     * @return The hash code value for this item
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}