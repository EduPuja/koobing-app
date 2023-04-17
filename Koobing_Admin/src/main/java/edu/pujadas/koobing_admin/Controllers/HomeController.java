package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.*;
import edu.pujadas.koobing_admin.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    private ArrayList<Usuari> listsUsuaris = new ArrayList<Usuari>();



    // ---- Autors Stuff ---- //
    public Tab autorsTap;

    public TableView2<Autor> taulaAutors;
    public TableColumn<Autor,Integer> idAutorColum;
    public TableColumn<Autor,String> nomAutorColum;
    public TableColumn<Autor,Date> dataNaixAutorColum;

    private ArrayList<Autor> listAutores = new ArrayList<Autor>();



    // ---- Llibres stuff ---- //

    private final ArrayList<Llibre> listLlibres = new ArrayList<Llibre>();
    public TableView2<Llibre> taulaLlibres;
    public TableColumn<Llibre,Integer>isbnColum;
    public TableColumn <Llibre,String>autorColum;
    public TableColumn<Llibre,String> editorColum;
    public TableColumn<Llibre,String> idiomaColum;
    public TableColumn <Llibre,String>genereColum;
    public TableColumn <Llibre,String>titolColum;
    public TableColumn <Llibre,Integer>versioColum;
    public TableColumn <Llibre, Date>dataPubliColum;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        infoUsuaris();
        infoAutor();
        //infoLlibre();
    }


    /**
     * Metode que es utilizat en el initialize()
     */
    private void infoUsuaris()
    {
        try
        {   // usuaris
            GestioUsuari gestioUsuari = new GestioUsuari();

            listsUsuaris = gestioUsuari.consultar10Usuaris();

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
            System.out.println("Failed to infoUsuaris ..." +e.getMessage());
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
            listAutores = gestioAutor.consultar10Autors();
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
            System.out.println("Failed to infoAutors ..." +e.getMessage());
        }
    }


    private void infoLlibre()
    {
        try
        {
            GestioLlibre gestioLlibre = new GestioLlibre();


            ResultSet rs =gestioLlibre.conusltar10Llibres();
            while (rs.next())
            {
                System.out.println(rs.getLong("l.isbn"));
                System.out.println(rs.getString("a.nom_autor"));
                System.out.println(rs.getString("e.nom_editorial"));
                System.out.println(rs.getString("i.nom_idioma"));
                System.out.println(rs.getString("g.descrip"));
                System.out.println(rs.getString("l.titol"));
                System.out.println(rs.getInt("l.versio"));
                System.out.println(rs.getDate("l.data_publi") +"\n");

            }


            /*if(rs.next())
            {
                System.out.println("Hi ha info");
            }
            else System.out.println("no hi ha info");*/



                /*Genere genere = gestioGenere.findGenere(rs.getInt("id_genere"));
                Idioma idioma =gestioIdioma.findIdioma(rs.getInt("id_idioma"));
                Autor autor = gestioAutor.findAutor(rs.getInt("id_autor"));

                Editorial  editorial = gestioEditorial.findEditorial(rs.getInt("id_editorial"));

                Llibre llibre =new Llibre(rs.getInt("ISBN"),autor,editorial,idioma,genere,rs.getString("titol")
                        ,rs.getInt("versio"),rs.getDate("data_publi"));*/



                //listLlibres.add(llibre);



            //observablelist llibres
            ObservableList<Llibre> observableListLlibre = FXCollections.observableArrayList(
                    listLlibres
            );



            isbnColum.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
            autorColum.setCellValueFactory(new PropertyValueFactory<>("autor"));
            editorColum.setCellValueFactory(new PropertyValueFactory<>("editor"));
            idiomaColum.setCellValueFactory(new PropertyValueFactory<>("idioma"));
            genereColum.setCellValueFactory(new PropertyValueFactory<>("genere"));
            titolColum.setCellValueFactory(new PropertyValueFactory<>("titol"));
            versioColum.setCellValueFactory(new PropertyValueFactory<>("versio"));
            dataPubliColum.setCellValueFactory(new PropertyValueFactory<>("dataPubli"));

            taulaLlibres.setItems(observableListLlibre);

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
