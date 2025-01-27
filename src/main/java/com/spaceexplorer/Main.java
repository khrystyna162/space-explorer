package main.java.com.spaceexplorer;

import main.java.com.spaceexplorer.repository.GameRepository;
import main.java.com.spaceexplorer.repository.ItemRepository;
import main.java.com.spaceexplorer.repository.PlayerRepository;
import main.java.com.spaceexplorer.service.auth.AuthService;
import main.java.com.spaceexplorer.ui.ConsoleUI;
import main.java.com.spaceexplorer.config.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        setupEncoding();

        try {
            var gameMap = "src/main/resources/data/game_map.json";//ConfigLoader.getPath("gameMap");
            var players = "src/main/resources/data/players.json";//ConfigLoader.getPath("players");
            var items = "src/main/resources/data/items.json";//ConfigLoader.getPath("items");
            GameRepository gameRepository = new GameRepository(gameMap);
            PlayerRepository playerRepository = new PlayerRepository(players);
            ItemRepository itemRepository = new ItemRepository(items);
            AuthService authService = new AuthService(playerRepository);

            ConsoleUI ui = new ConsoleUI(authService, gameRepository, itemRepository);
            ui.start();
        } catch (Exception e) {
            logger.error("Application error: {}", e.getMessage(), e);
            System.exit(1);
        }
    }

    private static void setupEncoding() {
        System.setProperty("file.encoding", "UTF-8");
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "chcp", "65001").inheritIO().start().waitFor();
            } catch (Exception e) {
                logger.error("Error setting console encoding: {}", e.getMessage());
            }
        }
    }
}