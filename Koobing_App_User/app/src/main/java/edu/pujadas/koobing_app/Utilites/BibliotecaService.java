package edu.pujadas.koobing_app.Utilites;

import java.util.List;
import edu.pujadas.koobing_app.Models.Biblioteca;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BibliotecaService {

    @GET("/biblioteques")
    Call<List<Biblioteca>> getBiblioteques();
}
