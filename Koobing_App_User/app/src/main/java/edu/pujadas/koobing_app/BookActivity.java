package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Socket;

import edu.pujadas.koobing_app.Loaders.LlibreLoader;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Services.ApiCallback;
import retrofit2.Call;

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







    }



    public void onReservar(View view) {

        LlibreLoader llibreLoader = new LlibreLoader();
        llibreLoader.findBookByISBN("765434231123", new ApiCallback<Llibre>() {
            @Override
            public void onSuccess(Llibre data) {
                if(data!=null)
                {
                    Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(getApplicationContext(), " error :" +statusCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Filure :" +throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}