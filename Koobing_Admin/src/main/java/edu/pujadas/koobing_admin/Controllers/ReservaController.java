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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.tableview2.TableView2;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReferenceArray;


public class ReservaController implements Initializable
{

    Parent root;
    Stage stage;
    Scene scene;


    public ImageView avatarWorker;

    // taula
    public TableView2<Reserva> taulaReserves;

    public TableColumn<Reserva,String> nomUserColum;
    public TableColumn<Reserva,Integer> idReservaColum;
    public TableColumn<Reserva,String> nomWorkerColum;
    public TableColumn<Reserva,String> nomBiblioColum;
    public TableColumn<Reserva,String> bookTitleColum;
    public TableColumn<Reserva, Timestamp> dataHoraIniciColum;
    public TableColumn<Reserva,Timestamp> dataHoraFiciColum;



    ArrayList<Reserva> listReserves = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Reserva Screen!");
        loadWorkerInfo();
        loadInfoReserves();
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
     * Metode per carregar les dades de la reserva
     */
    public void loadInfoReserves()
    {
       // System.out.println("Loading info reserva"); // debug testing
        try
        {
            GestioReserva gestioReserva= new GestioReserva();
            listReserves = gestioReserva.consultarReserves();
            ObservableList<Reserva> observableListReserva = FXCollections.observableArrayList(
                    listReserves
            );
            //afegint el observable list en el tableview
            taulaReserves.setItems(observableListReserva);
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
            nomBiblioColum.setCellValueFactory(cellData ->{
                Biblioteca bilbio = cellData.getValue().getBiblio();
                String nomBiblio = bilbio.getNomBiblioteca();
                return new SimpleStringProperty(nomBiblio);
            });
            bookTitleColum.setCellValueFactory(cellData ->{
                Llibre book = cellData.getValue().getLlibre();
                String titol = book.getTitol();
              return new SimpleStringProperty(titol);
            });
            dataHoraIniciColum.setCellValueFactory(new PropertyValueFactory<>("dataHoraReserva"));
            dataHoraFiciColum.setCellValueFactory(new PropertyValueFactory<>("dataHoraEntrega"));



        }
        catch (Exception e)
        {
            e.printStackTrace();    //degub error
        }
    }


    //todo IMPORNTANT se ha de compovar que la dataInici sigui més PETTIT que la de final . La final no pot ser més gran

    public void onAddReserva(ActionEvent event )
    {
        try {


            //usuari
            ComboBox<Usuari> usuariComboBox = new ComboBox<Usuari>();


            //treballador
            Treballador worker = TrabajadorSingleton.getInstance().getTrabajador();
            TextField workerName = new TextField(worker.getNom());
            workerName.setDisable(true); // el desabilito perque no es pigui modificar

            //biblioteca

            ComboBox<Biblioteca> bibliotecaComboBox = new ComboBox<Biblioteca>();



            // libre
            ComboBox<Llibre > llibreComboBox  = new ComboBox<Llibre>();




            //IMPORTANT AFEGIR LES DADES als comboboxes
            addDataComboxes(usuariComboBox,bibliotecaComboBox,llibreComboBox);



            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String formatStartDate = LocalDateTime.now().format(formatter);
            TextField dataInici = new TextField(formatStartDate);
            dataInici.setDisable(true);



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
            gridPane.addRow(1, new Label("Nom del treballador :"), workerName);
            gridPane.addRow(2, new Label("Biblioteca"),bibliotecaComboBox);
            gridPane.addRow(3, new Label("Llibre"),llibreComboBox);
            gridPane.addRow(4,new Label("Data de inicio:"),dataInici);
            gridPane.addRow(5,new Label("Digues la finalizació de la reserva"),dataEndComboBox);


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
               Reserva reserva = new Reserva();



                //set user
                UsuariStringConverter userConverter = new UsuariStringConverter();
                int idUser = userConverter.getIdUsuari(usuariComboBox.getValue());
                GestioUsuari gestioUsuari = new GestioUsuari();
                Usuari user = gestioUsuari.findUserID(idUser);
                reserva.setUsuari(user);


                //set worker
                reserva.setTreballador(worker);



                //biblioteca
                BibliotecaStringConverter converterBiblio = new BibliotecaStringConverter();
                int idBiblio = converterBiblio.getIdBiblioteca(bibliotecaComboBox.getValue());
                GestioBiblioteca gestioBiblioteca = new GestioBiblioteca();
                Biblioteca biblioteca = gestioBiblioteca.findBiblioteca(idBiblio);
                reserva.setBiblio(biblioteca);

                //set llibre to reserva
                LlibreStringConverter llibreStringConverter = new LlibreStringConverter();
                long isbnBook =llibreStringConverter.getISBNLlibre(llibreComboBox.getValue());
                GestioLlibre gestioLlibre = new GestioLlibre();
                Llibre book = gestioLlibre.findLLibre(isbnBook);
                reserva.setLlibre(book);


                //data hora inci
                String dataValue = dataInici.getText();
                Timestamp timeStart = Timestamp.valueOf(dataValue);
                reserva.setDataHoraReserva(timeStart);

                //date end
                if(dataEndComboBox.getValue().equals("1 mes"))
                {
                    LocalDate newDate = LocalDate.now().plusMonths(1);
                    LocalDateTime dateTime = LocalDateTime.of(newDate, LocalTime.parse("00:00"));
                    Timestamp timeEnd = Timestamp.valueOf(dateTime);
                    reserva.setDataHoraEntrega(timeEnd);
                }
                else if(dataEndComboBox.getValue().equals("10 dies"))
                {
                    LocalDate newDate = LocalDate.now().plusDays(10);
                    LocalDateTime dateTime = LocalDateTime.of(newDate, LocalTime.parse("00:00"));
                    Timestamp timeEnd = Timestamp.valueOf(dateTime);
                    reserva.setDataHoraEntrega(timeEnd);
                }
                else if (dataEndComboBox.getValue().equals("5 dies")) {
                    LocalDate newDate = LocalDate.now().plusDays(5);
                    Timestamp timeEnd = Timestamp.valueOf(newDate.atStartOfDay());
                    reserva.setDataHoraEntrega(timeEnd);
                }





            }

        }
        catch (Exception e)
        {
            System.out.println("Error adding Reserva: " + e.getMessage());
        }


    }

    public void onDeleteReserva(ActionEvent event )
    {
        try {

        }
        catch (Exception e)
        {
            System.out.println("Error Delete Reserva: " + e.getMessage());
        }


    }
    public void onModifyReserva(ActionEvent event )
    {
        try {

        }
        catch (Exception e)
        {
            System.out.println("Error Modify Reserva: " + e.getMessage());
        }


    }


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
