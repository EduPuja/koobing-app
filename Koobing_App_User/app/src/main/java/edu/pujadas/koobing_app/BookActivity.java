package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.net.Socket;

import edu.pujadas.koobing_app.Loaders.LlibreLoader;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.LlibreService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookActivity extends AppCompatActivity {

    TextView titolLlibre,autorName,editorial,genere,idioma,edicio,dataPublicacio;
    ImageView logoLlibre;

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


        Intent intent = getIntent();

        if (intent.hasExtra("bookGson")) {
            String bookGson = intent.getStringExtra("bookGson");
            Gson gson = new Gson();
            Llibre llibreOnlyIsbn = gson.fromJson(bookGson, Llibre.class);

            String url = "http://192.168.0.33:3000/book/" + llibreOnlyIsbn.getISBN() + "/";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            LlibreService apiService = retrofit.create(LlibreService.class);
            Call<Llibre> call = apiService.getBookByISBN(llibreOnlyIsbn.getISBN());
            call.enqueue(new Callback<Llibre>() {
                @Override
                public void onResponse(Call<Llibre> call, Response<Llibre> response) {
                    if (response.isSuccessful()) {
                        Llibre book = response.body();

                        System.out.println("Tota info Llibre: "+book.getAllInfoBook());


                    }
                    else {

                    }
                }

                @Override
                public void onFailure(Call<Llibre> call, Throwable t) {

                }
            });

        }
    }
}