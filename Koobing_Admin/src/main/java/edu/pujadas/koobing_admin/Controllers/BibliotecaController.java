package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioBiblioteca;
import edu.pujadas.koobing_admin.Database.GestioPoblacio;
import edu.pujadas.koobing_admin.Models.Autor;
import edu.pujadas.koobing_admin.Models.Biblioteca;
import edu.pujadas.koobing_admin.Models.Poblacio;
import edu.pujadas.koobing_admin.Models.Treballador;
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

public class BibliotecaController implements Initializable
{


    Parent root;
    Scene scene;
    Stage stage;

    public ImageView avatarWorker;

    private ArrayList<Biblioteca>listBiblioteca = new ArrayList<>();

    public TableColumn<Biblioteca,Integer> idBiblio;
    public TableColumn<Biblioteca,String> nomBiblio;
    public TableColumn<Biblioteca,String> poblacio;
    public TableColumn<Biblioteca,Double> latitud;
    public TableColumn<Biblioteca,Double> longitud;
    public TableView2<Biblioteca> taulaBiblio;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Bilioteca Screen !");
        loadWorkerInfo();
        loadBibliotecaInfo();
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
     * Metode per carregar la info de la biblioteca a la taulaBilioteca
     */
    private void loadBibliotecaInfo()
    {
        try
        {
            GestioBiblioteca gestioBiblioteca = new GestioBiblioteca();
            listBiblioteca = gestioBiblioteca.consultarBiblioteques();
            ObservableList<Biblioteca> observableListBilio = FXCollections.observableArrayList(
                    listBiblioteca
            );
            idBiblio.setCellValueFactory(new PropertyValueFactory<>("idBiblioteca"));
            nomBiblio.setCellValueFactory(new PropertyValueFactory<>("nomBiblioteca"));
            poblacio.setCellValueFactory(cellData -> {
                Poblacio poblacio = cellData.getValue().getPoblacio();
                String nomPoblacio = poblacio.getNomPoble();
                return new SimpleStringProperty(nomPoblacio);
            });
            latitud.setCellValueFactory(new PropertyValueFactory<>("latitud"));
            longitud.setCellValueFactory(new PropertyValueFactory<>("longitud"));

            taulaBiblio.setItems(observableListBilio);

        }
        catch (Exception e)
        {
            System.out.println("Error loading biblioteca info: " + e.getMessage());
        }

    }

    public void onAddBiblioteca(ActionEvent event)
    {
        try
        {
            TextField nomBilio = new TextField();
            ComboBox<String> poblacions = new ComboBox<String>();
            TextField latitudBilio = new TextField();
            TextField longitudBilio = new TextField();

            //afegint un fomrat de tipus doble que no es pugi insertar text
            TextFormatter<Double> latitudFormat = new TextFormatter<>(change -> {
                if (change.getControlNewText().matches("\\d*\\.?\\d*")) {
                    return change;
                } else {
                    return null;
                }
            });
            //format per la longitud especific per poder posar decimals
            TextFormatter<Double> longitudFormat = new TextFormatter<>(change -> {
                if (change.getControlNewText().matches("\\d*\\.?\\d*")) {
                    return change;
                } else {
                    return null;
                }

            });

            latitudBilio.setTextFormatter(latitudFormat);
            longitudBilio.setTextFormatter(longitudFormat);


            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Afegir Bilioteca");
            alert.setHeaderText("Introdueix les dades de la biblioteca");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK)
            {
                Biblioteca biblioteca = new Biblioteca();
                biblioteca.setNomBiblioteca(nomBilio.getText());
               biblioteca.setLatitud(Double.parseDouble(latitudBilio.getText()));
               biblioteca.setLongitud(Double.parseDouble(longitudBilio.getText()));
               //poblacio
                String nomPoblacio = poblacions.getValue();
                GestioPoblacio gestioPoblacio = new GestioPoblacio();
                Poblacio p =gestioPoblacio.findPoblacioByName(nomPoblacio);
                biblioteca.setPoblacio(p);

                //actualizar la taula
                taulaBiblio.refresh();
                //insertar a la base de dades
                GestioBiblioteca gestioBiblioteca = new GestioBiblioteca();
                gestioBiblioteca.crearBiblioteca(biblioteca);

                //tornar a carregar la pantalla de biblioteca debut a que no s'actulaiza bé la taual
                switchToBiblioteca(event);
            }



        }
        catch (Exception e)
        {
            System.out.println("Error adding a biblio :" + e.getMessage());
        }
    }

    public void onEditBiblioteca(ActionEvent event)
    {

    }

    public void onDeleteBiblioteca(ActionEvent event)
    {

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
