package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.App;
import edu.pujadas.koobing_admin.Database.GestioTreballador;
import edu.pujadas.koobing_admin.Models.Treballador;
import edu.pujadas.koobing_admin.Utilities.PasswordUtilites;
import edu.pujadas.koobing_admin.Utilities.TrabajadorSingleton;
import edu.pujadas.koobing_admin.Utilities.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    private App app;
    private Parent root;
    private Scene scene;
    private Stage stage;

    public TextField emailField;
    public PasswordField passwordField;



    public void setMainActivity(App app) {
        this.app = app;
    }
    public void login(ActionEvent event) 
    {
        System.out.println("Login");
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

     try
     {
         if(treballador !=null)
         {
             boolean isValidPassword = PasswordUtilites.checkPassword(password, treballador.getPassword());
             if(!isValidPassword)
             {
                 showAlert("La contrasenya no és vàlid");
                 return;
             }


             if(treballador.isAdmin() ==1)
             {
                 showAlert("Bienvenido Administrador: " + treballador.getNom());
                 TrabajadorSingleton.getInstance().setTrabajador(treballador);
                 switchToHome(event);

                 //crido a la aplicacio per canviar de pantalla


                 return;
             }
             else {
                 showAlert("Bienvenido Treballador: " + treballador.getNom());
                 app.switchHome(treballador);

                 return;
             }

         }

     }
     catch (Exception e)
     {
         System.out.println("Failed to login error : " + e.getMessage());
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


    public void switchToHome(ActionEvent event) throws Exception
    {


        root = FXMLLoader.load(getClass().getResource("/edu/pujadas/koobing_admin/screens/home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }









}
