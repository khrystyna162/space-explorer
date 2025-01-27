package test.java.com.spaceexplorer;

import main.java.com.spaceexplorer.model.Player;
import main.java.com.spaceexplorer.repository.PlayerRepository;
import main.java.com.spaceexplorer.service.auth.AuthService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static test.java.com.spaceexplorer.TestConstants.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class AuthenticationTest {
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private PlayerRepository playerRepository;
    private AuthService authService;

    @Before
    public void setup() {
        playerRepository = new PlayerRepository(TEST_PLAYERS);
        authService = new AuthService(playerRepository);
    }

    @Test
    public void testRegistration() {
        Player player = authService.register("test_user", "test123");
        assertNotNull(player);
        assertEquals("test_user", player.getUsername());
        assertTrue(playerRepository.exists(player.getId()));
    }

    @Test(expected = RuntimeException.class)
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

    @Test(expected = RuntimeException.class)
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
