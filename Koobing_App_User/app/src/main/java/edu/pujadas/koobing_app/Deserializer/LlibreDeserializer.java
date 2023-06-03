package edu.pujadas.koobing_app.Deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Locale;

import edu.pujadas.koobing_app.Loaders.AutorLoader;
import edu.pujadas.koobing_app.Models.Autor;
import edu.pujadas.koobing_app.Models.Editorial;
import edu.pujadas.koobing_app.Models.Genere;
import edu.pujadas.koobing_app.Models.Idioma;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Services.ApiCallback;
import edu.pujadas.koobing_app.Services.AutorService;
import edu.pujadas.koobing_app.Services.EditorialService;
import edu.pujadas.koobing_app.Services.GenereService;
import edu.pujadas.koobing_app.Services.IdiomaService;
import edu.pujadas.koobing_app.Utilites.RetrofitConnection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LlibreDeserializer implements JsonDeserializer<Llibre> {


    @Override
    public Llibre deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException  {
        JsonObject jsonObject = json.getAsJsonObject();

        // Obtener los campos del JSON
        long isbn = jsonObject.get("ISBN").getAsLong();
        int idAutor = jsonObject.get("id_autor").getAsInt();
        int idEditor = jsonObject.get("id_editor").getAsInt();
        int idIdioma = jsonObject.get("id_idioma").getAsInt();
        int idGenere = jsonObject.get("id_genere").getAsInt();
        String titol = jsonObject.get("titol").getAsString();
        int versio = jsonObject.get("versio").getAsInt();
        String dataPubliStr = jsonObject.get("data_publi").getAsString();
        int stock = jsonObject.get("stock").getAsInt();


        // Construir el objeto Llibre
        Llibre llibre = new Llibre();
        llibre.setISBN(isbn);
        llibre.setTitol(titol);
        llibre.setVersio(versio);
       // llibre.setDataPubli(dataPubli);
        llibre.setStock(stock);

        // -- Obtenit els objectes dels loaders

        //autor

        // Obtener el objeto Autor utilizando el AutorLoader

        AutorLoader autorLoader = new AutorLoader();
        autorLoader.getAutorById(idAutor, new ApiCallback<Autor>() {
            @Override
            public void onSuccess(Autor autor) {
                llibre.setAutor(autor);
            }

            @Override
            public void onError(int statusCode) {
                // Manejar el error si la obtención del Autor falla
                System.out.println("Error BOOK_DESERIALIZE Autor:" +statusCode);
            }

            @Override
            public void onFailure(Throwable throwable) {
                // Manejar la falla si ocurre algún error de comunicación
                System.out.println("Failure BOOK_DESERIALIZE Autor: " + throwable.getMessage());
            }
        });



        return llibre;
    }




}
