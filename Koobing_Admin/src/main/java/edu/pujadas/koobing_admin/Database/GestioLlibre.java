package edu.pujadas.koobing_admin.Database;

import java.sql.ResultSet;
import java.sql.Statement;

public class GestioLlibre
{
    public void crearLlibre()
    {

    }

    public void modificarLlibre()
    {

    }

    public void eliminarLlibre()
    {

    }
    public void consultarLlibres()
    {

    }
    public ResultSet conusltar10Llibres()
    {
        try
        {
            Statement con = ConnexioMYSQL.connexioMYSQL();
            String query = "SELECT l.isbn,a.nom_autor,e.nom_editorial,i.nom_idioma,g.descrip,l.titol,l.versio,l.data_publi FROM llibre l \n" +
                    "INNER JOIN autor a ON a.id_autor = l.id_autor \n" +
                    "INNER JOIN editorial e ON e.id_editorial = l.id_editor\n" +
                    "INNER JOIN idioma i ON i.id_idioma = l.id_idioma\n" +
                    "INNER JOIN genere g ON g.id_genere = l.id_genere\n Limit 10";

            ResultSet rs = con.executeQuery(query);

            return rs;
        }
        catch (Exception e)
        {
            System.out.println("Hi ha hagut algun error");
        }
        return null;
    }
}
