package edu.pujadas.koobing_admin.Controllers;

import edu.pujadas.koobing_admin.Database.*;
import edu.pujadas.koobing_admin.Models.*;
import edu.pujadas.koobing_admin.Utilities.*;
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
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PrestecController implements Initializable
{


    public Button trebaladorBtn;
    public Button bibliotecaBtn;
    public Button genereBtn;
    public Button editioralBtn;
    public ComboBox<String> filtreTaulaComboBox;

    Parent root;
    Stage stage;
    Scene scene;


    public ImageView avatarWorker;

    // taula
    public TableView2<Prestec> taulaReserves;

    public TableColumn<Prestec,String> nomUserColum;
    public TableColumn<Prestec,Integer> idReservaColum;
    public TableColumn<Prestec,String> nomWorkerColum;
    public TableColumn<Prestec,String> nomBiblioColum;
    public TableColumn<Prestec,String> bookTitleColum;
    public TableColumn<Prestec, Date> dataInici;
    public TableColumn<Prestec,Date> dataFi;
    public TableColumn<Prestec,String> estat;




    ArrayList<Prestec> listReserves = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Reserva Screen!");

        //carregar informacio del treballador
        loadWorkerInfo();


        filtreTaulaComboBox.setValue("Tota Inforamció"); // poso per default que carregi tota la inforamció
        carregarTotaInforamcio(); // carrego tota la informacio

        filtreTaulaComboBox.setOnAction(event -> {
            String valor = filtreTaulaComboBox.getValue();
            if(valor.equals("Reservats"))
            {
                // todo carregar reservats
                System.out.println("reservat");
            }
            else if(valor.equals("Cancelats"))
            {
            //todo carrecagar cacnelat
                System.out.println("cacnelats");
            }
            else if(valor.equals("Tornats"))
            {
            // todo carregar tornats
                System.out.println("tornats");
            }
            else if (valor.equals("En Prèstec"))
            {
                // todo carregar en prestec
                System.out.println("prestec");
            }
            else if(valor.equals("Tota Inforamció"))
            {
                System.out.println("Tota Inforamció");
                carregarTotaInforamcio();
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


    private void carregarTotaInforamcio()
    {
        GestioPrestec gestioPrestec = new GestioPrestec();
        listReserves = gestioPrestec.consultarReserves();
        ObservableList<Prestec> observableListPrestec = FXCollections.observableArrayList(
                listReserves
        );
        //afegint el observable list en el tableview
        taulaReserves.setItems(observableListPrestec);
        idReservaColum.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        nomUserColum.setCellValueFactory(cellData ->{
            Usuari usuari = cellData.getValue().getUsuari();
            String nom = usuari.getNom();
            return new SimpleStringProperty(nom);
        });
        nomWorkerColum.setCellValueFactory(cellData->{
            Treballador treballador = cellData.getValue().getTreballador();
            String nom = treballador.getNom();
            return new SimpleStringProperty(nom);
        });

        bookTitleColum.setCellValueFactory(cellData ->{
            Llibre book = cellData.getValue().getLlibre();
            String titol = book.getTitol();
            return new SimpleStringProperty(titol);
        });
        dataInici.setCellValueFactory(new PropertyValueFactory<>("dataInici"));
        dataFi.setCellValueFactory(new PropertyValueFactory<>("dataFI"));
        estat.setCellValueFactory(cellData ->{
            int estat =cellData.getValue().getEstat();
            switch (estat)
            {
                case 1 :
                    return new SimpleStringProperty("Reservat");

                case 2 :
                    return new SimpleStringProperty("Cancelat");

                case 3:
                    return new SimpleStringProperty("Tornat");

                case 4 :
                    return new SimpleStringProperty("En Prèstec");

                default:
                    return new SimpleStringProperty(" ");



            }

        });
    }


    /**
     * metode que carrega la informacio filtrada en la taula de prestec depenen de quin estat tiguis
     * @param idEstat 1 resevat , 2 cancelat
     */
    private void carregarInforamcioEstat(int idEstat)
    {
        GestioPrestec gestioPrestec = new GestioPrestec();
        listReserves = gestioPrestec.consultarReservesByEstat(idEstat);
        ObservableList<Prestec> observableListPrestec = FXCollections.observableArrayList(
                listReserves
        );
        //afegint el observable list en el tableview
        taulaReserves.setItems(observableListPrestec);
        idReservaColum.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        nomUserColum.setCellValueFactory(cellData ->{
            Usuari usuari = cellData.getValue().getUsuari();
            String nom = usuari.getNom();
            return new SimpleStringProperty(nom);
        });
        nomWorkerColum.setCellValueFactory(cellData->{
            Treballador treballador = cellData.getValue().getTreballador();
            String nom = treballador.getNom();
            return new SimpleStringProperty(nom);
        });

        bookTitleColum.setCellValueFactory(cellData ->{
            Llibre book = cellData.getValue().getLlibre();
            String titol = book.getTitol();
            return new SimpleStringProperty(titol);
        });
        dataInici.setCellValueFactory(new PropertyValueFactory<>("dataInici"));
        dataFi.setCellValueFactory(new PropertyValueFactory<>("dataFI"));
        estat.setCellValueFactory(cellData ->{
            int estat =cellData.getValue().getEstat();
            switch (estat)
            {
                case 1 :
                    return new SimpleStringProperty("Reservat");

                case 2 :
                    return new SimpleStringProperty("Cancelat");

                case 3:
                    return new SimpleStringProperty("Tornat");

                case 4 :
                    return new SimpleStringProperty("En Prèstec");

                default:
                    return new SimpleStringProperty(" ");
            }

        });
    }




    public void onAddReserva(ActionEvent event)
    {
        try
        {
            //treballador
            Treballador worker = TrabajadorSingleton.getInstance().getTrabajador();

            //comboboxes
            ComboBox<Usuari> usuariComboBox= new ComboBox<>();
            //usuari
            GestioUsuari gestioUsuari = new GestioUsuari();
            ArrayList<Usuari> listaUsuarios = gestioUsuari.consultarUsuaris();
            UsuariStringConverter userConverter = new UsuariStringConverter();
            usuariComboBox.setConverter(userConverter);
            usuariComboBox.getItems().addAll(listaUsuarios);




            ComboBox<Llibre> llibreComboBox = new ComboBox<>();
            llibreComboBox.setDisable(true);
            /*ComboBox<Biblioteca> bibliotecaComboBox= new ComboBox<>();
            bibliotecaComboBox.setOnAction(actionEvent ->
            {
                if((bibliotecaComboBox.getSelectionModel().getSelectedItem() != null))
                {
                    llibreComboBox.getItems().clear();
                    llibreComboBox.setDisable(false);
                    Biblioteca bibliotecaSelected = bibliotecaComboBox.getSelectionModel().getSelectedItem();

                    System.out.println("INFO BIBLIOTECA: "+ bibliotecaSelected.getIdBiblioteca());
                    //afegir dades llibre
                    GestioLlibreBiblioteca gestioLlibreBiblioteca = new GestioLlibreBiblioteca();
                    LlibreStringConverter llibreStringConverter = new LlibreStringConverter();

                    ArrayList<Llibre> llistatLlibresByBiblio = gestioLlibreBiblioteca.getLlibreBibliotecaByBilio(bibliotecaSelected.getIdBiblioteca());
                    llibreComboBox.getItems().addAll(llistatLlibresByBiblio);
                    llibreComboBox.setConverter(llibreStringConverter);
                }
            });*/

            //add dades a biblioteca
            /*GestioBiblioteca gestioBiblioteca = new GestioBiblioteca();
            ArrayList<Biblioteca> listaBiblio = gestioBiblioteca.consultarBiblioteques();
            BibliotecaStringConverter bibliotecaStringConverter = new BibliotecaStringConverter();
            bibliotecaComboBox.getItems().addAll(listaBiblio);
            bibliotecaComboBox.setConverter(bibliotecaStringConverter);*/



            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formatStartDate = LocalDate.now().format(formatter);


            ComboBox<String> dataEndComboBox = new ComboBox<String>();
            ObservableList<String> dies = FXCollections.observableArrayList(
                    "1 mes" ,"10 dies ","5 dies"
            );
            dataEndComboBox.setItems(dies);


            //crear el gridpane per posar els 2 camps a l'hora
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);



            gridPane.addRow(0,new Label("Digues el usuari: ") ,usuariComboBox);
            gridPane.addRow(1, new Label("Nom del treballador :"), new Label(worker.getNom()));
            //gridPane.addRow(2, new Label("Seleciona la biblioteca: "),bibliotecaComboBox);
            gridPane.addRow(3, new Label("Seleciona el llibre: "),llibreComboBox);
            gridPane.addRow(4,new Label("Data d'inici: "),new Label(formatStartDate));
            gridPane.addRow(5,new Label("Data de finalizació: "),dataEndComboBox);


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
                Prestec prestec = new Prestec();
                //prestec.setBiblio(bibliotecaComboBox.getValue());
                prestec.setTreballador(worker);
                //prestec.setEstat(false);
                prestec.setUsuari(usuariComboBox.getValue());
                prestec.setLlibre(llibreComboBox.getValue());

                Date dateInici = Date.valueOf(LocalDate.now());
                prestec.setDataInici(dateInici);

                if(dataEndComboBox.getValue() != null)
                {
                    if(dataEndComboBox.getValue().equals("1 mes"))
                    {
                        Date endDate = Date.valueOf(LocalDate.now().plusMonths(1));
                        prestec.setDataFI(endDate);

                        //afegir a memoria
                        listReserves.add(prestec);
                        ObservableList<Prestec> prestecObservableList = FXCollections.observableArrayList(listReserves);
                        taulaReserves.setItems(prestecObservableList);

                        //base de dades
                    /*GestioPrestec gestioPrestec = new GestioPrestec();
                    gestioPrestec.crearReserva(prestec);*/
                    }
                    else if(dataEndComboBox.getValue().equals("10 dies"))
                    {
                        Date endDate = Date.valueOf(LocalDate.now().plusDays(10));
                        prestec.setDataFI(endDate);

                        //afegir a memoria
                        listReserves.add(prestec);
                        ObservableList<Prestec> prestecObservableList = FXCollections.observableArrayList(listReserves);
                        taulaReserves.setItems(prestecObservableList);

                        //base de dades
                    /*GestioPrestec gestioPrestec = new GestioPrestec();
                    gestioPrestec.crearReserva(prestec);*/
                    }
                    else if(dataEndComboBox.getValue().equals("5 dies"))
                    {
                        Date endDate = Date.valueOf(LocalDate.now().plusDays(5));
                        prestec.setDataFI(endDate);

                        //afegir a memoria
                        listReserves.add(prestec);
                        ObservableList<Prestec> prestecObservableList = FXCollections.observableArrayList(listReserves);
                        taulaReserves.setItems(prestecObservableList);


                        //base de dades
                    /*GestioPrestec gestioPrestec = new GestioPrestec();
                    gestioPrestec.crearReserva(prestec);*/
                    }


                    GestioPrestec  gestioPrestec = new GestioPrestec();
                    gestioPrestec.crearReserva(prestec);

                    switchToReserva(event);

                }
                else
                {
                    Alert wromg = new Alert(Alert.AlertType.ERROR);

                    wromg.setTitle("Emplena els camps");
                    wromg.setHeaderText("Els camps estan vuits");
                    wromg.setContentText("Torna a emplenar els camps");
                    wromg.show();
                }







            }



        }
        catch (Exception e)
        {
            //System.out.println("Error adding Reserva: " + e.getMessage());
            e.printStackTrace();
        }


    }

    public void onDeleteReserva(ActionEvent event )
    {
        try {

            GestioPrestec gestioPrestec = new GestioPrestec();
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
                Prestec prestec =taulaReserves.getSelectionModel().getSelectedItem();
                if(prestec !=null)
                {
                    Alert sucessAlert = new Alert(Alert.AlertType.INFORMATION);
                    sucessAlert.setTitle("Success!");
                    sucessAlert.setHeaderText("Has eliminat una reserva!");
                    sucessAlert.setContentText("La reserva s'ha eliminat correctament!");
                    sucessAlert.show();


                    //delte to memory
                    ObservableList<Prestec> itemsReserves = taulaReserves.getItems();
                    itemsReserves.remove(prestec);



                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error Delete Reserva: " + e.getMessage());
        }


    }
    public void onModifyReserva(ActionEvent event)
    {
        try {
            Prestec prestec = taulaReserves.getSelectionModel().getSelectedItem();
            Treballador worker = TrabajadorSingleton.getInstance().getTrabajador();
            if(prestec !=null){
                ComboBox<Usuari> userComboBox= new ComboBox<>();
                //ComboBox<Biblioteca> bibliotecaComboBox = new ComboBox<>();
                ComboBox<Llibre> llibreComboBox= new ComboBox<>();
                llibreComboBox.setDisable(true);

                //afegir dades usuari
                GestioUsuari gestioUsuari = new GestioUsuari();
                ArrayList<Usuari> listaUsuarios = gestioUsuari.consultarUsuaris();
                UsuariStringConverter userConverter = new UsuariStringConverter();
                userComboBox.setConverter(userConverter);
                userComboBox.getItems().addAll(listaUsuarios);


                //afegit dades biblioteca
                /*bibliotecaComboBox.setOnAction(actionEvent ->
                {
                    if((bibliotecaComboBox.getSelectionModel().getSelectedItem() != null))
                    {
                        llibreComboBox.getItems().clear();
                        llibreComboBox.setDisable(false);
                        Biblioteca bibliotecaSelected = bibliotecaComboBox.getSelectionModel().getSelectedItem();

                        System.out.println("INFO BIBLIOTECA: "+ bibliotecaSelected.getIdBiblioteca());
                        //afegir dades llibre
                        GestioLlibreBiblioteca gestioLlibreBiblioteca = new GestioLlibreBiblioteca();
                        LlibreStringConverter llibreStringConverter = new LlibreStringConverter();

                        ArrayList<Llibre> llistatLlibresByBiblio = gestioLlibreBiblioteca.getLlibreBibliotecaByBilio(bibliotecaSelected.getIdBiblioteca());
                        llibreComboBox.getItems().addAll(llistatLlibresByBiblio);
                        llibreComboBox.setConverter(llibreStringConverter);
                    }
                });*/

                //add dades a biblioteca
                /*GestioBiblioteca gestioBiblioteca = new GestioBiblioteca();
                ArrayList<Biblioteca> listaBiblio = gestioBiblioteca.consultarBiblioteques();
                BibliotecaStringConverter bibliotecaStringConverter = new BibliotecaStringConverter();
                bibliotecaComboBox.getItems().addAll(listaBiblio);
                bibliotecaComboBox.setConverter(bibliotecaStringConverter);*/



                DatePicker datePickerStart = new DatePicker(prestec.getDataInici().toLocalDate());
                DatePicker datePickerEnd = new DatePicker(prestec.getDataFI().toLocalDate());
                CheckBox isRetornart = new CheckBox();






                //crear el gridpane per posar els 2 camps a l'hora
                GridPane gridPane = new GridPane();
                gridPane.setHgap(10);
                gridPane.setVgap(10);



                gridPane.addRow(0,new Label("Treballdor Actual: "),new Label(worker.getNom() +" "+ worker.getCognom()));
                gridPane.addRow(1,new Label("Usuari Actual: "),new Label(prestec.getUsuari().getNom() +" " + prestec.getUsuari().getCognom()));
                gridPane.addRow(2,new Label("Digues el nou usuari ") ,userComboBox);
               // gridPane.addRow(3,new Label("Biblioteca Actual :") ,new Label(prestec.getBiblio().getNomBiblioteca()));
               // gridPane.addRow(4, new Label("Vols Canviar de biblioteca? : "), bibliotecaComboBox);
                gridPane.addRow(5,new Label("Titol del llibre actual: ") ,new Label(prestec.getLlibre().getTitol()));
                gridPane.addRow(6, new Label("Digues el llibre:  "),llibreComboBox);
                gridPane.addRow(7, new Label("Data de inicio"), datePickerStart);
                gridPane.addRow(8, new Label("Data de fin"), datePickerEnd);
                gridPane.addRow(9, new Label("Estat :"),isRetornart);



                Alert alert = new Alert(Alert.AlertType.NONE);

                alert.setTitle("Modificar la reserva");
                alert.setHeaderText("Introduïu les noves dades de la reserva");
                alert.getDialogPane().setContent(gridPane);
                alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                Optional<ButtonType> resultat = alert.showAndWait();
                if (resultat.isPresent() && resultat.get() == ButtonType.OK)
                {


                   /* if(bibliotecaComboBox.getSelectionModel().getSelectedItem() != null && userComboBox.getSelectionModel().getSelectedItem() != null && llibreComboBox.getSelectionModel().getSelectedItem() != null)
                    {
                        System.out.println("Actualizat");
                        prestec.setBiblio(bibliotecaComboBox.getValue());
                        prestec.setTreballador(worker);
                        prestec.setEstat(false);
                        prestec.setUsuari(userComboBox.getValue());
                        prestec.setLlibre(llibreComboBox.getValue());

                        Date dateInici = Date.valueOf(datePickerStart.getValue());
                        prestec.setDataInici(dateInici);

                        Date dataFi = Date.valueOf(datePickerEnd.getValue());
                        prestec.setDataFI(dataFi);


                        //actualizar memoria
                        taulaReserves.refresh();


                        //actualizar base de dades

                        GestioPrestec gestioPrestec = new GestioPrestec();
                        gestioPrestec.modificarReserva(prestec);
                    }

                    else
                    {

                        Alert wrong = new Alert(Alert.AlertType.ERROR);
                        wrong.setTitle("Error");
                        wrong.setHeaderText("Els camps son buits");
                        wrong.setContentText("Torna a provar de modifcar el prestec");
                        wrong.show();
                    }*/












                }
            }

        }
        catch (Exception e)
        {
            System.out.println("Error Modify Reserva: " + e.getMessage());
        }


    }


    /*
    private void addDataComboxes(ComboBox<Usuari> usuariComboBox, ComboBox<Biblioteca> bibliotecaComboBox, ComboBox<Llibre> llibreComboBox)
    {

        //usuari
        GestioUsuari gestioUsuari = new GestioUsuari();
        ArrayList<Usuari> listaUsuarios = gestioUsuari.consultarUsuaris();
        UsuariStringConverter userConverter = new UsuariStringConverter();
        usuariComboBox.setConverter(userConverter);
        usuariComboBox.getItems().addAll(listaUsuarios);



        //biblioteca
        GestioBiblioteca gestioBiblioteca = new GestioBiblioteca();
        ArrayList<Biblioteca> listaBiblio = gestioBiblioteca.consultarBiblioteques();
        BibliotecaStringConverter bibliotecaStringConverter = new BibliotecaStringConverter();
        bibliotecaComboBox.getItems().addAll(listaBiblio);
        bibliotecaComboBox.setConverter(bibliotecaStringConverter);


        //llibre
        GestioLlibre gestioLlibre = new GestioLlibre();
        ArrayList<Llibre> listLlibres = gestioLlibre.consultarLlibres();
        LlibreStringConverter llibreStringConverter = new LlibreStringConverter();
        llibreComboBox.getItems().addAll(listLlibres);
        llibreComboBox.setConverter(llibreStringConverter);


    }*/


    public void onTornarLlibre(ActionEvent event)
    {
        try
        {
            Alert wrong = new Alert(Alert.AlertType.ERROR);
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            Prestec res  = taulaReserves.getSelectionModel().getSelectedItem();
            if(res != null)
            {
                GestioPrestec gestioPrestec = new GestioPrestec();
                gestioPrestec.canviarEstat(res.getIdReserva());
                success.setTitle("Se ha tornat el llibre");
                success.setContentText(null);
                success.setHeaderText("El llibre se ha tornat correctament!");
                success.show();
                switchToReserva(event);
            }
            else {
                wrong.setTitle("Selecciona una reserva");
                wrong.setHeaderText("Has de selecionar una reserva");
                wrong.show();
            }

        }
        catch (Exception e)
        {
            System.out.println("Tornar Llibre Error :" +e.getMessage());
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
