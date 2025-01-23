package main.java.com.spaceexplorer.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.com.spaceexplorer.model.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerRepository implements Repository<Player> {
    private final ObjectMapper mapper;
    private final String filePath;
    private List<Player> players;

    public PlayerRepository(String filePath) {
        this.mapper = new ObjectMapper();
        this.filePath = filePath;
        this.loadPlayers();
    }

    private void loadPlayers() {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                players = mapper.readValue(file, new TypeReference<List<Player>>() {});
            } else {
                players = new ArrayList<>();
            }
        } catch (Exception e) {
            System.err.println("Error loading players: " + e.toString());
            players = new ArrayList<>();
        }
    }

    private void savePlayers() {
        try {
            mapper.writeValue(new File(filePath), players);
        } catch (Exception e) {
            System.err.println("Error saving players: " + e.toString());
        }
    }

    @Override
    public void save(Player player) {
        player.setId(UUID.randomUUID().toString());
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
    public void update(Player player) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId().equals(player.getId())) {
                players.set(i, player);
                savePlayers();
                return;
            }
        }
    }

    public Optional<Player> findByUsername(String username) {
        return players.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst();
    }
}