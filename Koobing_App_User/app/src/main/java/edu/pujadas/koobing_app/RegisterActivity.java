package edu.pujadas.koobing_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;

import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Services.UserService;
import edu.pujadas.koobing_app.Utilites.RetrofitConnection;
import edu.pujadas.koobing_app.Utilites.Validator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private UserService userService;

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
        passwordField = findViewById(R.id.contrassenyaField);

    }


    /**
     * Metode per doanr d'alta un usuairo
     * @param v vista
     */
    public void onRegisterClick(View v)
    {
        //recollint tot el que ha entrat el usuari per text
        String dni = dniField.getText().toString();
        String nom = nomField.getText().toString();
        String cognom = cognomField.getText().toString();
        String dataText = dataNaixField.getText().toString();
        String email = correuField.getText().toString();
        String password = passwordField.getText().toString();


        if(dni.isEmpty() &&  nom.isEmpty() &&  cognom.isEmpty()  && dataText.isEmpty() &&  email.isEmpty() && password.isEmpty())
        {
            // posar els editext en error
            String erroMsg = "Emplena aquest camps";

            dniField.setError(erroMsg);
            nomField.setError(erroMsg);
            cognomField.setError(erroMsg);
            dataNaixField.setError(erroMsg);
            correuField.setError(erroMsg);
            passwordField.setError(erroMsg);
        }
        else
        {
            Usuari usuari = new Usuari();
            usuari.setDni(dni);
            usuari.setNom(nom);
            usuari.setCognom(cognom);

            //convertir la data de naixament amb un objetce java.sql.Date
            Date dataNaixament = Date.valueOf(dataText);
            usuari.setDataNaix(dataNaixament);
            usuari.setEmail(email);
            //encriptar la contrassenya
            String hashedPasss =Validator.encryptPassword(password);
            usuari.setPassword(hashedPasss);


            sendUserPost(usuari);
        }






    }

    private void sendUserPost(Usuari usuari)
    {

        //ip instutut
        //String url = "http://192.168.16.254:3000/register/";
        //ip home
        String url ="http://192.168.0.33:3000/register/";

        RetrofitConnection retrofitConnection = new RetrofitConnection(url);


        userService = retrofitConnection.getRetrofit().create(UserService.class);

        Call<Void> call = userService.register(usuari);


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