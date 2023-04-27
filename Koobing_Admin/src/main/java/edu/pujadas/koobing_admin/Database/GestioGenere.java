package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Genere;
import edu.pujadas.koobing_admin.Models.Idioma;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//todo falta create, update, delete
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
    public ArrayList<Genere> consultarGeneres()
    {
        ArrayList<Genere> list = new ArrayList<Genere>();
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM genere");
            while (rs.next())
            {
                list.add(new Genere(rs.getInt("id_genere"),rs.getString("descrip")));
            }
            con.desconectar();
            return list;
        }
        catch (Exception e)
        {
            System.out.println("Erorr consultar generes :" + e.getMessage());
        }
        return null;
    }

    /**
     * Metode que et parmet trobar un genere ✅
     * @param idGenere identificador del genere
     * @return objecte genere
     */
    public Genere findGenere(int idGenere)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM genere where id_genere = " + idGenere);
            if (rs.next())
            {   Genere genere =new Genere(rs.getInt("id_genere"),rs.getString("descrip"));
                con.desconectar();
                return genere;
            }


        }
        catch (Exception e)
        {
            System.out.println("No he trobat el genere ;(");
        }
        return null;
    }
}
