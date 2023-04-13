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
            
        }
        catch (Exception e)
        {
            System.out.println("Hi ha hagut algun error");
        }
        return null;
    }
}
