package edu.pujadas.koobing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import edu.pujadas.koobing_app.Adapters.CarouselAdapter;
import edu.pujadas.koobing_app.Loaders.LlibreLoader;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Services.ApiCallback;


public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;
    ViewPager viewPager;
    CarouselAdapter carouselAdapter;
    TextView homeLabel;
    LlibreLoader llibreLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        llibreLoader = new LlibreLoader();
        setContentView(R.layout.home_activity);
        setTitle("Home");

        //find by id
        homeLabel = findViewById(R.id.searchLabel);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        viewPager = findViewById(R.id.viewPager);

        // Posar el home como activat
        bottom_navigation.setSelectedItemId(R.id.navigation_home);
        setBottom_navigation();
        loadBookInfo();

        //peticio llibre
        test();


    }

    //test fer petcio llibre
    public void test()
    {
        llibreLoader.findBookByISBN("12376217637612", new ApiCallback<Llibre>() {
            @Override
            public void onSuccess(Llibre data) {
                Toast.makeText(getApplicationContext(),"Succes", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(getApplicationContext(),"Error :"+statusCode ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT).show();
            }
        });
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
                    return true;
                } else if (itemId == R.id.search_bar) {
                    // Navegar a la actividad SearchActivity
                    startActivity(new Intent(HomeActivity.this, SearchActivity.class));

                    return true;
                } else if (itemId == R.id.profile) {
                    // Navegar a la actividad MapActivity
                    //Toast.makeText(getApplicationContext(),"Mapa",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeActivity.this, ProfileActivit.class));

                    return true;
                }
                return false;

            }
        });

    }


    /**
     * Metode per carregar la informacio del llibre en el carrusel de la homeActivity
     */
    public void loadBookInfo()
    {


        //mostar els 10 llibres de la base de dades en el carrusel
        llibreLoader.obtenir10Llibres(new ApiCallback<List<Llibre>>() {
            @Override
            public void onSuccess(List<Llibre> data) {
                if(data!=null && !data.isEmpty())
                {

                    // Crea una instancia del adaptador personalizado
                    carouselAdapter = new CarouselAdapter(data, getLayoutInflater());

                    // Asigna el adaptador al ViewPager
                    viewPager.setAdapter(carouselAdapter);


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