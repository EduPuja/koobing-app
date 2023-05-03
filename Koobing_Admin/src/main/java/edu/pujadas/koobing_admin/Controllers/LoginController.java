package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioTreballador;
import edu.pujadas.koobing_admin.Models.Treballador;
import edu.pujadas.koobing_admin.Utilities.PasswordUtilites;
import edu.pujadas.koobing_admin.Utilities.Validation;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {


    public TextField emailField;
    public PasswordField passwordField;


    public void login(ActionEvent event)
    {
        String email = emailField.getText();
        String password = passwordField.getText();


        if(email.isEmpty() || password.isEmpty() )
        {
            showAlert("Emplena els camp necessaris");
            return;
        }

        boolean isEmailValid = Validation.isValidEmail(email);

        if(!isEmailValid)
        {
            showAlert("El correu no és vàlid");
            return;
        }
        GestioTreballador gestioTreballador = new GestioTreballador();
        Treballador treballador = gestioTreballador.findWorkerByEmail(email);

        if(treballador !=null)
        {
            boolean isValidPassword = PasswordUtilites.checkPassword(password, treballador.getPassword());
            if(!isValidPassword)
            {
                showAlert("La contrasenya no és vàlid");
                return;
            }
            showAlert("Bienvenido: " + treballador.getNom());
        }
      


    }



    /***
     * Metode per fer una Alerta de tipus informacio
     * @param message missatge d'alerta
     */
    private void showAlert(String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }








}
