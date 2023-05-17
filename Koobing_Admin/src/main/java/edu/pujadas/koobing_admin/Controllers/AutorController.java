package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioAutor;
import edu.pujadas.koobing_admin.Models.Autor;
import edu.pujadas.koobing_admin.Models.Treballador;
import edu.pujadas.koobing_admin.Utilities.TrabajadorSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.TableView2;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AutorController implements Initializable
{

    public TableView2<Autor>taulaAutors;
    public TableColumn<Autor,Integer> idAutor;
    public TableColumn<Autor,String> nomAutor;
    public TableColumn<Autor, Date> dataNaix;
    public ImageView avatarWorker;
    public Button trebaladorBtn;
    public Button bibliotecaBtn;
    public Button editioralBtn;
    public Button genereBtn;

    ArrayList<Autor> listAutores = new ArrayList<>();
    Parent root;
    Scene scene;
    Stage stage;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Autor Screen! :D");
        loadWorkerInfo();
        loadAutorData();
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

    public void loadAutorData()
    {
        try
        {
            GestioAutor gestioAutor = new GestioAutor();
            listAutores = gestioAutor.consultarAutors();
            //observablelist autors
            ObservableList<Autor> observableListAutors = FXCollections.observableArrayList(
                    listAutores
            );

            idAutor.setCellValueFactory(new PropertyValueFactory<>("idAutor"));
            nomAutor.setCellValueFactory(new PropertyValueFactory<>("nomAutor"));
            dataNaix.setCellValueFactory(new PropertyValueFactory<>("dataNaixAutor"));
            taulaAutors.setItems(observableListAutors);


        }
        catch (Exception e)
        {
            System.out.println("Failed to infoAutors ..." +e.getMessage());
        }
    }

    public void onAddAutor(ActionEvent event)
    {


        try
        {


            TextField nomAutor = new TextField();
            DatePicker dataNaix = new DatePicker();

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            //gridPane.addRow( 0,new Label("Digues el id de autor "),idAutor);
            gridPane.addRow(1, new Label("Digues el nom de autor "),nomAutor);
            gridPane.addRow(2,new Label("Digues la data de naixament del autor") ,dataNaix);

            //mostra alerta
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setTitle("Afegir nou autor");
            alert.setHeaderText("Introdueix les dades del autor");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> resultat = alert.showAndWait();
            if (resultat.isPresent() && resultat.get() == ButtonType.OK)
            {
                Autor autor = new Autor();

                autor.setNomAutor(nomAutor.getText());
                LocalDate data = dataNaix.getValue();
                Date d = Date.valueOf(data);
                autor.setDataNaixAutor(d);

                //actualizar la taula
                taulaAutors.refresh();;
                GestioAutor gestioAutor = new GestioAutor();
                gestioAutor.crearAutor(autor);
                switchToAutor(event);
            }

        }
        catch (Exception e)
        {
            System.out.println("Error al añadir autor: " +e.getMessage());
        }
    }


    /**
     * Metode per elimianr un autor de la base de dades y de la taula
     * @param event
     */
    public void onDeleteAutor(ActionEvent event)
    {
        GestioAutor gestioAutor = new GestioAutor();

        //confirmacion
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        //error
        Alert wrong = new Alert(Alert.AlertType.ERROR);
        // mostrar el una alerta de tipus confirmacio per poder eliminar el llibre
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("Estàs segur de que vols continuar?");

        Optional<ButtonType> resultado = alerta.showAndWait();

        if(resultado.isPresent() && resultado.get() == ButtonType.OK)
        {
            Autor autor = taulaAutors.getSelectionModel().getSelectedItem();


            if(autor !=null)
            {
                boolean reservas = gestioAutor.hayReservasAutor(autor.getIdAutor());
                if(reservas)
                {
                    wrong.setTitle("Error");
                    wrong.setHeaderText(null);
                    wrong.setContentText("Aquest autor no es pot eliminar perque te llibres ");
                    wrong.show();
                }
                else
                {
                    Alert sucessAlert = new Alert(Alert.AlertType.INFORMATION);
                    sucessAlert.setTitle("Success!");
                    sucessAlert.setHeaderText("Has eliminat el autor!");
                    sucessAlert.setContentText("Autor eliminat correctament");
                    sucessAlert.show();


                    //delte to memory
                    ObservableList<Autor> itmesAutors = taulaAutors.getItems();
                    itmesAutors.remove(autor);

                    // delete bd
                    try
                    {
                        //eliminar llibre de la base de dades and refrescar la taula
                        gestioAutor.eliminarAutor(autor.getIdAutor());
                        taulaAutors.refresh();

                    }
                    catch (Exception e)
                    {
                        System.out.println("Error deleting llibre : " + e.getMessage());
                    }
                }
            }
        }


    }


    public void onModifyAutor(ActionEvent event)
    {
        Autor autor = taulaAutors.getSelectionModel().getSelectedItem();
        if(autor!=null)
        {
            TextField nomAutor = new TextField(autor.getNomAutor());

            DatePicker dataNaix = new DatePicker();

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.addRow(0,new Label("Digues el nom del autor "),nomAutor);
            gridPane.addRow(1,new Label("Digues la data de naixament del autor"),dataNaix);

            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Modificar autor");
            alert.setHeaderText("Introdueix les noves dades del autor");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> resultat = alert.showAndWait();
            if(resultat.isPresent() && resultat.get() == ButtonType.OK)
            {
                //actualizar els camps del autor
                autor.setNomAutor(nomAutor.getText());
                Date data = Date.valueOf(dataNaix.getValue());
                autor.setDataNaixAutor(data);

                //update de table
                taulaAutors.refresh();

                //update in bbdd
                GestioAutor gestioAutor = new GestioAutor();
                gestioAutor.modificarAutor(autor);
            }
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
        root = FXMLLoader.load(getClass().getResource("/edu/pujadas/koobing_admin/screens/prestec.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
