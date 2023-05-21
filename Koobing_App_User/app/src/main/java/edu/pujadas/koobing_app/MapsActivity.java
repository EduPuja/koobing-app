package edu.pujadas.koobing_app;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import edu.pujadas.koobing_app.Models.Biblioteca;
import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Utilites.ApiCallback;
import edu.pujadas.koobing_app.Utilites.BibilotecaLoader;
import edu.pujadas.koobing_app.databinding.MapsActivityBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapsActivityBinding binding;

    private BibilotecaLoader bibilotecaLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MapsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // instacio el loader de bilioteca
        bibilotecaLoader = new BibilotecaLoader();




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


        // carregnat les dades de en el maps

        bibilotecaLoader.obtenerBiblioteques(new ApiCallback<List<Biblioteca>>() {
            @Override
            public void onSuccess(List<Biblioteca> data) {
                System.out.println("Success! On Biblioteca");
                if(data != null && !data.isEmpty()) {

                   // listBilioteca = (ArrayList<Biblioteca>) data;

                    for(int i = 0; i < data.size(); i++) {
                        LatLng ubicacions = new LatLng(data.get(i).getLatitud(), data.get(i).getLatitud());

                        String nom = data.get(i).getNomBiblioteca();
                        mMap.addMarker(new MarkerOptions().position(ubicacions).title(nom));


                    }

                }
                mMap.getUiSettings().setZoomControlsEnabled(true);

            }

            @Override
            public void onError(int statusCode) {
                System.out.println("Error on mapa bilioteca : " + statusCode);

            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("Failure Biblioteca: " + throwable.getMessage());
            }
        });
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