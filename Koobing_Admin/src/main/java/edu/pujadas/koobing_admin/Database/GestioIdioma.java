package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Database.ConnexioMYSQL;
import edu.pujadas.koobing_admin.Models.Idioma;

import java.sql.ResultSet;
import java.sql.Statement;

public class GestioIdioma
{
    public void crearIdioma()
    {

    }

    public void eliminarIdioma()
    {

    }
    public void modificarIdioma()
    {

    }
    public void consultarIdiomes()
    {

    }

    public Idioma findIdioma(int idIdioma)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM idioma WHERE id_idioma" + idIdioma);
            while (rs.next())
            {
                return new Idioma(rs.getInt("id_idioma"),rs.getString("nom_idioma"));
            }
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("No he trobat l'idioma");
        }
        return null;
    }
}
