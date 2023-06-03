package edu.pujadas.koobing_app.Deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Locale;

import edu.pujadas.koobing_app.Models.Autor;
import edu.pujadas.koobing_app.Models.Editorial;
import edu.pujadas.koobing_app.Models.Genere;
import edu.pujadas.koobing_app.Models.Idioma;
import edu.pujadas.koobing_app.Models.Llibre;
import edu.pujadas.koobing_app.Services.AutorService;
import edu.pujadas.koobing_app.Services.EditorialService;
import edu.pujadas.koobing_app.Services.GenereService;
import edu.pujadas.koobing_app.Services.IdiomaService;

public class LlibreDeserializer implements JsonDeserializer<Llibre> {

    private AutorService autorService;
    private EditorialService editorialService;
    private IdiomaService idiomaService;
    private GenereService genereService;

    public LlibreDeserializer(AutorService autorService, EditorialService editorialService, IdiomaService idiomaService, GenereService genereService) {
        this.autorService = autorService;
        this.editorialService = editorialService;
        this.idiomaService = idiomaService;
        this.genereService = genereService;
    }

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

        // Crear los objetos Autor, Editorial, Genere e Idioma utilizando los IDs correspondientes
        Autor autor = autorService.getAutorById(idAutor);
        Editorial editorial = editorialService.getEditorialById(idEditor);
        Genere genere = genereService.getGenereById(idGenere);
        Idioma idioma = idiomaService.getIdiomaById(idIdioma);

        // Crear el objeto Llibre y asignar los valores correspondientes
        Llibre llibre = new Llibre();
        llibre.setISBN(isbn);
        llibre.setAutor(autor);
        llibre.setEditor(editorial);
        llibre.setIdioma(idioma);
        llibre.setGenere(genere);
        llibre.setTitol(titol);
        llibre.setVersio(versio);
        //llibre.setDataPubli(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).parse(dataPubliStr));
        llibre.setStock(stock);

        return llibre;
    }
}
