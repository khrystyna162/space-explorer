package main.java.com.spaceexplorer.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private static final Logger logger = LoggerFactory.getLogger(Player.class);
    private static final int DEFAULT_HEALTH = 100;
    private static final int DEFAULT_INVENTORY_SIZE = 10;

    private String id;
    private String username;
    private String password;
    private int health;
    private int inventorySize;
    private String currentSector;
    private String currentPlanet;
    private List<Item> inventory;

    public Player() {
        this.health = DEFAULT_HEALTH;
        this.inventorySize = DEFAULT_INVENTORY_SIZE;
        this.inventory = new ArrayList<>();
    }

    public Player(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    // Getters and Setters with validation
    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Player ID cannot be empty");
        }
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.password = password;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health < 0 || health > 100) {
            throw new IllegalArgumentException("Health must be between 0 and 100");
        }
        this.health = health;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public void setInventorySize(int inventorySize) {
        if (inventorySize < 0) {
            throw new IllegalArgumentException("Inventory size cannot be negative");
        }
        this.inventorySize = inventorySize;
    }

    public String getCurrentSector() {
        return currentSector;
    }

    public void setCurrentSector(String currentSector) {
        this.currentSector = currentSector;
    }

    public String getCurrentPlanet() {
        return currentPlanet;
    }

    public void setCurrentPlanet(String currentPlanet) {
        this.currentPlanet = currentPlanet;
    }

    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = new ArrayList<>(Objects.requireNonNull(inventory, "Inventory cannot be null"));
    }

    public void addItem(Item item) {
        Objects.requireNonNull(item, "Item cannot be null");
        if (inventory.size() >= inventorySize) {
            logger.warn("Cannot add item '{}': Inventory is full", item.getName());
            throw new IllegalStateException("Inventory is full");
        }
        inventory.add(item);
        logger.debug("Added item '{}' to player '{}'s inventory", item.getName(), username);
    }

    public void removeItem(Item item) {
        Objects.requireNonNull(item, "Item cannot be null");
        if (inventory.remove(item)) {
            logger.debug("Removed item '{}' from player '{}'s inventory", item.getName(), username);
        } else {
            logger.warn("Item '{}' not found in player '{}'s inventory", item.getName(), username);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
