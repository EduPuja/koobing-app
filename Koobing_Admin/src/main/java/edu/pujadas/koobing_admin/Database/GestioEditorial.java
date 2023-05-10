package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Editorial;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class GestioEditorial
{
    public void crearEditorial(Editorial editorial)
    {
        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat =con.conectar();
            String sql ="INSERT INTO editorial (nom_editorial) VALUES ('" + editorial.getNomEditor()+"')";
            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("editor insert succeeded");
            }
            else System.out.println("editor insert failed");
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error Inserting Editorial: " + e.getMessage());
        }
    }

    public void eliminarEditor()
    {

        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat =con.conectar();
            String sql ="";
        }
        catch (Exception e)
        {
            System.out.println("Error Deleting Editorial: " + e.getMessage());
        }
    }
    public void modificarEditorial()
    {
        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat =con.conectar();
            String sql ="";
        }
        catch (Exception e)
        {
            System.out.println("Error Modify Editorial: " + e.getMessage());
        }
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


    /**
     * Metode que una editorial por su nombre.
     * Necessari perque els combobox utilizen el nom
     * @param name nom del editorial
     * @return Objecte Editorial
     */
    public Editorial findEditorialByName(String name)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();

            Statement stat = con.conectar();

            ResultSet rs = stat.executeQuery("Select * from editorial where nom_editorial = '"+name+"'");
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
