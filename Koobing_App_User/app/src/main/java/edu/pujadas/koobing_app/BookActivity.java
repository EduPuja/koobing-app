package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.util.Locale;

import edu.pujadas.koobing_app.Loaders.LlibreLoader;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Models.Reserva;
import edu.pujadas.koobing_app.Models.Treballador;
import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.LlibreService;
import edu.pujadas.koobing_app.Services.ReservaService;
import edu.pujadas.koobing_app.Utilites.RetrofitConnection;
import edu.pujadas.koobing_app.Utilites.UsuarioSingleton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookActivity extends AppCompatActivity {

    TextView titolLlibre,autorName,editorial,genere,idioma,edicio,dataPublicacio;
    ImageView logoLlibre;

    LlibreLoader llibreLoader;

    LlibreService llibreService;

    //private String BASE_URL= "http://192.168.0.33:3000/";
    private  String BASE_URL = "http://192.168.16.254:3000/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        //find all elements in xml file
        logoLlibre = findViewById(R.id.logoLlibre);
        titolLlibre = findViewById(R.id.nomLlibre);
        autorName = findViewById(R.id.autorName);
        editorial = findViewById(R.id.editorialName);
        genere = findViewById(R.id.genereName);
        idioma = findViewById(R.id.idioma);
        edicio = findViewById(R.id.edicio);
        dataPublicacio = findViewById(R.id.dataPubli);

        llibreLoader = new LlibreLoader();

        Intent intent =getIntent();
        if(intent.hasExtra("bookGson"))
        {
            String bookGson = intent.getStringExtra("bookGson");
            Gson gson = new Gson();
            Llibre bookIntent = gson.fromJson(bookGson, Llibre.class);
            getInfoBook(bookIntent.getISBN().toString());
        }





    }

    /**
     * Metode per carregar la informacio del llibre
     * @param isbn String
     */
    public void getInfoBook(String isbn)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        llibreService = retrofit.create(LlibreService.class);
        Call<ResponseBody> call = llibreService.getBookByISBN(isbn);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);

                        // Extrae los valores del JSONObject y actualiza los TextView correspondientes
                        titolLlibre.setText(jsonObject.getString("titol"));
                        autorName.setText(jsonObject.getString("nom_autor"));
                        editorial.setText(jsonObject.getString("nom_editorial"));
                        genere.setText(jsonObject.getString("descrip"));
                        idioma.setText(jsonObject.getString("nom_idioma"));
                        edicio.setText(jsonObject.getString("versio"));

                        String dataPubli= jsonObject.getString("data_publi");
                        SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                        SimpleDateFormat formatoDeseado = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());


                        java.util.Date fecha = formatoOriginal.parse(dataPubli);
                        String fechaFormateada = formatoDeseado.format(fecha);
                        dataPublicacio.setText(fechaFormateada);


                    } catch (JSONException | IOException  | ParseException e ) {
                        e.printStackTrace();
                    }
                } else {
                    // La solicitud no fue exitosa, maneja el error aquí
                    Toast.makeText(getApplicationContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Maneja el error de la solicitud aquí
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void onReservar(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // Crea un diálogo DatePicker
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
                // Aquí obtienes la fecha seleccionada
               LocalDate dataEnd = LocalDate.of(selectedYear, monthOfYear,dayOfMonth);
                Date sqlDate = Date.valueOf(dataEnd.toString());
                // Haz lo que quieras con la fecha seleccionada
                sendReservaPOST(sqlDate);

            }
        }, year, month, day);

        datePickerDialog.setTitle("Seleciona la data de retorn");
        // Muestra el diálogo DatePicker
        datePickerDialog.show();






    }

    public void sendReservaPOST(java.sql.Date dataEnd)
    {
        RetrofitConnection connection = new RetrofitConnection(BASE_URL);
        ReservaService reservaService = connection.getRetrofit().create(ReservaService.class);

        Reserva reserva = new Reserva();

        //afegint el treballadro
        Treballador treballador = new Treballador();
        treballador.setId(1);   // es el adminsitrador del sistema
        treballador.setAdmin(true);
        treballador.setEmail("admin@mail.com");
        treballador.setNom("Admin");
        //usuari
        Usuari user = UsuarioSingleton.getInstance().getUsuario();


        //llibre
        Intent intent =getIntent();
        if(intent.hasExtra("bookGson"))
        {
            String bookGson = intent.getStringExtra("bookGson");
            Gson gson = new Gson();
            Llibre bookIntent = gson.fromJson(bookGson, Llibre.class);
            reserva.setLlibre(bookIntent);
        }
        reserva.setTreballador(treballador);
        reserva.setUsuari(user);



        // todo falta data inici data fi

        reserva.setDataFI(dataEnd);



        reserva.setEstat(1);
        Call<Void> call = reservaService.hacerReserva(reserva);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "succes Reserva", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error :" +response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure Reserva " +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}