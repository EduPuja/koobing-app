package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioGenere;
import edu.pujadas.koobing_admin.Models.Genere;
import edu.pujadas.koobing_admin.Models.Idioma;
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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class GenereController implements Initializable
{

    Parent root;
    Stage stage;
    Scene scene;

    public ImageView avatarWorker;

    //taula
    public TableView2<Genere> taulaGenere;
    public TableColumn<Genere,Integer> idGenereColum;
    public TableColumn<Genere,String > descripGenereColum;

    private ArrayList<Genere> listGenere = new ArrayList<Genere>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Genere Screen! ");
        loadWorkerInfo();
        loadGenereInfo();
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
                }



            }

        }
        catch (Exception e) {
            System.out.println("Error loading worker info: " + e.getMessage());
        }
    }


    /**
     * Metode per carregar la informacio de la taula genere
     */
    private void loadGenereInfo()
    {
        GestioGenere gestioGenere = new GestioGenere();
        listGenere = gestioGenere.consultarGeneres();


        ObservableList<Genere> observableList = FXCollections.observableArrayList(listGenere);

        idGenereColum.setCellValueFactory(new PropertyValueFactory<>("idGenere"));
        descripGenereColum.setCellValueFactory(new PropertyValueFactory<>("nomGenere"));
        taulaGenere.setItems(observableList);

    }


    /**
     * Metode que quant fas click al buto afegir , pots afegir un genere
     * @param event ActionEvent
     */

    public void onAddGenere(ActionEvent event)
    {
        try {
            TextField nouGenere = new TextField();
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            gridPane.addRow(0,new Label("Digues el nou genere"),nouGenere);

            // Mostrar los dos diálogos en la misma ventana
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setTitle("Afegir nou Idioma");
            alert.setHeaderText("Introdueix el nou idioma");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Esperar a que el usuario presione OK o Cancel
            Optional<ButtonType> resultat = alert.showAndWait();
            if(resultat.isPresent() && resultat.get() == ButtonType.OK)
            {
                Genere genere = new Genere();
                genere.setNomGenere(nouGenere.getText());

                GestioGenere gestioGenere  = new GestioGenere();
                gestioGenere.crearGenere(genere);

                //actualizar la taula
                taulaGenere.refresh();;

                switchToGenere(event);
            }
        }
        catch (Exception e) {
            System.out.println("Error Afegint un nou Genere : " +e.getMessage());
        }
    }

    /**
     * Metode per eliminar un genere fent click al buto de eliminar
     * @param event ActionEvent
     */
    public void onDeleteGenere(ActionEvent event)
    {
        try
        {
            GestioGenere gestioGenere = new GestioGenere();
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            Alert wrong  = new Alert(Alert.AlertType.ERROR);

            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("Estàs segur de que vols continuar?");


            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK)
            {
                Genere genere = taulaGenere.getSelectionModel().getSelectedItem();
                if(genere!= null)
                {
                    boolean isGenereInBook = gestioGenere.isGenereInBook(genere.getIdGenere());
                    if(isGenereInBook)
                    {
                        wrong.setTitle("Error");
                        wrong.setHeaderText("No es pot eliminar aquest Genere");
                        wrong.setContentText("Aquest genere està assignat a un llibre o a varis");
                        wrong.show();
                    }
                    else
                    {
                        //alerta succes
                        Alert sucessAlert = new Alert(Alert.AlertType.INFORMATION);
                        sucessAlert.setTitle("Success!");
                        sucessAlert.setHeaderText("Has eliminat el idioma");
                        sucessAlert.setContentText("El idioma ha sigut eliminat correctament");
                        sucessAlert.show();

                        //delete memory
                        ObservableList<Genere> generes = taulaGenere.getItems();
                        generes.remove(genere);

                        gestioGenere.eliminarGenere(genere.getIdGenere());

                        //actualizar la taula
                        taulaGenere.refresh();

                        switchToGenere(event);
                    }
                }
            }

        }
        catch (Exception e)
        {
            System.out.println("Error al eliminar el genere : " +e.getMessage());
        }
    }

    public void onModifyGenere(ActionEvent event)
    {
        try {

        }
        catch (Exception e)
        {
            System.out.println("Error modificar el genere : " +e.getMessage());
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
