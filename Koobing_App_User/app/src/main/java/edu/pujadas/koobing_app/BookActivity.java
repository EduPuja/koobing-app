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
import edu.pujadas.koobing_app.Models.Autor;
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

            //Toast.makeText(getApplicationContext(),"ISBN ? "+ llibreOnlyIsbn.getISBN(),Toast.LENGTH_SHORT).show();
            LlibreLoader llibreLoader = new LlibreLoader();

            llibreLoader.findBookByISBN(llibreOnlyIsbn.getISBN(), new ApiCallback<Llibre>() {
                @Override
                public void onSuccess(Llibre data) {
                    if(data !=null)
                    {
                        System.out.println("INFO LLIBRE GOOD");

                        Toast.makeText(getApplicationContext(),"llibre good",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(int statusCode) {
                    System.out.println("Info llibre error: " + statusCode);
                    Toast.makeText(getApplicationContext(),"llibre error " +statusCode,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println("On faliure : " + throwable.getMessage());
                    Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();
                }
            });



        }
    }
}