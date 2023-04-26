package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioLlibre;
import edu.pujadas.koobing_admin.Database.GestioUsuari;
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


    public void onAddLlibre(ActionEvent event) 
    {
        System.out.println("hola aqui es el boto");


        Llibre llibre = taulaLlibres.getSelectionModel().getSelectedItem();
        if(llibre!=null)
        {
            // observable lists per poder poder crear els combobox
            ObservableList<Autor> observableAutor = FXCollections.observableArrayList();
            ObservableList<Idioma> observListIdioma = FXCollections.observableArrayList();
            ObservableList<Genere> observableGenere = FXCollections.observableArrayList();
            ObservableList<Editorial> observableEditorial = FXCollections.observableArrayList();

            //afegint cada observable list el seu objecte corresponent
            observableAutor.add(llibre.getAutor());
            observListIdioma.add(llibre.getIdioma());
            observableGenere.add(llibre.getGenere());
            observableEditorial.add(llibre.getEditor());


            //Dialegs
            TextInputDialog titolDialog = new TextInputDialog(llibre.getTitol());

            // Combobox de idiomas, autors,genere i editorial

            ComboBox<Autor> autors = new ComboBox<Autor>(observableAutor);
            ComboBox<Idioma> idiomas = new ComboBox<Idioma>(observListIdioma);
            ComboBox <Genere>generes = new ComboBox<Genere>(observableGenere);
            ComboBox <Editorial>editorials = new ComboBox<Editorial>(observableEditorial);


            //crear el gridpane per posar els 2 camps a l'hora
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);



            gridPane.addRow(0,new Label("Digues el Autor: ") ,autors.getEditor());
            gridPane.addRow(1, new Label("Digues el Idioma:"), idiomas.getEditor());
            gridPane.addRow(2, new Label("Digues el Genere:"),generes.getEditor());
            gridPane.addRow(3, new Label("Digues la Editorial:"),editorials.getEditor());

            // Mostrar los dos diálogos en la misma ventana
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setTitle("Afegir Llibre");
            alert.setHeaderText("Introduïu les noves dades per afegir un llibre");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Esperar a que el usuario presione OK o Cancel
            Optional<ButtonType> resultat = alert.showAndWait();

            if (resultat.isPresent() && resultat.get() == ButtonType.OK)
            {
                // Actualizar los campos 'nombre' y 'cognom' de la persona seleccionada
                llibre.setTitol(titolDialog.getEditor().getText());
                llibre.setAutor(observableAutor.get(0));


                // Actualizar la tabla
                taulaLlibres.refresh();

                //actualizar la base de dades
                //GestioLlibre gestioLlibre = new GestioLlibre();
                //gestioLlibre.crearLlibre(llibre);

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
