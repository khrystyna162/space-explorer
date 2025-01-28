package test.java.com.spaceexplorer;

import main.java.com.spaceexplorer.model.Player;
import main.java.com.spaceexplorer.repository.PlayerRepository;
import main.java.com.spaceexplorer.service.auth.AuthService;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.*;

public class AuthenticationTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private PlayerRepository playerRepository;
    private AuthService authService;
    private File testFile;

    @Before
    public void setup() throws Exception {
        // Створюємо тимчасовий файл для кожного тесту
        testFile = tempFolder.newFile("test_players.json");
        playerRepository = new PlayerRepository(testFile.getAbsolutePath());
        authService = new AuthService(playerRepository);
    }

    @After
    public void cleanup() {
        // Видаляємо файл після кожного тесту
        if (testFile != null && testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testRegistration() {
        Player player = authService.register("test_user", "test123");
        assertNotNull(player);
        assertEquals("test_user", player.getUsername());
        assertTrue(playerRepository.exists(player.getId()));
    }

    @Test(expected = IllegalStateException.class) // Змінено з RuntimeException на IllegalStateException
    public void testDuplicateRegistration() {
        authService.register("test_user", "test123");
        authService.register("test_user", "test123");
    }

    @Test
    public void testLogin() {
        Player registered = authService.register("test_user", "test123");
        Player loggedIn = authService.login("test_user", "test123");
        assertEquals(registered.getId(), loggedIn.getId());
    }

    @Test(expected = IllegalStateException.class) // Змінено з RuntimeException на IllegalStateException
    public void testInvalidLogin() {
        authService.login("nonexistent_user", "test123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterWithEmptyUsername() {
        authService.register("", "test123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterWithEmptyPassword() {
        authService.register("test_user", "");
    }
}