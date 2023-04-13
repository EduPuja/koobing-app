package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioUsuari;
import edu.pujadas.koobing_admin.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.tableview2.TableView2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
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

    private final ArrayList<Usuari> listsUsuaris = new ArrayList<Usuari>();
  
    // ---- End usuari Stuff ---- //
    public Tab autorsTap;
    public TableColumn idAutorColum;
    public TableColumn nomAutorColum;
    public TableColumn dataNaixAutorColum;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // todo agafar de la base de dades
        try
        {   // usuaris
            GestioUsuari gestioUsuari = new GestioUsuari();
            ResultSet usersResult = gestioUsuari.consultar10Usuaris();

            while (usersResult.next())
            {
                //recullo el avatar amb forma de blob
                Blob avatarBlob = usersResult.getBlob("avatar");
                // el converteixo
                byte[] bytes = avatarBlob.getBytes(1, (int) avatarBlob.length());
                // Crear un objeto InputStream a partir del arreglo de bytes
                InputStream is = new ByteArrayInputStream(bytes);
                //finalment el converteixo en el objecte imatge
                Image avatar = new Image(is);
                //usuari
                Usuari user = new Usuari(usersResult.getInt("id_usuari"),usersResult.getString("dni")
                        ,avatar,usersResult.getString("nom"),usersResult.getString("cognom"),
                        usersResult.getDate("data_naix"),usersResult.getString("email"),usersResult.getString("password"));
                listsUsuaris.add(user);


            }


            //observablelist
            ObservableList<Usuari> obserListUser = FXCollections.observableArrayList(
                    listsUsuaris
            );



            idUsuari.setCellValueFactory(new PropertyValueFactory<>("id"));
            dniColum.setCellValueFactory(new PropertyValueFactory<>("dni"));
            nomColum.setCellValueFactory(new PropertyValueFactory<>("nom"));
            cognomColum.setCellValueFactory(new PropertyValueFactory<>("cognom"));
            dataNaixColum.setCellValueFactory(new PropertyValueFactory<>("dataNaix"));
            emailColum.setCellValueFactory(new PropertyValueFactory<>("email"));
            passwordColum.setCellValueFactory(new PropertyValueFactory<>("password"));
            taulaUsuaris.setItems(obserListUser);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




       /* Usuari usuari1 = new Usuari("12345678A", null, "Pepe", "García", LocalDate.of(1990, 5, 10), "pepe@gmail.com", "contraseña");
        Usuari usuari2 = new Usuari("98765432B", null, "María", "Gómez", LocalDate.of(1995, 8, 20), "maria@gmail.com", "contraseña");
        // Crear un ObservableList de Usuari
        listaUsuaris = FXCollections.observableArrayList(
                usuari1,
                usuari2
        );

        // Configurar las columnas del TableView
        //idUsuari.setCellValueFactory(new PropertyValueFactory<>("idUsuari"));
        dniColum.setCellValueFactory(new PropertyValueFactory<>("DNI"));
        nomColum.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cognomColum.setCellValueFactory(new PropertyValueFactory<>("cognom"));
        dataNaixColum.setCellValueFactory(new PropertyValueFactory<>("dataNaix"));
        emailColum.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColum.setCellValueFactory(new PropertyValueFactory<>("password"));

        // Agregar los datos al TableView
        taulaUsuaris.setItems(listaUsuaris);
        //taulaUsuaris.getColumns().addAll(dniColum, nomColum, cognomColum, dataNaixColum, emailColum,passwordColum);
        */

    }

}
