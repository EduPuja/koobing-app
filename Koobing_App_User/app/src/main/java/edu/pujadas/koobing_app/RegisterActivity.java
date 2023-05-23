package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Services.RegisterService;
import edu.pujadas.koobing_app.Services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private RegisterService registerService;

    EditText dniField ,nomField,cognomField,dataNaixField,correuField,passwordField;
    Button registerBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        setTitle("Register");

        dniField = findViewById(R.id.dniField);
        nomField = findViewById(R.id.nomField);
        cognomField = findViewById(R.id.cognomField);
        dataNaixField = findViewById(R.id.dataNaixField);
        correuField =  findViewById(R.id.correuField);
        passwordField = findViewById(R.id.passwordField);

    }


    /**
     * Metode per doanr d'alta un usuairo
     * @param v vista
     */
    public void onRegisterClick(View v)
    {





    }

    private void sendUserPost(Usuari usuari)
    {

        //ip instutut
        String url = "http://192.168.16.254:3000/register/";
        //ip home
        // String url ="http://192.168.0.33:3000/register/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        registerService = retrofit.create(RegisterService.class);

        Call<Void> call = registerService.register(usuari);


        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), "T'has donat d'alta correctament ! "+usuari.getNom() +" "+usuari.getCognom(), Toast.LENGTH_SHORT).show();;
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Hi ha hagut un error \uD83D\uDE14", Toast.LENGTH_SHORT).show();;
            }
        });
    }
}