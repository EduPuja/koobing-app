package edu.pujadas.koobing_admin.Utilities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean isValidDni(String dni) {
        String pattern = "^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(dni);

        if (!m.matches()) {
            return false;
        }

        String letter = Character.toString(dni.charAt(8));
        int number = Integer.parseInt(dni.substring(0, 8));

        String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
        String expectedLetter = Character.toString(letters.charAt(number % 23));

        return letter.equals(expectedLetter);
    }

    public static String encryptPassword(String password) {
        try {
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(passwordBytes);
            String hash = Base64.getEncoder().encodeToString(hashBytes);
            password = hash;

            return password;
        }
        catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Error al encriptar la contraseña", ex);
        }
    }


    /**
     * Metode per comprovar la contrassenya
     * @param password password a not encrtypted
     * @param hashedPassword passwordEncrypted
     * @return true si la contraseña es correcta, false en caso contrario
     */
    public static boolean checkPassword(String password, String hashedPassword) {
        try {
            String passwordEnpryt = encryptPassword(password);

            System.out.println(passwordEnpryt);
            if(passwordEnpryt.equals(hashedPassword))
            {
                return true;
            }

            /*byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(passwordBytes);
            String hash = Base64.getEncoder().encodeToString(hashBytes);

            System.out.println("Checking password : " + password);
            System.out.println("hash : " + hash);
            return hash.equals(hashedPassword);*/
        }
        catch (Exception ex) {

            System.out.println("Checking password ERROR : " + ex.getMessage());
            //throw new RuntimeException("Error al comprobar la contraseña", ex);
        }
        return false;
    }
}
