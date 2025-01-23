package main.java.com.spaceexplorer;

import main.java.com.spaceexplorer.model.*;
import main.java.com.spaceexplorer.repository.PlayerRepository;
import main.java.com.spaceexplorer.repository.UnitOfWork;
import main.java.com.spaceexplorer.service.auth.AuthService;
import main.java.com.spaceexplorer.util.JsonReader;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        PlayerRepository playerRepository = new PlayerRepository("src/main/resources/players.json");
        AuthService authService = new AuthService(playerRepository);

        try {
            // Тест реєстрації
            authService.register("test_user", "password123");
            System.out.println("Registration successful");

            // Тест входу
            var player = authService.login("test_user", "password123");
            System.out.println("Login successful for: " + player.getUsername());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
