package edu.pujadas.koobing_app.Services;

import java.util.List;

import edu.pujadas.koobing_app.Models.Llibre;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LlibreService {

    @GET("/books")
    Call<List<Llibre>> getAllBooks();
}
