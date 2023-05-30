package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.pujadas.koobing_app.Adapters.LlibreAdapter;
import edu.pujadas.koobing_app.Models.Llibre;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;

    private List<Llibre> listLlibres;



    RecyclerView recyclerView;
    LlibreAdapter llibreAdapter;
    LinearLayoutManager layoutManager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        //instanciantel arraylist
        listLlibres = new ArrayList<Llibre>();


        initRecylcerView();



        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filterList(newText);
                return false;
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