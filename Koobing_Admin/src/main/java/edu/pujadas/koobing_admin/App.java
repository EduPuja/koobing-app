package edu.pujadas.koobing_admin;

import edu.pujadas.koobing_admin.Controllers.HomeController;
import edu.pujadas.koobing_admin.Controllers.LoginController;
import edu.pujadas.koobing_admin.Models.Treballador;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import java.io.IOException;
import java.util.Objects;


public class App extends Application
{

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception
    {
        this.stage = stage;
        switchToLogin();
        ;
      /*  FXMLLoader loader   = new FXMLLoader(App.class.getResource("/edu/pujadas/koobing_admin/screens/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle("Koobing App Admin!");
        stage.setScene(scene);
        //icono
        String icono ="/edu/pujadas/koobing_admin/img/libro.png";
        stage.getIcons().add(new Image(App.class.getResource(icono).toExternalForm())); // Agrega el icono
        //boostrap
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setResizable(false);

        stage.show();*/
    }
    public void switchToLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/pujadas/koobing_admin/screens/login.fxml"));
        Parent root = loader.load();

        LoginController loginController = loader.getController();
        loginController.setMainActivity(this);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    public void switchHome(Treballador worker) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/pujadas/koobing_admin/screens/home.fxml"));
        Parent root = loader.load();

        HomeController homeController = loader.getController();
        homeController.setTreballador(worker);

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}