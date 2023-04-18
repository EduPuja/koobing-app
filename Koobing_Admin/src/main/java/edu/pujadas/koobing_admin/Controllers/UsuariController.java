package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioUsuari;
import edu.pujadas.koobing_admin.Models.Genere;
import edu.pujadas.koobing_admin.Models.Usuari;
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
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.TableView2;

import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsuariController implements Initializable
{

    public Button addBtn;
    public Button modifyBtn;
    public Button deleteBtn;
    Parent root ;
    Stage stage;
    Scene scene;

    //stuff taules
    public TableView2<Usuari> taulaUsuaris;
    public TableColumn<Usuari,Integer> idUsuariColum;
    public TableColumn<Usuari,String> dniColum;

    public TableColumn<Usuari,String>nomColum;
    public TableColumn<Usuari,String>cognomColum;
    public TableColumn<Usuari, Date>dataNaixColum;
    public TableColumn<Usuari,String> emailColum;

    public TableColumn<Usuari,String> passwordColum;


    private ArrayList<Usuari> listUsuaris = new ArrayList<Usuari>();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        System.out.println("Carregant el Usuari");
        loadInfoUser();
    }


    public void loadInfoUser()
    {
        GestioUsuari gestioUsuari = new GestioUsuari();

       listUsuaris= gestioUsuari.consultarUsuaris();

       ObservableList<Usuari> obserListUser = FXCollections.observableArrayList(
                listUsuaris
        );

       taulaUsuaris.setItems(obserListUser);

        idUsuariColum.setCellValueFactory(new PropertyValueFactory<>("id"));
        dniColum.setCellValueFactory(new PropertyValueFactory<>("dni"));
        nomColum.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cognomColum.setCellValueFactory(new PropertyValueFactory<>("cognom"));
        dataNaixColum.setCellValueFactory(new PropertyValueFactory<>("dataNaix"));
        emailColum.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordColum.setCellValueFactory(new PropertyValueFactory<>("password"));
    }


    /**
     *Aquesta funció permet eliminar un usuari de la base de dades i la seva taula corresponent.
     * Es mostra una alerta per confirmar l'eliminació i s'ha de respondre si es vol acceptar o no.
     * És important tenir en compte que l'eliminació és permanent i les dades de l'usuari seleccionat es perdran definitivament.
     */
    public void onRowDelete()
    {

        //como puedo comprovar que la fila este selecionada?
        // eliminar a memoria , aixo no elimina a la base de dades

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        Alert wrong  = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("Estàs segur de que vols continuar?");

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK)
        {

           System.out.println("Button Eliminar selected");

            Usuari user = taulaUsuaris.getSelectionModel().getSelectedItem();
            if(user == null)
            {
                wrong.setTitle("Error");
                wrong.setHeaderText(null);
                wrong.setContentText("Seleciona la fila que vols eliminar");
            }
            else
            {
                ObservableList<Usuari> itemsUser = taulaUsuaris.getItems();
                itemsUser.remove(user);
                //part que elimina de la base de dades
                GestioUsuari gestioUsuari = new GestioUsuari();
                gestioUsuari.eliminarUsuari(user.getDni());

            }

        }
        else
        {
            // Acción a realizar cuando se hace clic en el botón "Cancelar" o se cierra la alerta
            System.out.println("El usuario ha hecho clic en Cancelar o ha cerrado la alerta");
        }




    }

    public void onRowModify()
    {
        Usuari user = taulaUsuaris.getSelectionModel().getSelectedItem();
        if(user != null)
        {
            TextInputDialog dialog = new TextInputDialog(user.getNom());
            dialog.setTitle("Modificar el nom");
            dialog.setHeaderText("Modificar el nom del usuari selecionat");
            dialog.setContentText("Nou nom:");
            //resultat del dialeg
            Optional<String> resultado = dialog.showAndWait();

            if (resultado.isPresent()) {
                // Actualizar el campo 'nombre' de la persona seleccionada
                user.setNom(resultado.get());

                // Actualizar la tabla
                taulaUsuaris.refresh();
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
