package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioUsuari;
import edu.pujadas.koobing_admin.Models.Treballador;
import edu.pujadas.koobing_admin.Models.Usuari;
import edu.pujadas.koobing_admin.Utilities.TrabajadorSingleton;
import edu.pujadas.koobing_admin.Utilities.Validation;
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
import org.controlsfx.control.tableview2.TableView2;;
//import com.sun.javafx.scene.traversal.Traversal;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsuariController implements Initializable
{

    //buttons del mig per elimianar modificar i afegir un usuari
    public Button addBtn;
    public Button modifyBtn;
    public Button deleteBtn;



    public ImageView avatarWorker;

    // navabar lateral
    public Button homeBtn;
    public Button usuariBtn;
    public Button trebaladorBtn;
    public Button llibreBtn;
    public Button autorBtn;
    public Button bibliotecaBtn;
    public Button idiomaBtn;
    public Button genereBtn;
    public Button editioralBtn;
    public Button reservaBtn;
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

    //public TableColumn<Usuari,String> passwordColum;


    private ArrayList<Usuari> listUsuaris = new ArrayList<Usuari>();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        System.out.println("User Screen!");
        loadWorkerInfo();
        loadInfoUser();
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
        //passwordColum.setCellValueFactory(new PropertyValueFactory<>("password"));
    }


    /**
     *Aquesta funció permet eliminar un usuari de la base de dades i la seva taula corresponent.
     * Es mostra una alerta per confirmar l'eliminació i s'ha de respondre si es vol acceptar o no.
     * És important tenir en compte que l'eliminació és permanent i les dades de l'usuari seleccionat es perdran definitivament.
     */
    public void onDeleteUser()
    {
        GestioUsuari gestioUsuari = new GestioUsuari();

        //alerta per si passa algun error per poder mostrar per pantalla
        Alert wrong  = new Alert(Alert.AlertType.ERROR);

        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        //alerta de confirmacio
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("Estàs segur de que vols continuar?");

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK)
        {



            Usuari user = taulaUsuaris.getSelectionModel().getSelectedItem();
            if(user != null)
            {
                boolean userReserved = gestioUsuari.isUserReserved(user.getId());

                if(userReserved)
                {
                    // esta reservat no et pot eliminar
                    wrong.setTitle("Error");
                    wrong.setHeaderText(null);
                    wrong.setContentText("Aquest Usuari té una o més reserves ... No es pot eliminar!!");
                    wrong.show();
                }
                else
                {
                    Alert sucessAlert = new Alert(Alert.AlertType.INFORMATION);
                    sucessAlert.setTitle("Success!");
                    sucessAlert.setHeaderText("Has eliminat el usuari!");
                    sucessAlert.setContentText("El usuari se ha eliminat correctament");
                    sucessAlert.show();

                    //delte to memory
                    ObservableList<Usuari> itemsUser = taulaUsuaris.getItems();
                    itemsUser.remove(user);
                    // delete from database
                    try
                    {
                        gestioUsuari.eliminarUsuari(user.getDni());
                        taulaUsuaris.refresh();
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error deleting user :" +e.getMessage());
                    }

                }



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
            // Creacio dels Textes
            TextInputDialog nomDialeg = new TextInputDialog(user.getNom());
            TextInputDialog cognomDialeg = new TextInputDialog(user.getCognom());

            //poner la contrassenya tipo passwordfield
            PasswordField passwordField = new PasswordField();
           //passwordField.setText(user.getPassword());
            TextInputDialog passwordDialeg = new TextInputDialog(user.getPassword());


            //crear el gridpane per posar els 2 camps a l'hora
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);



            gridPane.addRow(0,new Label("Nou Nom: ") ,nomDialeg.getEditor());
            gridPane.addRow(1, new Label("Nou Cognom:"), cognomDialeg.getEditor());
            gridPane.addRow(2, new Label("Nova Contrassenya"),passwordField);


            // Mostrar los dos diálogos en la misma ventana
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setTitle("Modificar Usuari");
            alert.setHeaderText("Introduïu les noves dades de l'usuari:");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);



            // Esperar a que el usuario presione OK o Cancel
            Optional<ButtonType> resultat = alert.showAndWait();

            if (resultat.isPresent() && resultat.get() == ButtonType.OK)
            {
                // Actualizar los campos 'nombre' y 'cognom' de la persona seleccionada
                user.setNom(nomDialeg.getEditor().getText());
                user.setCognom(cognomDialeg.getEditor().getText());
                user.setPassword(passwordDialeg.getEditor().getText());

                // Actualizar la tabla
                taulaUsuaris.refresh();

                //actualizar la base de dades
                GestioUsuari gestioUsuari = new GestioUsuari();
                gestioUsuari.modificarUsuari(user);
            }
        }
    }


    /**
     * Aquest metode el crida el buto afegir un usuari.
     * El que crea es un pantalla temporal amb un formulari, si l'emplenes crees
     * un nou usuari, aquest es mostra en la taula
     */
    public void onInsertarUsuari(ActionEvent event)
    {
        Alert error = new Alert(Alert.AlertType.ERROR);

        try {
            // Creacio dels Textes

            TextInputDialog dniDialeg = new TextInputDialog();
            TextInputDialog nomDialeg = new TextInputDialog();
            TextInputDialog cognomDialeg = new TextInputDialog();
            DatePicker dataNaix = new DatePicker();
            TextInputDialog emailDialeg = new TextInputDialog();
            PasswordField passwordField = new PasswordField();



            //crear el gridpane per posar els 2 camps a l'hora
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            gridPane.addRow(0, new Label("Digues el Teu DNI: "), dniDialeg.getEditor());
            gridPane.addRow(1, new Label("Nou Nom: "), nomDialeg.getEditor());
            gridPane.addRow(2, new Label("Nou Cognom:"), cognomDialeg.getEditor());
            gridPane.addRow(3, new Label("Data de Naixament:"), dataNaix);
            gridPane.addRow(4, new Label("Correu Electroinc: "), emailDialeg.getEditor());
            gridPane.addRow(5, new Label("Contrassenya"), passwordField);



            // Mostrar los dos diálogos en la misma ventana
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setTitle("Afegir nou Usuari");
            alert.setHeaderText("Introduïu les noves dades del usuari:");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Esperar a que el usuario presione OK o Cancel
            Optional<ButtonType> resultat = alert.showAndWait();
            if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
                // creacio d'un usuari
                Usuari usuari = new Usuari();


                // ---- START VALIDATION-- //
                boolean okaDNI = Validation.isValidDni(dniDialeg.getEditor().getText());

                if (okaDNI) {
                    System.out.println("DNI CORRECT");
                    usuari.setDni(dniDialeg.getEditor().getText());
                    usuari.setNom(nomDialeg.getEditor().getText());
                    usuari.setCognom(cognomDialeg.getEditor().getText());

                    boolean okaEmail = Validation.isValidEmail(emailDialeg.getEditor().getText());

                    if (okaEmail) {
                        usuari.setEmail(emailDialeg.getEditor().getText());
                        usuari.setPassword(passwordField.getText());
                        LocalDate data = dataNaix.getValue();
                        Date dataSQL = Date.valueOf(data);
                        usuari.setDataNaix(dataSQL);


                        // Actualizar la tabla
                        taulaUsuaris.refresh();

                        //actualitzar la base de dades
                        GestioUsuari gestioUsuari = new GestioUsuari();
                        gestioUsuari.crearUsuari(usuari);
                        System.out.println("Usuari afegit correctament");

                        switchToUsuari(event);

                    } else {
                        error.setTitle("Error");
                        error.setHeaderText(null);
                        error.setContentText("Correu electrònic no és valid o és vuit");
                        error.showAndWait();

                    }
                } else {
                    error.setTitle("Error");
                    error.setHeaderText(null);
                    error.setContentText("El dni és incorrecte o vuit");
                    error.showAndWait();
                }


                // ---- END VALIDATION-- //
            }
        }
        catch (Exception e) {
            System.out.println("Error Inserting a User: " + e.getMessage());
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
