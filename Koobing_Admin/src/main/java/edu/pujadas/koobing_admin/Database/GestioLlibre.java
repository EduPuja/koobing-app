package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GestioLlibre
{



    public void crearLlibre(Llibre l)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();

            String sql = "INSERT INTO `llibre`(`ISBN`, `id_autor`, `id_editor`, `id_idioma`, `id_genere`, `titol`, `versio`, `data_publi`) " +
                    "VALUES ('"+l.getISBN()+"','"+l.getAutor().getIdAutor()+"','"+l.getEditor().getIdEditorial()+"','"+l.getIdioma().getIdIdioma()+"','"+l.getGenere().getIdGenere()+"','"+l.getTitol()+"','"+l.getVersio()+"','"+l.getDataPubli()+"')";

            if(stat.executeUpdate(sql) ==1)
            {
                System.out.println("Llibre insertado correctamente");
            }
            else System.out.println("Llibre no insertado correctamente");

            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error LLibre:"+ e.getMessage());
        }
    }

    public void modificarLlibre(Llibre llibre)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql = "UPDATE `llibre` SET `ISBN`='"+llibre.getISBN()+"',`id_autor`='"+llibre.getAutor().getIdAutor()+"'," +
                    "`id_editor`='"+llibre.getEditor().getIdEditorial()+"',`id_idioma`='"+llibre.getIdioma().getIdIdioma()+"'," +
                    "`id_genere`='"+llibre.getGenere().getIdGenere()+"',`titol`='"+llibre.getTitol()+"',`versio`='"+llibre.getVersio()+"',`data_publi`='"+llibre.getDataPubli()+"' WHERE ISBN='"+llibre.getISBN()+"'";

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
