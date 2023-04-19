package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Treballador;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GestioTreballador
{
    public void crearTreballador()
    {

    }
    public  void modificarTreballador()
    {

    }
    public void eliminarTreballador()
    {

    }

    public ArrayList<Treballador> consultarTreballadors()
    {
        ArrayList<Treballador> listTreballs = new ArrayList<Treballador>();

        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM treballador ");

            if(rs.next())
            {
                Treballador t = new Treballador();
                t.setId(rs.getInt("id_treballador"));
                t.setAvatar(rs.getBlob("avatar"));
                t.setNom(rs.getString("dni"));
                t.setCognom(rs.getString("cognom"));
                t.setDataNaix(rs.getDate("data_naix"));
                t.setEmail(rs.getString("email"));
                t.setPassword(rs.getString("password"));
                t.setNumSegSocial(rs.getString("num_seg_social"));
                t.setAdmin(rs.getBoolean("isAdmin"));

                listTreballs.add(t);
            }
            con.desconectar();
            return listTreballs;
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    public void findTreballador()
    {

    }


}
