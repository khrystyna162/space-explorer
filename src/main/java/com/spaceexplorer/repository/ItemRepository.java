package main.java.com.spaceexplorer.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.com.spaceexplorer.model.Item;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ItemRepository extends BaseRepository<Item> implements Repository<Item, String> {
    private List<Item> items;

    public ItemRepository(String filePath) {
        super(filePath);
        loadItems();
    }

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

    private void saveItems() {
        writeToFile(items);
    }

    @Override
    public void save(Item item) {
        validateItem(item);
        items.add(item);
        saveItems();
    }

    @Override
    public Optional<Item> findById(String id) {
        return items.stream()
                .filter(item -> item.getName().equals(id))
                .findFirst();
    }

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(items);
    }

    @Override
    public void delete(String id) {
        items.removeIf(item -> item.getName().equals(id));
        saveItems();
    }

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

    @Override
    public boolean exists(String id) {
        return items.stream().anyMatch(item -> item.getName().equals(id));
    }

    public List<Item> findByType(String type) {
        return items.stream()
                .filter(item -> item.getType().equals(type))
                .collect(Collectors.toList());
    }

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