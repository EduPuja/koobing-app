package edu.pujadas.koobing_app.Utilites;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Validator {

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
        try
        {
            String passwordEnpryt = encryptPassword(password);
            return passwordEnpryt.equals(hashedPassword);

        }
        catch (Exception ex) {

            System.out.println("Checking password ERROR : " + ex.getMessage());

        }
        return false;
    }


    /**
     * Metode que valida el dni si es correcte
     * @param dni String dni
     * @return true si es correcte i false si es incorrecte
     */
    public static boolean validarDni(String dni)
    {
        String codiValidacioDNI="TRWAGMYFPDXBNJZSQVHLCKE";

        boolean valid=true;

        if (dni.length()==9) {
            //primer mirem si hi ha 8 nombres
            int i=0;
            while(valid&&i<8) {
                if(!Character.isDigit(dni.charAt(i))) valid=false;
                else i++;
            }

            //i ara comprovem la lletra
            long valorDniLong=(Long.parseLong(dni.substring(0,8)))%23;// els 8 numeros del dni son massa per integer, hem de fer servir long
            int valorDni=(int)valorDniLong;	// Ara convertim el resultat a enter.
            if(!(dni.charAt(8)==codiValidacioDNI.charAt(valorDni))) valid=false;
        }
        else valid = false;

        return valid;

    }

    /**
     * Metode per validar el correu electronic si escorrecte
     * @param correo correu electronic
     * @return true si es valid , si no retorna false
     */
    public static boolean validarCorreoElectronico(String correo) {

        String patron = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return correo.matches(patron);
    }


}
