package edu.pujadas.koobing_app.Database;

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

    public void eliminarEditor(int idEditor)
    {

        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat =con.conectar();
            String sql ="DELETE FROM editorial  WHERE id_editorial = " + idEditor;
            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("Editorial eliminat correctament");
            }
            else System.out.println("Editorial not deleted");
            con.desconectar();

        }
        catch (Exception e)
        {
            System.out.println("Error Deleting Editorial: " + e.getMessage());
        }
    }
    public void modificarEditorial(Editorial editorial)
    {
        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat =con.conectar();
            String sql ="UPDATE `editorial` SET nom_editorial= '"+editorial.getNomEditor()+"' WHERE id_editorial = "+ editorial.getIdEditorial();

            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("Editorial se ha modificat correctament");
            }
            else System.out.println("Editorial no se ha modificat");
            con.desconectar();
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

    /**
     * Metode per comprovar si aquesta editorial esta en un llibre
     * @param idEditorial integer id del editor
     * @return true si esta en un llibre , si no et retorna false
     */
    public boolean isEditorInBook(int idEditorial) {
        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql = "SELECT COUNT(*) AS count FROM llibre WHERE id_editor="+idEditorial;
            ResultSet result = stat.executeQuery(sql);
            if(result.next()) {
                int count = result.getInt("count");
                if(count > 0) {
                   return true;
                }
            }
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error isEditorInBook : "+e.getMessage());
        }
        return false;
    }
}
