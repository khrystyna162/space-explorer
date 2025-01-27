package test.java.com.spaceexplorer;

import main.java.com.spaceexplorer.model.*;
import main.java.com.spaceexplorer.repository.*;
import main.java.com.spaceexplorer.service.auth.AuthService;
import main.java.com.spaceexplorer.ui.ConsoleUI;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.*;
import static test.java.com.spaceexplorer.TestConstants.*;

public class ConsoleUITest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private ItemRepository itemRepository;
    private AuthService authService;
    private ByteArrayOutputStream outputStream;

    @Before
    public void setup() throws Exception {
        gameRepository = new GameRepository(TEST_GAME_MAP);
        playerRepository = new PlayerRepository(TEST_PLAYERS);
        itemRepository = new ItemRepository(TEST_ITEMS);
        authService = new AuthService(playerRepository);

        // Setup test game map
        GameMap gameMap = new GameMap();
        Sector sector = new Sector();
        sector.setId("test_sector");
        sector.setName("Test Sector");
        SpaceObject planet = new SpaceObject();
        planet.setName("Test Planet");
        planet.setType(ObjectType.PLANET);
        planet.setResources(Arrays.asList(ResourceType.IRON, ResourceType.WATER));
        sector.setObjects(Arrays.asList(planet));
        gameMap.setSectors(Arrays.asList(sector));
        gameRepository.save(gameMap);

        // Capture console output
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testLoginAndLogout() {
        // Simulate user input for registration and login
        String input = "2\ntestuser\npassword\n1\ntestuser\npassword\n6\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ConsoleUI ui = new ConsoleUI(authService, gameRepository, itemRepository);
        ui.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Registration successful"));
        assertTrue(output.contains("Welcome, testuser!"));
    }

    @Test
    public void testInvalidLogin() {
        String input = "1\nnonexistent\nwrong\n3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ConsoleUI ui = new ConsoleUI(authService, gameRepository, itemRepository);
        ui.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Login failed"));
    }
}
