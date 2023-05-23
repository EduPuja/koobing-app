package edu.pujadas.koobing_app.Services;

import edu.pujadas.koobing_app.Models.Reserva;
import edu.pujadas.koobing_app.Models.Usuari;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {

    @POST("/register")
    Call<Void> register(@Body Usuari userRegisted);
}
