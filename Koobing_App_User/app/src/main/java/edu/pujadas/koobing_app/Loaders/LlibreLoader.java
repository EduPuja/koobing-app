package edu.pujadas.koobing_app.Loaders;

import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import edu.pujadas.koobing_app.Models.Autor;
import edu.pujadas.koobing_app.Models.Editorial;
import edu.pujadas.koobing_app.Models.Genere;
import edu.pujadas.koobing_app.Models.Idioma;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.AutorService;
import edu.pujadas.koobing_app.Services.LlibreService;
import edu.pujadas.koobing_app.Utilites.RetrofitConnection;
import okhttp3.ResponseBody;
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

    public void findBookByISBN(long isbn ,final ApiCallback<Llibre> callback)
    {
        String url = "http://192.168.0.33:3000/book/" + isbn+"/";

       RetrofitConnection retrofit = new RetrofitConnection(url);
       LlibreService llibreService = retrofit.getRetrofit().create(LlibreService.class);

        Call<ResponseBody> call = llibreService.getBookByISBN(isbn);

        call.enqueue((new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    String jsonResponse = null;
                    try
                    {
                        jsonResponse = response.body().string();

                        // si aquest repsonse ha donat valor de tipus json començo a consturir el llibre...
                        if(jsonResponse !=null)
                        {
                            JSONObject jsonObject = new JSONObject(jsonResponse);
                            Llibre llibre = new Llibre();
                            llibre.setISBN(jsonObject.getLong("ISBN"));
                            llibre.setTitol(jsonObject.getString("titol"));
                            llibre.setVersio(jsonObject.getInt("versio"));
                            llibre.setStock(jsonObject.getInt("stock"));

                            //conversio a date

                            String fecha = jsonObject.getString("data_naix");
                            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            formatoFecha.setTimeZone(TimeZone.getTimeZone("UTC"));
                            java.util.Date utilDate = formatoFecha.parse(fecha);
                            Date sqlDate = new java.sql.Date(utilDate.getTime());
                            //afegim la data
                            llibre.setDataPubli(sqlDate);

                            //todo buscar autor per id ...
                            //cridar el loader de autor





                            //finalment poso el llibre al callback
                            callback.onSuccess(llibre);
                        }

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                else
                {
                    callback.onError(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        }));

    }


}
