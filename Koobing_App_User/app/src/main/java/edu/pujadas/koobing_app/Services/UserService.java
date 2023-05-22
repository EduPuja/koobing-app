package edu.pujadas.koobing_app.Services;

import java.util.List;

import edu.pujadas.koobing_app.Models.Usuari;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {



    @GET("/users/")
    Call<List<Usuari>>getUsuaris();

    @GET("/user/{email}")
    Call<ResponseBody> getUserByEmail(@Path("email") String email);

}
