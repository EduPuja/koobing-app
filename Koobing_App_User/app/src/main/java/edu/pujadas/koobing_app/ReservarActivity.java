package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;

import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Models.Reserva;
import edu.pujadas.koobing_app.Models.Treballador;
import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Services.ReservaService;
import edu.pujadas.koobing_app.Utilites.RetrofitConnection;
import edu.pujadas.koobing_app.Utilites.UsuarioSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservarActivity extends AppCompatActivity {
    private String BASE_URL= "http://192.168.0.33:3000/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservar_activity);
    }


    public void reservarBook()
    {
        RetrofitConnection connection = new RetrofitConnection(BASE_URL);
        ReservaService reservaService = connection.getRetrofit().create(ReservaService.class);

        Reserva reserva = new Reserva();

        //afegint el treballadro
        Treballador treballador = new Treballador();
        treballador.setId(1);   // es el adminsitrador del sistema
        treballador.setAdmin(true);
        treballador.setEmail("admin@mail.com");
        treballador.setNom("Admin");
        //usuari
        Usuari user = UsuarioSingleton.getInstance().getUsuario();


        //llibre
        Intent intent =getIntent();
        if(intent.hasExtra("bookGson"))
        {
            String bookGson = intent.getStringExtra("bookGson");
            Gson gson = new Gson();
            Llibre bookIntent = gson.fromJson(bookGson, Llibre.class);
            reserva.setLlibre(bookIntent);
        }
        reserva.setTreballador(treballador);
        reserva.setUsuari(user);



        // todo falta data inici data fi



        reserva.setEstat(1);
        Call<Void> call = reservaService.hacerReserva(reserva);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "succes Reserva", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error :" +response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure Reserva " +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}