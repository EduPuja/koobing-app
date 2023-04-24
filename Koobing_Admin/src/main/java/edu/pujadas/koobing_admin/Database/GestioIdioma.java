package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Database.ConnexioMYSQL;
import edu.pujadas.koobing_admin.Models.Idioma;

import java.sql.ResultSet;
import java.sql.Statement;
//todo falta create delete mod
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

    /**
     * Metodo que busca el idioma d'una base de datos ✅'
     * @param idIdioma identificador de la base de datos
     * @return Idioma object
     */
    public Idioma findIdioma(int idIdioma)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM idioma WHERE id_idioma =" + idIdioma);
            if (rs.next())
            {
                Idioma idioma =new Idioma(rs.getInt("id_idioma"),rs.getString("nom_idioma"));
                con.desconectar();
                return idioma;
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
