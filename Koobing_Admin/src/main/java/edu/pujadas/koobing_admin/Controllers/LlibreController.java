package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.*;
import edu.pujadas.koobing_admin.Models.*;
import edu.pujadas.koobing_admin.Utilities.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.TableView2;

import java.io.ByteArrayInputStream;
import java.net.URL;

import java.sql.Blob;
import java.util.ArrayList;
import java.sql.Date;
import java.util.ResourceBundle;

public class LlibreController implements Initializable
{

    public ImageView avatarWorker;

    // taula biblioteca llibre
    public TableView2<LlibreBiblio> taulaBiblioLlibre;
    public TableColumn<LlibreBiblio,Integer> id;
    public TableColumn<LlibreBiblio,String> nomLlibre;
    public TableColumn<LlibreBiblio,String > nomBiblioteca;
    public TableColumn<LlibreBiblio,Integer> stock;
    public TableColumn<LlibreBiblio,Integer> dsponibles;


    // llibre

    ArrayList<Llibre> listLlibres = new ArrayList<Llibre>();
    public TableView2<Llibre> taulaLlibres;
    public TableColumn<Llibre,Long> isbnColum;
    public TableColumn<Llibre,String> autorColum;
    public TableColumn<Llibre,String> editorColum;

    public TableColumn<Biblioteca,String> bibliotecaColum;

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
        loadLlibreBiblioteca();
       // loadLlibres();
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

           // taulaLlibres.setItems(observableListLlibre);

