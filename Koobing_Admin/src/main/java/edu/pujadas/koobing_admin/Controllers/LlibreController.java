package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.*;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.TableView2;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class LlibreController implements Initializable
{

    public ImageView avatarWorker;
    ArrayList<Llibre> listLlibres =new ArrayList<Llibre>();
    public TableView2<Llibre> taulaLlibres;
    public TableColumn<Llibre,Long> isbnColum;
    public TableColumn<Llibre,String> autorColum;
    public TableColumn<Llibre,String> editorColum;

    public TableColumn <Llibre,String> idiomaColum;
    public TableColumn<Llibre,String> genereColum;
    public TableColumn<Llibre,String> titleColum;
    public TableColumn<Llibre,String> versionColum;
    public TableColumn<Llibre, Date> dataPubliColum;
    Parent root;
    Stage stage;
    Scene scene;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Book Screen");
        loadWorkerInfo();
        loadLlibres();
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
     * Metode per carregar les dades del llibre desde la base de dades MYSQL
     */
    public void loadLlibres()
    {
        try
        {
            // classe per agafar la info de la base de dades
            GestioLlibre gestioLlibre = new GestioLlibre();
            listLlibres = gestioLlibre.conusltar10Llibres();



            //observablelist llibres
            ObservableList<Llibre> observableListLlibre = FXCollections.observableArrayList(
                    listLlibres
            );

            taulaLlibres.setItems(observableListLlibre);

            isbnColum.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
            autorColum.setCellValueFactory(cellData -> {

                Autor actor = cellData.getValue().getAutor();
                String nombreAutor = actor.getNomAutor();
                return new SimpleStringProperty(nombreAutor);

            });
            editorColum.setCellValueFactory(cellData -> {
                Editorial editor = cellData.getValue().getEditor();
                String nomEditor = editor.getNomEditor();
                return new SimpleStringProperty(nomEditor);
            });
            idiomaColum.setCellValueFactory(cellData ->{
                Idioma idioma = cellData.getValue().getIdioma();
                String nomIdioma = idioma.getNomIdioma();
                return new SimpleStringProperty(nomIdioma);
            });
            genereColum.setCellValueFactory(cellData ->{
                Genere genere = cellData.getValue().getGenere();
                String nomGenere = genere.getNomGenere();
                return new SimpleStringProperty(nomGenere);
            });
            titleColum.setCellValueFactory(new PropertyValueFactory<>("titol"));
            versionColum.setCellValueFactory(new PropertyValueFactory<>("versio"));
            dataPubliColum.setCellValueFactory(new PropertyValueFactory<>("dataPubli"));





        }
        catch (Exception e)
        {
            System.out.println("Error loading data LLibres : " + e.getMessage());
        }
    }


    /**
     * Metode per afegir un llibre a la base de dades
     * @param event ActionEvent
     */
    public void onAddBook(ActionEvent event)
    {
        try
        {

            // test isbn
            TextField isbnInput = new TextField();
            TextField versionInput = new TextField();

            //format per el isbn
            TextFormatter<Integer> isbnFormat = new TextFormatter<>(change -> {
                if (change.getControlNewText().matches("\\d*")) {
                    return change;
                } else {
                    return null;
                }
            });

            //format per la versio que nomes utilizi un numeric
            TextFormatter<Integer> versionFormat = new TextFormatter<>(change -> {
                if (change.getControlNewText().matches("\\d*")) {
                    return change;
                } else {
                    return null;
                }
            });


            //inputs number format in this case Long
            isbnInput.setTextFormatter(isbnFormat);
            versionInput.setTextFormatter(versionFormat);

            //comboboxes

            ComboBox<String> autorComboBox = new ComboBox<String>();
            ComboBox<String> editorialComboBox = new ComboBox<String>();
            ComboBox<String>idiomaComboBox = new ComboBox<String>();
            ComboBox<String> genereComboBox = new ComboBox<String>();

            //afegint la info dels autors
            addDataAllComboBox(autorComboBox,editorialComboBox,idiomaComboBox,genereComboBox);



            //  Creacio dels Textes
            TextInputDialog titolInput = new TextInputDialog();
            //data
            DatePicker dataPubliInput = new DatePicker();

            //crear el gridpane per posar els 2 camps a l'hora
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            gridPane.addRow(0, new Label("Digues el ISBN del llibre: "),isbnInput);
            gridPane.addRow(1,new Label("Digues el autor") ,autorComboBox);
            gridPane.addRow(2, new Label("Entra la Editorial: "), editorialComboBox);
            gridPane.addRow(3, new Label("Idioma: "),idiomaComboBox);
            gridPane.addRow(4, new Label("Genere: "),genereComboBox);
            gridPane.addRow(5, new Label("Titol: "),titolInput.getEditor());
            gridPane.addRow(6, new Label("Versio: "),versionInput);
            gridPane.addRow(7, new Label("Data Publi "),dataPubliInput);

            // Mostrar los dos diálogos en la misma ventana
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setTitle("Afegir nou Llibre");
            alert.setHeaderText("Introduïu les noves dades del llibre:");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Esperar a que el usuario presione OK o Cancel
            Optional<ButtonType> resultat = alert.showAndWait();
            if (resultat.isPresent() && resultat.get() == ButtonType.OK)
            {
                //insertar tot el introduit al formulari en el objecte llibre
                Llibre llibre = new Llibre();

                llibre.setISBN(Long.parseLong(isbnInput.getText()));

                //autor
                String nomAutor =autorComboBox.getValue();
                GestioAutor gestioAutor = new GestioAutor();
                Autor autor = gestioAutor.findAutorByNom(nomAutor);
                llibre.setAutor(autor);

                //editorial
                String nomEditorial = editorialComboBox.getValue();
                GestioEditorial gestioEditorial = new GestioEditorial();
                Editorial editorial = gestioEditorial.findEditorialByName(nomEditorial);
                llibre.setEditor(editorial);

                //idioma
                String nomIdioma = idiomaComboBox.getValue();
                GestioIdioma gestioIdioma = new GestioIdioma();
                Idioma idioma = gestioIdioma.findIdiomaByName(nomIdioma);
                llibre.setIdioma(idioma);

                //genere
                String nomGenere = genereComboBox.getValue();
                GestioGenere gestioGenere = new GestioGenere();
                Genere genere = gestioGenere.findGenereByName(nomGenere);
                llibre.setGenere(genere);


                // final stuff llibre
                llibre.setTitol(titolInput.getEditor().getText());
                llibre.setVersio(Integer.parseInt(versionInput.getText()));
                LocalDate data = dataPubliInput.getValue();
                Date d = Date.valueOf(data);
                llibre.setDataPubli(d);

                // Actualizar la tabla
                taulaLlibres.refresh();

                //actualizar la base de dades
                GestioLlibre gestioLlibre = new GestioLlibre();
                gestioLlibre.crearLlibre(llibre);

                switchToLlibre(event);
            }


        }
        catch (Exception e)
        {
            System.out.println("Error Insert Llibre: "+ e.getMessage());
        }


    }

    /**
     * Metode per eliminar un llibre
     * @param event
     */
    public void deleteBook(ActionEvent event)
    {
        //gestio llibre per poder comprovar si el llibre esta en la base de dades
        // and per poder eliminar desde la base de dades
        GestioLlibre gestioLlibre = new GestioLlibre();
        //confirmacion
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        //error
        Alert wrong = new Alert(Alert.AlertType.ERROR);

        // mostrar el una alerta de tipus confirmacio per poder eliminar el llibre
        alerta.setTitle("Confirmación");
        alerta.setHeaderText(null);
        alerta.setContentText("Estàs segur de que vols continuar?");


        Optional<ButtonType> resultado = alerta.showAndWait();


        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Llibre llibre = taulaLlibres.getSelectionModel().getSelectedItem();
            if (llibre != null) {
                boolean isReserved = gestioLlibre.hayReservasActivas(llibre.getISBN());

                if (isReserved) {
                    wrong.setTitle("Error");
                    wrong.setHeaderText(null);
                    wrong.setContentText("Aquest llibre no es pot eliminar, esta en una reserva activa");
                    wrong.show();
                }
                else {
                    Alert sucessAlert = new Alert(Alert.AlertType.INFORMATION);
                    sucessAlert.setTitle("Success!");
                    sucessAlert.setHeaderText("Has eliminat llibre!");
                    sucessAlert.setContentText("Llibre s'ha eliminat correctament");
                    sucessAlert.show();


                    //delte to memory
                    ObservableList<Llibre> itemsLlibres = taulaLlibres.getItems();
                    itemsLlibres.remove(llibre);

                    // delete bd
                    try
                    {
                        //eliminar llibre de la base de dades and refrescar la taula
                        gestioLlibre.eliminarLlibre(llibre.getISBN());
                        taulaLlibres.refresh();

                    }
                    catch (Exception e)
                    {
                        System.out.println("Error deleting llibre : " + e.getMessage());
                    }

                }
            }

        }

    }

    /**
     * Metode per modifcar un llibre
     * @param event Action evnet
     * @throws Exception exception
     */
    public void onModifyLLibre(ActionEvent event) throws Exception
    {
        Llibre book = taulaLlibres.getSelectionModel().getSelectedItem();
        if(book != null) {
            // Creacio dels Textes

            ComboBox<String> autors = new ComboBox<String>();
            ComboBox<String> editorials = new ComboBox<String>();
            ComboBox<String> idioma = new ComboBox<String>();
            ComboBox<String> genere = new ComboBox<String>();
            TextField titol = new TextField();
            TextField version = new TextField();
            DatePicker dataPublicacio = new DatePicker();


            //crear el gridpane per posar els 2 camps a l'hora
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);


            gridPane.addRow(0, new Label("Nou Tittol: "), titol);
            gridPane.addRow(1, new Label("Nou Autor:"), autors);
            gridPane.addRow(2, new Label("Editorial: "), editorials);
            gridPane.addRow(3, new Label("Idioma: "), idioma);
            gridPane.addRow(4, new Label("Genere: "), genere);
            gridPane.addRow(5, new Label("Versio: "), version);


            // Mostrar los dos diálogos en la misma ventana
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setTitle("Modificar el LLibre");
            alert.setHeaderText("Introduïu les noves dades del Lllibre selecionat:");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


            // Esperar a que el usuario presione OK o Cancel
            Optional<ButtonType> resultat = alert.showAndWait();

            if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
                // Actualizar los campos 'nombre' y 'cognom' de la persona seleccionada
                //book.setNom(nomDialeg.getEditor().getText());
                //book.setCognom(cognomDialeg.getEditor().getText());
                //book.setPassword(passwordDialeg.getEditor().getText());

                // Actualizar la tabla
                taulaLlibres.refresh();

                //actualizar la base de dades
               /*GestioLlibre gestioLlibre = new GestioLlibre();
                gestioLlibre.modificarLlibre(book);*/
            }
        }
    }


    /**
     * Metode per poder emplanar les dades dels comboboxes de forma més rapida
     * @param autorComboBox Combobox de Autors
     * @param editorialComboBox Combobox de Editorials
     * @param idiomaComboBox ComboBox de Idiomas
     * @param genereComboBox Combobox de Generes
     */
    private void addDataAllComboBox(ComboBox<String> autorComboBox, ComboBox<String> editorialComboBox, ComboBox<String> idiomaComboBox,ComboBox<String> genereComboBox)
    {
        //afegint la info dels autors
        GestioAutor gestioAutor = new GestioAutor();
        ArrayList<Autor> listAutors = gestioAutor.consultarAutors();

        for(int i=0;i<listAutors.size();i++)
        {
            autorComboBox.getItems().addAll(listAutors.get(i).getNomAutor());
        }

        //afegint la info dels editorials
        GestioEditorial gestioEditorial = new GestioEditorial();
        ArrayList<Editorial> listEditorials = gestioEditorial.consultarEditorials();
        for (int i=0;i<listEditorials.size();i++)
        {
            editorialComboBox.getItems().addAll(listEditorials.get(i).getNomEditor());
        }
        //afegint la info dels idiomas

        GestioIdioma gestioIdioma = new GestioIdioma();
        ArrayList<Idioma> listIdiomas = gestioIdioma.consultarIdiomes();
        for (int i=0;i<listIdiomas.size();i++)
        {
            idiomaComboBox.getItems().addAll(listIdiomas.get(i).getNomIdioma());
        }

        //afegint la info dels generes

        GestioGenere gestioGenere = new GestioGenere();
        ArrayList<Genere> listGeneres = gestioGenere.consultarGeneres();
        for (int i=0;i<listGeneres.size();i++)
        {
            genereComboBox.getItems().addAll(listGeneres.get(i).getNomGenere());
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
