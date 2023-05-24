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
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
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

                System.out.println("reservat");
                carregarInforamcioEstat(1);
            }
            else if(valor.equals("Cancelats"))
            {

                System.out.println("cacnelats");
                carregarInforamcioEstat(2);
            }
            else if(valor.equals("Tornats"))
            {

                System.out.println("tornats");
                carregarInforamcioEstat(3);
            }
            else if (valor.equals("En Prèstec"))
            {

                System.out.println("prestec");
                carregarInforamcioEstat(4);
            }
            else if(valor.equals("Tota Informació"))
            {
                System.out.println("Tota Informació");
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


    /**
     * Metode afgir una reserva
     * @param event Action Event
     */
    public void onAddReserva(ActionEvent event)
    {
        try
        {
            //treballador
            Treballador worker = TrabajadorSingleton.getInstance().getTrabajador();

            //usari combobox
            ComboBox<Usuari> usuariComboBox= new ComboBox<>();
            GestioUsuari gestioUsuari = new GestioUsuari();
            ArrayList<Usuari> listaUsuarios = gestioUsuari.consultarUsuaris();
            UsuariStringConverter userConverter = new UsuariStringConverter();
            usuariComboBox.setConverter(userConverter);
            usuariComboBox.getItems().addAll(listaUsuarios);

            // llibre combobox
            ComboBox<Llibre> llibreComboBox = new ComboBox<>();
            GestioLlibre gestioLlibre = new GestioLlibre();
            ArrayList<Llibre> llistatLlbires = gestioLlibre.consultarLlibresAmbStock();
            LlibreStringConverter stringBookConverter = new LlibreStringConverter();
            llibreComboBox.setConverter(stringBookConverter);
            llibreComboBox.getItems().addAll(llistatLlbires);


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formatStartDate = LocalDate.now().format(formatter);

            //data finalizacio
            ComboBox<String> dataEndComboBox = new ComboBox<String>();
            ObservableList<String> dies = FXCollections.observableArrayList(
                    "1 mes" ,"10 dies ","5 dies"
            );
            dataEndComboBox.setItems(dies);


            //combobox de estatComboBox
            ComboBox<String> estatComboBox= new ComboBox<String>();
            ObservableList<String> observableList = FXCollections.observableArrayList(
                    "Reservat" ,"Cancelat ","Tornat","En Prèstec"
            );
            estatComboBox.setItems(observableList);

            //crear el gridpane per posar els 2 camps a l'hora
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            gridPane.addRow(0,new Label("Digues el usuari: ") ,usuariComboBox);
            gridPane.addRow(1, new Label("Nom del treballador :"), new Label(worker.getNom()));
            gridPane.addRow(2, new Label("Seleciona el llibre: "),llibreComboBox);
            gridPane.addRow(3,new Label("Data d'inici: "),new Label(formatStartDate));
            gridPane.addRow(4,new Label("Data de finalizació: "),dataEndComboBox);
            gridPane.addRow(5,new Label("Estats:"),estatComboBox);


            //alerta
            Alert alert = new Alert(Alert.AlertType.NONE);

            alert.setTitle("Afegir Reserva");
            alert.setHeaderText("Introduïu les noves dades del treballador:");
            alert.getDialogPane().setContent(gridPane);
            alert.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);



            // Esperar a que el usuario presione OK o Cancel
            Optional<ButtonType> resultat = alert.showAndWait();

            if (resultat.isPresent() && resultat.get() == ButtonType.OK)
            {

                // si els combooxes no estan buits
                if(dataEndComboBox.getValue() != null && estatComboBox.getValue() != null &&
                        usuariComboBox.getValue()!=null && llibreComboBox.getValue()!=null) {

                    //creacio del prestec
                    Prestec prestec = new Prestec();

                    prestec.setTreballador(worker); // afegieixo el treballador
                    prestec.setUsuari(usuariComboBox.getValue());   // el usuari
                    prestec.setLlibre(llibreComboBox.getValue()); // el llibre

                    //afegixio la data de avui com a data d'inici
                    Date dateInici = Date.valueOf(LocalDate.now());
                    prestec.setDataInici(dateInici);

                   if(estatComboBox.getValue().equals("Reservat"))
                   {
                       prestec.setEstat(1);
                       Date endDate = Date.valueOf(LocalDate.now().plusDays(5));
                       prestec.setDataFI(endDate);

                       //afegir a memoria
                       listReserves.add(prestec);
                       ObservableList<Prestec> prestecObservableList = FXCollections.observableArrayList(listReserves);
                       taulaReserves.setItems(prestecObservableList);

                       GestioPrestec  gestioPrestec = new GestioPrestec();
                       gestioPrestec.crearReserva(prestec);

                       switchToReserva(event);
                   }
                   else if(estatComboBox.getValue().equals("Cancelat"))
                   {
                        prestec.setEstat(2);
                       Date endDate = Date.valueOf(LocalDate.now().plusDays(5));
                       prestec.setDataFI(endDate);

                       //afegir a memoria
                       listReserves.add(prestec);
                       ObservableList<Prestec> prestecObservableList = FXCollections.observableArrayList(listReserves);
                       taulaReserves.setItems(prestecObservableList);

                       GestioPrestec  gestioPrestec = new GestioPrestec();
                       gestioPrestec.crearReserva(prestec);

                       switchToReserva(event);
                   }
                   else if(estatComboBox.getValue().equals("Tornat"))
                   {
                       prestec.setEstat(3);
                       Date endDate = Date.valueOf(LocalDate.now().plusDays(5));
                       prestec.setDataFI(endDate);

                       //afegir a memoria
                       listReserves.add(prestec);
                       ObservableList<Prestec> prestecObservableList = FXCollections.observableArrayList(listReserves);
                       taulaReserves.setItems(prestecObservableList);

                       //update the database
                       GestioPrestec  gestioPrestec = new GestioPrestec();
                       gestioPrestec.crearReserva(prestec);

                       switchToReserva(event);
                   }
                   else if(estatComboBox.getValue().equals("En Prèstec"))
                   {
                        prestec.setEstat(4);
                       Date endDate = Date.valueOf(LocalDate.now().plusDays(5));
                       prestec.setDataFI(endDate);

                       //afegir a memoria
                       listReserves.add(prestec);
                       ObservableList<Prestec> prestecObservableList = FXCollections.observableArrayList(listReserves);
                       taulaReserves.setItems(prestecObservableList);


                       //actualizar la base de datos
                       GestioPrestec  gestioPrestec = new GestioPrestec();
                       gestioPrestec.crearReserva(prestec);

                       switchToReserva(event);

                   }


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
            System.out.println("Error adding Reserva: " + e.getMessage());

        }


    }


    /**
     * Metode que el que fa es canviar el estat de la prestec selecionat
     * @param event actionevent
     */
    public void onPrestecReservat(ActionEvent event)
    {
        try {

            Prestec prestec = taulaReserves.getSelectionModel().getSelectedItem();
            if(prestec!=null)
            {
                // cambio el estat a 1
                prestec.setEstat(1);
                GestioPrestec gestioPrestec= new GestioPrestec();
                gestioPrestec.modificarEstatReserva(prestec);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error Canviant el estat a  Reservat: " +e.getMessage());
        }
    }

    /**
     * Metode per canvia el estat de la prestec selecionat a cancelat
     * @param event action event
     */
    public void onPrestecCancelat(ActionEvent event)
    {
        try {
            Prestec prestec = taulaReserves.getSelectionModel().getSelectedItem();
            if(prestec!=null)
            {
                // cambio el estat a 2
                prestec.setEstat(2);
                GestioPrestec gestioPrestec= new GestioPrestec();
                gestioPrestec.modificarEstatReserva(prestec);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error canceled prestec: " +e.getMessage());
        }
    }

    /**
     * Metode que canvia el estat de la reserva a tornat
     * @param event
     */
    public void onPrestecTornat(ActionEvent event)
    {
        try
        {
            Prestec prestec = taulaReserves.getSelectionModel().getSelectedItem();
            if(prestec!=null)
            {
                // cambio el estat a 3
                prestec.setEstat(3);
                GestioPrestec gestioPrestec= new GestioPrestec();
                gestioPrestec.modificarEstatReserva(prestec);
            }

        }
        catch (Exception e)
        {
            System.out.println("Tornar Llibre Error :" +e.getMessage());
        }
    }


    /**
     * metode per canvia  el estat de la reserva a EN PRÈSTEC
     * @param event actionevent
     */
    public void onPrestecEnPrestat(ActionEvent event)
    {
        try {
            Prestec prestec = taulaReserves.getSelectionModel().getSelectedItem();
            if(prestec!=null)
            {
                // cambio el estat a 4
                prestec.setEstat(4);
                GestioPrestec gestioPrestec= new GestioPrestec();
                gestioPrestec.modificarEstatReserva(prestec);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error EN PRESTEC  la reserva: " +e.getMessage());
        }
    }




    // **** CANVIS DE PANTALLA **** //

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
