package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Genere;
import edu.pujadas.koobing_admin.Models.Idioma;

import java.sql.ResultSet;
import java.sql.Statement;

public class GestioGenere
{
    public void crearGenere()
    {

    }
    public void modificarGenere()
    {

    }
    public void eliminarGenere()
    {

    }
    public void consultarGeneres()
    {

    }
    public Genere findGenere(int idGenere)
    {
        try
        {
            Statement con = ConnexioMYSQL.connexioMYSQL();
            ResultSet rs = con.executeQuery("SELECT * FROM genere where id_genere " + idGenere);
            while (rs.next())
            {
                return new Genere(rs.getInt("id_genere"),rs.getString("descrip"));
            }
        }
        catch (Exception e)
        {
            System.out.println("No he trobat el genere ;(");
        }
        return null;
    }
}
