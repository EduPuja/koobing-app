package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioAutor;
import edu.pujadas.koobing_admin.Database.GestioLlibre;
import edu.pujadas.koobing_admin.Models.Autor;
import edu.pujadas.koobing_admin.Models.Llibre;
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
import org.w3c.dom.Text;

import java.net.URL;
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

    ArrayList<Autor> listAutores = new ArrayList<>();
    Parent root;
    Scene scene;
    Stage stage;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAutorData();
    }

    public void loadAutorData()
    {
        try
        {
            GestioAutor gestioAutor = new GestioAutor();
            listAutores = gestioAutor.consultar10Autors();
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
            TextFormatter<Integer> idAutorFormatt = new TextFormatter<>(change -> {
                if (change.getControlNewText().matches("\\d*")) {
                    return change;
                } else {
                    return null;
                }
            });

            TextField idAutor = new TextField();
            idAutor.setTextFormatter(idAutorFormatt);

            TextField nomAutor = new TextField();
            DatePicker dataNaix = new DatePicker();

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            gridPane.addRow( 0,new Label("Digues el id de autor "),idAutor);
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
                autor.setIdAutor(Integer.parseInt(idAutor.getText()));
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
                    sucessAlert.setHeaderText("Has eliminat llibre!");
                    sucessAlert.setContentText("Llibre s'ha eliminat correctament");
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
