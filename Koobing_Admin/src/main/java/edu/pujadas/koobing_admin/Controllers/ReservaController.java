package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioReserva;
import edu.pujadas.koobing_admin.Models.*;
import edu.pujadas.koobing_admin.Utilities.TrabajadorSingleton;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.TableView2;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservaController implements Initializable
{

    Parent root;
    Stage stage;
    Scene scene;


    public ImageView avatarWorker;

    // taula
    public TableView2<Reserva> taulaReserves;

    public TableColumn<Reserva,String> nomUserColum;
    public TableColumn<Reserva,Integer> idReservaColum;
    public TableColumn<Reserva,String> nomWorkerColum;
    public TableColumn<Reserva,String> nomBiblioColum;
    public TableColumn<Reserva,String> bookTitleColum;
    public TableColumn<Reserva, Timestamp> dataHoraIniciColum;
    public TableColumn<Reserva,Timestamp> dataHoraFiciColum;



    ArrayList<Reserva> listReserves = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Reserva Screen!");
        loadWorkerInfo();
        loadInfoReserves();
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
            idReservaColum.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
            nomUserColum.setCellValueFactory(cellData ->{
                Usuari usuari = cellData.getValue().getUsuari();
                String nom = usuari.getNom();
                return new SimpleStringProperty(nom);
            });
            nomWorkerColum.setCellValueFactory(cellData->{
                Treballador treballador = cellData.getValue().getTreballador();
                String nom = treballador.getNom();
                return new SimpleStringProperty(nom);
            });
            nomBiblioColum.setCellValueFactory(cellData ->{
                Biblioteca bilbio = cellData.getValue().getBiblio();
                String nomBiblio = bilbio.getNomBiblioteca();
                return new SimpleStringProperty(nomBiblio);
            });
            bookTitleColum.setCellValueFactory(cellData ->{
                Llibre book = cellData.getValue().getLlibre();
                String titol = book.getTitol();
              return new SimpleStringProperty(titol);
            });
            dataHoraIniciColum.setCellValueFactory(new PropertyValueFactory<>("dataHoraReserva"));
            dataHoraFiciColum.setCellValueFactory(new PropertyValueFactory<>("dataHoraEntrega"));



        }
        catch (Exception e)
        {
            e.printStackTrace();    //degub error
        }
    }

    //todo fer els metodes de afegir , modificar , eliminar
    //todo IMPORNTANT se ha de compovar que la dataInici sigui més PETTIT que la de final . La final no pot ser més gran

    public void onAddReserva(ActionEvent event )
    {
        try {

        }
        catch (Exception e)
        {
            System.out.println("Error adding Reserva: " + e.getMessage());
        }


    }

    public void onDeleteReserva(ActionEvent event )
    {
        try {

        }
        catch (Exception e)
        {
            System.out.println("Error Delete Reserva: " + e.getMessage());
        }


    }
    public void onModifyReserva(ActionEvent event )
    {
        try {

        }
        catch (Exception e)
        {
            System.out.println("Error Modify Reserva: " + e.getMessage());
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
