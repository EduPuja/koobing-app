package edu.pujadas.koobing_admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application
{

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception
    {
        this.stage = stage;
        stage.setTitle("Koobing App Admin!");

        //icono
        String icono ="/edu/pujadas/koobing_admin/img/libro.png";
        stage.getIcons().add(new Image(App.class.getResource(icono).toExternalForm())); // Agrega el icono




        switchToLogin();

    }
   public void switchToLogin() throws IOException
   {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/pujadas/koobing_admin/screens/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1000, 600);
       //css

       String cssFile = getClass().getResource("css/main.css").toExternalForm();
       scene.getStylesheets().add(cssFile);
       stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args)
    {
        launch();
    }
}