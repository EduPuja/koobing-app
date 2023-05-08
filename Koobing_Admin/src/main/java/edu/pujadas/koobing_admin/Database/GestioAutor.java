package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Autor;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//✅
public class GestioAutor
{


    /**
     * Metode per insertar un autor en la base de dades
     * @param autor Objecte autor
     */
    public void crearAutor(Autor autor)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();

            String sql = "INSERT INTO autor (id_autor,nom_autor, data_naix) VALUES ("+autor.getIdAutor()+",'"+autor.getNomAutor()+"','"+autor.getDataNaixAutor()+"')";

            if(stat.executeUpdate(sql)==1)
            {
                System.out.println("El autor se ha insertat correctament");
            }
            else System.out.println("El autor NO se ha insertat correctament ;(");

            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("ERROR : El autor NO se ha insertat correctament");
        }
    }

    /**
     * Metode per buscar un autor de la base de dades i modificar les dades que
     * el administrador vulgui
     * @param autor Objecte autor
     */
    public void modificarAutor(Autor autor)
    {
        try
        {
                ConnexioMYSQL con = new ConnexioMYSQL();
                Statement stat = con.conectar();

                String sql = "UPDATE autor SET nom_autor='"+autor.getNomAutor()+"', data_naix='"+autor.getDataNaixAutor()+"' WHERE id_autor="+autor.getIdAutor();

                if(stat.executeUpdate(sql)==1)
                {
                    System.out.println("El autor se ha modificat correctament");
                }
                else System.out.println("El autor NO se ha modificat correctament ;(");

                con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("El autor NO se ha modificar correctament");
        }
    }

    /**
     * Metode per eliminar un autor de la base de dades
     * @param idAutor el identifciador del autor a eliminar
     */
    public void eliminarAutor(int idAutor)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();

            String sql = "DELETE FROM autor WHERE id_autor="+idAutor;

            if(stat.executeUpdate(sql)==1)
            {
                System.out.println("El autor se ha eliminat correctament");
            }
            else System.out.println("El autor NO se ha eliminat correctament ;(");

            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("El autor NO se ha eliminat correctament");
        }
    }

    /**
     * Metode que permet de consultar TOTS els autors de la base de dades
     * @return ArrayList<Autor>
     */
    public ArrayList<Autor> consultarAutors()
    {
        ArrayList<Autor> listAutors = new ArrayList<Autor>();

        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM autor");

            while (rs.next())
            {
                Autor autor = new Autor(rs.getInt("id_autor"),
                        rs.getString("nom_autor"),rs.getDate("data_naix"));
                // afegiexo tost els autos de la base de dades al arraylist
                listAutors.add(autor);
            }

            con.desconectar();
            return listAutors;

        }
        catch (Exception e)
        {
            System.out.println("Error: No se ha trobat cap autor :(");
        }
        return null;

    }


    /**
     * Metode que permet de consultar els 10 , te un limit dels 10 PRIMERS, primers autors de la base de dades
     * aquest metode s'utiliza en el homeController
     * @return ArrayList<Autor>
     */
    public ArrayList<Autor> consultar10Autors()
    {
        ArrayList<Autor> listAutors = new ArrayList<Autor>();

        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM autor Limit 10");

            while (rs.next())
            {
                Autor autor = new Autor(rs.getInt("id_autor"),
                        rs.getString("nom_autor"),rs.getDate("data_naix"));
                // afegiexo tost els autos de la base de dades al arraylist
                listAutors.add(autor);
            }

            con.desconectar();
            return listAutors;

        }
        catch (Exception e)
        {
            System.out.println("No se ha trobat cap autor :(");
        }
        return null;
    }

    public Autor findAutor(int idAutor)
    {
        try
        {  ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM `autor` WHERE id_autor= "+ idAutor);
            if(rs.next())
            {
                Autor autor = new Autor(rs.getInt("id_autor"),rs.getString("nom_autor"),rs.getDate("data_naix"));
                con.desconectar();
                return autor;
            }



        }
        catch (Exception e)
        {
            System.out.println("No se ha trobat cap autor :( " + e.getMessage());
        }
        return null;
    }


    /**
     * Metode que busca el autor per el nom.
     * És necessari degut que els combox han de mostrar el nom del autor.
     * @param nomAutor String nom del autor
     * @return Objecte autor
     */
    public Autor findAutorByNom(String nomAutor)
    {
        try
        {  ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM `autor` WHERE nom_autor= '"+nomAutor+"'");
            if(rs.next())
            {
                Autor autor = new Autor(rs.getInt("id_autor"),rs.getString("nom_autor"),rs.getDate("data_naix"));
                con.desconectar();
                return autor;
            }



        }
        catch (Exception e)
        {
            System.out.println("No se ha trobat cap autor :( " + e.getMessage());
        }
        return null;
    }

    public boolean hayReservasAutor(int idAutor)
    {
        boolean existe = false;
        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql = "SELECT COUNT(*) AS count FROM llibre WHERE id_autor="+idAutor;
            ResultSet result = stat.executeQuery(sql);
            if(result.next()) {
                int count = result.getInt("count");
                if(count > 0) {
                    existe = true;
                }
            }
            con.desconectar();
        } catch(Exception e) {
            System.out.println("Error al comprobar si el autor existe: " + e.getMessage());
        }
        return existe;

    }
}
