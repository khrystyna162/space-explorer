package main.java.com.spaceexplorer.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a player in the Space Explorer game.
 * Players can explore sectors, collect resources, manage inventory,
 * and interact with various space objects in the game world.
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public class Player {
    private static final Logger logger = LoggerFactory.getLogger(Player.class);

    /** Default health value for new players */
    private static final int DEFAULT_HEALTH = 100;

    /** Default inventory size for new players */
    private static final int DEFAULT_INVENTORY_SIZE = 10;

    /** Unique identifier for the player */
    private String id;

    /** Player's username */
    private String username;

    /** Player's hashed password */
    private String password;

    /** Current health level (0-100) */
    private int health;

    /** Maximum number of items the player can carry */
    private int inventorySize;

    /** Current sector location */
    private String currentSector;

    /** Current planet or object location */
    private String currentPlanet;

    /** List of items in player's inventory */
    private List<Item> inventory;

    /**
     * Creates a new Player with default health and inventory size.
     */
    public Player() {
        this.health = DEFAULT_HEALTH;
        this.inventorySize = DEFAULT_INVENTORY_SIZE;
        this.inventory = new ArrayList<>();
    }

    /**
     * Creates a new Player with specified username and password.
     *
     * @param username The player's username
     * @param password The player's password (should be pre-hashed)
     */
    public Player(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the player's unique identifier.
     *
     * @return The player's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the player's unique identifier.
     *
     * @param id The new ID
     * @throws IllegalArgumentException if id is null or empty
     */
    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Player ID cannot be empty");
        }
        this.id = id;
    }

    /**
     * Gets the player's username.
     *
     * @return The player's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the player's username.
     *
     * @param username The new username
     * @throws IllegalArgumentException if username is null or empty
     */
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.username = username;
    }

    /**
     * Gets the player's password hash.
     *
     * @return The hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the player's password (should be pre-hashed).
     *
     * @param password The new hashed password
     * @throws IllegalArgumentException if password is null or empty
     */
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.password = password;
    }

    /**
     * Gets the player's current health level.
     *
     * @return The current health value (0-100)
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the player's health level.
     *
     * @param health The new health value
     * @throws IllegalArgumentException if health is not between 0 and 100
     */
    public void setHealth(int health) {
        if (health < 0 || health > 100) {
            throw new IllegalArgumentException("Health must be between 0 and 100");
        }
        this.health = health;
    }

    /**
     * Gets the player's maximum inventory size.
     *
     * @return The maximum number of items the player can carry
     */
    public int getInventorySize() {
        return inventorySize;
    }

    /**
     * Sets the player's maximum inventory size.
     *
     * @param inventorySize The new inventory size
     * @throws IllegalArgumentException if inventory size is negative
     */
    public void setInventorySize(int inventorySize) {
        if (inventorySize < 0) {
            throw new IllegalArgumentException("Inventory size cannot be negative");
        }
        this.inventorySize = inventorySize;
    }

    /**
     * Gets the ID of the sector where the player is currently located.
     *
     * @return The current sector ID, or null if not in any sector
     */
    public String getCurrentSector() {
        return currentSector;
    }

    /**
     * Sets the player's current sector location.
     *
     * @param currentSector The new sector ID
     */
    public void setCurrentSector(String currentSector) {
        this.currentSector = currentSector;
    }

    /**
     * Gets the name of the space object where the player is currently located.
     *
     * @return The current space object name, or null if not at any location
     */
    public String getCurrentPlanet() {
        return currentPlanet;
    }

    /**
     * Sets the player's current space object location.
     *
     * @param currentPlanet The new space object name
     */
    public void setCurrentPlanet(String currentPlanet) {
        this.currentPlanet = currentPlanet;
    }

    /**
     * Gets a copy of the player's inventory.
     *
     * @return A new ArrayList containing all items in the inventory
     */
    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    /**
     * Sets the player's inventory contents.
     *
     * @param inventory The new inventory list
     * @throws NullPointerException if inventory is null
     */
    public void setInventory(List<Item> inventory) {
        this.inventory = new ArrayList<>(Objects.requireNonNull(inventory, "Inventory cannot be null"));
    }

    /**
     * Adds an item to the player's inventory.
     *
     * @param item The item to add
     * @throws NullPointerException if item is null
     * @throws IllegalStateException if the inventory is full
     */
    public void addItem(Item item) {
        Objects.requireNonNull(item, "Item cannot be null");
        if (inventory.size() >= inventorySize) {
            logger.warn("Cannot add item '{}': Inventory is full", item.getName());
            throw new IllegalStateException("Inventory is full");
        }
        inventory.add(item);
        logger.debug("Added item '{}' to player '{}'s inventory", item.getName(), username);
    }

    /**
     * Removes an item from the player's inventory.
     *
     * @param item The item to remove
     * @throws NullPointerException if item is null
     */
    public void removeItem(Item item) {
        Objects.requireNonNull(item, "Item cannot be null");
        if (inventory.remove(item)) {
            logger.debug("Removed item '{}' from player '{}'s inventory", item.getName(), username);
        } else {
            logger.warn("Item '{}' not found in player '{}'s inventory", item.getName(), username);
        }
    }

    /**
     * Checks if this Player is equal to another object.
     * Players are considered equal if they have the same ID.
     *
     * @param o The object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    /**
     * Generates a hash code for this Player.
     *
     * @return The hash code value for this player
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}