package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Autor;

import java.sql.ResultSet;
import java.sql.Statement;

public class GestioAutor
{


    public void crearAutor(Autor autor)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();

            //Todo insertar un autor


            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("El autor NO se ha insertat correctament");
        }
    }

    public void modificarAutor(int idAutor)
    {
        try
        {

        }
        catch (Exception e)
        {
            System.out.println("El autor NO se ha modificar correctament");
        }
    }

    public void eliminarAutor(int idAutor)
    {

    }
    public void consultarAutors()
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM autor");

            while(rs.next())
            {
                System.out.println("ID_Autor: " +rs.getInt("id_autor"));
                System.out.println("NOM Autor: " +rs.getString("nom_autor"));
                System.out.println("Data Naix: " +rs.getDate("data_naix"));


            }
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("No se ha trobat cap autor :(");
        }
    }

    public ResultSet consultar10Autors()
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM autor Limit 10");

            return rs;
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
            Autor autor = new Autor(rs.getInt("id_autor"),rs.getString("nom_autor"),rs.getDate("data_naix"));
            con.desconectar();
            return autor;
        }
        catch (Exception e)
        {
            System.out.println("No se ha trobat cap autor :(");
        }
        return null;
    }
}
