package edu.pujadas.koobing_app;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import edu.pujadas.koobing_app.Models.Biblioteca;
import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Utilites.ApiCallback;
import edu.pujadas.koobing_app.Utilites.BibilotecaLoader;
import edu.pujadas.koobing_app.databinding.MapsActivityBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapsActivityBinding binding;

    private BibilotecaLoader bibilotecaLoader;

    public ArrayList<Biblioteca> listBiblioteques = new ArrayList<Biblioteca>();


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
                if(!data.isEmpty() && data != null)
                {
                    System.out.println("Success! On Biblioteca");

                    // Cordenades de Catalunya
                    LatLngBounds catalunyaBounds = new LatLngBounds(
                            new LatLng(40.2, 0.8),
                            new LatLng(42.9, 4.8)
                    );


                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(catalunyaBounds, 0);

                    // moure la camara fins a la ubicacio
                    mMap.moveCamera(cameraUpdate);
                    mMap.animateCamera(cameraUpdate);


                    for (int i = 0; i < data.size(); i++)
                    {
                        double latitud =data.get(i).getLatitud();
                        double longitud =data.get(i).getLongitud();
                        String noms = data.get(i).getNomBiblioteca();
                        //System.out.println("Lat :  " + latitud + " Long: " + longitud);


                        LatLng ubicacions = new LatLng(latitud,longitud);

                        //afegint cada ubicacio en les opcions i el titul i el icona
                        mMap.addMarker(new MarkerOptions().position(ubicacions).
                                title(noms)
                                .snippet(noms)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.libro_tiny_icon)));
                    }


                }


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





    }
}