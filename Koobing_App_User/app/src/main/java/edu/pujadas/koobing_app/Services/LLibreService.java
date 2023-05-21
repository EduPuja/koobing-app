package edu.pujadas.koobing_app.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LLibreService {

    @GET("/books")
    Call<List<Ll>>
}
