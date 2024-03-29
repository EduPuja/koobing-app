package edu.pujadas.koobing_app.Services;

import edu.pujadas.koobing_app.Models.Reserva;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReservaService {

    @POST("/reservarLlibre")
    Call<Void> hacerReserva(@Body Reserva reserva);

    @GET("/llibresReservats/{id_usuari}")
    Call<ResponseBody> obtenirLlibresReservats(@Path("id_usuari") int id_usuari);
    @GET("/llibresCancelats/{id_usuari}")
    Call<ResponseBody> obtenirLlibresCancelats(@Path("id_usuari") int id_usuari);

    @GET("/llibresTornats/{id_usuari}")
    Call<ResponseBody> obtenirLlibresTorants(@Path("id_usuari") int id_usuari);
    @GET("/llibresPrestec/{id_usuari}")
    Call<ResponseBody> obtenirLlibresPrestec(@Path("id_usuari") int id_usuari);


}
