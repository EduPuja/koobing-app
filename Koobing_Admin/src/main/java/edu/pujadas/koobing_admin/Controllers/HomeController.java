package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.App;
import edu.pujadas.koobing_admin.Database.*;
import edu.pujadas.koobing_admin.Models.*;
import edu.pujadas.koobing_admin.Utilities.TrabajadorSingleton;
import edu.pujadas.koobing_admin.Utilities.Validation;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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

    public Button logoutBtn;
    // Stage and parent stuff//
    private Stage stage ;
    private Scene scene;
    private Parent root;

    /***XML STUFF**/

    // --- buttons navbar--//
    public Button homeBtn;
    public Button usuariBtn;
    public Button trebaladorBtn;
    public Button llibreBtn;
    public Button autorBtn;
    public Button bibliotecaBtn;
    public Button idiomaBtn;
    public Button genereBtn;
    public Button editioralBtn;
    public Button reservaBtn;
    public VBox container = new VBox();

    public ImageView logo;

    // ---- Treballador stuff ---- //

    public ImageView avatarWorker;



    // ---- Usuari Stuff ----   //

    public TableView2<Usuari> taulaUsuaris;
    public TableColumn<Usuari,Integer> idUsuari;
    public TableColumn<Usuari,String> dniColum;
    public TableColumn<Usuari,String> nomColum;
    public TableColumn<Usuari,String> cognomColum;
    public TableColumn<Usuari, Date> dataNaixColum;
    public TableColumn<Usuari,String> emailColum;

    private  ArrayList<Usuari> listsUsuaris = new ArrayList<Usuari>();



    // ---- Autors Stuff ---- //


    public TableView2<Autor> taulaAutors;
    public TableColumn<Autor,Integer> idAutorColum;
    public TableColumn<Autor,String> nomAutorColum;
    public TableColumn<Autor,Date> dataNaixAutorColum;

    private ArrayList<Autor> listAutores = new ArrayList<Autor>();



    // ---- Llibres stuff ---- //

    private  ArrayList<Llibre> listLlibres = new ArrayList<Llibre>();
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
        System.out.println("Home Screen!");

        loadWorkerInfo();
        infoAutor();
        infoUsuaris();
        infoLlibre();



    }


    public void onLogout(ActionEvent event) throws Exception
    {
        Treballador worker = TrabajadorSingleton.getInstance().getTrabajador();
        worker = null;

        root = FXMLLoader.load(getClass().getResource("/edu/pujadas/koobing_admin/screens/login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene.getStylesheets().add(getClass().getResource("css/main.css").toExternalForm());
        stage.setScene(scene);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    /**
     * Metode que carrega la info del treballador
     */
    private void loadWorkerInfo()
    {
            try {
                Treballador worker = TrabajadorSingleton.getInstance().getTrabajador();
                if(worker != null)
                {
                    Blob blob = worker.getAvatar();
                    if (blob != null) {
                        byte[] byteArray = blob.getBytes(1, (int) blob.length());
                        ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);

                        Image avatar = new Image(bis);
                        avatarWorker.setImage(avatar);
                    }



                    if(worker.isAdmin() == 1)
                    {
                        System.out.println("admin ");
                    }
                    else
                    {
                        System.out.println("worker");
                        trebaladorBtn.setVisible(false);
                        bibliotecaBtn.setVisible(false);
                        editioralBtn.setVisible(false);
                        genereBtn.setVisible(false);

                    }



                }

            }
            catch (Exception e) {
                System.out.println("Error loading worker info: " + e.getMessage());
            }
    }


    /**
     * Metode que es utilizat en el initialize()
     */
    private void infoUsuaris()
    {
        //System.out.println("info usuaris"); // degug
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
            //passwordColum.setCellValueFactory(new PropertyValueFactory<>("password"));
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
        //System.out.println("info autor");
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
        //System.out.println("info llibre");
        try
        {
            // classe per agafar la info de la base de dades
            GestioLlibre gestioLlibre = new GestioLlibre();
            listLlibres = gestioLlibre.conusltar10Llibres();



            //observablelist llibres
            ObservableList<Llibre> observableListLlibre = FXCollections.observableArrayList(
                    listLlibres
            );

            taulaLlibres.setItems(observableListLlibre);

            isbnColum.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
            autorColum.setCellValueFactory(cellData -> {

                Autor actor = cellData.getValue().getAutor();
                String nombreAutor = actor.getNomAutor();
                return new SimpleStringProperty(nombreAutor);

            });
           editorColum.setCellValueFactory(cellData -> {
                Editorial editor = cellData.getValue().getEditor();
                String nomEditor = editor.getNomEditor();
                return new SimpleStringProperty(nomEditor);
            });
            idiomaColum.setCellValueFactory(cellData ->{
                Idioma idioma = cellData.getValue().getIdioma();
                String nomIdioma = idioma.getNomIdioma();
                return new SimpleStringProperty(nomIdioma);
            });
            genereColum.setCellValueFactory(cellData ->{
                Genere genere = cellData.getValue().getGenere();
                String nomGenere = genere.getNomGenere();
                return new SimpleStringProperty(nomGenere);
            });
            titolColum.setCellValueFactory(new PropertyValueFactory<>("titol"));
            versioColum.setCellValueFactory(new PropertyValueFactory<>("versio"));
            dataPubliColum.setCellValueFactory(new PropertyValueFactory<>("dataPubli"));





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
