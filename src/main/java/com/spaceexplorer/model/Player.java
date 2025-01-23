package main.java.com.spaceexplorer.model;


public class Player {
    private String id;
    private String username;
    private String password;
    private int health;
    private int inventorySize;
    private String currentSector;

    public Player() {
        this.health = 100;
        this.inventorySize = 10;
    }

    public Player(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getInventorySize() {
        return inventorySize;
    }

    public void setInventorySize(int inventorySize) {
        this.inventorySize = inventorySize;
    }

    public String getCurrentSector() {
        return currentSector;
    }

    public void setCurrentSector(String currentSector) {
        this.currentSector = currentSector;
    }
}
