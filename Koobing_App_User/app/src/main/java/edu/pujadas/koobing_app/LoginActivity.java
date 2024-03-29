package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

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

    /**
     * Metode que el que fa es comprovar el si usuari esta donat d'alta en la api web
     * @param view vista
     */
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
                public void onSuccess(Usuari usuari) {
                    //System.out.println("Has obtingut el usuari correctament");

                    if(Validator.checkPassword(password,usuari.getPassword()))
                    {
                        Toast.makeText(getApplicationContext(),"Benvingut: "+ usuari.getNom() +" "+usuari.getCognom(),Toast.LENGTH_SHORT).show();
                        //guardar la sessio del usuari en el shared preferences
                        UsuarioSingleton.getInstance().setUsuario(usuari);

                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);

                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Contrassenya incorrecte \uD83D\uDE14",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onError(int statusCode) {
                    System.out.println("Error ");

                    if(statusCode == 404)
                    {
                        Toast.makeText(getApplicationContext(),"No hem trobat el usauri amb correu: " +email,Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Error: " +statusCode,Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println("Failure LOGIN:" +throwable.getMessage());
                    Toast.makeText(getApplicationContext(),"Error al servidor" ,Toast.LENGTH_SHORT).show();
                }
            });


        }





    }

    /**
     * Metode per anar al register al fer click en el text de registre
     * @param vista vista
     */
    public void onClickRegister(View vista)
    {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}