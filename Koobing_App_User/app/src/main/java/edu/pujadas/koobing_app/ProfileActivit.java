package edu.pujadas.koobing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
    BottomNavigationView bottom_navigation;

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
        bottom_navigation = findViewById(R.id.bottom_navigation);

        setBottom_navigation();

        // load info user
        loadInfoUser();
        // start the spinner
        startSpinner();

        listLlibres = new ArrayList<Llibre>();
        listReserva = new ArrayList<Reserva>();


    }


    /**
     * Metode per el buto de navegacio
     */
    public void setBottom_navigation()
    {
        // menu inferior
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    // Navegar a la actividad HomeActivity
                    startActivity(new Intent(ProfileActivit.this, HomeActivity.class));
                    return true;
                } else if (itemId == R.id.search_bar) {
                    // Navegar a la actividad SearchActivity
                    startActivity(new Intent(ProfileActivit.this, SearchActivity.class));

                    return true;
                } else if (itemId == R.id.profile) {
                    // Navegar a la actividad MapActivity
                    //Toast.makeText(getApplicationContext(),"Mapa",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProfileActivit.this, ProfileActivit.class));

                    return true;
                }
                return false;

            }
        });
        bottom_navigation.getMenu().getItem(2).setChecked(true);
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
                sendRequestReseves(opcionSelected);


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


    public void sendRequestReseves(String opcionSelected)
    {
        listReserva.clear();
        if (opcionSelected.equals("Reservat")) {


            reservats();
        }

        else if(opcionSelected.equals("Cancelat"))
        {
            //cancelats
            cancelats();
        }
        else if(opcionSelected.equals("Tornat")){

        //torrants
            tornats();
        }
        else if(opcionSelected.equals("En Prèstec"))
        {
            //prestec
        }
    }


    /**
     * Metode per els llibres tornat
     */
    private void tornats() {
        RetrofitConnection retrofitConnection = new RetrofitConnection(baseUrl);
        ReservaService reservaService = retrofitConnection.getRetrofit().create(ReservaService.class);
        Usuari usuari = UsuarioSingleton.getInstance().getUsuario();
        Call<ResponseBody> callReserv = reservaService.obtenirLlibresTorants(usuari.getId());
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

                Toast.makeText(getApplicationContext(),"Failure "+t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * Metode que es dels llibres tornats
     */
    public void reservats()
    {

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

                Toast.makeText(getApplicationContext(),"Failure "+t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Metode que et crea els llistat de cancelats
     */
    public void cancelats()
    {
        RetrofitConnection retrofitConnection = new RetrofitConnection(baseUrl);
        ReservaService reservaService = retrofitConnection.getRetrofit().create(ReservaService.class);
        Usuari usuari = UsuarioSingleton.getInstance().getUsuario();
        Call<ResponseBody> callReserv = reservaService.obtenirLlibresCancelats(usuari.getId());
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

                Toast.makeText(getApplicationContext(),"Failure "+t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });

    }

}