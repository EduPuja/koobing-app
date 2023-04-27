package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Editorial;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//todo falta create delete mod
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
    public ArrayList<Editorial> consultarEditorials()
    {
        ArrayList<Editorial> list = new ArrayList<Editorial>();
        try
        {

            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("Select * from editorial");
            while(rs.next())
            {
                list.add(new Editorial(rs.getInt("id_editorial"), rs.getString("nom_editorial")));
            }

            con.desconectar();
            return list;
        }
        catch (Exception e)
        {
            System.out.println("Error Consultar Editorials: " + e.getMessage());
        }

        return null;
    }

    /**
     *  Metode que busca un editorial por su id ✅✅✅
     * @param idEditorial id del editorial
     * @return Objecte Editorial
     */
    public Editorial findEditorial(int idEditorial)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();

            Statement stat = con.conectar();

            ResultSet rs = stat.executeQuery("Select * from editorial where id_editorial = "+idEditorial);
            if(rs.next())
            {
                Editorial editor = new Editorial(rs.getInt("id_editorial"), rs.getString("nom_editorial"));
                con.desconectar();
                return editor;
            }


        }
        catch (Exception e)
        {
            System.out.println("Editorial not found: " + e.getMessage());
        }
        return null;
    }
}
