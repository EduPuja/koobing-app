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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class LoginController {



    private Parent root;
    private Scene scene;
    private Stage stage;

    public TextField emailField;
    public PasswordField passwordField;



    public void login(ActionEvent event) 
    {
        System.out.println("Login");
        String email = emailField.getText();
        String password = passwordField.getText();

        try
        {
            if(!email.isEmpty() || !password.isEmpty() )
            {
                //validar el correu
                boolean isEmailValid = Validation.isValidEmail(email);

                if(!isEmailValid)
                {
                    showAlert("El correu no és vàlid");

                }
                else
                {
                    GestioTreballador gestioTreballador = new GestioTreballador();
                    Treballador treballador = gestioTreballador.findWorkerByEmail(email);

                    if(treballador != null)
                    {
                        //validar la contrassenay
                        boolean isValidPassword = PasswordUtilites.checkPassword(password, treballador.getPassword());
                        if(!isValidPassword)
                        {
                            showAlert("El contrassenya no és correcta!");
                            return;
                        }
                        else
                        {
                            //cookie
                            TrabajadorSingleton.getInstance().setTrabajador(treballador);

                            //canvi de pantalla

                            if(treballador.isAdmin() == 1)
                            {
                                showAlert("Bienvenido Administrador: " + treballador.getNom());
                                switchToHome(event);
                            }
                            else
                            {
                                showAlert("Bienvenido Treballador: " + treballador.getNom());
                                switchToHome(event);
                            }
                        }

                    }

                    else
                    {
                        showAlert("No exgisteix aquest treballador amb aquest correu");
                        return;
                    }


                }

            }
            else
            {
                showAlert("Emplena els camp necessaris");
                return;

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


        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/edu/pujadas/koobing_admin/screens/home.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String css = "/edu/pujadas/koobing_admin/css/main.css";
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(css).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }









}
