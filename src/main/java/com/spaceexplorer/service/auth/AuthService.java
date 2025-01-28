package main.java.com.spaceexplorer.service.auth;

import main.java.com.spaceexplorer.model.Player;
import main.java.com.spaceexplorer.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Objects;

/**
 * Service class handling player authentication and registration.
 * Provides methods for secure player registration and login verification.
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    /** Repository for managing player data */
    private final PlayerRepository playerRepository;

    /**
     * Creates a new AuthService with the specified player repository.
     *
     * @param playerRepository The repository to use for player data management
     * @throws NullPointerException if playerRepository is null
     */
    public AuthService(PlayerRepository playerRepository) {
        this.playerRepository = Objects.requireNonNull(playerRepository, "PlayerRepository cannot be null");
    }

    /**
     * Registers a new player with the specified username and password.
     * The password will be hashed before storage.
     *
     * @param username The desired username
     * @param password The player's password
     * @return The newly created Player object
     * @throws IllegalArgumentException if username or password is invalid
     * @throws IllegalStateException if username already exists
     * @throws RuntimeException if registration fails
     */
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

    /**
     * Authenticates a player with the specified username and password.
     *
     * @param username The player's username
     * @param password The player's password
     * @return The authenticated Player object
     * @throws IllegalStateException if credentials are invalid
     */
    public Player login(String username, String password) {
        return playerRepository.findByUsername(username)
                .filter(p -> PasswordHasher.verifyPassword(password, p.getPassword()))
                .orElseThrow(() -> {
                    logger.warn("Login failed: Invalid credentials for username '{}'", username);
                    return new IllegalStateException("Invalid username or password");
                });
    }

    /**
     * Validates the provided username and password.
     *
     * @param username The username to validate
     * @param password The password to validate
     * @throws IllegalArgumentException if username is null or empty
     * @throws IllegalArgumentException if password is null, empty, or too short
     */
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