package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Poblacio;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GestioPoblacio {

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
