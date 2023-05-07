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
    
    //--- User stuff ---//
    public Tab usuarisTab;
    public TableView2<Usuari> taulaUsuaris ;
    public TableColumn<Usuari,Integer> idUsuari;
    public TableColumn<Usuari,String> dniColum;
    public TableColumn<Usuari,String> nomColum;
    public TableColumn<Usuari,String> cognomColum;
    public TableColumn<Usuari, Date> dataNaixColum;
    public TableColumn<Usuari,String > emailColum;
    private final ArrayList<Usuari> listUsuaris = new ArrayList<Usuari>();

    //--- Book stuff ---//
    public Tab llibresTab;
    public TableView2<Llibre> taulaLlibres;
    public TableColumn<Llibre,Long> isbnColum;
    public TableColumn<Llibre,String> autorColum;
    public TableColumn<Llibre,String >editorColum;
    public TableColumn<Llibre,String> idiomaColum;
    public TableColumn<Llibre,String> genereColum;
    public TableColumn<Llibre,String> titolColum;
    public TableColumn<Llibre,Integer> versioColum;
    public TableColumn<Llibre, Date> dataPubliColum;
    private final ArrayList<Llibre> listLlibres = new ArrayList<Llibre>();


    //-- author stuff ---//
    public Tab autorsTap;
    public TableView2<Autor> taulaAutors;
    public TableColumn<Autor,Integer> idAutorColum;
    public TableColumn<Autor,String> nomAutorColum;
    public TableColumn<Autor,Date> dataNaixAutorColum;
    private final ArrayList<Autor> listAutors = new ArrayList<Autor>();


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
