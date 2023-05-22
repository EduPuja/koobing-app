package edu.pujadas.koobing_app.Services;

import java.util.List;

import edu.pujadas.koobing_app.Models.Usuari;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {


    //ruta insti 192.168.137.1

    //String ruta = "http://192.168.0.33:3000/users/";
    String ruta = "http://192.168.137.1:3000/users/";
    @GET(ruta)
    Call<List<Usuari>>getUsuaris();

    @GET("user/{email}")
    Call<Usuari> getUserByEmail(@Path("email") String email);

}
