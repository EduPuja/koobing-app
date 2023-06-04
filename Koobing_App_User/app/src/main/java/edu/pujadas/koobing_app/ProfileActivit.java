package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import edu.pujadas.koobing_app.Adapters.LlibreAdapter;
import edu.pujadas.koobing_app.Adapters.ReservaAdapter;
import edu.pujadas.koobing_app.Loaders.LlibreLoader;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Models.Reserva;
import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Services.ReservaService;
import edu.pujadas.koobing_app.Utilites.RetrofitConnection;
import edu.pujadas.koobing_app.Utilites.UsuarioSingleton;
import edu.pujadas.koobing_app.Utilites.Validator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivit extends AppCompatActivity {


    String baseUrl = "http://192.168.0.33:3000/";
    TextView nom,cognom,email,dni;


    LlibreLoader llibreLoader;
    LlibreAdapter bookAdapter;
    ReservaAdapter reservaAdapter;

    RecyclerView recyclerView;

    Spinner spinner;
    private LinearLayoutManager layoutManager;

    List<Llibre> listLlibres;
    List<Reserva> listReserva;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        //find by id
        nom = findViewById(R.id.userName);
        cognom = findViewById(R.id.userSurname);
        email = findViewById(R.id.userEmail);
        dni = findViewById(R.id.userDni);
        spinner = findViewById(R.id.spinner);
        // load info user
        loadInfoUser();
        // start the spinner
        startSpinner();

        listLlibres = new ArrayList<Llibre>();
        listReserva = new ArrayList<Reserva>();
        //initialiazing the recycler view
        /*recyclerView = findViewById(R.id.booksRecycler);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        bookAdapter = new LlibreAdapter(listLlibres);
        recyclerView.setAdapter(bookAdapter);*/

    }


    /**
     * Metode per carregar la informació del usuari
     */
    public void loadInfoUser()
    {
        // obntenint la info del usuari per posar-ho per pentalla
        Usuari usuari =UsuarioSingleton.getInstance().getUsuario();

        nom.setText(usuari.getNom());
        cognom.setText(usuari.getCognom());
        email.setText(usuari.getEmail());
        dni.setText(usuari.getDni());
    }

    /**
     * Metode que inicialitza el spinner
     */
    public void startSpinner()
    {
        List<String> opciones = new ArrayList<>();
        opciones.add("Reservat");
        opciones.add("Cancelat");
        opciones.add("Tornat");
        opciones.add("En Prèstec");

        ArrayAdapter<String> adapterSipnner = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, opciones);
        adapterSipnner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSipnner);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String opcionSelected = opciones.get(position);

                if (opcionSelected.equals("Reservat")) {

                    RetrofitConnection retrofitConnection = new RetrofitConnection(baseUrl);
                    ReservaService reservaService = retrofitConnection.getRetrofit().create(ReservaService.class);
                    Usuari usuari = UsuarioSingleton.getInstance().getUsuario();
                    Call<ResponseBody> callReserv = reservaService.obtenirLlibresReservats(usuari.getId());
                    callReserv.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    String responseString = response.body().string();
                                    JSONArray jsonArray = new JSONArray(responseString);
                                    List<Reserva> listReserva = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        int idPrestec =jsonObject.getInt("id_prestec");
                                        long isbn = jsonObject.getLong("isbn");
                                        String titol = jsonObject.getString("titol");
                                        String dataIniciString = jsonObject.getString("data_inici");
                                        String dataFiString = jsonObject.getString("data_fi");

                                        Date dataInici = Validator.convertirStringADateSQL(dataIniciString);
                                        Date dataEnd = Validator.convertirStringADateSQL(dataFiString);

                                        Reserva reserva = new Reserva();
                                        reserva.setIdReserva(idPrestec);

                                        Llibre llibre = new Llibre();
                                        llibre.setISBN(isbn);
                                        llibre.setTitol(titol);
                                        reserva.setLlibre(llibre);

                                        reserva.setDataInici(dataInici);
                                        reserva.setDataFI(dataEnd);

                                        listReserva.add(reserva);
                                    }
                                    initRecyclerReseva(listReserva);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            // Manejar la falla en la llamada a la API
                        }
                    });
                }

                //sendRequest(opcionSelected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }




    private void initRecyclerReseva(List<Reserva> listReserva)
    {
        recyclerView = findViewById(R.id.booksRecycler);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        reservaAdapter = new ReservaAdapter(listReserva);
        recyclerView.setAdapter(reservaAdapter);
    }

}