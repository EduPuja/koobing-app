package edu.pujadas.koobing_admin.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {


    public TextField emailField;
    public PasswordField passwordField;


    public void login(ActionEvent event)
    {
        String email = emailField.getText();
        String password = passwordField.getText();


        System.out.println("Email: " + email + " Password: " + password);;

    }

    private boolean isEmailValid(String email) {
        // Expresión regular para validar correos electrónicos
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        // Validar correo electrónico con la expresión regular
        return email.matches(emailRegex);
    }
}
