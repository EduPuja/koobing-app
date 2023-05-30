package edu.pujadas.koobing_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import edu.pujadas.koobing_app.Adapters.LlibreAdapter;
import edu.pujadas.koobing_app.Loaders.LlibreLoader;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Services.ApiCallback;

public class SearchActivity extends AppCompatActivity {



    BottomNavigationView bottom_navigation;
    SearchView searchView;
    List<Llibre> listLlibres;

    //recilcer v
    RecyclerView recyclerView;
    LlibreAdapter llibreAdapter;
    LinearLayoutManager layoutManager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        // per la barre de navegacio
        setBottom_navigation();
        //afegir dades
        initData();
        //instanciantel arraylist
        listLlibres = new ArrayList<Llibre>();
        //inicialitzar el recicleryview
        initRecylcerView();

        //buscador
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                filterList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //filterList(newText);
                return false;
            }
        });


    }


    public void setBottom_navigation()
    {
        // menu inferior

        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    // Navegar a la actividad HomeActivity
                    startActivity(new Intent(SearchActivity.this, HomeActivity.class));

                    return true;
                } else if (itemId == R.id.search_bar) {
                    // Navegar a la actividad SearchActivity
                    startActivity(new Intent(SearchActivity.this, SearchActivity.class));

                    return true;
                } else if (itemId == R.id.profile) {
                    // Navegar a la actividad MapActivity

                    startActivity(new Intent(SearchActivity.this, ProfileActivit.class));

                    return true;
                }
                return false;

            }
        });

    }


    /** Metode per carregar totes les dades de la base de dades **/
    private void initData()
    {
        LlibreLoader llibreLoader =new LlibreLoader();
        llibreLoader.obtenerLibrosfinal(new ApiCallback<List<Llibre>>() {
            @Override
            public void onSuccess(List<Llibre> data) {
                if(data !=null  && !data.isEmpty())
                {
                    listLlibres = data;
                }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(getApplicationContext(),"Status code :  "+statusCode,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(getApplicationContext(),"Fail carregant dades",Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Metode per inicialitzar el recylcerview
     */
    private void initRecylcerView() {
        //recicleview
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        llibreAdapter = new LlibreAdapter(listLlibres);
        recyclerView.setAdapter(llibreAdapter);


    }

    /**
     * Metode que busca el llibre si coinicdeix et mostra el contigut filtrat en el recicler view
     * @param newText
     */
    private void filterList(String newText) {
        List<Llibre> filtreredList = new ArrayList<Llibre>();
        for (Llibre l : listLlibres)
        {
            if(l.getTitol().toLowerCase().contains(newText.toLowerCase()))
            {
                //afegint el llibre filtrat
                filtreredList.add(l);
            }


        }

        if(filtreredList.isEmpty()){
            Toast.makeText(this,"Llibre no trobat ",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //posant el filtratj de llibres
            llibreAdapter.setFiltredList(filtreredList);
        }
    }
}