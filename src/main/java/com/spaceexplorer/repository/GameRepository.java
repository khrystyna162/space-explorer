package main.java.com.spaceexplorer.repository;

import main.java.com.spaceexplorer.model.GameMap;
import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing game map data.
 * Provides CRUD operations for the game map, storing it in a JSON file.
 * Note that this implementation supports only one game map at a time.
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public class GameRepository extends BaseRepository<GameMap> implements Repository<GameMap, String> {

    /**
     * Creates a new GameRepository with the specified file path.
     *
     * @param filePath Path to the JSON file storing the game map data
     */
    public GameRepository(String filePath) {
        super(filePath);
    }

    /**
     * Saves a game map to the repository.
     *
     * @param map The game map to save
     * @throws IllegalArgumentException if the map is null or invalid
     */
    @Override
    public void save(GameMap map) {
        validateGameMap(map);
        writeToFile(map);
    }

    /**
     * Finds a game map by ID.
     * Note: The ID parameter is not used in the current implementation
     * as only one game map is supported.
     *
     * @param id The ID to look up (not used)
     * @return Optional containing the game map if it exists
     */
    @Override
    public Optional<GameMap> findById(String id) {
        GameMap map = readFromFile(GameMap.class);
        return Optional.ofNullable(map);
    }

    /**
     * Returns all game maps in the repository.
     * Note: In the current implementation, this either returns a singleton list
     * containing the game map or an empty list.
     *
     * @return List containing the game map if it exists, empty list otherwise
     */
    @Override
    public List<GameMap> findAll() {
        GameMap map = readFromFile(GameMap.class);
        return map != null ? List.of(map) : List.of();
    }

    /**
     * Deletes the game map.
     * Note: The ID parameter is not used in the current implementation.
     *
     * @param id The ID of the map to delete (not used)
     */
    @Override
    public void delete(String id) {
        new File(filePath).delete();
        logger.info("Game map deleted");
    }

    /**
     * Updates the game map.
     * Note: The ID parameter is not used in the current implementation.
     *
     * @param id The ID of the map to update (not used)
     * @param map The new game map data
     * @throws IllegalArgumentException if the map is null or invalid
     */
    @Override
    public void update(String id, GameMap map) {
        validateGameMap(map);
        writeToFile(map);
    }

    /**
     * Checks if a game map exists.
     * Note: The ID parameter is not used in the current implementation.
     *
     * @param id The ID to check (not used)
     * @return true if the game map file exists, false otherwise
     */
    @Override
    public boolean exists(String id) {
        return new File(filePath).exists();
    }

    /**
     * Validates a game map object.
     *
     * @param map The game map to validate
     * @throws IllegalArgumentException if the map is null or has no sectors
     */
    private void validateGameMap(GameMap map) {
        if (map == null) {
            throw new IllegalArgumentException("Game map cannot be null");
        }
        if (map.getSectors() == null || map.getSectors().isEmpty()) {
            throw new IllegalArgumentException("Game map must have at least one sector");
        }
    }
}