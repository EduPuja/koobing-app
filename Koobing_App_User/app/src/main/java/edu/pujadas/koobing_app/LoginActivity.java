package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.pujadas.koobing_app.Loaders.UserLoader;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.UserService;
import edu.pujadas.koobing_app.Utilites.*;
import java.util.ArrayList;
import java.util.List;

import edu.pujadas.koobing_app.Models.Usuari;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    TextView loginLabel;
    EditText emailField;
    EditText passwordField;
    Button loginSubmit;

    UserLoader userLoader;

    public ArrayList<Usuari> listUsers = new ArrayList<Usuari>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // find by id
        loginLabel = findViewById(R.id.loginLabel);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        loginSubmit = findViewById(R.id.loginSubmit);

        //user loader classe per carregar les dades necesaries de la base de dade i la api
        userLoader = new UserLoader();




    }

    public void onLoginSubmit(View view)
    {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();


        if(email.isEmpty() && password.isEmpty())
        {
            emailField.setError("Emplena aquest camp");
            passwordField.setError("Emplena aquest camp ");
        }
        else
        {

            userLoader.obtenerUsuarioPorCorreo(email, new ApiCallback<Usuari>() {
                @Override
                public void onSuccess(Usuari data) {
                    //System.out.println("Has obtingut el usuari correctament");

                    if(Validator.checkPassword(password,data.getPassword()))
                    {
                        Toast.makeText(getApplicationContext(),"Benvingut: "+ data.getNom() +" "+data.getCognom(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Contrassenya incorrecte \uD83D\uDE14",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onError(int statusCode) {
                    System.out.println("Error");
                    Toast.makeText(getApplicationContext(),"Error: " +statusCode,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println("Failure");
                    Toast.makeText(getApplicationContext(),"Error al servidor" ,Toast.LENGTH_SHORT).show();
                }
            });

            //ip institut
            /*String url = "http://192.168.16.254:3000/users/";
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            UserService userService = retrofit.create(UserService.class);

            Call<Usuari> call = userService.getUserByEmail(email);

            call.enqueue(new Callback<Usuari>() {

                @Override
                public void onResponse(Call<Usuari> call, Response<Usuari> response) {
                    if (response.isSuccessful()) {
                        System.out.println("NASHE");
                    }

                }

                @Override
                public void onFailure(Call<Usuari> call, Throwable t) {
                    System.out.println("Failure: " + t.getMessage());
                }


            }); //end callback enque*/

        }


    }
}