            isbnColum.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
            /*bibliotecaColum.setCellValueFactory(cellData ->{
                Biblioteca biblioteca = cellData.getValue().getBiblioteca();
                String nomBiblioteca = biblioteca.getNomBiblioteca();
                return new SimpleStringProperty(nomBiblioteca);
            });*/
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
     * Metode per carregar dades de llibre biblioteca la nova relacio
     */
    public void loadLlibreBiblioteca()
    {
        taulaBiblioLlibre.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                LlibreBiblio llibreSeleccionado = taulaBiblioLlibre.getSelectionModel().getSelectedItem();

                if (llibreSeleccionado != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información del libro");
                    alert.setHeaderText(null);
                    alert.setContentText("ISBN: " + llibreSeleccionado.getBook().getISBN() +
                            "\nTítulo: " + llibreSeleccionado.getBook().getTitol() +
                            "\nAutor: " + llibreSeleccionado.getBook().getAutor().getNomAutor() +
                            // Agrega aquí más información del libro
                            "\nStock: " + llibreSeleccionado.getStock());

                    alert.showAndWait();
                }
            }
        });


        try
        {
            GestioLlibreBiblioteca gestioLlibreBiblioteca = new GestioLlibreBiblioteca();

            ArrayList<LlibreBiblio >listBiblioLLibre = gestioLlibreBiblioteca.consultarLlibreBiblioteca();

            ObservableList<LlibreBiblio > observableList= FXCollections.observableArrayList(listBiblioLLibre);

            taulaBiblioLlibre.setItems(observableList);

            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomLlibre.setCellValueFactory(cellData ->{
                Llibre book = cellData.getValue().getBook();
                String nomLLibre =book.getTitol();
                return new SimpleStringProperty(nomLLibre);
            });

            nomBiblioteca.setCellValueFactory(cellData ->{
                Biblioteca biblioteca = cellData.getValue().getBiblioteca();
                //int id = biblioteca.getIdBiblioteca();
                String nombiblio = biblioteca.getNomBiblioteca();
                return new SimpleStringProperty(nombiblio);
                //return new SimpleIntegerProperty(id).asObject();
            });

            stock.setCellValueFactory(new PropertyValueFactory<>("stock"));

            GestioLlibre gestioLlibre  =  new GestioLlibre();
            dsponibles.setCellValueFactory(cellData ->{
                Llibre llibre =cellData.getValue().getBook();
                int stock = cellData.getValue().getStock();

                int numReserves =gestioLlibre.getNumReservas(llibre.getISBN());
                if (numReserves != -1)
                {
                    int calcul = stock -1;

                    return new SimpleIntegerProperty(calcul).asObject();
                }
                else
                {
                    return new SimpleIntegerProperty(stock).asObject();
                }
            });


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    /**
     * Metode per afegir un llibre a la base de dades
     * @param event ActionEvent
     */
    /*public void onAddBook(ActionEvent event)
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

            ComboBox<Autor> autorComboBox = new ComboBox<Autor>();
            ComboBox<Editorial> editorialComboBox = new ComboBox<Editorial>();
            ComboBox<Idioma>idiomaComboBox = new ComboBox<Idioma>();
            ComboBox<Genere> genereComboBox = new ComboBox<Genere>();

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

                //Actualizar les dades del llibre selecionat

                //gestors
                GestioAutor gestioAutor = new GestioAutor();
                GestioEditorial gestioEditorial = new GestioEditorial();
                GestioIdioma  gestioIdioma = new GestioIdioma();
                GestioGenere gestioGenere = new GestioGenere();


                //converters
                AutorStringConverter converterAutor = new AutorStringConverter();
                EditorialStringConverter converterEditorial = new EditorialStringConverter();
                GenereStringConverter converterGenere = new GenereStringConverter();
                IdiomaStringConverter converterIdioma = new IdiomaStringConverter();

                //titol
                llibre.setTitol(titolInput.getEditor().getText());

                //autor
                int idAutor = converterAutor.getIdAutor(autorComboBox.getValue());
                Autor a = gestioAutor.findAutor(idAutor);
                llibre.setAutor(a);

                //editorial
                int idEditorial = converterEditorial.getIdEditor(editorialComboBox.getValue());
                Editorial e = gestioEditorial.findEditorial(idEditorial);
                llibre.setEditor(e);

                //genere
                int idGenere = converterGenere.getIdGenere(genereComboBox.getValue());
                Genere g = gestioGenere.findGenere(idGenere);
                llibre.setGenere(g);

                //idioma
                int idIdioma = converterIdioma.getIdIdioma(idiomaComboBox.getValue());
                Idioma i = gestioIdioma.findIdioma(idIdioma);
                llibre.setIdioma(i);

                //versio
                llibre.setVersio(Integer.parseInt(versionInput.getText()));
                //data
                LocalDate data = dataPubliInput.getValue();
                Date d = Date.valueOf(data);
                llibre.setDataPubli(d);

                //refresh
                taulaLlibres.refresh();

                //update database
                GestioLlibre gest = new GestioLlibre();
                gest.crearLlibre(llibre);


                //actualiar la pagina
                switchToLlibre(event);

            }


        }
        catch (Exception e)
        {
            System.out.println("Error Insert Llibre: "+ e.getMessage());
        }


    }*/

    /**
     * Metode per eliminar un llibre
     * @param event
     */
    /*public void deleteBook(ActionEvent event)
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
                    //ObservableList<Llibre> itemsLlibres = taulaLlibres.getItems();
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

    }*/

    /**
     * Metode per modifcar un llibre
     * @param event Action evnet
     * @throws Exception exception
     */
   /* public void onModifyLLibre(ActionEvent event) throws Exception
    {
        //Llibre book = taulaLlibres.getSelectionModel().getSelectedItem();
        if(book != null) {
            // Creacio dels Textes

            TextField titol = new TextField(book.getTitol());

            //autor
            ComboBox<Autor> autors = new ComboBox<Autor>();
            // editorial
            ComboBox<Editorial> editorials = new ComboBox<Editorial>();
            //idioma
            ComboBox<Idioma> idioma = new ComboBox<Idioma>();

            //genere
            ComboBox<Genere> genere = new ComboBox<Genere>();


            addDataAllComboBox(autors,editorials,idioma,genere);


            TextField version = new TextField(Integer.toString(book.getVersio()));

            DatePicker dataPublicacio = new DatePicker(book.getDataPubli().toLocalDate());

           // addDataAllComboBox(autors, editorials, idioma,genere);
            //crear el gridpane per posar els 2 camps a l'hora
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);


            gridPane.addRow(0, new Label("Nou Tittol: "), titol);
            gridPane.addRow(1, new Label("Escull nou Autor:"), autors);
            gridPane.addRow(2, new Label("Escull nova Editorial: "), editorials);
            gridPane.addRow(3, new Label("Escull el nou Idioma: "), idioma);
            gridPane.addRow(4, new Label("Escull el nou Genere: "), genere);
            gridPane.addRow(5, new Label("Versio: "), version);
            gridPane.addRow(6, new Label ("Data Publicicació: "), dataPublicacio);


            // Mostrar los dos diálogos en la misma ventana
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setTitle("Modificar el LLibre");
            alert.setHeaderText("Introduïu les noves dades del Lllibre selecionat:");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


            // Esperar a que el usuario presione OK o Cancel
            Optional<ButtonType> resultat = alert.showAndWait();

            if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
               //Actualizar les dades del llibre selecionat

                //gestors
                GestioAutor gestioAutor = new GestioAutor();
                GestioEditorial gestioEditorial = new GestioEditorial();
                GestioIdioma  gestioIdioma = new GestioIdioma();
                GestioGenere gestioGenere = new GestioGenere();


                //converters
                AutorStringConverter converterAutor = new AutorStringConverter();
                EditorialStringConverter converterEditorial = new EditorialStringConverter();
                GenereStringConverter converterGenere = new GenereStringConverter();
                IdiomaStringConverter converterIdioma = new IdiomaStringConverter();

                //titol
                book.setTitol(titol.getText());

                //autor
                int idAutor = converterAutor.getIdAutor(autors.getValue());
                Autor a = gestioAutor.findAutor(idAutor);
                book.setAutor(a);

                //editorial
                int idEditorial = converterEditorial.getIdEditor(editorials.getValue());
                Editorial e = gestioEditorial.findEditorial(idEditorial);
                book.setEditor(e);

                //genere
                int idGenere = converterGenere.getIdGenere(genere.getValue());
                Genere g = gestioGenere.findGenere(idGenere);
                book.setGenere(g);

                //idioma
                int idIdioma = converterIdioma.getIdIdioma(idioma.getValue());
                Idioma i = gestioIdioma.findIdioma(idIdioma);
                book.setIdioma(i);

                //versio
                book.setVersio(Integer.parseInt(version.getText()));
                //data
                LocalDate data = dataPublicacio.getValue();
                Date d = Date.valueOf(data);
                book.setDataPubli(d);

                //refresh
                taulaLlibres.refresh();

                //update database
                GestioLlibre gest = new GestioLlibre();
                gest.modificarLlibre(book);



            }
        }
    }*/


    /**
     * Metode per poder emplanar les dades dels comboboxes de forma més rapida
     * @param autorComboBox Combobox de Autors
     * @param editorialComboBox Combobox de Editorials
     * @param idiomaComboBox ComboBox de Idiomas
     * @param genereComboBox Combobox de Generes
     */
    /*private void addDataAllComboBox(ComboBox<Autor> autorComboBox, ComboBox<Editorial> editorialComboBox, ComboBox<Idioma> idiomaComboBox,ComboBox<Genere> genereComboBox)
    {
        AutorStringConverter converterAutor = new AutorStringConverter();
        GestioAutor gestioAutor = new GestioAutor();
        ArrayList<Autor> listAutors = gestioAutor.consultarAutors();

        autorComboBox.getItems().addAll(listAutors);
        autorComboBox.setConverter(converterAutor);

        // editorial


        EditorialStringConverter converterEditorial = new EditorialStringConverter();
        GestioEditorial gestioEditorial = new GestioEditorial();
        ArrayList<Editorial> listEditorials = gestioEditorial.consultarEditorials();
        editorialComboBox.getItems().addAll(listEditorials);
        editorialComboBox.setConverter(converterEditorial);



        //idioma

        IdiomaStringConverter idoiomaConverter = new IdiomaStringConverter();
        GestioIdioma gestioIdioma = new GestioIdioma();
        ArrayList<Idioma> listIdiomes = gestioIdioma.consultarIdiomes();
        idiomaComboBox.getItems().addAll(listIdiomes);
        idiomaComboBox.setConverter(idoiomaConverter);  //convertidor de idioma

        //genere

        GenereStringConverter genereConverter = new GenereStringConverter();
        GestioGenere  gestioGenre =  new GestioGenere();
        ArrayList<Genere> listGeneres = gestioGenre.consultarGeneres();
        genereComboBox.getItems().addAll(listGeneres);
        genereComboBox.setConverter(genereConverter);

    }*/



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
