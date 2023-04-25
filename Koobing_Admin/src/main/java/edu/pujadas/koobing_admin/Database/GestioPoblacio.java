package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Poblacio;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GestioPoblacio {

    /**
     * Metode per crear una poblacio de la base de dades
     * @param poble
     */
    public void crearPoblacio(Poblacio poble)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String query = "Insert into poblacio (id_poblacio, nom_poble) VALUES (" + poble.getIdPoblacio()+", '"+ poble.getNomPoble()+"');";
            if(stat.executeUpdate(query) == 1)
            {
                System.out.println("Poblacio created successfully");
            }
            else System.out.println("Poblacio not created successfully");
        }
        catch (Exception e)
        {
            System.out.println("Crear poblacio Error : " + e.getMessage());
        }


    }

    /**
     * Metode per poder modifcar una poblacio de la base de dades
     * @param poble objecte poble a modificar
     */
    public void modificarPoblacio(Poblacio poble)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String query = "Update poblacio set nom_poble = '"+ poble.getNomPoble()+"' where id_poblacio = "+ poble.getIdPoblacio()+";";
            if(stat.executeUpdate(query) == 1)
            {
                System.out.println("Poblacio updated successfully");
            }
            else System.out.println("Poblacio not updated successfully");
        }
        catch (Exception e)
        {
            System.out.println("Modificar poblacio Error : " + e.getMessage());
        }

    }


    /**
     * Metode per eliminar un poble de la base de dades
     * @param poble
     */
    public void eliminarPoblacio(Poblacio poble)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String query = "Delete from poblacio where id_poblacio = "+ poble.getIdPoblacio()+";";
            if(stat.executeUpdate(query) == 1)
            {
                System.out.println("Poblacio deleted successfully");
            }
            else System.out.println("Poblacio not deleted successfully");
        }
        catch (Exception e)
        {
            System.out.println("Delete poblacio Error : " + e.getMessage());
        }

    }

    /**
     * Metode per consultar totes les poblacions de la base de dades
     * @return ArrayList de poblacions
     */
    public ArrayList<Poblacio> consultarPoblacions()
    {   ArrayList<Poblacio> poblacions = new ArrayList<Poblacio>();
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String query = "Select * from poblacio;";
            ResultSet rs = stat.executeQuery(query);

            if(rs.next())
            {
                Poblacio p = new Poblacio(rs.getInt("id_poblacio"), rs.getString("nom_poble"));
                poblacions.add(p);
                return poblacions;
            }

        }
        catch (Exception e)
        {
            System.out.println("Consultar poblacio Error : " + e.getMessage());
        }

        return null;
    }

    /***
     * Metode per torbar una poblacio en la base de dades
     * @param idPoblacio idetnificador
     * @return Objecte poblacio
     */
    public Poblacio findPoblacio(int idPoblacio)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String query = "Select * from poblacio where id_poblacio = "+ idPoblacio+";";
            ResultSet rs = stat.executeQuery(query);

            if(rs.next())
            {
                Poblacio p = new Poblacio(rs.getInt("id_poblacio"), rs.getString("nom_poble"));
                return p;
            }
        }
        catch (Exception e)
        {
            System.out.println("Find poblacio Error : " + e.getMessage());
        }

        return null;
    }
}
