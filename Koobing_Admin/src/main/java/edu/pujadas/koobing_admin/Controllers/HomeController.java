package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Models.Usuari;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.controlsfx.control.tableview2.TableView2;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HomeController implements Initializable
{

    public ImageView avatarWorker;

    // ---- Usuari Stuff ----   //
    public Tab usuarisTab;
    public TableView2<Usuari> taulaUsuaris;
    public TableColumn<Usuari,Integer> idUsuari;
    public TableColumn<Usuari,String> dniColum;
    public TableColumn<Usuari,String> nomColum;
    public TableColumn<Usuari,String> cognomColum;
    public TableColumn<Usuari, LocalDate> dataNaixColum;
    public TableColumn<Usuari,String> emailColum;
    public TableColumn<Usuari,String> passwordColum;
    private ObservableList<Usuari> listaUsuaris;


    // ---- End usuari Stuff ---- //

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // todo agafar de la base de dades
        Usuari usuari1 = new Usuari("12345678A", null, "Pepe", "García", LocalDate.of(1990, 5, 10), "pepe@gmail.com", "contraseña");
        Usuari usuari2 = new Usuari("98765432B", null, "María", "Gómez", LocalDate.of(1995, 8, 20), "maria@gmail.com", "contraseña");
        // Crear un ObservableList de Usuari
        listaUsuaris = FXCollections.observableArrayList(
                usuari1,
                usuari2
        );

        // Configurar las columnas del TableView
        idUsuari.setCellValueFactory(new PropertyValueFactory<>("idUsuari"));
        dniColum.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        nomColum.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cognomColum.setCellValueFactory(new PropertyValueFactory<>("cognom"));
        dataNaixColum.setCellValueFactory(new PropertyValueFactory<>("dataNaix"));
        emailColum.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColum.setCellValueFactory(new PropertyValueFactory<>("password"));

        // Agregar los datos al TableView
        taulaUsuaris.setItems(listaUsuaris);
        //taulaUsuaris.getColumns().addAll(dniColum, nomColum, cognomColum, dataNaixColum, emailColum,passwordColum);


    }

}
