package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioTreballador;
import edu.pujadas.koobing_admin.Models.Treballador;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


import java.net.URL;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.ResourceBundle;

public class LoginController {


    public TextField emailField;
    public PasswordField passwordField;


    public void login(ActionEvent event)
    {
        String email = emailField.getText();
        String password = passwordField.getText();



        boolean okaEmail = isEmailValid(email);
        if(okaEmail)
        {
            System.out.println("Email correcto");

            System.out.println("Email: " + email + " Password: " + password);;
        }
        else
        {
            System.out.println("Email incorrect");
            Alert wrongAlert = new Alert(Alert.AlertType.ERROR);
            wrongAlert.setTitle("Error");
            wrongAlert.setHeaderText("Correu incorrect");
            wrongAlert.setContentText("Introduce una dirección de correo electrónico válida");
            wrongAlert.show();
        }


    }

    private boolean isEmailValid(String email) {
        // Expresión regular para validar correos electrónicos
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        // Validar correo electrónico con la expresión regular
        return email.matches(emailRegex);
    }

    private boolean checkWorker(String email, String password) {
        GestioTreballador gestioTreballador = new GestioTreballador();
        Treballador treballador = gestioTreballador.findWorkerByEmail(email);
        if (treballador != null) {
            // Obtener el hash almacenado en la base de datos
            String hashedPassword = treballador.getPassword();
            // Generar un hash de la contraseña ingresada
            String hashedInputPassword = hashPassword(password);
            // Verificar si el hash generado es igual al hash almacenado
            return hashedPassword.equals(hashedInputPassword);
        } else {
            return false;
        }
    }


    private String hashPassword(String password) {
        try {
            // Obtener una instancia de MessageDigest para generar el hash
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            // Generar el hash de la contraseña
            byte[] hashedBytes = md.digest(password.getBytes());
            // Codificar el hash en Base64 para almacenarlo como texto
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (Exception e) {
            // Manejar cualquier error que pueda ocurrir al generar el hash
            //  e.printStackTrace();
            System.out.println("Error hashing password : " + e.getMessage());
            return null;
        }
    }

}
