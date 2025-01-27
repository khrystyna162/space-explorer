package main.java.com.spaceexplorer.service.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class PasswordHasher {
    private static final Logger logger = LoggerFactory.getLogger(PasswordHasher.class);
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    // Фіксована сіль для спрощення (в реальному проекті краще зберігати сіль для кожного пароля)
    private static final byte[] SALT = "SpaceExplorerSalt123".getBytes();

    public static String hashPassword(String password) {
        try {
            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    SALT,
                    ITERATIONS,
                    KEY_LENGTH
            );
            SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            logger.error("Error hashing password: {}", e.getMessage());
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static boolean verifyPassword(String password, String storedHash) {
        String newHash = hashPassword(password);
        return storedHash.equals(newHash);
    }
}