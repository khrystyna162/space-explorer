package main.java.com.spaceexplorer.ui;

import main.java.com.spaceexplorer.model.*;
import main.java.com.spaceexplorer.repository.GameRepository;
import main.java.com.spaceexplorer.repository.ItemRepository;
import main.java.com.spaceexplorer.service.auth.AuthService;

import java.util.Scanner;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleUI {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleUI.class);
    private final Scanner scanner;
    private final AuthService authService;
    private final GameRepository gameRepository;
    private final ItemRepository itemRepository;
    private boolean running;

    public ConsoleUI(AuthService authService, GameRepository gameRepository, ItemRepository itemRepository) {
        this.scanner = new Scanner(System.in);
        this.authService = authService;
        this.gameRepository = gameRepository;
        this.itemRepository = itemRepository;
    }

    public void start() {
        running = true;
        showWelcomeMessage();

        while (running) {
            try {
                showMainMenu();
                processMainMenuChoice();
            } catch (Exception e) {
                logger.error("Error in main menu: {}", e.getMessage());
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private void showWelcomeMessage() {
        System.out.println("Welcome to Space Explorer!");
        System.out.println("Embark on an adventure through the solar system.");
        System.out.println("Collect resources, explore planets, and survive in space.\n");
    }

    private void showMainMenu() {
        System.out.println("\n=== Space Explorer ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choice: ");
    }

    private void processMainMenuChoice() {
        String choice = safeReadLine();
        switch (choice) {
            case "1" -> handleLogin();
            case "2" -> handleRegistration();
            case "3" -> {
                running = false;
                System.out.println("Thank you for playing Space Explorer!");
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void handleLogin() {
        System.out.print("Username: ");
        String username = safeReadLine();
        System.out.print("Password: ");
        String password = safeReadLine();

        try {
            var player = authService.login(username, password);
            logger.info("Player {} logged in successfully", username);
            System.out.println("Welcome back, " + player.getUsername() + "!");
            showGameMenu(player);
        } catch (Exception e) {
            logger.warn("Login failed for user {}: {}", username, e.getMessage());
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private void handleRegistration() {
        System.out.print("Username: ");
        String username = safeReadLine();
        System.out.print("Password: ");
        String password = safeReadLine();

        try {
            authService.register(username, password);
            logger.info("New player registered: {}", username);
            System.out.println("Registration successful! Please login to play.");
        } catch (Exception e) {
            logger.warn("Registration failed for user {}: {}", username, e.getMessage());
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private void showGameMenu(Player player) {
        boolean inGame = true;
        while (inGame && running) {
            try {
                System.out.println("\n=== Game Menu ===");
                System.out.println("1. Explore sector");
                System.out.println("2. View inventory");
                System.out.println("3. Player status");
                System.out.println("4. Collect resources");
                System.out.println("5. Drop item");
                System.out.println("6. Logout");
                System.out.print("Choice: ");

                String choice = safeReadLine();
                switch (choice) {
                    case "1" -> exploreSector(player);
                    case "2" -> viewInventory(player);
                    case "3" -> showPlayerStatus(player);
                    case "4" -> collectResources(player);
                    case "5" -> dropItem(player);
                    case "6" -> {
                        inGame = false;
                        logger.info("Player {} logged out", player.getUsername());
                    }
                    default -> System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                logger.error("Error in game menu for player {}: {}", player.getUsername(), e.getMessage());
                System.out.println("An error occurred. Please try again.");
            }
        }
    }

    private void exploreSector(Player player) {
        var gameMap = gameRepository.findById("").orElse(null);
        if (gameMap == null || gameMap.getSectors().isEmpty()) {
            System.out.println("No sectors available for exploration");
            return;
        }

        System.out.println("\n=== Exploring Solar System ===");
        List<Sector> sectors = gameMap.getSectors();

        System.out.println("Available sectors:");
        for (int i = 0; i < sectors.size(); i++) {
            System.out.printf("%d: %s%n", i + 1, sectors.get(i).getName());
        }

        Sector selectedSector = selectFromList("Choose sector", sectors);
        if (selectedSector == null) return;

        System.out.println("\nAvailable objects in " + selectedSector.getName() + ":");
        List<SpaceObject> objects = selectedSector.getObjects();
        for (int i = 0; i < objects.size(); i++) {
            System.out.printf("%d: %s (%s)%n", i + 1, objects.get(i).getName(),
                    objects.get(i).getType().getDescription());
        }

        SpaceObject selectedObject = selectFromList("Choose destination", objects);
        if (selectedObject == null) return;

        player.setCurrentSector(selectedSector.getId());
        player.setCurrentPlanet(selectedObject.getName());

        System.out.println("\nExploring " + selectedObject.getName());
        System.out.println("Type: " + selectedObject.getType().getDescription());
        System.out.println("Description: " + selectedObject.getDescription());

        System.out.println("\nAvailable resources:");
        selectedObject.getResources().forEach(resource ->
                System.out.println("- " + resource.getDescription())
        );

        System.out.println("\nInteresting facts:");
        selectedObject.getFacts().forEach(fact ->
                System.out.println("- " + fact)
        );

        logger.info("Player {} explored {} in sector {}",
                player.getUsername(), selectedObject.getName(), selectedSector.getName());
    }

    private void viewInventory(Player player) {
        System.out.println("\n=== Inventory ===");
        System.out.printf("Capacity: %d/%d%n",
                player.getInventory().size(), player.getInventorySize());

        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty");
            return;
        }

        System.out.println("\nItems:");
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, item.getName(), item.getType());
            if (item.getDescription() != null) {
                System.out.println("   Description: " + item.getDescription());
            }
        }
    }

    private void collectResources(Player player) {
        if (player.getCurrentSector() == null || player.getCurrentPlanet() == null) {
            System.out.println("You must be at a location to collect resources");
            return;
        }

        var gameMap = gameRepository.findById("").orElse(null);
        if (gameMap == null) {
            System.out.println("Error accessing game data");
            return;
        }

        SpaceObject currentLocation = findCurrentLocation(gameMap, player);
        if (currentLocation == null) {
            System.out.println("Current location not found");
            return;
        }

        List<ResourceType> availableResources = currentLocation.getResources();
        System.out.println("\n=== Available Resources on " + currentLocation.getName() + " ===");
        for (int i = 0; i < availableResources.size(); i++) {
            System.out.printf("%d. %s (%s)%n", i + 1,
                    availableResources.get(i).name(),
                    availableResources.get(i).getDescription());
        }

        ResourceType selectedResource = selectFromList("Choose resource to collect", availableResources);
        if (selectedResource == null) return;

        try {
            if (player.getInventory().size() >= player.getInventorySize()) {
                System.out.println("Inventory is full!");
                return;
            }

            Item item = new Item(selectedResource.name(), "RESOURCE");
            item.setDescription(selectedResource.getDescription());
            player.addItem(item);
            System.out.println("Collected: " + selectedResource.getDescription());

            logger.info("Player {} collected {} from {}",
                    player.getUsername(), selectedResource, currentLocation.getName());
        } catch (Exception e) {
            logger.error("Error collecting resource for player {}: {}",
                    player.getUsername(), e.getMessage());
            System.out.println("Failed to collect resource: " + e.getMessage());
        }
    }

    private void dropItem(Player player) {
        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty");
            return;
        }

        System.out.println("\n=== Drop Item ===");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.printf("%d. %s (%s)%n", i + 1,
                    inventory.get(i).getName(),
                    inventory.get(i).getType());
        }

        Item selectedItem = selectFromList("Choose item to drop", inventory);
        if (selectedItem == null) return;

        try {
            player.removeItem(selectedItem);
            System.out.println("Dropped: " + selectedItem.getName());
            logger.info("Player {} dropped {}", player.getUsername(), selectedItem.getName());
        } catch (Exception e) {
            logger.error("Error dropping item for player {}: {}",
                    player.getUsername(), e.getMessage());
            System.out.println("Failed to drop item: " + e.getMessage());
        }
    }

    private void showPlayerStatus(Player player) {
        System.out.println("\n=== Player Status ===");
        System.out.println("Username: " + player.getUsername());
        System.out.println("Health: " + player.getHealth() + "%");
        System.out.println("Current sector: " +
                (player.getCurrentSector() != null ? player.getCurrentSector() : "Not in any sector"));
        System.out.println("Current location: " +
                (player.getCurrentPlanet() != null ? player.getCurrentPlanet() : "Not at any location"));
        System.out.printf("Inventory: %d/%d items%n",
                player.getInventory().size(), player.getInventorySize());
    }

    private SpaceObject findCurrentLocation(GameMap gameMap, Player player) {
        return gameMap.getSectors().stream()
                .filter(s -> s.getId().equals(player.getCurrentSector()))
                .flatMap(s -> s.getObjects().stream())
                .filter(o -> o.getName().equals(player.getCurrentPlanet()))
                .findFirst()
                .orElse(null);
    }

    private <T> T selectFromList(String prompt, List<T> items) {
        if (items.isEmpty()) {
            System.out.println("No items available");
            return null;
        }

        System.out.print(prompt + " (1-" + items.size() + "): ");
        try {
            int choice = Integer.parseInt(safeReadLine()) - 1;
            if (choice >= 0 && choice < items.size()) {
                return items.get(choice);
            } else {
                System.out.println("Invalid choice");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            return null;
        }
    }

    private String safeReadLine() {
        try {
            return scanner.nextLine().trim();
        } catch (Exception e) {
            logger.error("Error reading input: {}", e.getMessage());
            return "";
        }
    }
}
