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
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM genere where id_genere " + idGenere);
            while (rs.next())
            {
                return new Genere(rs.getInt("id_genere"),rs.getString("descrip"));
            }

            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("No he trobat el genere ;(");
        }
        return null;
    }
}
