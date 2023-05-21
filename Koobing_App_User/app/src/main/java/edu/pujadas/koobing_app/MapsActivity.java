package edu.pujadas.koobing_app;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import edu.pujadas.koobing_app.Models.Biblioteca;
import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Services.BibliotecaService;
import edu.pujadas.koobing_app.databinding.MapsActivityBinding;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapsActivityBinding binding;

    private BibliotecaService bibliotecaService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MapsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // carrgant el retorofit
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.33:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creant la instacion per poder recollir totes les biblioteces
        bibliotecaService = retrofit.create(BibliotecaService.class);


        List<Biblioteca> listBiblioteques = (List<Biblioteca>) bibliotecaService.getBiblioteques();

        // Afegint les ubuicacions gracies

        for(int i = 0; i < listBiblioteques.size(); i++)
        {
            LatLng ubicacions = new LatLng(listBiblioteques.get(i).getLatitud(), listBiblioteques.get(i).getLatitud());
            mMap.addMarker(new MarkerOptions().position(ubicacions).title(listBiblioteques.get(i).getNomBiblioteca()));
        }*/

       // mMap.moveCamera(CameraUpdateFactory.newLatLng());
    }
}