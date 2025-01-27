package main.java.com.spaceexplorer.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.com.spaceexplorer.model.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerRepository extends BaseRepository<Player> implements Repository<Player, String> {
    private List<Player> players;

    public PlayerRepository(String filePath) {
        super(filePath);
        loadPlayers();
    }

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

    private void savePlayers() {
        writeToFile(players);
    }

    @Override
    public void save(Player player) {
        validatePlayer(player);
        if (player.getId() == null) {
            player.setId(UUID.randomUUID().toString());
        }
        players.add(player);
        savePlayers();
    }

    @Override
    public Optional<Player> findById(String id) {
        return players.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Player> findAll() {
        return new ArrayList<>(players);
    }

    @Override
    public void delete(String id) {
        players.removeIf(p -> p.getId().equals(id));
        savePlayers();
    }

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

    @Override
    public boolean exists(String id) {
        return players.stream().anyMatch(p -> p.getId().equals(id));
    }

    public Optional<Player> findByUsername(String username) {
        return players.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst();
    }

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