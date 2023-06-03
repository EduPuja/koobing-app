package edu.pujadas.koobing_app.Deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import edu.pujadas.koobing_app.Loaders.AutorLoader;
import edu.pujadas.koobing_app.Loaders.EditorialLoader;
import edu.pujadas.koobing_app.Loaders.GenereLoader;
import edu.pujadas.koobing_app.Loaders.IdiomaLoader;
import edu.pujadas.koobing_app.Models.Autor;
import edu.pujadas.koobing_app.Models.Editorial;
import edu.pujadas.koobing_app.Models.Genere;
import edu.pujadas.koobing_app.Models.Idioma;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Services.ApiCallback;

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
       llibre.setDataPubli(parseFechaPublicacion(dataPubliStr));
        llibre.setStock(stock);

        // -- Obtenit els objectes dels loader
        //autor
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

        // EDITORIAl
        EditorialLoader editorialLoader = new EditorialLoader();
        editorialLoader.getEditorialById(idEditor, new ApiCallback<Editorial>() {
            @Override
            public void onSuccess(Editorial editorial) {
                llibre.setEditor(editorial);
            }

            @Override
            public void onError(int statusCode) {
                System.out.println("Error BOOK_DESERIALIZE Editorial:" +statusCode);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("Failure BOOK_DESERIALIZE Editorial: " + throwable.getMessage());
            }
        });

        //idioma
        IdiomaLoader idiomaLoader = new IdiomaLoader();
        idiomaLoader.getIdiomaById(idIdioma, new ApiCallback<Idioma>() {
            @Override
            public void onSuccess(Idioma idioma) {
                llibre.setIdioma(idioma);
            }

            @Override
            public void onError(int statusCode) {
                // Manejar el error si la obtención del Idioma falla
                System.out.println("Error BOOK_DESERIALIZE Idioma: " + statusCode);
            }

            @Override
            public void onFailure(Throwable throwable) {
                // Manejar la falla si ocurre algún error de comunicación
                System.out.println("Failure BOOK_DESERIALIZE Idioma: " + throwable.getMessage());
            }
        });


        //genere
        GenereLoader genereLoader = new GenereLoader();
        genereLoader.getGenereById(idGenere, new ApiCallback<Genere>() {
            @Override
            public void onSuccess(Genere genere) {
                llibre.setGenere(genere);
            }

            @Override
            public void onError(int statusCode) {
                // Manejar el error si la obtención del Genere falla
                System.out.println("Error BOOK_DESERIALIZE genere: " + statusCode);

            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("Failure BOOK_DESERIALIZE genere: " + throwable.getMessage());
            }
        });


        return llibre;
    }

    /**
     * Metode per transformar la data de publicacio del llibre en un SQL DATE
     * @param fechaString data en formt string
     * @return java.sql.Date / null
     */
    private Date parseFechaPublicacion(String fechaString) {
        try {
            // Formato de fecha en el JSON: "2023-05-10T22:00:00.000Z"
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            // Parsear la fecha de String a java.util.Date
            java.util.Date utilDate = sdf.parse(fechaString);

            // Convertir java.util.Date a java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            return sqlDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null; // Devolver null en caso de error
    }




}
