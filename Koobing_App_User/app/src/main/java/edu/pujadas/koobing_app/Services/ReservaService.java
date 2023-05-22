package edu.pujadas.koobing_app.Services;

import edu.pujadas.koobing_app.Models.Reserva;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ReservaService {

    @POST("/reservarLlibre")
    Call<Void> hacerReserva(@Body Reserva reserva);
}
