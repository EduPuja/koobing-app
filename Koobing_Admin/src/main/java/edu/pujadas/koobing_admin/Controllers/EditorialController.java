package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioEditorial;
import edu.pujadas.koobing_admin.Models.Editorial;
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

public class EditorialController implements Initializable
{

    Parent root ;
    Stage stage;
    Scene scene;


    public TableView2<Editorial> taulaEditorials;
    public TableColumn<Editorial,Integer> idEditorColum;
    public TableColumn<Editorial,String> nomEditorColum;


    private ArrayList<Editorial> listEditorials = new ArrayList<Editorial>();


    public ImageView avatarWorker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Editorial Screen! ");
        loadWorkerInfo();
        loadEditorialData();
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
     * Metode per carregar la informacio de la editorial
     */
    private void loadEditorialData()
    {
        listEditorials = new GestioEditorial().consultarEditorials();

        ObservableList<Editorial> observableList = FXCollections.observableArrayList(listEditorials);

        idEditorColum.setCellValueFactory(new PropertyValueFactory<>("idEditorial"));
        nomEditorColum.setCellValueFactory(new PropertyValueFactory("nomEditor"));

        taulaEditorials.setItems(observableList);
    }


    /**
     * Metode per afegir una editorial
     * @param event ActionEvent
     */
    public void onAddEditorial(ActionEvent event)
    {
        try
        {
            TextField nomEditor = new TextField();
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            gridPane.addRow(0, new Label("Digues el nom de la nova Editorial "),nomEditor);
            // Mostrar los dos diálogos en la misma ventana
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setTitle("Afegir nou Treballador");
            alert.setHeaderText("Introduïu les noves dades del treballador:");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Esperar a que el usuario presione OK o Cancel
            Optional<ButtonType> resultat = alert.showAndWait();
            if (resultat.isPresent() && resultat.get() == ButtonType.OK)
            {
                Editorial editor = new Editorial();
                editor.setNomEditor(nomEditor.getText());

                //insertar
                GestioEditorial gestioEditorial = new GestioEditorial();
                gestioEditorial.crearEditorial(editor);

                //referscar
                taulaEditorials.refresh();
                switchToEditorial(event);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error afegint Editorial: " +e.getMessage());
        }
    }

    /**
     * Metode per eliminar una editorial
     * @param event ActionEvent
     */
    public void onDeleteEditorial(ActionEvent event)
    {
        try {
            GestioEditorial gestioEditorial = new GestioEditorial();
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            Alert wrong  = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("Estàs segur de que vols continuar?");

            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK)
            {
                Editorial editorial = taulaEditorials.getSelectionModel().getSelectedItem();
                if(editorial != null)
                {
                    boolean isEditorInBook = gestioEditorial.isEditorInBook(editorial.getIdEditorial());
                    if(isEditorInBook)
                    {
                        // no es pot elimianr :(
                        wrong.setTitle("Error");
                        wrong.setHeaderText("No es pot eliminar la editorial");
                        wrong.setContentText("Aquesta editorial esta a llibres");
                        wrong.show();


                    }
                    else
                    {
                        //alerta succes
                        Alert sucessAlert = new Alert(Alert.AlertType.INFORMATION);
                        sucessAlert.setTitle("Success!");
                        sucessAlert.setHeaderText("Has eliminat el treballador!");
                        sucessAlert.setContentText("El treballador ha sigut eliminat correctament");
                        sucessAlert.show();

                        //delete memory
                        ObservableList<Editorial> editorialsObservable = taulaEditorials.getItems();
                        editorialsObservable.remove(editorial);

                        //delete database
                        gestioEditorial.eliminarEditor(editorial.getIdEditorial());

                        //refrescar
                        taulaEditorials.refresh();
                        switchToEditorial(event);

                    }
                }
            }


        }
        catch (Exception e)
        {
            System.out.println("Error Deleting Editor: " +e.getMessage());
        }
    }

    /**
     * Metode per modificar una editorial
     * @param event ActionEvent
     */
    public void onModifyEditorial(ActionEvent event)
    {
        try {
            Editorial editorial = taulaEditorials.getSelectionModel().getSelectedItem();
            if(editorial != null)
            {
                TextField novaEditorial = new TextField();
                GridPane gridPane = new GridPane();
                gridPane.setHgap(10);
                gridPane.setVgap(10);

                gridPane.addRow(0,new Label("Nou nom Editorial: ") , novaEditorial);
                // Mostrar los dos diálogos en la misma ventana
                Alert alert = new Alert(Alert.AlertType.NONE);

                alert.setTitle("Modificar Editorial");
                alert.setHeaderText("Introduïu les noves dades de la Editorial:");
                alert.getDialogPane().setContent(gridPane);
                alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);



                // Esperar a que el usuario presione OK o Cancel
                Optional<ButtonType> resultat = alert.showAndWait();

                if (resultat.isPresent() && resultat.get() == ButtonType.OK)
                {
                    editorial.setNomEditor(novaEditorial.getText());

                    //actualizar a la base de dades
                    GestioEditorial gestioEditorial = new GestioEditorial();
                    gestioEditorial.modificarEditorial(editorial);

                    //refrescar
                    taulaEditorials.refresh();
                    switchToEditorial(event);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error modify Editor: " +e.getMessage());
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
