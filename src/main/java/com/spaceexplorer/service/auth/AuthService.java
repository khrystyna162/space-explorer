package main.java.com.spaceexplorer.service.auth;

import main.java.com.spaceexplorer.model.Player;
import main.java.com.spaceexplorer.repository.PlayerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final PlayerRepository playerRepository;

    public AuthService(PlayerRepository playerRepository) {
        this.playerRepository = Objects.requireNonNull(playerRepository, "PlayerRepository cannot be null");
    }

    public Player register(String username, String password) {
        validateCredentials(username, password);

        if (playerRepository.findByUsername(username).isPresent()) {
            logger.warn("Registration failed: Username '{}' already exists", username);
            throw new IllegalStateException("Username already exists");
        }

        try {
            String hashedPassword = PasswordHasher.hashPassword(password);
            Player player = new Player(username, hashedPassword);
            playerRepository.save(player);
            logger.info("Successfully registered new player: {}", username);
            return player;
        } catch (Exception e) {
            logger.error("Registration failed for username '{}': {}", username, e.getMessage());
            throw new RuntimeException("Registration failed", e);
        }
    }

    public Player login(String username, String password) {
        return playerRepository.findByUsername(username)
                .filter(p -> PasswordHasher.verifyPassword(password, p.getPassword()))
                .orElseThrow(() -> {
                    logger.warn("Login failed: Invalid credentials for username '{}'", username);
                    return new IllegalStateException("Invalid username or password");
                });
    }

    private void validateCredentials(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
    }
}

