package main.java.com.spaceexplorer.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.com.spaceexplorer.model.Item;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Repository for managing game items.
 * Provides CRUD operations for items and maintains a list of default items
 * that are available in the game. Items are persisted in a JSON file.
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public class ItemRepository extends BaseRepository<Item> implements Repository<Item, String> {
    /** In-memory cache of items */
    private List<Item> items;

    /**
     * Creates a new ItemRepository with the specified file path.
     *
     * @param filePath Path to the JSON file storing item data
     */
    public ItemRepository(String filePath) {
        super(filePath);
        loadItems();
    }

    /**
     * Loads items from the JSON file. If the file doesn't exist or is invalid,
     * initializes the repository with default items.
     */
    private void loadItems() {
        try {
            items = readFromFile(new TypeReference<List<Item>>() {});
            if (items == null) {
                items = new ArrayList<>();
                initializeDefaultItems();
            }
        } catch (Exception e) {
            logger.error("Error loading items: {}", e.getMessage());
            items = new ArrayList<>();
            initializeDefaultItems();
        }
    }

    /**
     * Initializes the repository with a set of default items.
     * This includes basic resources, artifacts, and tools.
     */
    private void initializeDefaultItems() {
        items.addAll(Arrays.asList(
                new Item("Water", "RESOURCE"),
                new Item("Minerals", "RESOURCE"),
                new Item("Fuel", "RESOURCE"),
                new Item("Ancient Artifact", "ARTIFACT"),
                new Item("Space Map", "TOOL")
        ));
        saveItems();
    }

    /**
     * Persists the current list of items to the JSON file.
     */
    private void saveItems() {
        writeToFile(items);
    }

    /**
     * Saves a new item to the repository.
     *
     * @param item The item to save
     * @throws IllegalArgumentException if the item is invalid
     */
    @Override
    public void save(Item item) {
        validateItem(item);
        items.add(item);
        saveItems();
    }

    /**
     * Finds an item by its name.
     *
     * @param id The name of the item to find
     * @return Optional containing the item if found
     */
    @Override
    public Optional<Item> findById(String id) {
        return items.stream()
                .filter(item -> item.getName().equals(id))
                .findFirst();
    }

    /**
     * Returns a copy of all items in the repository.
     *
     * @return List of all items
     */
    @Override
    public List<Item> findAll() {
        return new ArrayList<>(items);
    }

    /**
     * Deletes an item by its name.
     *
     * @param id The name of the item to delete
     */
    @Override
    public void delete(String id) {
        items.removeIf(item -> item.getName().equals(id));
        saveItems();
    }

    /**
     * Updates an existing item.
     *
     * @param id The name of the item to update
     * @param item The new item data
     * @throws IllegalArgumentException if the item is not found or invalid
     */
    @Override
    public void update(String id, Item item) {
        validateItem(item);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(id)) {
                items.set(i, item);
                saveItems();
                return;
            }
        }
        throw new IllegalArgumentException("Item not found: " + id);
    }

    /**
     * Checks if an item exists by its name.
     *
     * @param id The name of the item to check
     * @return true if the item exists, false otherwise
     */
    @Override
    public boolean exists(String id) {
        return items.stream().anyMatch(item -> item.getName().equals(id));
    }

    /**
     * Finds all items of a specific type.
     *
     * @param type The type of items to find
     * @return List of items matching the specified type
     */
    public List<Item> findByType(String type) {
        return items.stream()
                .filter(item -> item.getType().equals(type))
                .collect(Collectors.toList());
    }

    /**
     * Validates an item's data.
     *
     * @param item The item to validate
     * @throws IllegalArgumentException if the item is invalid
     */
    private void validateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (item.getName() == null || item.getName().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty");
        }
        if (item.getType() == null || item.getType().isEmpty()) {
            throw new IllegalArgumentException("Item type cannot be empty");
        }
    }
}