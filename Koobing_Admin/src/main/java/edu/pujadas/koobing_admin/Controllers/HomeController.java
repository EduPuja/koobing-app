package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioAutor;
import edu.pujadas.koobing_admin.Database.GestioLlibre;
import edu.pujadas.koobing_admin.Database.GestioUsuari;
import edu.pujadas.koobing_admin.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.TableView2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable
{

    // todo avatar del treballador quant inicia session


    private Stage stage ;
    private Scene scene;
    private Parent root;
    public ImageView avatarWorker;

    // ---- Usuari Stuff ----   //
    public Tab usuarisTab;
    public TableView2<Usuari> taulaUsuaris;
    public TableColumn<Usuari,Integer> idUsuari;
    public TableColumn<Usuari,String> dniColum;
    public TableColumn<Usuari,String> nomColum;
    public TableColumn<Usuari,String> cognomColum;
    public TableColumn<Usuari, Date> dataNaixColum;
    public TableColumn<Usuari,String> emailColum;
    public TableColumn<Usuari,String> passwordColum;

    //arraylist usuaris
    private final ArrayList<Usuari> listsUsuaris = new ArrayList<Usuari>();



    // ---- Autors Stuff ---- //
    public Tab autorsTap;

    public TableView2<Autor> taulaAutors;
    public TableColumn<Autor,Integer> idAutorColum;
    public TableColumn<Autor,String> nomAutorColum;
    public TableColumn<Autor,Date> dataNaixAutorColum;

    private final ArrayList<Autor> listAutores = new ArrayList<Autor>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        infoUsuaris();
        infoAutor();

    }


    /**
     * Metode que es utilizat en el initialize()
     */
    private void infoUsuaris()
    {
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
            usersResult.close();

            //observablelist usuaris
            ObservableList<Usuari> obserListUser = FXCollections.observableArrayList(
                    listsUsuaris
            );

            //stuff taula usuaris

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



    }

    /**
     * Metode per recollir la info de un autor de la base de dades
     */
    private void infoAutor()
    {
        try
        {
            GestioAutor gestioAutor = new GestioAutor();
            ResultSet rs = gestioAutor.consultar10Autors();

            while (rs.next())
            {
                Autor autor = new Autor(rs.getInt("id_autor"),rs.getString("nom_autor"),rs.getDate("data_naix"));
                listAutores.add(autor);
            }
            //observablelist autors
            ObservableList<Autor> observableListAutors = FXCollections.observableArrayList(
                    listAutores
            );

            idAutorColum.setCellValueFactory(new PropertyValueFactory<>("idAutor"));
            nomAutorColum.setCellValueFactory(new PropertyValueFactory<>("nomAutor"));
            dataNaixAutorColum.setCellValueFactory(new PropertyValueFactory<>("dataNaixAutor"));
            taulaAutors.setItems(observableListAutors);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private void infoLlibre()
    {
        try
        {
            GestioLlibre gestioLlibre = new GestioLlibre();
            GestioAutor gestioAutor = new GestioAutor();
            ResultSet rs =gestioLlibre.conusltar10Llibres();


            while (rs.next())
            {

                Autor autor = gestioAutor.findAutor(rs.getInt("id_autor"));
                Llibre llibre =new Llibre(rs.getInt("ISBN",autor);
                        //gestioAutor.consultarAutor(rs.getInt("idAutor" )));

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    // CANVIS DE PANTALLA


    /**
     * Funcio per canviar a la pantalla de home
     * @param event action evnet
     * @throws Exception exeception
     */
    public void switchToHome(ActionEvent event) throws Exception
    {
        root = FXMLLoader.load(getClass().getResource("/edu/pujadas/koobing_admin/screens/home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Funcio per canviar a la pantalla de usuari
     * @param event action evnet
     * @throws Exception exeception
     */
    public void switchToUsuari(ActionEvent event) throws Exception
    {
        root = FXMLLoader.load(getClass().getResource("/edu/pujadas/koobing_admin/screens/usuari.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Funcio per canviar a la pantalla de TREBALLADOR
     * @param event action evnet
     * @throws Exception exeception
     */
    public void switchToTreballador(ActionEvent event) throws Exception
    {
        root = FXMLLoader.load(getClass().getResource("/edu/pujadas/koobing_admin/screens/treballador.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Funcio per canviar a la pantalla de LLIBRE
     * @param event action evnet
     * @throws Exception exeception
     */
    public void switchToLlibre(ActionEvent event) throws Exception
    {
        root = FXMLLoader.load(getClass().getResource("/edu/pujadas/koobing_admin/screens/llibre.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Funcio per canviar a la pantalla de Autor
     * @param event action evnet
     * @throws Exception exeception
     */
    public void switchToAutor(ActionEvent event) throws Exception
    {
        root = FXMLLoader.load(getClass().getResource("/edu/pujadas/koobing_admin/screens/autor.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Funcio per canviar a la pantalla de Biblioteca
     * @param event action evnet
     * @throws Exception exeception
     */
    public void switchToBiblioteca(ActionEvent event) throws Exception
    {
        root = FXMLLoader.load(getClass().getResource("/edu/pujadas/koobing_admin/screens/biblioteca.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Funcio per canviar a la pantalla de usuari
     * @param event action evnet
     * @throws Exception exeception
     */
    public void switchToIdioma(ActionEvent event) throws Exception
    {
        root = FXMLLoader.load(getClass().getResource("/edu/pujadas/koobing_admin/screens/idioma.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Funcio per canviar a la pantalla de Genere
     * @param event action evnet
     * @throws Exception exeception
     */
    public void switchToGenere(ActionEvent event) throws Exception
    {
        root = FXMLLoader.load(getClass().getResource("/edu/pujadas/koobing_admin/screens/genere.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Funcio per canviar a la pantalla de Editorial
     * @param event action evnet
     * @throws Exception exeception
     */
    public void switchToEditorial(ActionEvent event) throws Exception
    {
        root = FXMLLoader.load(getClass().getResource("/edu/pujadas/koobing_admin/screens/editorial.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Funcio per canviar a la pantalla de Reserves
     * @param event action evnet
     * @throws Exception exeception
     */
    public void switchToReserva(ActionEvent event) throws Exception
    {
        root = FXMLLoader.load(getClass().getResource("/edu/pujadas/koobing_admin/screens/reserva.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
