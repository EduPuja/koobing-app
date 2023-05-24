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
import javafx.scene.layout.BackgroundRepeat;
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

    //navbar
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

    // taula llibre


    public TableView2<Llibre> taulaLlibres;
    public TableColumn<Llibre,Integer> isbnColum;
    public TableColumn<Llibre,String> titolColum;
    public TableColumn<Llibre,String> autorColum;
    public TableColumn <Llibre,String>editorColum;
    public TableColumn<Llibre,String> idiomaColum;
    public TableColumn<Llibre,String> genereColum;
    public TableColumn<Llibre,Integer> edicioColum;
    public TableColumn<Llibre,Date> dataPubliColum;
    public TableColumn <Llibre,String>stockColum;
    public TableColumn<Llibre,String> disponibleColum;


    // llibre

    ArrayList<Llibre> listLlibres = new ArrayList<Llibre>();


    Parent root;
    Stage stage;
    Scene scene;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Book Screen");
        loadWorkerInfo();
        onLoadInfoLlibre();



        // doble click tabla
        taulaLlibres.setOnMouseClicked(event ->{
            if(event.getClickCount()== 2)
            {
                Llibre libro = taulaLlibres.getSelectionModel().getSelectedItem();
                if(libro != null)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informació del llibre");

                    alert.setHeaderText(null);
                    GestioLlibre gestioLlibre = new GestioLlibre();
                   int estat = gestioLlibre.getEstadoLlibre(libro.getISBN());
                   switch(estat)
                   {
                       case  1:
                           alert.setContentText("Llibre: "+libro.getTitol()+" DISPONIBLE");
                           break;
                       case 2:
                           alert.setContentText("Llibre: "+libro.getTitol()+" CANCELAT");
                           break;
                       case 3:
                           alert.setContentText("Llibre: "+libro.getTitol()+" PRESTEC");
                           break;
                       case 4:
                           alert.setContentText("Llibre: "+libro.getTitol()+" TORNAT");
                           break;
                       case 5:
                       alert.setContentText("Llibre: "+libro.getTitol()+" Vençut");
                       break;
                       case 6:
                           alert.setContentText("Llibre: "+libro.getTitol()+" Reservat");
                       break;

                       case 7:
                           alert.setContentText("Llibre: "+libro.getTitol()+" No Stock");
                           break;

                       default:
                           alert.setContentText("No tenim aquesta opcio");
                           break;


                   }
                }
            }
        });

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
     * Metode carregar la info del llibre
     */
    public void onLoadInfoLlibre()
    {
        try
        {
            GestioLlibre gestioLlibre = new GestioLlibre();
            listLlibres = gestioLlibre.consultarLlibres();

            ObservableList<Llibre> observableList = FXCollections.observableArrayList(listLlibres);
            taulaLlibres.setItems(observableList);

            isbnColum.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
            titolColum.setCellValueFactory(new PropertyValueFactory<>("titol"));
            autorColum.setCellValueFactory(cellData->
            {
                String nomAutor =cellData.getValue().getAutor().getNomAutor();
                return new SimpleStringProperty(nomAutor);
            });
            editorColum.setCellValueFactory(cellData->
            {
                String nomEditor = cellData.getValue().getEditor().getNomEditor();
                return  new SimpleStringProperty(nomEditor);
            });

            idiomaColum.setCellValueFactory(cellData->
            {
                String idioma = cellData.getValue().getIdioma().getNomIdioma();
                return new SimpleStringProperty(idioma);
            });
            genereColum.setCellValueFactory(cellData->
            {
                String genere = cellData.getValue().getGenere().getNomGenere();
                return new SimpleStringProperty(genere);
            });
            edicioColum.setCellValueFactory(new PropertyValueFactory<>("versio"));
            dataPubliColum.setCellValueFactory(new PropertyValueFactory<>("dataPubli"));
            //stockColum.setCellValueFactory(new PropertyValueFactory<>("stock"));
            stockColum.setCellValueFactory(cellData->
            {
                int stock = cellData.getValue().getStock();
                if(stock == 0)
                {
                    disponibleColum.setCellValueFactory(cellData2 ->
                    {
                        return new SimpleStringProperty("No disponible");
                    });
                }
                else
                {
                    disponibleColum.setCellValueFactory(cellData2 ->{
                        return new SimpleStringProperty("Disponible");
                    });
                }
                return new SimpleStringProperty(String.valueOf(stock));
            });

           



        }
        catch (Exception e)
        {
            System.out.println("Error loading book info : "+ e.getMessage());
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


            addDataAllComboBox(autorComboBox,editorialComboBox,idiomaComboBox,genereComboBox);


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
            gridPane.addRow(4, new Label("Idioma: "),idiomaComboBox);
            gridPane.addRow(5, new Label("Genere: "),genereComboBox);
            gridPane.addRow(6, new Label("Edició: "),versionInput);
            gridPane.addRow(7, new Label("Data Publi "),dataPubliInput);
            gridPane.addRow(8, new Label("Digues el stock: "),stock);

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


                //versio
                llibre.setVersio(Integer.parseInt(versionInput.getText()));
                //data
                LocalDate data = dataPubliInput.getValue();
                Date d = Date.valueOf(data);
                llibre.setDataPubli(d);


                // refrescar la taula
                taulaLlibres.refresh();
                GestioLlibre gestioLlibre = new GestioLlibre();

                gestioLlibre.crearLlibre(llibre);

                //tornaar a carregar la pantalla
                switchToLlibre(event);


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
           // GestioLlibreBiblioteca gestioLlibreBiblioteca =new GestioLlibreBiblioteca();
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);

            Alert wrong = new Alert(Alert.AlertType.ERROR);


            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("Estàs segur de que vols continuar?");


            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

                Llibre llibre = taulaLlibres.getSelectionModel().getSelectedItem();


                if (llibre != null) {
                    //comprovar si te prestecs
                    boolean hasState = gestioLlibre.canBeDeleted(llibre.getISBN());
                    if(hasState)
                    {
                        wrong.setTitle("Error");
                        wrong.setHeaderText("Aquest llibre no es pot eliminar");
                        wrong.setContentText("Aquest llibre pot tenir reserves");
                        wrong.show();
                    }
                    else
                    {
                        Alert sucessAlert = new Alert(Alert.AlertType.INFORMATION);
                        sucessAlert.setTitle("Success!");
                        sucessAlert.setHeaderText("Has eliminat llibre!");
                        sucessAlert.setContentText("Llibre s'ha eliminat correctament");
                        sucessAlert.show();


                        //delte to memory
                        ObservableList<Llibre> items = taulaLlibres.getItems();
                        items.remove(llibre);

                        //eliminare de la base de dadecs de la taula llibre && de biblio
                        gestioLlibre.eliminarLlibre(llibre.getISBN());
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
   public void onModifyBook(ActionEvent event) throws Exception
    {
        Llibre llibre = taulaLlibres.getSelectionModel().getSelectedItem();
        if(llibre != null) {
            // Creacio dels Textes

            TextField titol = new TextField(llibre.getTitol());
            TextField stock = new TextField(String.valueOf(llibre.getStock()));

            TextFormatter<Integer> stockFormater = new TextFormatter<>(change -> {
                if (change.getControlNewText().matches("\\d*")) {
                    return change;
                } else {
                    return null;
                }
            });
            stock.setTextFormatter(stockFormater);

           //comboboxes
            ComboBox<Autor> autorComboBox = new ComboBox<Autor>();
            ComboBox<Editorial> editorialComboBox = new ComboBox<Editorial>();
            ComboBox<Idioma>idiomaComboBox = new ComboBox<Idioma>();
            ComboBox<Genere> genereComboBox = new ComboBox<Genere>();



            //afegint un tamany
            double tamany= 200;
            autorComboBox.setMaxWidth(tamany);
            editorialComboBox.setMaxWidth(tamany);
            idiomaComboBox.setMaxWidth(tamany);
            genereComboBox.setMaxWidth(tamany);


            //afegint dades
            addDataAllComboBox(autorComboBox,editorialComboBox,idiomaComboBox,genereComboBox);

            TextField version = new TextField(Integer.toString(llibre.getVersio()));

            DatePicker dataPublicacio = new DatePicker(llibre.getDataPubli().toLocalDate());

           // addDataAllComboBox(autors, editorials, idioma,genere);
            //crear el gridpane per posar els 2 camps a l'hora
            //crear el gridpane per posar els 2 camps a l'hora
            GridPane gridPane = new GridPane();
            gridPane.setHgap(15);
            gridPane.setVgap(15);

            gridPane.addRow(0, new Label("Titol: "),titol);
            gridPane.addRow(1,new Label("Autor actual: "),new Label(llibre.getAutor().getNomAutor()) );
            gridPane.addRow(2,new Label("Canvia el autor :"),autorComboBox);
            gridPane.addRow(3, new Label("Editorial Actual: "),new Label(llibre.getEditor().getNomEditor()));
            gridPane.addRow(4, new Label("Canvia l'editorial: "),editorialComboBox);
            gridPane.addRow(5, new Label("Idioma Actual : "),new Label(llibre.getIdioma().getNomIdioma()));
            gridPane.addRow(6, new Label("Canvi Idioma: "),idiomaComboBox);
            gridPane.addRow(7, new Label("Genere Actual: "),new Label(llibre.getGenere().getNomGenere()));
            gridPane.addRow(8,new Label("Canvi Genere: "),genereComboBox);
            gridPane.addRow(9, new Label("Versio: "),version);
            gridPane.addRow(10, new Label("Data Publi "),dataPublicacio);
            gridPane.addRow(11, new Label("Digues el stock: "),stock);





            // Mostrar los dos diálogos en la misma ventana
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setTitle("Modificar el LLibre");
            alert.setHeaderText("Introduïu les noves dades del Lllibre selecionat:");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);



            // Esperar a que el usuario presione OK o Cancel
            Optional<ButtonType> resultat = alert.showAndWait();

            if (resultat.isPresent() && resultat.get() == ButtonType.OK) {


                if(autorComboBox.getValue() != null && editorialComboBox.getValue() != null &&
                genereComboBox.getValue() != null && idiomaComboBox.getValue()!=null )
                {
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
                    llibre.setTitol(titol.getText());

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
                    llibre.setVersio(Integer.parseInt(version.getText()));
                    //data
                    LocalDate data = dataPublicacio.getValue();
                    Date d = Date.valueOf(data);
                    llibre.setDataPubli(d);

                    //stock
                    llibre.setStock(Integer.parseInt(stock.getText()));


                    //refresh
                    taulaLlibres.refresh();

                    //modificar la relacio
                   GestioLlibre gestioLlibreBiblioteca = new GestioLlibre();
                    gestioLlibreBiblioteca.modificarLlibre(llibre);

                  switchToLlibre(event);
                }

                else
                {
                    System.out.println("Camps vuits");
                    Alert badAlert = new Alert(Alert.AlertType.ERROR);
                    badAlert.setTitle("Camps vuits");
                    badAlert.setContentText("Emplena els camps");
                    badAlert.show();
                }



            }
        }
    }


    /**
     * Metode per poder emplanar les dades dels comboboxes de forma més rapida
     *
     * @param autorComboBox      Combobox de Autors
     * @param editorialComboBox  Combobox de Editorials
     * @param idiomaComboBox     ComboBox de Idiomas
     * @param genereComboBox     Combobox de Generes
     */
    private void addDataAllComboBox(ComboBox<Autor> autorComboBox, ComboBox<Editorial> editorialComboBox, ComboBox<Idioma> idiomaComboBox, ComboBox<Genere> genereComboBox)
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

        /*BibliotecaStringConverter bibliotecaStringConverter = new BibliotecaStringConverter();
        GestioBiblioteca gestioBiblioteca = new GestioBiblioteca();
        ArrayList<Biblioteca> listBiblios = gestioBiblioteca.consultarBiblioteques();
        bibliotecaComboBox.getItems().addAll(listBiblios);
        bibliotecaComboBox.setConverter(bibliotecaStringConverter);*/

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
