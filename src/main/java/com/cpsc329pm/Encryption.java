package com.cpsc329pm;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

// Credit to https://www.baeldung.com/java-password-hashing for helping understand password hashing and salting
public class Encryption {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY_ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;
    private static final int IV_SIZE = 16;

    /**
     * Encrypts a password using AES-256 encryption
     * @param password The plaintext password to encrypt
     * @return A string containing the IV and encrypted password, separated by a colon
     */
    public static String encrypt(String password) {
        try {
            // Generate a random IV
            SecureRandom random = new SecureRandom();
            byte[] iv = new byte[IV_SIZE];
            random.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Generate a random key
            KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGen.init(KEY_SIZE);
            SecretKey key = keyGen.generateKey();

            // Encrypt the password
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            byte[] encrypted = cipher.doFinal(password.getBytes());

            // Combine IV and encrypted data
            String ivBase64 = Base64.getEncoder().encodeToString(iv);
            String encryptedBase64 = Base64.getEncoder().encodeToString(encrypted);
            String keyBase64 = Base64.getEncoder().encodeToString(key.getEncoded());

            return ivBase64 + ":" + encryptedBase64 + ":" + keyBase64;
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    /**
     * Decrypts a password using AES-256 encryption
     * @param encryptedData The encrypted data in "iv:encrypted:key" format
     * @return The decrypted password
     */
    public static String decrypt(String encryptedData) {
        try {
            String[] parts = encryptedData.split(":");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid encrypted data format");
            }

            // Decode the components
            byte[] iv = Base64.getDecoder().decode(parts[0]);
            byte[] encrypted = Base64.getDecoder().decode(parts[1]);
            byte[] keyBytes = Base64.getDecoder().decode(parts[2]);

            // Reconstruct the key and IV
            SecretKey key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // Decrypt the password
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting password", e);
        }
    }

    /**
     * Verifies if a plaintext password matches an encrypted password
     * @param password The plaintext password to verify
     * @param encryptedData The encrypted password to verify against
     * @return true if the passwords match, false otherwise
     */
    public static boolean verifyPassword(String password, String encryptedData) {
        try {
            String decrypted = decrypt(encryptedData);
            return password.equals(decrypted);
        } catch (Exception e) {
            return false;
        }
    }

}