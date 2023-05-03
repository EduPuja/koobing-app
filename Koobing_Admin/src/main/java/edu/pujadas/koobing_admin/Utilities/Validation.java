package edu.pujadas.koobing_admin.Utilities;

import edu.pujadas.koobing_admin.Database.GestioLlibre;
import edu.pujadas.koobing_admin.Database.GestioTreballador;
import edu.pujadas.koobing_admin.Models.Treballador;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    /**
     * Validacio  del correo electrónico
     * @param email string
     * @return true si es valido, false en caso contrario
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     *  Metode per comrvar la contraseña tingui 8 caracteres, incluir al menos una letra mayúscula, una minúscula y un número
     * @param password String contraseña
     * @return true si es valido, false en caso contrario
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        // La contraseña debe tener al menos 8 caracteres, incluir al menos una letra mayúscula, una minúscula y un número
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * Metode per validar el DNI
     * @param dni String DNI
     * @return true si es valido, false en caso contrario
     */
    public static boolean isValidDni(String dni) {
        if (dni == null || dni.isEmpty()) {
            return false;
        }

        // La longitud del DNI debe ser 9 (8 dígitos y una letra)
        if (dni.length() != 9) {
            return false;
        }

        // Los primeros 8 caracteres deben ser dígitos
        String dniNumeros = dni.substring(0, 8);
        if (!dniNumeros.matches("\\d+")) {
            return false;
        }

        // El último carácter debe ser una letra (mayúscula o minúscula)
        String dniLetra = dni.substring(8);
        if (!dniLetra.matches("[a-zA-Z]")) {
            return false;
        }

        // Comprobar la letra del DNI
        String letrasDni = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numeroDni = Integer.parseInt(dniNumeros) % 23;
        char letraDniCalculada = letrasDni.charAt(numeroDni);
        char letraDniIntroducida = dniLetra.toUpperCase().charAt(0);
        return letraDniCalculada == letraDniIntroducida;
    }


}
