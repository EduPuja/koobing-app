package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Editorial;

import java.sql.ResultSet;
import java.sql.Statement;

public class GestioEditorial
{
    public void crearEditorial()
    {

    }

    public void eliminarEditor()
    {

    }
    public void modificarEditorial()
    {

    }
    public void consultarEditorials()
    {

    }
    public Editorial findEditorial(int idEditorial)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();

            Statement stat = con.conectar();

            ResultSet rs = stat.executeQuery("SELECT * FROM `editorial` WHERE id_editorial = "+idEditorial);
            if(rs.next())
            {
                Editorial editor = new Editorial(rs.getInt("idEditorial"), rs.getString("nom_editorial"));
                con.desconectar();
                return editor;
            }


        }
        catch (Exception e)
        {
            System.out.println("No he trobat la editorial que me has dit");
        }
        return null;
    }
}
