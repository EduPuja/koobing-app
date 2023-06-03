package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.pujadas.koobing_app.Loaders.LlibreLoader;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.LlibreService;
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
                .baseUrl("http://192.168.0.33:3000/")
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


                        Date fecha = formatoOriginal.parse(dataPubli);
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


       // todo fer peticio post per fer reserva en estat RESERVAT

    }

}