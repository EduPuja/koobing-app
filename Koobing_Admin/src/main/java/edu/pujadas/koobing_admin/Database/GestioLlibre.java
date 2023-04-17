package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GestioLlibre
{



    public void crearLlibre()
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();


            con.desconectar();
        }
        catch (Exception e)
        {

        }
    }

    public void modificarLlibre()
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();


            con.desconectar();
        }
        catch (Exception e)
        {

        }
    }

    public void eliminarLlibre()
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();


            con.desconectar();
        }
        catch (Exception e)
        {

        }
    }
    public void consultarLlibres()
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();


            con.desconectar();
        }
        catch (Exception e)
        {

        }
    }
    public ArrayList<Llibre> conusltar10Llibres()
    {
        ArrayList<Llibre> llibresList = new ArrayList<Llibre>();


        //gestions
        GestioAutor gestioAutor = new GestioAutor();
        GestioEditorial gestioEditorial = new GestioEditorial();
        GestioIdioma gestioIdioma = new GestioIdioma();
        GestioGenere gestioGenere = new GestioGenere();


        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();



           Statement stat = con.conectar();

            /*String query = "SELECT l.isbn,a.nom_autor,e.nom_editorial,i.nom_idioma,g.descrip,l.titol,l.versio,l.data_publi FROM llibre l " +
                    "INNER JOIN autor a ON a.id_autor = l.id_autor INNER JOIN editorial e ON e.id_editorial = l.id_editor " +
                    "INNER JOIN idioma i ON i.id_idioma = l.id_idioma INNER JOIN genere g ON g.id_genere = l.id_genere  Limit 10";*/

            String query2 = "SELECT * from llibre limit 10";


            ResultSet rs = stat.executeQuery(query2);

            while (rs.next())
            {
                Llibre llibre = new Llibre();
                // isbn
                llibre.setISBN(rs.getLong("ISBN"));

                //buscant el autor , editorials, idioma and genere per poder crear un objeto de cada
                //System.out.println("Buscant Autors ... editorials etc..\n");

                Autor autor = gestioAutor.findAutor(rs.getInt("id_autor"));
                Editorial editor =gestioEditorial.findEditorial(rs.getInt("id_editor"));
                Idioma idioma = gestioIdioma.findIdioma(rs.getInt("id_idioma"));
                Genere genere = gestioGenere.findGenere(rs.getInt("id_genere"));

                //afegint tot les dades el objecte llibre
                llibre.setAutor(autor);
                llibre.setEditor(editor);
                llibre.setIdioma(idioma);
               llibre.setGenere(genere);

                //afegint les dades que falten
                llibre.setTitol(rs.getString("titol"));
                llibre.setVersio(rs.getInt("versio"));
                llibre.setDataPubli(rs.getDate("data_publi"));

                //afegint tots els llibres al arraylist
                llibresList.add(llibre);
            }


            con.desconectar();

            return llibresList;

        }
        catch (Exception e)
        {
            System.out.println("Hi ha hagut algun error" + e.getMessage());
        }
        return null;
    }
}
