package edu.pujadas.koobing_app.Services;

import edu.pujadas.koobing_app.Models.Autor;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AutorService {

    @GET("/author/{id}")
    Call<Autor> getAutorById(@Path("id") int id);
}
