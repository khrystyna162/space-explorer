package main.java.com.spaceexplorer.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.com.spaceexplorer.model.GameMap;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class GameRepository implements Repository<GameMap> {
    private final ObjectMapper mapper;
    private final String filePath;

    public GameRepository(String filePath) {
        this.mapper = new ObjectMapper();
        this.filePath = filePath;
    }

    @Override
    public void save(GameMap entity) {
        try {
            mapper.writeValue(new File(filePath), entity);
        } catch (Exception e) {
            System.err.println("Error saving: " + e.toString());
        }
    }

    @Override
    public Optional<GameMap> findById(String id) {
        try {
            return Optional.ofNullable(mapper.readValue(new File(filePath), GameMap.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<GameMap> findAll() {
        return List.of(findById("").orElse(new GameMap()));
    }

    @Override
    public void delete(String id) {
        File file = new File(filePath);
        file.delete();
    }

    @Override
    public void update(GameMap entity) {
        save(entity);
    }
}