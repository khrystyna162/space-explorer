package test.java.com.spaceexplorer;

import main.java.com.spaceexplorer.model.*;
import main.java.com.spaceexplorer.repository.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static test.java.com.spaceexplorer.TestConstants.*;

public class RepositoryTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private GameRepository gameRepository;
    private ItemRepository itemRepository;
    private PlayerRepository playerRepository;

    @Before
    public void setup() {
        gameRepository = new GameRepository(TEST_GAME_MAP);
        itemRepository = new ItemRepository(TEST_ITEMS);
        playerRepository = new PlayerRepository(TEST_PLAYERS);
    }

    @Test
    public void testGameMapCRUD() {
        // Create
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
        assertTrue(gameRepository.exists(""));

        // Read
        Optional<GameMap> loaded = gameRepository.findById("");
        assertTrue(loaded.isPresent());
        assertEquals("Test Sector", loaded.get().getSectors().get(0).getName());

        // Update
        sector.setName("Updated Sector");
        gameMap.setSectors(Arrays.asList(sector));
        gameRepository.update("", gameMap);

        loaded = gameRepository.findById("");
        assertTrue(loaded.isPresent());
        assertEquals("Updated Sector", loaded.get().getSectors().get(0).getName());

        // Delete
        gameRepository.delete("");
        assertFalse(gameRepository.exists(""));
    }

    @Test
    public void testItemCRUD() {
        // Create
        Item item = new Item("Test Item", "RESOURCE");
        itemRepository.save(item);
        assertTrue(itemRepository.exists("Test Item"));

        // Read
        Optional<Item> loaded = itemRepository.findById("Test Item");
        assertTrue(loaded.isPresent());
        assertEquals("RESOURCE", loaded.get().getType());

        // Update
        item.setType("ARTIFACT");
        itemRepository.update("Test Item", item);

        loaded = itemRepository.findById("Test Item");
        assertTrue(loaded.isPresent());
        assertEquals("ARTIFACT", loaded.get().getType());

        // Delete
        itemRepository.delete("Test Item");
        assertFalse(itemRepository.exists("Test Item"));
    }

    @Test
    public void testPlayerCRUD() {
        // Create
        Player player = new Player("testuser", "password");
        playerRepository.save(player);
        String playerId = player.getId();
        assertTrue(playerRepository.exists(playerId));

        // Read
        Optional<Player> loaded = playerRepository.findById(playerId);
        assertTrue(loaded.isPresent());
        assertEquals("testuser", loaded.get().getUsername());

        // Update
        player.setHealth(90);
        playerRepository.update(playerId, player);

        loaded = playerRepository.findById(playerId);
        assertTrue(loaded.isPresent());
        assertEquals(90, loaded.get().getHealth());

        // Delete
        playerRepository.delete(playerId);
        assertFalse(playerRepository.exists(playerId));
    }

    @Test
    public void testPlayerInventoryOperations() {
        Player player = new Player("testuser", "password");
        playerRepository.save(player);
        String playerId = player.getId();

        // Add items to inventory
        Item item1 = new Item("Water", "RESOURCE");
        Item item2 = new Item("Fuel", "RESOURCE");

        player.addItem(item1);
        player.addItem(item2);
        playerRepository.update(playerId, player);

        Optional<Player> loaded = playerRepository.findById(playerId);
        assertTrue(loaded.isPresent());
        assertEquals(2, loaded.get().getInventory().size());

        // Remove item
        player.removeItem(item1);
        playerRepository.update(playerId, player);

        loaded = playerRepository.findById(playerId);
        assertTrue(loaded.isPresent());
        assertEquals(1, loaded.get().getInventory().size());
    }

    @Test
    public void testItemFiltering() {
        itemRepository.save(new Item("Water", "RESOURCE"));
        itemRepository.save(new Item("Map", "TOOL"));
        itemRepository.save(new Item("Minerals", "RESOURCE"));

        List<Item> resources = itemRepository.findByType("RESOURCE");
        assertEquals(6, resources.size()); // 4 def + 2 постійно наростає
        assertTrue(resources.stream().allMatch(item -> item.getType().equals("RESOURCE")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidGameMapValidation() {
        gameRepository.save(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidItemValidation() {
        itemRepository.save(new Item(null, ""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPlayerValidation() {
        playerRepository.save(new Player(null, null));
    }
}
