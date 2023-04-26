package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioLlibre;

import edu.pujadas.koobing_admin.Models.*;
import javafx.beans.property.SimpleStringProperty;
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

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.TableView2;

import java.net.URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class LlibreController implements Initializable
{

    ArrayList<Llibre> listLlibres =new ArrayList<Llibre>();
    public TableView2<Llibre> taulaLlibres;
    public TableColumn<Llibre,Long> isbnColum;
    public TableColumn<Llibre,String> autorColum;
    public TableColumn<Llibre,String> editorColum;

    public TableColumn <Llibre,String> idiomaColum;
    public TableColumn<Llibre,String> genereColum;
    public TableColumn<Llibre,String> titleColum;
    public TableColumn<Llibre,String> versionColum;
    public TableColumn<Llibre, Date> dataPubliColum;
    Parent root;
    Stage stage;
    Scene scene;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Book Screen");
        loadLlibres();
    }



    public void loadLlibres()
    {
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
            titleColum.setCellValueFactory(new PropertyValueFactory<>("titol"));
            versionColum.setCellValueFactory(new PropertyValueFactory<>("versio"));
            dataPubliColum.setCellValueFactory(new PropertyValueFactory<>("dataPubli"));





        }
        catch (Exception e)
        {
            System.out.println("Error loading data LLibres : " + e.getMessage());
        }
    }


    public void deleteBook(ActionEvent event)
    {
        //gestio llibre per poder comprovar si el llibre esta en la base de dades
        // and per poder eliminar desde la base de dades
        GestioLlibre gestioLlibre = new GestioLlibre();
        //confirmacion
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        //error
        Alert wrong = new Alert(Alert.AlertType.ERROR);

        // mostrar el una alerta de tipus confirmacio per poder eliminar el llibre
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("Estàs segur de que vols continuar?");


        Optional<ButtonType> resultado = alerta.showAndWait();


        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Llibre llibre = taulaLlibres.getSelectionModel().getSelectedItem();
            if (llibre != null) {
                boolean isReserved = gestioLlibre.hayReservasActivas(llibre.getISBN());

                if (isReserved) {
                    wrong.setTitle("Error");
                    wrong.setHeaderText(null);
                    wrong.setContentText("Aquest llibre no es pot eliminar, esta en una reserva activa");
                    wrong.show();
                }
                else {
                    Alert sucessAlert = new Alert(Alert.AlertType.INFORMATION);
                    sucessAlert.setTitle("Success!");
                    sucessAlert.setHeaderText("Has eliminat llibre!");
                    sucessAlert.setContentText("Llibre s'ha eliminat correctament");
                    sucessAlert.show();


                    //delte to memory
                    ObservableList<Llibre> itemsLlibres = taulaLlibres.getItems();
                    itemsLlibres.remove(llibre);

                    // delete bd
                    try
                    {
                        //eliminar llibre de la base de dades and refrescar la taula
                        gestioLlibre.eliminarLlibre(llibre.getISBN());
                        taulaLlibres.refresh();

                    }
                    catch (Exception e)
                    {
                        System.out.println("Error deleting llibre : " + e.getMessage());
                    }

                }
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
        root = FXMLLoader.load(getClass().getResource("/edu/pujadas/koobing_admin/screens/reserva.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
