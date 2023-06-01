package edu.pujadas.koobing_app.Services;

import java.util.List;

import edu.pujadas.koobing_app.Models.Llibre;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LlibreService {

    @GET("/books")
    Call<List<Llibre>> getAllBooks();
    @GET("/books/{isbn}")
    Call<Llibre> getBookByISBN(@Path("isbn") long isbn);
}
