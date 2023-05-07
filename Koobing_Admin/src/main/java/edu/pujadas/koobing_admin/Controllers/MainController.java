package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Models.Autor;
import edu.pujadas.koobing_admin.Models.Llibre;
import edu.pujadas.koobing_admin.Models.Treballador;
import edu.pujadas.koobing_admin.Models.Usuari;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.tableview2.TableView2;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    //nav bar lateral
    public Button homeBtn ,usuariBtn ,trebaladorBtn, llibreBtn;
    public Button autorBtn,bibliotecaBtn,idiomaBtn,genereBtn,editorialBtn,reservaBtn;

    // treballador stuff
    public ImageView avatarWorker;
    public ImageView logo;

    private Treballador worker;


    //tab pane stuff
    public TabPane tabPane;
    
  







    //container vbox from inserting all tabs and stuff
    public VBox containerBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main Controller....");
        loadLabelFXML();
    }

    public void loadLabelFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/pujadas/koobing_admin/screens/homeTabs.fxml"));
            tabPane = loader.load();
            containerBox.getChildren().removeAll();
            containerBox.getChildren().add(tabPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
