package edu.pujadas.koobing_admin.Database;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class PasswordUtils {

    private static final String SECRET_KEY = "Eduard2023!"; // Clave secreta para el cifrado AES
    private static final String SALT = "SaltPujadas"; // Valor de sal para el cifrado

    public static String encryptPassword(String password) throws Exception {
        byte[] salt = SALT.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = createSecretKeySpec();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[cipher.getBlockSize()];
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptedPasswordBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
        byte[] encryptedPassword = new byte[iv.length + encryptedPasswordBytes.length];
        System.arraycopy(iv, 0, encryptedPassword, 0, iv.length);
        System.arraycopy(encryptedPasswordBytes, 0, encryptedPassword, iv.length, encryptedPasswordBytes.length);

        return Base64.getEncoder().encodeToString(encryptedPassword);
    }

    public static String decryptPassword(String encryptedPassword) throws Exception {
        byte[] decodedPassword = Base64.getDecoder().decode(encryptedPassword); // Decodifica la contraseña encriptada

        byte[] salt = SALT.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec secretKeySpec = createSecretKeySpec();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(Arrays.copyOfRange(decodedPassword, 0, 16));
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decryptedPasswordBytes = cipher.doFinal(Arrays.copyOfRange(decodedPassword, 16, decodedPassword.length));

        return new String(decryptedPasswordBytes, StandardCharsets.UTF_8);
    }

    private static SecretKeySpec createSecretKeySpec() throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] key = sha.digest(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        key = Arrays.copyOf(key, 16);
        return new SecretKeySpec(key, "AES");
    }
}

