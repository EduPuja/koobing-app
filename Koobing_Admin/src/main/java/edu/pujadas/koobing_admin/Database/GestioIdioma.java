package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Database.ConnexioMYSQL;
import edu.pujadas.koobing_admin.Models.Idioma;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//todo falta:  delete ,mod
public class GestioIdioma
{
    public void crearIdioma(Idioma idioma)
    {
        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat  = con.conectar();
            String sql = "INSERT INTO idioma (nom_idioma) values ( '"+idioma.getNomIdioma()+"')";
            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("Idioma inserted successfully");
            }
            else System.out.println("Idioma not inserted");
            con.desconectar();
        }
        catch (Exception e) {
            System.out.println("Error inserting Idioma: " +e.getMessage());
        }
    }

    public void eliminarIdioma()
    {

    }
    public void modificarIdioma()
    {

    }
    public ArrayList<Idioma> consultarIdiomes()
    {
        ArrayList<Idioma> idiomas = new ArrayList<>();

        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM idioma");
            while (rs.next())
            {
                idiomas.add(new Idioma(rs.getInt("id_idioma"),rs.getString("nom_idioma")));
            }
            con.desconectar();
            return idiomas;
        }
        catch (Exception e)
        {
            System.out.println("Error consultar idiomes: " + e.getMessage());
        }

        return null;
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
            System.out.println("Error consultar idioma: " + e.getMessage());
        }
        return null;
    }

    public Idioma findIdiomaByName(String nomIdioma)
    {

        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM idioma WHERE nom_idioma ='" + nomIdioma+"'");
            if (rs.next())
            {
                Idioma idioma =new Idioma(rs.getInt("id_idioma"),rs.getString("nom_idioma"));
                con.desconectar();
                return idioma;
            }

        }
        catch (Exception e)
        {
            System.out.println("Error find idioma name: " + e.getMessage());
        }
      return  null;
    }
}
