package edu.pujadas.koobing_admin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;



public class App extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        //String css = this.getClass().getResource("main.css").toExternalForm();
        //scene.getStylesheets().addAll(css);

        stage.setTitle("Koobing App Admin!");

        String libro = getClass().getResource("/img/libro.png").toExternalForm();
        System.out.println(libro);


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}