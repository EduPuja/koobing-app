package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.GestioTreballador;
import edu.pujadas.koobing_admin.Models.Treballador;
import edu.pujadas.koobing_admin.Utilities.Validation;
import javafx.beans.property.SimpleBooleanProperty;
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

        taulaTreballadors.setItems(obserListUser);

        idTreballador.setCellValueFactory(new PropertyValueFactory<>("id"));
        dniColum.setCellValueFactory(new PropertyValueFactory<>("dni"));
        nomColum.setCellValueFactory(new PropertyValueFactory<>("nom"));
        cognomColum.setCellValueFactory(new PropertyValueFactory<>("cognom"));
        dataNaixColum.setCellValueFactory(new PropertyValueFactory<>("dataNaix"));
        emailColum.setCellValueFactory(new PropertyValueFactory<>("email"));
        numSegSocialColum.setCellValueFactory(new PropertyValueFactory<>("numSegSocial"));
        isAdminColum.setCellValueFactory(cellData ->{
            int isAdmin = cellData.getValue().isAdmin();
            if(isAdmin == 1)
            {
                return new SimpleBooleanProperty(true);
            }
            return new SimpleBooleanProperty(false);
        });


    }

    /**
     * Metode que s'utiliza per inserir un treballador quant un admin fa click en el butó
     * de afegir un treballador
     * @param event ActionEvent
     */
    public void onInsertarTreballador(ActionEvent event)
    {
        try
        {
            // Creacio dels Textes

            TextInputDialog dniDialeg = new TextInputDialog();
            TextInputDialog nomDialeg = new TextInputDialog();
            TextInputDialog cognomDialeg = new TextInputDialog();
            DatePicker dataNaix = new DatePicker();
            TextInputDialog emailDialeg = new TextInputDialog();
            PasswordField passwordField = new PasswordField();

            //stuff treballador
            TextInputDialog numSegSocialDialeg = new TextInputDialog();
            CheckBox adminDialeg = new CheckBox();

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
            gridPane.addRow(6, new Label("Num Seguretat Social: "),numSegSocialDialeg.getEditor());
            gridPane.addRow(7, new Label("Admin: "),adminDialeg);


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
                // creacio d'un treballador
                Treballador treballador = new Treballador();


                // ---- START VALIDATION-- //
                boolean okaDNI = Validation.isValidDni(dniDialeg.getEditor().getText());

                if(okaDNI) {
                    treballador.setDni(dniDialeg.getEditor().getText());
                    treballador.setNom(nomDialeg.getEditor().getText());
                    treballador.setCognom(cognomDialeg.getEditor().getText());

                    boolean okaEmail = Validation.isValidEmail(emailDialeg.getEditor().getText());

                    if(okaEmail)
                    {
                        treballador.setEmail(emailDialeg.getEditor().getText());
                    }
                    else
                    {
                        //todo posar una alert
                        System.out.println("Email correu electrònic no és un correu electrònic valid");
                    }
                }
                else
                {
                    System.out.println("DNI INVALID ");
                }




                /*Treballador treballador = new Treballador();

                // validacio del dni
                if(Validation.isValidDni(dniDialeg.getEditor().getText()))
                {
                    treballador.setDni(dniDialeg.getEditor().getText());
                }




                treballador.setNom(nomDialeg.getEditor().getText());
                treballador.setCognom(cognomDialeg.getEditor().getText());


                treballador.setEmail(emailDialeg.getEditor().getText());

                LocalDate data= dataNaix.getValue();
                Date dataSQL = Date.valueOf(data);
                treballador.setDataNaix(dataSQL);
                treballador.setPassword(passwordField.getText());


                treballador.setNumSegSocial(numSegSocialDialeg.getEditor().getText());
                treballador.setAdmin(adminDialeg.isSelected());




                // Actualizar la tabla
                taulaTreballadors.refresh();

                //actualizar la base de dades
               // GestioTreballador gestioTreballador = new GestioTreballador();
                //gestioTreballador.crearTreballador(treballador);

               // switchToTreballador(event);*/
            }


        }
        catch (Exception e)
        {
            System.out.println("Error: "+ e.getMessage());
        }

    }


    /**
     *Aquesta funció permet eliminar un usuari de la base de dades i la seva taula corresponent.
     * Es mostra una alerta per confirmar l'eliminació i s'ha de respondre si es vol acceptar o no.
     * És important tenir en compte que l'eliminació és permanent i les dades de l'usuari seleccionat es perdran definitivament.
     */
    public void onDeleteTreballador()
    {

        GestioTreballador gestioTreballador = new GestioTreballador();


        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        Alert wrong  = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("Estàs segur de que vols continuar?");

        Optional<ButtonType> resultado = alerta.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK)
        {
            //objecte treballador
            Treballador treballador = taulaTreballadors.getSelectionModel().getSelectedItem();
            if(treballador != null) {
                //comprovar si no esta en la taula reserves
                boolean workerReserved = gestioTreballador.isTreballadorReserved(treballador.getId());
                if(workerReserved)
                {
                    // no es pot elimianr :(
                    wrong.setTitle("Error");
                    wrong.setHeaderText("No es pot eliminar el treballador");
                    wrong.setContentText("El treballador ha fet reserves");
                    wrong.show();
                }
                else {
                    //alerta succes
                    Alert sucessAlert = new Alert(Alert.AlertType.INFORMATION);
                    sucessAlert.setTitle("Success!");
                    sucessAlert.setHeaderText("Has eliminat el treballador!");
                    sucessAlert.setContentText("El treballador ha sigut eliminat correctament");
                    sucessAlert.show();

                    //delete memory
                    ObservableList<Treballador> itemsTreballador = taulaTreballadors.getItems();
                    itemsTreballador.remove(treballador);

                    try
                    {
                        //delete database
                        gestioTreballador.eliminarTreballador(treballador.getId());
                        taulaTreballadors.refresh();
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error Deleting a Worker: "+ e.getMessage());
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




    public void onEditarTreballador(ActionEvent event)
    {
        Treballador treballador = taulaTreballadors.getSelectionModel().getSelectedItem();
        if(treballador != null)
        {
            // Creacio dels Textes
            TextInputDialog nomDialeg = new TextInputDialog(treballador.getNom());
            TextInputDialog cognomDialeg = new TextInputDialog(treballador.getCognom());

            //poner la contrassenya tipo passwordfield
            PasswordField passwordField = new PasswordField();
            //passwordField.setText(treballador.getPassword());
            TextInputDialog passwordDialeg = new TextInputDialog(treballador.getPassword());


            //crear el gridpane per posar els 2 camps a l'hora
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);



            gridPane.addRow(0,new Label("Nou Nom: ") ,nomDialeg.getEditor());
            gridPane.addRow(1, new Label("Nou Cognom:"), cognomDialeg.getEditor());
            gridPane.addRow(2, new Label("Nova Contrassenya"),passwordField);


            // Mostrar los dos diálogos en la misma ventana
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setTitle("Modificar Treballador");
            alert.setHeaderText("Introduïu les noves dades del treballador:");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);



            // Esperar a que el usuario presione OK o Cancel
            Optional<ButtonType> resultat = alert.showAndWait();

            if (resultat.isPresent() && resultat.get() == ButtonType.OK)
            {
                // Actualizar los campos 'nombre' y 'cognom' de la persona seleccionada
                treballador.setNom(nomDialeg.getEditor().getText());
                treballador.setCognom(cognomDialeg.getEditor().getText());
                treballador.setPassword(passwordDialeg.getEditor().getText());

                // Actualizar la tabla
                taulaTreballadors.refresh();

                //actualizar la base de dades
                GestioTreballador gestioTreballador = new GestioTreballador();
                gestioTreballador.modificarTreballador(treballador);
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
