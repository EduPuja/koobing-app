package edu.pujadas.koobing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.pujadas.koobing_app.Models.Usuari;


public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;
    ScrollView scrollView;
    TextView homeLabel;
    




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        setTitle("Home");

        //find by id
        homeLabel = findViewById(R.id.homeLabel);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        scrollView = findViewById(R.id.scrollView);



        // Posar el home como activat
        bottom_navigation.setSelectedItemId(R.id.navigation_home);

        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        // canvi de pantalla a mapa home
                        startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                        return true;
                    case R.id.search_bar:
                        // canvi de pantalla a buscador
                        startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                        return true;
                    case R.id.mapa:
                        //canvi de pantalla a mapa
                        startActivity(new Intent(HomeActivity.this, MapsActivity.class));
                        return true;
                }
                return false;
            }
        });



    }




}