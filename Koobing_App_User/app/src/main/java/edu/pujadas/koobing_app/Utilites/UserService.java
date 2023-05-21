package edu.pujadas.koobing_app.Utilites;

import java.util.List;

import edu.pujadas.koobing_app.Models.Usuari;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {

    String ruta = "http://192.168.0.33:3000/users/";
    @GET(ruta)
    Call<List<Usuari>>getUsuaris();

}
