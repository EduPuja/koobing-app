package edu.pujadas.koobing_app.Services;

import edu.pujadas.koobing_app.Models.Reserva;
import edu.pujadas.koobing_app.Models.Usuari;
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

}
