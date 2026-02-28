package org.example.clubmanager.model;

import com.google.cloud.firestore.annotation.Exclude;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class Key {
    private String encryptedKey;
    private String purpose;

    @Exclude
    private static final String MASTER_SECRET = "46515035950334719375791987487874";
    @Exclude
    private static final String RAW_MASTER_KEY = "F9485D439D87A5FA";
    
    public Key() {}

    public String getMasterKey() { return RAW_MASTER_KEY; }

    public String getEncryptedKey() { return encryptedKey; }
    public void setEncryptedKey(String encryptedKey) { this.encryptedKey = encryptedKey; }
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public void encryptAndSetKey(String rawKey) throws Exception {
        byte[] iv = new byte[12]; // GCM standard IV size
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        SecretKeySpec keySpec = new SecretKeySpec(MASTER_SECRET.getBytes(), "AES");

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec);
        byte[] cipherText = cipher.doFinal(rawKey.getBytes());

        // Store as IV:CipherText in Base64
        this.encryptedKey = Base64.getEncoder().encodeToString(iv) + ":" +
                Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decryptKey(Key key) throws Exception {
        String[] parts = key.getEncryptedKey().split(":");
        byte[] iv = Base64.getDecoder().decode(parts[0]);
        byte[] cipherText = Base64.getDecoder().decode(parts[1]);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        SecretKeySpec keySpec = new SecretKeySpec(MASTER_SECRET.getBytes(), "AES");

        cipher.init(Cipher.DECRYPT_MODE, keySpec, spec);
        return new String(cipher.doFinal(cipherText));
    }
}
