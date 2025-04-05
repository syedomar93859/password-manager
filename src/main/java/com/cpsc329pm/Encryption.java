package com.cpsc329pm;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

// Credit to https://www.baeldung.com/java-password-hashing for helping understand password hashing and salting
public class Encryption {

    /**
     * Generates a hashed password using PBKDF2 with HMAC SHA-256.
     * A random salt is generated for added security.
     *
     * @param password The plaintext password to hash.
     * @return A string containing the salt and hashed password, separated by a colon.
     */
    public static String HashingSalting(String password) {
        SecureRandom Rand = new SecureRandom(); // SecureRandom instance for generating salt
        byte[] saltString = new byte[16]; // 16-byte salt
        Rand.nextBytes(saltString); // Generate random salt

        // Prepare the key specification with password, salt, iterations, and key length
        KeySpec encryptPrep = new PBEKeySpec(password.toCharArray(), saltString, 10000, 256);
        SecretKeyFactory keys;
        byte[] hash;

        try {
            // Get an instance of PBKDF2WithHmacSHA256 for secure password hashing
            keys = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            hash = keys.generateSecret(encryptPrep).getEncoded(); // Generate hash
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("There was an error while hashing password", e);
        }

        // Encode the salt and hash to Base64 for storage
        String hashPass = Base64.getEncoder().encodeToString(hash);
        String saltPass = Base64.getEncoder().encodeToString(saltString);

        return saltPass + ":" + hashPass; // Store salt and hash together
    }

    /**
     * Verifies a plaintext password against a stored hashed password.
     *
     * @param password The plaintext password entered by the user.
     * @param hash The stored hashed password in "salt:hash" format.
     * @return True if the password matches, false otherwise.
     */
    public static boolean VerifyPassword(String password, String hash) {
        String[] parts = hash.split(":"); // Split stored hash into salt and hash parts
        if (parts.length != 2) {
            throw new IllegalArgumentException("Hash is invalid");
        }

        // Decode the stored salt and hash from Base64
        byte[] DecryptedSalt = Base64.getDecoder().decode(parts[0]);
        byte[] DecryptedHash = Base64.getDecoder().decode(parts[1]);

        // Recreate the hash using the provided password and extracted salt
        KeySpec spec = new PBEKeySpec(password.toCharArray(), DecryptedSalt, 10000, 256);
        byte[] newHash;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            newHash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("There was an error while verifying password", e);
        }

        // Compare the newly generated hash with the stored hash
        return java.util.Arrays.equals(newHash, DecryptedHash); // Use Arrays.equals for byte array comparison
    }
}