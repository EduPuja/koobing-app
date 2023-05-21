package edu.pujadas.koobing_app.Services;

import java.util.List;

import edu.pujadas.koobing_app.Models.LlibreBiblioteca;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LLibreService {

    @GET("/books")
    Call<List<LlibreBiblioteca>> getAllBooks();
}
