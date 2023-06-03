package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.pujadas.koobing_app.Adapters.LlibreAdapter;
import edu.pujadas.koobing_app.Loaders.LlibreLoader;
import edu.pujadas.koobing_app.Models.Llibre;

public class ProfileActivit extends AppCompatActivity {

    TextView nom,cognom,email,dni;
    LlibreLoader llibreLoader;
    LlibreAdapter bookAdapter;
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    List<Llibre> listLlibres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        nom = findViewById(R.id.userName);
        cognom = findViewById(R.id.userSurname);
        email = findViewById(R.id.userEmail);
        dni = findViewById(R.id.userDni);

        listLlibres = new ArrayList<Llibre>();

        //initialiazing the recycler view
        recyclerView = findViewById(R.id.booksRecycler);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        bookAdapter = new LlibreAdapter(listLlibres);
        recyclerView.setAdapter(bookAdapter);

    }

}