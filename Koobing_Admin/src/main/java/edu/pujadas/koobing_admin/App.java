package edu.pujadas.koobing_admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class App extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Koobing App Admin!");
        stage.setScene(scene);
        stage.getIcons().add(new Image(App.class.getResource("/edu/pujadas/koobing_admin/img/libro.png").toExternalForm())); // Agrega el icono
        scene.getStylesheets().add(App.class.getResource("/edu/pujadas/koobing_admin/css/main.css").toExternalForm()); // Agrega el archivo CSS
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}