package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.*;
import edu.pujadas.koobing_admin.Models.*;
import edu.pujadas.koobing_admin.Utilities.*;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.TableView2;

import java.io.ByteArrayInputStream;
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

    // taula biblioteca llibre

    ArrayList<LlibreBiblio> listBiblioLLibre = new ArrayList<LlibreBiblio>();
    public TableView2<LlibreBiblio> taulaBiblioLlibre;
    public TableColumn<LlibreBiblio,Integer> id;
    public TableColumn<LlibreBiblio,String> nomLlibre;
    public TableColumn<LlibreBiblio,String > nomBiblioteca;
    public TableColumn<LlibreBiblio,Integer> stock;
    public TableColumn<LlibreBiblio,Integer> dsponibles;


    // llibre

    ArrayList<Llibre> listLlibres = new ArrayList<Llibre>();


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
     * Metode per carregar dades de llibre biblioteca la nova relacio
     */
    public void loadLlibreBiblioteca()
    {
        taulaBiblioLlibre.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                LlibreBiblio llibreSeleccionado = taulaBiblioLlibre.getSelectionModel().getSelectedItem();

                if (llibreSeleccionado != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informació del libro");
                    alert.setHeaderText(null);
                    alert.setContentText("ISBN: " + llibreSeleccionado.getBook().getISBN() +
                            "\nTítul: " + llibreSeleccionado.getBook().getTitol() +
                            "\nAutor: " + llibreSeleccionado.getBook().getAutor().getNomAutor() +

                            "\nStock: " + llibreSeleccionado.getStock()
                    +"\nEditorial: " + llibreSeleccionado.getBook().getEditor().getNomEditor()
                            +"\nGenére: " + llibreSeleccionado.getBook().getGenere().getNomGenere()
                    +"\nData de Publicació: " + llibreSeleccionado.getBook().getDataPubli());

                    alert.showAndWait();
                }
            }
        });


        try
        {
            GestioLlibreBiblioteca gestioLlibreBiblioteca = new GestioLlibreBiblioteca();

            listBiblioLLibre = gestioLlibreBiblioteca.consultarLlibreBiblioteca();

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
                Biblioteca bibilio = cellData.getValue().getBiblioteca();
                int stock = cellData.getValue().getStock();

                int numReserves =gestioLlibre.getNumReservasWithBiblio(llibre.getISBN(),bibilio.getIdBiblioteca());
                if (numReserves != -1)
                {
                    int calcul = stock -numReserves;

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
            double tamany= 200;

            ComboBox<Autor> autorComboBox = new ComboBox<Autor>();
            autorComboBox.setMaxWidth(tamany);
            ComboBox<Editorial> editorialComboBox = new ComboBox<Editorial>();
            editorialComboBox.setMaxWidth(tamany);
            ComboBox<Idioma>idiomaComboBox = new ComboBox<Idioma>();
            idiomaComboBox.setMaxWidth(tamany);
            ComboBox<Genere> genereComboBox = new ComboBox<Genere>();
            genereComboBox.setMaxWidth(tamany);
            ComboBox<Biblioteca> bibliotecaComboBox = new ComboBox<Biblioteca>();
            bibliotecaComboBox.setMaxWidth(tamany);

            //afegint la info dels autors
            addDataAllComboBox(autorComboBox,editorialComboBox,idiomaComboBox,genereComboBox,bibliotecaComboBox);



            //  Creacio dels Textes
            TextField titolInput = new TextField();

            TextField stock = new TextField();
            TextFormatter<Integer> stockFormater = new TextFormatter<>(change -> {
                if (change.getControlNewText().matches("\\d*")) {
                    return change;
                } else {
                    return null;
                }
            });
            stock.setTextFormatter(stockFormater);


            //data
            DatePicker dataPubliInput = new DatePicker();
            dataPubliInput.setMaxHeight(tamany);

            //crear el gridpane per posar els 2 camps a l'hora
            GridPane gridPane = new GridPane();
            gridPane.setHgap(15);
            gridPane.setVgap(15);


            gridPane.addRow(0, new Label("Titol: "),titolInput);
            gridPane.addRow(1, new Label("ISBN del llibre: "),isbnInput);
            gridPane.addRow(2,new Label("Digues el autor") ,autorComboBox);
            gridPane.addRow(3, new Label("Entra la Editorial: "), editorialComboBox);
            gridPane.addRow(4, new Label("Biblioteca :"),bibliotecaComboBox);
            gridPane.addRow(5, new Label("Idioma: "),idiomaComboBox);
            gridPane.addRow(6, new Label("Genere: "),genereComboBox);
            gridPane.addRow(7, new Label("Versio: "),versionInput);
            gridPane.addRow(8, new Label("Data Publi "),dataPubliInput);
            gridPane.addRow(9, new Label("Digues el stock: "),stock);

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
                GestioBiblioteca gestioBiblioteca = new GestioBiblioteca();


                //converters
                AutorStringConverter converterAutor = new AutorStringConverter();
                EditorialStringConverter converterEditorial = new EditorialStringConverter();
                GenereStringConverter converterGenere = new GenereStringConverter();
                IdiomaStringConverter converterIdioma = new IdiomaStringConverter();
                BibliotecaStringConverter converterBibblioteca = new BibliotecaStringConverter();


                //titol
                llibre.setTitol(titolInput.getText());

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


                //biblitoeca
                int idBiblitoeca = converterBibblioteca.getIdBiblioteca(bibliotecaComboBox.getValue());
                Biblioteca b = gestioBiblioteca.findBiblioteca(idBiblitoeca);


                //versio
                llibre.setVersio(Integer.parseInt(versionInput.getText()));
                //data
                LocalDate data = dataPubliInput.getValue();
                Date d = Date.valueOf(data);
                llibre.setDataPubli(d);

                //creating the bibliteca llibre
                LlibreBiblio llibreBiblio =new LlibreBiblio();
                llibreBiblio.setBook(llibre);
                llibreBiblio.setBiblioteca(b);
                llibreBiblio.setStock(Integer.parseInt(stock.getText()));

                if(llibreBiblio != null  && llibre!=null)
                {
                    //update memory
                    ObservableList<LlibreBiblio> items = taulaBiblioLlibre.getItems();
                    items.add(llibreBiblio);



                    // update the table
                    GestioLlibre gestioLlibre = new GestioLlibre();
                    gestioLlibre.crearLlibre(llibre);


                    //update the table biblio_llibre

                    GestioLlibreBiblioteca gestioLlibreBiblioteca =new GestioLlibreBiblioteca();
                    gestioLlibreBiblioteca.crearLlibreBiblioteca(llibreBiblio);




                    //actualiar la pagina
                    switchToLlibre(event);
                }
                else {
                    System.out.println("Vuit");
                }


            }


        }
        catch (Exception e)
        {
            System.out.println("Error Insert Llibre: "+ e.getMessage());
            Alert wrong = new Alert(Alert.AlertType.ERROR);
            wrong.setTitle("Error");
            wrong.setHeaderText(null);
            wrong.setContentText("Els camps estan buits ");
            wrong.show();
        }


    }

    /**
     * Metode per eliminar un llibre
     * @param event
     */
    public void onDeleteBook(ActionEvent event)
    {
        try
        {

            GestioLlibre gestioLlibre = new GestioLlibre();
            GestioLlibreBiblioteca gestioLlibreBiblioteca =new GestioLlibreBiblioteca();
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);

            Alert wrong = new Alert(Alert.AlertType.ERROR);


            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("Estàs segur de que vols continuar?");


            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

                LlibreBiblio llibreBiblio = taulaBiblioLlibre.getSelectionModel().getSelectedItem();


                if (llibreBiblio != null) {
                    boolean isReserved = gestioLlibre.hayReservasActivas(llibreBiblio.getBook().getISBN());

                    if (isReserved) {
                        wrong.setTitle("Error");
                        wrong.setHeaderText(null);
                        wrong.setContentText("Aquest llibre no es pot eliminar, esta en una reserva activa");
                        wrong.show();
                    } else {
                        Alert sucessAlert = new Alert(Alert.AlertType.INFORMATION);
                        sucessAlert.setTitle("Success!");
                        sucessAlert.setHeaderText("Has eliminat llibre!");
                        sucessAlert.setContentText("Llibre s'ha eliminat correctament");
                        sucessAlert.show();


                        //delte to memory
                        ObservableList<LlibreBiblio> items = taulaBiblioLlibre.getItems();
                        items.remove(llibreBiblio);

                        //eliminare de la base de dadecs de la taula llibre && de biblio
                        gestioLlibreBiblioteca.eliminarLlibreBiblioteca(llibreBiblio.getId());
                        gestioLlibre.eliminarLlibre(llibreBiblio.getBook().getISBN());


                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error Delete Book: "+ e.getMessage());
        }


    }

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
     *
     * @param autorComboBox      Combobox de Autors
     * @param editorialComboBox  Combobox de Editorials
     * @param idiomaComboBox     ComboBox de Idiomas
     * @param genereComboBox     Combobox de Generes
     * @param bibliotecaComboBox
     */
    private void addDataAllComboBox(ComboBox<Autor> autorComboBox, ComboBox<Editorial> editorialComboBox, ComboBox<Idioma> idiomaComboBox, ComboBox<Genere> genereComboBox, ComboBox<Biblioteca> bibliotecaComboBox)
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

        //bibioteca

        BibliotecaStringConverter bibliotecaStringConverter = new BibliotecaStringConverter();
        GestioBiblioteca gestioBiblioteca = new GestioBiblioteca();
        ArrayList<Biblioteca> listBiblios = gestioBiblioteca.consultarBiblioteques();
        bibliotecaComboBox.getItems().addAll(listBiblios);
        bibliotecaComboBox.setConverter(bibliotecaStringConverter);

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
