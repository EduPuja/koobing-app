package edu.pujadas.koobing_admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class App  extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/edu/pujadas/koobing_admin/screens/home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Koobing App Admin!");
        stage.setScene(scene);
        //icono
        String icono ="/edu/pujadas/koobing_admin/img/libro.png";
        stage.getIcons().add(new Image(App.class.getResource(icono).toExternalForm())); // Agrega el icono
        //css
        String css = "/edu/pujadas/koobing_admin/css/main.css";
        scene.getStylesheets().add(App.class.getResource(css).toExternalForm()); // Agrega el archivo CSS
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}