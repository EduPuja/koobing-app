package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.pujadas.koobing_app.Adapters.LlibreAdapter;
import edu.pujadas.koobing_app.Loaders.LlibreLoader;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Utilites.UsuarioSingleton;

public class ProfileActivit extends AppCompatActivity {

    TextView nom,cognom,email,dni;
    LlibreLoader llibreLoader;
    LlibreAdapter bookAdapter;
    RecyclerView recyclerView;

    Spinner spinner;
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
        spinner = findViewById(R.id.spinner);

        List<String> opciones = new ArrayList<>();
        opciones.add("1.Reservats");
        opciones.add("2.Cancelats");
        opciones.add("3.Tornats");
        opciones.add("4.En Prèstec");

        ArrayAdapter<String> adapterSipnner = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, opciones);
        adapterSipnner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSipnner);


        listLlibres = new ArrayList<Llibre>();

        //initialiazing the recycler view
        recyclerView = findViewById(R.id.booksRecycler);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        bookAdapter = new LlibreAdapter(listLlibres);
        recyclerView.setAdapter(bookAdapter);

        Usuari usuari =UsuarioSingleton.getInstance().getUsuario();

        nom.setText(usuari.getNom());
        cognom.setText(usuari.getCognom());
        email.setText(usuari.getEmail());
        dni.setText(usuari.getDni());
    }

}