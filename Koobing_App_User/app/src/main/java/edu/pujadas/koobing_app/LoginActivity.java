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


public class LoginActivity extends AppCompatActivity {

    TextView loginLabel;
    EditText emailField;
    EditText passwordField;
    Button loginSubmit;

    UserLoader userLoader;


    private  ArrayList<Usuari> listUsuarios = new ArrayList<Usuari>();
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
             if(data != null && !data.isEmpty()) {
                 Usuari primerUsuario = data.get(0);


                 // Crear el objeto Usuario a partir de los datos recibidos
                 String nombreUsuario = primerUsuario.getNom();


                 // Cambiar el texto de un TextView o Label
                 loginLabel.setText(nombreUsuario);
             }
            }

            @Override
            public void onError(int statusCode) {
                Toast.makeText(LoginActivity.this, "Error en la respuesta: " + statusCode, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(LoginActivity.this, "No se pudo realizar la solicitud: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }

    public void onLoginSubmit(View view)
    {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();




    }
}