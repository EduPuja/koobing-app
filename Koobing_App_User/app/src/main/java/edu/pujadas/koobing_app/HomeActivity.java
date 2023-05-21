package edu.pujadas.koobing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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

import edu.pujadas.koobing_app.Adapters.CarouselAdapter;
import edu.pujadas.koobing_app.Loaders.LlibreBibliotecaLoader;
import edu.pujadas.koobing_app.Models.Biblioteca;
import edu.pujadas.koobing_app.Models.LlibreBiblioteca;
import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Services.ApiCallback;


public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;
    ViewPager viewPager;
    CarouselAdapter carouselAdapter;

    TextView homeLabel;

    //loader per carrgear tota la info en un list
    LlibreBibliotecaLoader bookBiblioLoader;
    ArrayList<LlibreBiblioteca> listBiblios = new ArrayList<LlibreBiblioteca>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        setTitle("Home");

        //find by id
        homeLabel = findViewById(R.id.homeLabel);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewPager);


        // Posar el home como activat
        bottom_navigation.setSelectedItemId(R.id.navigation_home);
        setBottom_navigation();

        loadBookInfoToViewPage();








    }


    public void setBottom_navigation()
    {
        // menu inferior
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    // Navegar a la actividad HomeActivity
                    startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.search_bar) {
                    // Navegar a la actividad SearchActivity
                    startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_map) {
                    // Navegar a la actividad MapActivity
                    //Toast.makeText(getApplicationContext(),"Mapa",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeActivity.this, MapsActivity.class));
                    finish();
                    return true;
                }
                return false;

            }
        });

    }


    public void loadBookInfoToViewPage()
    {
        bookBiblioLoader = new LlibreBibliotecaLoader();

        bookBiblioLoader.obtenerLibrosfinal(new ApiCallback<List<LlibreBiblioteca>>() {
            @Override
            public void onSuccess(List<LlibreBiblioteca> data) {
                if(data!=null && !data.isEmpty())
                {
                    System.out.println("Success Book info");

                    String titol = data.get(0).getBook().getTitol();
                    System.out.println("Titols: " + titol);
                    // Crea una instancia del adaptador personalizado
                    //carouselAdapter = new CarouselAdapter(data, getLayoutInflater());

                    // Asigna el adaptador al ViewPager
                    //viewPager.setAdapter(carouselAdapter);
                }
            }

            @Override
            public void onError(int statusCode) {
                System.out.println("Error :" +statusCode);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("Failure Book " + throwable.getMessage());
            }
        });
    }





}