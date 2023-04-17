package edu.pujadas.koobing_admin.Database;

import java.sql.ResultSet;
import java.sql.Statement;

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
    public ResultSet conusltar10Llibres()
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();



           Statement stat = con.conectar();

            String query = "SELECT l.isbn,a.nom_autor,e.nom_editorial,i.nom_idioma,g.descrip,l.titol,l.versio,l.data_publi FROM llibre l INNER JOIN autor a ON a.id_autor = l.id_autor INNER JOIN editorial e ON e.id_editorial = l.id_editor INNER JOIN idioma i ON i.id_idioma = l.id_idioma INNER JOIN genere g ON g.id_genere = l.id_genere  Limit 10";

            //String query2 = "SELECT * from llibre";


            ResultSet rs = stat.executeQuery(query);
            con.desconectar();
            return rs;

        }
        catch (Exception e)
        {
            System.out.println("Hi ha hagut algun error");
        }
        return null;
    }
}
