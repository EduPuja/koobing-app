package edu.pujadas.koobing_app.Services;

import edu.pujadas.koobing_app.Models.Reserva;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ReservaService {

    @POST("/reservarLlibre")
    Call<Void> hacerReserva(@Body Reserva reserva);

    @GET("/llibresReservats")
    Call<ResponseBody> obtenirLlibresReservats(@Body Reserva reserva);

}
