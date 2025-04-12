package com.cpsc329pm;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

/**
 * Provides methods for encrypting, decrypting, and verifying passwords using AES encryption.
 *
 * <p>Credit to https://www.baeldung.com/java-password-hashing for helping understand password hashing and salting.</p>
 */
public class Encryption {
    // AES encryption configuration
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding"; // Cipher transformation
    private static final String KEY_ALGORITHM = "AES";              // Key algorithm
    private static final int KEY_SIZE = 256;                        // AES-256 bit key
    private static final int IV_SIZE = 16;                          // IV size in bytes

    /**
     * Encrypts a password using AES-256 encryption.
     *
     * @param password The plaintext password to encrypt
     * @return A string containing the Base64-encoded IV, encrypted password, and encryption key, separated by colons
     */
    public static String encrypt(String password) {
        try {
            // Generate a random IV
            SecureRandom random = new SecureRandom();
            byte[] iv = new byte[IV_SIZE];
            random.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv); // IV specification

            // Generate a random AES key
            KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGen.init(KEY_SIZE); // Set key size to 256 bits
            SecretKey key = keyGen.generateKey(); // Generate the AES key

            // Encrypt the password using the AES key and IV
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            byte[] encrypted = cipher.doFinal(password.getBytes()); // Encrypt password

            // Encode IV, encrypted data, and key in Base64
            String ivBase64 = Base64.getEncoder().encodeToString(iv);
            String encryptedBase64 = Base64.getEncoder().encodeToString(encrypted);
            String keyBase64 = Base64.getEncoder().encodeToString(key.getEncoded());

            // Return as a single string separated by colons
            return ivBase64 + ":" + encryptedBase64 + ":" + keyBase64;
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e); // Wrap and rethrow any exception
        }
    }

    /**
     * Decrypts a password that was encrypted using AES-256.
     *
     * @param encryptedData The encrypted data string in the format "iv:encrypted:key"
     * @return The original plaintext password
     */
    public static String decrypt(String encryptedData) {
        try {
            // Split the string into its components
            String[] parts = encryptedData.split(":");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid encrypted data format"); // Ensure all components exist
            }

            // Decode Base64-encoded IV, encrypted password, and key
            byte[] iv = Base64.getDecoder().decode(parts[0]);
            byte[] encrypted = Base64.getDecoder().decode(parts[1]);
            byte[] keyBytes = Base64.getDecoder().decode(parts[2]);

            // Reconstruct AES key and IV
            SecretKey key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Decrypt the encrypted password
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            byte[] decrypted = cipher.doFinal(encrypted); // Perform decryption

            return new String(decrypted); // Convert byte array back to string
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting password", e); // Wrap and rethrow any exception
        }
    }

    /**
     * Verifies whether a plaintext password matches the encrypted password.
     *
     * @param password      The plaintext password to check
     * @param encryptedData The encrypted password string to compare against
     * @return true if the passwords match, false otherwise
     */
    public static boolean verifyPassword(String password, String encryptedData) {
        try {
            // Decrypt stored password and compare with provided plaintext password
            String decrypted = decrypt(encryptedData);
            return password.equals(decrypted);
        } catch (Exception e) {
            return false; // Return false if decryption fails
        }
    }
}
