package main.java.com.spaceexplorer.repository;

import main.java.com.spaceexplorer.model.GameMap;
import java.io.File;
import java.util.List;
import java.util.Optional;
public class GameRepository extends BaseRepository<GameMap> implements Repository<GameMap, String> {

    public GameRepository(String filePath) {
        super(filePath);
    }

    @Override
    public void save(GameMap map) {
        validateGameMap(map);
        writeToFile(map);
    }

    @Override
    public Optional<GameMap> findById(String id) {
        GameMap map = readFromFile(GameMap.class);
        return Optional.ofNullable(map);
    }

    @Override
    public List<GameMap> findAll() {
        GameMap map = readFromFile(GameMap.class);
        return map != null ? List.of(map) : List.of();
    }

    @Override
    public void delete(String id) {
        new File(filePath).delete();
        logger.info("Game map deleted");
    }

    @Override
    public void update(String id, GameMap map) {
        validateGameMap(map);
        writeToFile(map);
    }

    @Override
    public boolean exists(String id) {
        return new File(filePath).exists();
    }

    private void validateGameMap(GameMap map) {
        if (map == null) {
            throw new IllegalArgumentException("Game map cannot be null");
        }
        if (map.getSectors() == null || map.getSectors().isEmpty()) {
            throw new IllegalArgumentException("Game map must have at least one sector");
        }
    }
}