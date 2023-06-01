package edu.pujadas.koobing_app.Loaders;

import java.util.List;

import edu.pujadas.koobing_app.Models.Autor;
import edu.pujadas.koobing_app.Models.Editorial;
import edu.pujadas.koobing_app.Models.Genere;
import edu.pujadas.koobing_app.Models.Idioma;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.AutorService;
import edu.pujadas.koobing_app.Services.LlibreService;
import edu.pujadas.koobing_app.Utilites.RetrofitConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LlibreLoader {





    /**
     * Metode per obtenir tots els llibres de la API
     * @param callback callback de tipus llibreBiblioteca on hi h totala inforamcio del llibre i la biblioteca
     */
    public void obtenerLibrosfinal  (final ApiCallback<List<Llibre>> callback)
    {

        String url = "http://192.168.0.33:3000/books/";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LlibreService llibreService = retrofit.create(LlibreService.class);

        Call <List<Llibre>> call = llibreService.getAllBooks();
        call.enqueue(new Callback<List<Llibre>>() {

            @Override
            public void onResponse(Call<List<Llibre>> call, Response<List<Llibre>> response) {
                if (response.isSuccessful()) {
                    List<Llibre> librosBiblioteca = response.body();

                    callback.onSuccess(librosBiblioteca);
                }
                else {
                    callback.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Llibre>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }


    /**
     * Metode per obtenir el llistat del 10 primers llibres
     * @param callback
     */
    public void obtenir10Llibres(final ApiCallback<List<Llibre>>callback)
    {
        String url = "http://192.168.0.33:3000/books_10/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LlibreService llibreService = retrofit.create(LlibreService.class);



        //String url ="http://192.168.16.254:3000/books/";
        Call <List<Llibre>> call = llibreService.getAllBooks();
        call.enqueue(new Callback<List<Llibre>>() {

            @Override
            public void onResponse(Call<List<Llibre>> call, Response<List<Llibre>> response) {
                if (response.isSuccessful()) {
                    List<Llibre> librosBiblioteca = response.body();

                    callback.onSuccess(librosBiblioteca);
                }
                else {
                    callback.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Llibre>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }


    /**
     * Metode per obtenir tot el llibre per isbn
     * @param callback callback per si hi ha erros
     * @param isbn isbn per filtrar
     */
    public void findAuthorByISBN(final ApiCallback<Autor> callback, long isbn) {
        String baseUrl = "http://192.168.0.33:3000/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LlibreService llibreService = retrofit.create(LlibreService.class);

        Call<Llibre> llibreCall = llibreService.getBookByISBN(isbn);
        llibreCall.enqueue(new Callback<Llibre>() {
            @Override
            public void onResponse(Call<Llibre> call, Response<Llibre> response) {
                if (response.isSuccessful()) {
                    Llibre llibre = response.body();

                    // Obtener el autor del libro
                    Autor autor = llibre.getAutor();
                    int autorId = autor.getIdAutor();

                    // Crear el servicio de Autor
                    AutorService autorService = retrofit.create(AutorService.class);
                    Call<Autor> autorCall = autorService.getAutorById(autorId);
                    autorCall.enqueue(new Callback<Autor>() {
                        @Override
                        public void onResponse(Call<Autor> call, Response<Autor> response) {
                            if (response.isSuccessful()) {
                                Autor autor = response.body();
                                callback.onSuccess(autor);
                            } else {
                                callback.onError(response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Autor> call, Throwable t) {
                            callback.onFailure(t);
                        }
                    });
                } else {
                    callback.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<Llibre> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }


}
