package main.java.com.spaceexplorer.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.com.spaceexplorer.model.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for managing player data.
 * Provides CRUD operations for players and maintains player persistence
 * using a JSON file. Each player is assigned a unique UUID.
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public class PlayerRepository extends BaseRepository<Player> implements Repository<Player, String> {
    /** In-memory cache of players */
    private List<Player> players;

    /**
     * Creates a new PlayerRepository with the specified file path.
     *
     * @param filePath Path to the JSON file storing player data
     */
    public PlayerRepository(String filePath) {
        super(filePath);
        loadPlayers();
    }

    /**
     * Loads players from the JSON file.
     * Initializes an empty list if the file doesn't exist or is invalid.
     */
    private void loadPlayers() {
        try {
            players = readFromFile(new TypeReference<List<Player>>() {});
            if (players == null) {
                players = new ArrayList<>();
            }
        } catch (Exception e) {
            logger.error("Error loading players: {}", e.getMessage());
            players = new ArrayList<>();
        }
    }

    /**
     * Persists the current list of players to the JSON file.
     */
    private void savePlayers() {
        writeToFile(players);
    }

    /**
     * Saves a new player to the repository.
     * Generates a new UUID if the player doesn't have one.
     *
     * @param player The player to save
     * @throws IllegalArgumentException if the player is invalid
     */
    @Override
    public void save(Player player) {
        validatePlayer(player);
        if (player.getId() == null) {
            player.setId(UUID.randomUUID().toString());
        }
        players.add(player);
        savePlayers();
    }

    /**
     * Finds a player by their ID.
     *
     * @param id The ID of the player to find
     * @return Optional containing the player if found
     */
    @Override
    public Optional<Player> findById(String id) {
        return players.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    /**
     * Returns a copy of all players in the repository.
     *
     * @return List of all players
     */
    @Override
    public List<Player> findAll() {
        return new ArrayList<>(players);
    }

    /**
     * Deletes a player by their ID.
     *
     * @param id The ID of the player to delete
     */
    @Override
    public void delete(String id) {
        players.removeIf(p -> p.getId().equals(id));
        savePlayers();
    }

    /**
     * Updates an existing player.
     *
     * @param id The ID of the player to update
     * @param player The new player data
     * @throws IllegalArgumentException if the player is not found or invalid
     */
    @Override
    public void update(String id, Player player) {
        validatePlayer(player);
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId().equals(id)) {
                players.set(i, player);
                savePlayers();
                return;
            }
        }
        throw new IllegalArgumentException("Player not found: " + id);
    }

    /**
     * Checks if a player exists by their ID.
     *
     * @param id The ID of the player to check
     * @return true if the player exists, false otherwise
     */
    @Override
    public boolean exists(String id) {
        return players.stream().anyMatch(p -> p.getId().equals(id));
    }

    /**
     * Finds a player by their username.
     *
     * @param username The username to search for
     * @return Optional containing the player if found
     */
    public Optional<Player> findByUsername(String username) {
        return players.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst();
    }

    /**
     * Validates a player's data.
     *
     * @param player The player to validate
     * @throws IllegalArgumentException if the player is invalid
     */
    private void validatePlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (player.getUsername() == null || player.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (player.getPassword() == null || player.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }
}