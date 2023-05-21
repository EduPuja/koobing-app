package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.pujadas.koobing_app.Utilites.*;
import java.util.ArrayList;
import java.util.List;

import edu.pujadas.koobing_app.Models.Usuari;
import retrofit2.Call;
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

        userLoader = new UserLoader();
        userLoader.obtenerUsuarios(new ApiCallback<List<Usuari>>() {
            @Override
            public void onSuccess(List<Usuari> data) {

                System.out.println("Succes!");
             if(data != null && !data.isEmpty()) {

                 listUsers = (ArrayList<Usuari>) data;

             }
            }

            @Override
            public void onError(int statusCode) {

                System.out.println("Error");
                //Toast.makeText(LoginActivity.this, "Error en la respuesta: " + statusCode, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Throwable throwable) {

                System.out.println("Failure: " + throwable.getMessage());
                ///Toast.makeText(LoginActivity.this, "No se pudo realizar la solicitud: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }

    public void onLoginSubmit(View view)
    {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.33:3000/") // Reemplaza con la dirección IP de tu servidor
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);

        Call<Usuari> call = userService.getUserByEmail(email);





    }
}