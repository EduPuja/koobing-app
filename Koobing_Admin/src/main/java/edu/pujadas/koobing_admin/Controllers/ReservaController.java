package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioReserva;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.TableView2;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservaController implements Initializable
{
    public TableView2<Reserva> taulaReserves;
    public TableColumn<Reserva,Integer> idReserva;
    public TableColumn<Reserva,String> nomUser;
    public TableColumn<Reserva,String>nomWorker;
    public TableColumn<Reserva,String> bookTitle;
    public TableColumn<Reserva,String> nomBiblio;
    Parent root;
    Stage stage;
    Scene scene;


    ArrayList<Reserva> listReserves = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Reserva Screen!");
        loadInfoReserves();
    }



    public void loadInfoReserves()
    {
       // System.out.println("Loading info reserva"); // debug testing
        try
        {
            GestioReserva gestioReserva= new GestioReserva();
            listReserves = gestioReserva.consultarReserves();
            ObservableList<Reserva> observableListReserva = FXCollections.observableArrayList(
                    listReserves
            );
            //afegint el observable list en el tableview
            taulaReserves.setItems(observableListReserva);
            idReserva.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
            nomUser.setCellValueFactory(cellData ->{
                Usuari usuari = cellData.getValue().getUsuari();
                String nom = usuari.getNom();
                return new SimpleStringProperty(nom);
            });
            nomWorker.setCellValueFactory(cellData->{
                Treballador treballador = cellData.getValue().getTreballador();
                String nom = treballador.getNom();
                return new SimpleStringProperty(nom);
            });
            nomBiblio.setCellValueFactory(cellData ->{
                Biblioteca bilbio = cellData.getValue().getBiblio();
                String nomBiblio = bilbio.getNomBiblioteca();
                return new SimpleStringProperty(nomBiblio);
            });
            bookTitle.setCellValueFactory(cellData ->{
                Llibre book = cellData.getValue().getLlibre();
                String titol = book.getTitol();
              return new SimpleStringProperty(titol);
            });


        }
        catch (Exception e)
        {
            e.printStackTrace();    //degub error
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
