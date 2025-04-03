package com.cpsc329pm.BackendStuff;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

// Credit to https://www.baeldung.com/java-password-hashing for helping me understand how to hash and salt properly
public class Encryption {

    public static String HashingSalting(String password) {
        SecureRandom Rand = new SecureRandom();
        byte[] saltString = new byte[16];
        Rand.nextBytes(saltString);

        KeySpec encryptPrep = new PBEKeySpec(password.toCharArray(), saltString, 10000, 256);
        SecretKeyFactory keys;
        byte[] hash;

        try {
            keys = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            hash = keys.generateSecret(encryptPrep).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("There was an error while hashing password", e);
        }
        String hashPass = Base64.getEncoder().encodeToString(hash);
        String saltPass = Base64.getEncoder().encodeToString(saltString);
        return saltPass + ":" + hashPass;
    }
    public static boolean VerifyPassword(String password, String hash){
        String[] parts = hash.split(":");
        if (parts.length != 2){
            throw new IllegalArgumentException("Hash is invalid");
        }
        byte[] DecryptedSalt = Base64.getDecoder().decode(parts[0]);
        byte[] DecryptedHash = Base64.getDecoder().decode(parts[1]);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), DecryptedSalt, 10000, 256);
        byte[] newHash;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            newHash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("There was an error while verifying password", e);
        }
        if (newHash == DecryptedHash){
            return true;
        }
        else{
            return false;
        }
    }



}