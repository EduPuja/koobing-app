package edu.pujadas.koobing_admin.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import org.controlsfx.control.tableview2.TableView2;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable
{
    // todo taules recollir de la base de datos la info del usuari / llibre / autor limit 10
    public ImageView avatarWorker;

    // ---- Usuari Stuff ----   //
    public Tab usuarisTab;
    public TableView2 taulaUsuaris;
    public TableColumn idUsuari;
    public TableColumn dniColum;
    public TableColumn nomColum;
    public TableColumn cognomColum;
    public TableColumn dataNaixColum;
    public TableColumn emailColum;
    public TableColumn passwordColum;
    // ---- End usuari Stuff ---- //

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

    }

}
