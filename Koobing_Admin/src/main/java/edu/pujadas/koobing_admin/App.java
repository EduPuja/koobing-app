package edu.pujadas.koobing_admin;

import edu.pujadas.koobing_admin.Controllers.HomeController;
import edu.pujadas.koobing_admin.Controllers.LoginController;
import edu.pujadas.koobing_admin.Controllers.MainController;
import edu.pujadas.koobing_admin.Controllers.TreballadorController;
import edu.pujadas.koobing_admin.Database.GestioTreballador;
import edu.pujadas.koobing_admin.Models.Treballador;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
        stage.setTitle("Koobing App Admin!");

        //icono
        String icono ="/edu/pujadas/koobing_admin/img/libro.png";
        stage.getIcons().add(new Image(App.class.getResource(icono).toExternalForm())); // Agrega el icono

        switchToLogin();
       // switchToLogin();
        // switch hom directly
       /*GestioTreballador gestio = new GestioTreballador();
        Treballador worker = gestio.findWorkerByEmail("edu@mail.com");
       switchHome(worker);*/




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




   /* public void switchTreballador() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/pujadas/koobing_admin/screens/treballador.fxml"));
        Parent root = loader.load();


        //todo treballador
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.show();
    }*/
    public static void main(String[] args)
    {
        launch();
    }
}