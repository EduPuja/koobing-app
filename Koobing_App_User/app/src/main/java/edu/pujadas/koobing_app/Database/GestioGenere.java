package edu.pujadas.koobing_app.Database;

import edu.pujadas.koobing_admin.Models.Genere;
import edu.pujadas.koobing_admin.Models.Idioma;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GestioGenere
{
    public void crearGenere(Genere genere)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat =con.conectar();
            String sql = "INSERT INTO genere (descrip) VALUES ('"+genere.getNomGenere()+"')";

            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("Genere insert successfully");
            }
            else System.out.println("Gener not inserted ");

            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error adding genere " +e.getMessage());
        }
    }
    public void modificarGenere(Genere genere)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat =con.conectar();
            String sql = "UPDATE genere Set descrip =" + genere.getNomGenere()+"' WHERE id_genere ="+genere.getIdGenere();

            if (stat.executeUpdate(sql) ==1)
            {
                System.out.println("Genere s'ha actualizat correctament");
            }
            else System.out.println("Genere not actualizat");

            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error Modfiy genere " +e.getMessage());
        }
    }
    public void eliminarGenere(int idGenere)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat =con.conectar();
            String sql = "DELETE FROM genere WHERE id_genere ="+ idGenere;
            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("Genere eliminat succesfully!");
            }
            else System.out.println("Genere not deleted");
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error Deleting genere " +e.getMessage());
        }
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

    public boolean isGenereInBook(int idGenere)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();

            String query = "SELECT COUNT(*) AS count FROM llibre WHERE id_genere="+ idGenere;
            ResultSet rs = stat.executeQuery(query);
            if(rs.next())
            {
                int count = rs.getInt("count");
                if(count>0)
                {
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error isGenreInBook : " +e.getMessage());
        }
        return false;
    }
}
