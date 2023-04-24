package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioTreballador;
import edu.pujadas.koobing_admin.Database.GestioUsuari;
import edu.pujadas.koobing_admin.Models.Treballador;
import edu.pujadas.koobing_admin.Models.Usuari;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class TreballadorController implements Initializable
{

    private Scene scene;
    private Parent root;
    private Stage stage;

    // stuff treballador

    public TableView2<Treballador> taulaTreballadors;

    public TableColumn<Treballador, Integer> idTreballador;
    public TableColumn<Treballador,String> dniColum;
    public TableColumn<Treballador,String> nomColum;
    public TableColumn<Treballador,String> cognomColum;
    public TableColumn<Treballador, Date> dataNaixColum;
    public TableColumn <Treballador,String>emailColum;

    public TableColumn <Treballador,Integer> numSegSocialColum;
    public TableColumn <Treballador,Boolean>isAdminColum;


   private ArrayList<Treballador> listTreballador = new ArrayList<Treballador>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        System.out.println("Treballador Screen");
        loagWorkerData();
    }


    private void loagWorkerData()
    {
        GestioTreballador gestioTreballador = new GestioTreballador();

        listTreballador= gestioTreballador.consultarTreballadors();

        ObservableList<Treballador> obserListUser = FXCollections.observableArrayList(
                listTreballador
        );



        idTreballador.setCellValueFactory( new PropertyValueFactory<>("id"));
        dniColum.setCellValueFactory( new PropertyValueFactory<>("dni"));
        nomColum.setCellValueFactory( new PropertyValueFactory<>("nom"));
        cognomColum.setCellValueFactory( new PropertyValueFactory<>("cognom"));
        dataNaixColum.setCellValueFactory( new PropertyValueFactory<>("dataNaix"));
        emailColum.setCellValueFactory( new PropertyValueFactory<>("email"));
        numSegSocialColum.setCellValueFactory( new PropertyValueFactory<>("numSegSocial"));
        isAdminColum.setCellValueFactory( new PropertyValueFactory<>("admin"));



        taulaTreballadors.setItems(obserListUser);
    }



    /**
     * Metode que el que fa es inserir un Treballador
     */
    public void onInsertarTreballador()
    {
        try
        {
            // Creacio dels Textes
            TextInputDialog nomDialeg = new TextInputDialog();
            TextInputDialog cognomDialeg = new TextInputDialog();
            PasswordField passwordField = new PasswordField();

            TextInputDialog passwordDialeg = new TextInputDialog(passwordField.getText());
            TextInputDialog dniDialeg = new TextInputDialog();
            TextInputDialog emailDialeg = new TextInputDialog();
            DatePicker dataNaix = new DatePicker();
            TextInputDialog numSegSocialDialeg = new TextInputDialog();
            CheckBox isAdminDialeg = new CheckBox();

            //crear el gridpane per posar els 2 camps a l'hora
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            gridPane.addRow(0, new Label("Digues el Teu DNI: "),dniDialeg.getEditor());
            gridPane.addRow(1,new Label("Nou Nom: ") ,nomDialeg.getEditor());
            gridPane.addRow(2, new Label("Nou Cognom:"), cognomDialeg.getEditor());
            gridPane.addRow(3, new Label("Data de Naixament:"),dataNaix);
            gridPane.addRow(4, new Label("Correu Electroinc: "),emailDialeg.getEditor());
            gridPane.addRow(5, new Label("Contrassenya"),passwordField);
            gridPane.addRow(6, new Label("Numero de SegSocial: "),numSegSocialDialeg.getEditor());
            gridPane.addRow(7, new Label("Es Administrador? "),isAdminDialeg);


            // Mostrar los dos diálogos en la misma ventana
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setTitle("Modificar dades del Treballador");
            alert.setHeaderText("Introduïu les noves dades del Treballador");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Esperar a que el usuario presione OK o Cancel
            Optional<ButtonType> resultat = alert.showAndWait();
            if (resultat.isPresent() && resultat.get() == ButtonType.OK)
            {
                System.out.println("Success Worker!");
               /* Usuari user = new Usuari();
                // Actualizar los campos 'nombre' y 'cognom' de la persona seleccionada
                user.setNom(nomDialeg.getEditor().getText());
                user.setDni(dniDialeg.getEditor().getText());
                user.setCognom(cognomDialeg.getEditor().getText());
                user.setEmail(emailDialeg.getEditor().getText());

                LocalDate data= dataNaix.getValue();
                Date dataSQL = Date.valueOf(data);
                user.setDataNaix(dataSQL);
                user.setPassword(passwordDialeg.getEditor().getText());



                //actualizar la base de dades
               // GestioUsuari gestioUsuari = new GestioUsuari();
                //gestioUsuari.crearUsuari(user);

                // Actualizar la tabla
                taulaTreballadors.refresh();*/
            }


        }
        catch (Exception e)
        {
            System.out.println("Error: "+ e.getMessage());
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
