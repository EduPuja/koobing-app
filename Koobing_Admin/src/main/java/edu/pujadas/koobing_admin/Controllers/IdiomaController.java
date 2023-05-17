package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioIdioma;
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

public class IdiomaController implements Initializable
{
    public TableView2<Idioma> taulaIdioma;
    public TableColumn<Idioma,Integer> idIdioma;
    public TableColumn<Idioma,String > nomIdioma;
    public ImageView avatarWorker;
    public Button trebaladorBtn;
    public Button bibliotecaBtn;
    public Button editioralBtn;
    public Button genereBtn;

    private ArrayList<Idioma> listIdioma = new ArrayList<Idioma>();
    Parent root;
    Stage stage;
    Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Idioam Screen !" );
        loadWorkerInfo();
        loadIdiomaInfo();
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
     * Metode per carregar les dades a la taula de idioma
     */
    private void loadIdiomaInfo()
    {
        GestioIdioma gestioIdioma = new GestioIdioma();
        listIdioma = gestioIdioma.consultarIdiomes();

        ObservableList<Idioma> observableList = FXCollections.observableArrayList(listIdioma);

        idIdioma.setCellValueFactory(new PropertyValueFactory<>("idIdioma"));
        nomIdioma.setCellValueFactory(new PropertyValueFactory<>("nomIdioma"));

        taulaIdioma.setItems(observableList);

    }


    public void onAddIdiom(ActionEvent event){
      
        try
        {
            //texfield
            TextField nomText = new TextField();

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            gridPane.addRow(0,new Label("Digues el nou idioma"),nomText);

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
                Idioma idioma = new Idioma();
                idioma.setNomIdioma(nomText.getText());

                GestioIdioma gestioIdioma = new GestioIdioma();
                gestioIdioma.crearIdioma(idioma);

                //actualizar la taula
                taulaIdioma.refresh();

                switchToIdioma(event);

            }


        }
        catch (Exception e)
        {
            System.out.println("Error Adding Idioma: " +e.getMessage());
        }

    }

    public void onRemoveIdiom(ActionEvent event){

        try {

            GestioIdioma gestioIdioma = new GestioIdioma();

            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            Alert wrong  = new Alert(Alert.AlertType.ERROR);

            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("Estàs segur de que vols continuar?");


            Optional<ButtonType> resultado = alerta.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK)
            {
                Idioma idioma = taulaIdioma.getSelectionModel().getSelectedItem();
                if(idioma !=null)
                {
                    boolean isIdomaInBook = gestioIdioma.isIdiomaInBook(idioma.getIdIdioma());

                    if(isIdomaInBook)
                    {
                        wrong.setTitle("Error");
                        wrong.setHeaderText("No es pot eliminar el idioma");
                        wrong.setContentText("Aquest idioma pertany a un llibre");
                        wrong.show();
                    }
                    else {
                        //alerta succes
                        Alert sucessAlert = new Alert(Alert.AlertType.INFORMATION);
                        sucessAlert.setTitle("Success!");
                        sucessAlert.setHeaderText("Has eliminat el idioma");
                        sucessAlert.setContentText("El idioma ha sigut eliminat correctament");
                        sucessAlert.show();

                        //delete memory
                        ObservableList<Idioma> idiomas = taulaIdioma.getItems();
                        idiomas.remove(idioma);

                        gestioIdioma.eliminarIdioma(idioma.getIdIdioma());

                        //actualizar la taula
                        taulaIdioma.refresh();

                        switchToIdioma(event);
                    }
                }
            }

        }
        catch (Exception e)
        {
            System.out.println("Error remvoing Idioma: " +e.getMessage());
        }

    }

    public void onModifyIdioma (ActionEvent event) {
        try
        {
            Idioma idioma = taulaIdioma.getSelectionModel().getSelectedItem();
            if(idioma!=null)
            {
                TextField changeName = new TextField(idioma.getNomIdioma());
                GridPane gridPane = new GridPane();
                gridPane.setVgap(10);
                gridPane.setHgap(10);

                gridPane.addRow(0,new Label("Modificar Idioma"),changeName);


                // Mostrar los dos diálogos en la misma ventana
                Alert alert = new Alert(Alert.AlertType.NONE);

                alert.setTitle("Modificar Treballador");
                alert.setHeaderText("Introduïu les noves dades del treballador:");
                alert.getDialogPane().setContent(gridPane);
                alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                Optional<ButtonType> resultat = alert.showAndWait();

                if (resultat.isPresent() && resultat.get() == ButtonType.OK)
                {
                    idioma.setNomIdioma(changeName.getText());

                    GestioIdioma gestioIdioma = new GestioIdioma();
                    gestioIdioma.modificarIdioma(idioma);
                    //actuaizar idioma
                    taulaIdioma.refresh();

                    switchToIdioma(event);

                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error modfiy Idioma: " +e.getMessage());
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
