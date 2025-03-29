package com.cpsc329pm.BackendStuff;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;

// Credit to https://www.baeldung.com/java-password-hashing for helping me understand how to hash and salt properly
public class Encryption {

    private static String HashingSalting(String password) {
        SecureRandom Rand = new SecureRandom();
        byte[] saltString = new byte[16];
        Rand.nextBytes(saltString);

        KeySpec encryptPrep = new PBEKeySpec(password.toCharArray(), saltString, 1000, 128);
        SecretKeyFactory keys = null;

        byte[] hash = null;
        try {
            keys = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = keys.generateSecret(encryptPrep).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        String hashPass = "Encryption failed";
        if (hash != null) {
            hashPass = new String(hash);
        }
        return hashPass;
    }



}