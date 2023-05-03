package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioTreballador;
import edu.pujadas.koobing_admin.Utilities.PasswordUtils;
import edu.pujadas.koobing_admin.Models.Treballador;
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

        boolean isEmailValid = isEmailValid(email);

        if(!isEmailValid)
        {
            showAlert("El correu no és vàlid");
            return;
        }
        GestioTreballador gestioTreballador = new GestioTreballador();
        Treballador treballador = gestioTreballador.findWorkerByEmail(email);
        if (treballador !=null)
        {
            System.out.println("Password Treballador: "+treballador.getPassword());
            System.out.println("Password Entrada: "+password);


            //todo comprovar el password
            boolean isPasswordValid = PasswordUtils.checkPassword(password, treballador.getPassword());

            System.out.println("Is valid password: "+isPasswordValid);

        }



    }

    /**
     * Metode que comrpvoa la validació de correos electronics
     * @param email String correuElectronic
     * @return un true si el correu és vàlid
     */
    private boolean isEmailValid(String email) {
        // Expresión regular para validar correos electrónicos
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        // Validar correo electrónico con la expresión regular
        return email.matches(emailRegex);
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
