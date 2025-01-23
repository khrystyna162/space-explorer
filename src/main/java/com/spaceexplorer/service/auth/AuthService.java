package main.java.com.spaceexplorer.service.auth;

import main.java.com.spaceexplorer.model.Player;
import main.java.com.spaceexplorer.repository.PlayerRepository;

public class AuthService {
    private final PlayerRepository playerRepository;

    public AuthService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player register(String username, String password) {
        if (playerRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Player player = new Player(username, PasswordHasher.hashPassword(password));
        playerRepository.save(player);
        return player;
    }

    public Player login(String username, String password) {
        return playerRepository.findByUsername(username)
                .filter(p -> p.getPassword().equals(PasswordHasher.hashPassword(password)))
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
    }
}

