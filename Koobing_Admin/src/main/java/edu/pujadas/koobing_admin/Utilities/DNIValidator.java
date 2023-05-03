package edu.pujadas.koobing_admin.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DNIValidator {

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
}
