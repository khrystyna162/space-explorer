package main.java.com.spaceexplorer.service.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Utility class for secure password hashing and verification.
 * Uses PBKDF2 (Password-Based Key Derivation Function 2) with HMAC-SHA1
 * for secure password hashing.
 *
 * Note: In a production environment, salt should be unique per password
 * and stored alongside the hash.
 *
 * @author Space Explorer Development Team
 * @version 1.0
 * @since 1.0
 */
public class PasswordHasher {
    private static final Logger logger = LoggerFactory.getLogger(PasswordHasher.class);

    /** The cryptographic algorithm used for password hashing */
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    /** Number of iterations for the hashing algorithm */
    private static final int ITERATIONS = 10000;

    /** Length of the generated hash in bits */
    private static final int KEY_LENGTH = 256;

    /** Fixed salt value (Note: In production, use unique salt per password) */
    private static final byte[] SALT = "SpaceExplorerSalt123".getBytes();

    /**
     * Hashes a password using PBKDF2 with HMAC-SHA1.
     *
     * @param password The password to hash
     * @return Base64-encoded hash of the password
     * @throws RuntimeException if hashing fails due to algorithm unavailability
     *                         or invalid key specification
     */
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

    /**
     * Verifies a password against a stored hash.
     *
     * @param password The password to verify
     * @param storedHash The stored hash to compare against
     * @return true if the password matches the hash, false otherwise
     */
    public static boolean verifyPassword(String password, String storedHash) {
        String newHash = hashPassword(password);
        return storedHash.equals(newHash);
    }
}