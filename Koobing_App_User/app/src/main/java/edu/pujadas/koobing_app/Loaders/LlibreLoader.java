package edu.pujadas.koobing_app.Loaders;

import android.content.Context;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import edu.pujadas.koobing_app.Models.Autor;
import edu.pujadas.koobing_app.Models.Editorial;
import edu.pujadas.koobing_app.Models.Genere;
import edu.pujadas.koobing_app.Models.Idioma;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.AutorService;
import edu.pujadas.koobing_app.Services.LlibreService;
import edu.pujadas.koobing_app.Services.UserService;
import edu.pujadas.koobing_app.Utilites.RetrofitConnection;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LlibreLoader {


    //looders per poder carregar la inforamcio dels objectes necessaris



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

    public Llibre findByEmail(long isbn, final ApiCallback<Llibre> callback) {


        //loaderes necessaries per carregar la inforamcio
        AutorLoader autorLoader = new AutorLoader();
        EditorialLoader editorialLoader =new EditorialLoader();
        GenereLoader genereLoader =new GenereLoader();
        IdiomaLoader idiomaLoader =new IdiomaLoader();

        String url = "http://192.168.0.33:3000/book/" + isbn+"/";
        //String url = "http://192.168.16.254:3000/book/"+isbn+"/";

        RetrofitConnection retrofit = new RetrofitConnection(url);

        LlibreService llibreService = retrofit.getRetrofit().create(LlibreService.class);


        Call<ResponseBody> call = llibreService.getBookByISBN(isbn);

        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response)
            {
                if(response.isSuccessful())
                {

                    try {
                        String jsonResponse = response.body().string();


                        JSONObject jsonObject = new JSONObject(jsonResponse);

                        Llibre llibre = new Llibre();
                        llibre.setISBN(jsonObject.getLong("ISBN"));
                        llibre.setTitol(jsonObject.getString("titol"));

                        //todo falta el autor , editorial, genere, idioma


                        //autor loader

                        autorLoader.getAutorById(jsonObject.getInt("id_autor"), new ApiCallback<Autor>() {
                            @Override
                            public void onSuccess(Autor autor) {
                                if(autor!=null)
                                {
                                    llibre.setAutor(autor);
                                }
                            }

                            @Override
                            public void onError(int statusCode) {
                                System.out.println("Error autor: " + statusCode);
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                System.out.println("Failure autor: " + throwable.getMessage());
                            }
                        });

                        // editorail
                        editorialLoader.getEditorialById(jsonObject.getInt("id_editor"),new ApiCallback<Editorial>() {

                            @Override
                            public void onSuccess(Editorial editorial) {
                                if(editorial!=null)
                                {
                                    llibre.setEditor(editorial);
                                }
                            }

                            @Override
                            public void onError(int statusCode) {
                                System.out.println("Error editor: " + statusCode);
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                System.out.println("Failure editor: " + throwable.getMessage());
                            }
                        });


                        // idioma loader

                        idiomaLoader.getIdiomaById(jsonObject.getInt("id_idioma"),new ApiCallback<Idioma>(){

                            @Override
                            public void onSuccess(Idioma idioma) {
                                if(idioma!=null){
                                    llibre.setIdioma(idioma);
                                }
                            }

                            @Override
                            public void onError(int statusCode) {
                                System.out.println("Error idioma: " +statusCode);
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                System.out.println("Failure Idioma: " + throwable.getMessage());
                            }
                        });

                        //genere loader

                        genereLoader.getGenereById(jsonObject.getInt("id_genere"),new ApiCallback<Genere>(){

                            @Override
                            public void onSuccess(Genere genere) {
                                if(genere!=null){
                                    llibre.setGenere(genere);
                                }
                            }

                            @Override
                            public void onError(int statusCode) {
                                System.out.println("Error genere: " +statusCode);
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                System.out.println("Failure genere: " + throwable.getMessage());
                            }
                        });


                        //data publicacio
                        String fecha = jsonObject.getString("data_publi");
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        formatoFecha.setTimeZone(TimeZone.getTimeZone("UTC"));
                        java.util.Date utilDate = formatoFecha.parse(fecha);
                        Date sqlDate = new java.sql.Date(utilDate.getTime());
                        llibre.setDataPubli(sqlDate);

                        llibre.setStock(jsonObject.getInt("stock"));
                        llibre.setVersio(jsonObject.getInt("versio"));






                        //finalment enviar el llire objecte llibre montat
                        callback.onSuccess(llibre);




                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                else
                {
                    //en cas d'error envio el codi d'error
                    callback.onError(response.code());
                }


            }



            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        });//end callback


        return null;

    }


    private void showErrorToast(String errorMessage) {

    }


}
