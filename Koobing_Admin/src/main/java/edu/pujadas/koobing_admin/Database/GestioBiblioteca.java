package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Biblioteca;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



public class GestioBiblioteca {

    /**
     * Metode per poder insertar una biblioteca en la base de datos
     * @param biblioteca Objeto Biblioteca
     */
    public void crearBiblioteca(Biblioteca biblioteca) {

        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();;
            String sql = "INSERT INTO biblioteca (id_biblioteca,id_poblacio,nom_biblio,latitud,longitud) VALUES"+
                    biblioteca.getIdBiblioteca()+","+biblioteca.getPoblacio().getIdPoblacio()+",'"+biblioteca.getNomBiblioteca()+"','"+biblioteca.getLatitud()+"','"+biblioteca.getLatitud()+"')";

            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("Biblioteca creado correctamente");
            }
            else System.out.println("Biblioteca not inserted");

            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("crearBiblioteca Error :" + e.getMessage());
        }
    }

    /**
     * Metode per poder actualizar una biblioteca en la base de datos
     * @param biblioteca Objeto Biblioteca
     */
    public void modificarBiblioteca(Biblioteca biblioteca) {
        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();;
            String sql = "UPDATE biblioteca SET nom_biblio = '"+biblioteca.getNomBiblioteca()+"',latitud = '"+biblioteca.getLatitud()+"',longitud = '"+biblioteca.getLatitud()+"' WHERE id_biblioteca = "+biblioteca.getIdBiblioteca();
            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("Biblioteca modificado correctamente");
            }
            else System.out.println("Biblioteca not updated");
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("modificarBiblioteca Error :" + e.getMessage());
        }
    }

    /**
     * Metode per poder eliminar una biblioteca en la base de datos
     * @param idBiblio Objeto Biblioteca
     */
    public void eliminarBiblioteca(int idBiblio) {

        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();;
            String sql = "DELETE FROM biblioteca WHERE id_biblioteca = "+idBiblio;
            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("Biblioteca eliminado correctamente");
            }
            else System.out.println("Biblioteca not deleted");
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("eliminarBiblioteca Error :" + e.getMessage());
        }

    }

    /**
     * Metode per poder consultar todas las bibliotecas en la base de datos
     * @return ArrayList de Biblioteca
     */
    public ArrayList<Biblioteca> consultarBiblioteques() {

        ArrayList<Biblioteca> listBiblioteques = new ArrayList<Biblioteca>();

        //gestio poblacio

        try {
                ConnexioMYSQL con = new ConnexioMYSQL();
                Statement stat = con.conectar();;
                String sql = "SELECT * FROM biblioteca";
                ResultSet rs = stat.executeQuery(sql);
                while (rs.next())
                {
                    Biblioteca biblioteca = new Biblioteca();
                    biblioteca.setIdBiblioteca(rs.getInt("id_biblioteca"));

                    biblioteca.setPoblacio(new GestioPoblacio().findPoblacio(rs.getInt("id_poblacio")));
                    biblioteca.setNomBiblioteca(rs.getString("nom_biblio"));
                    biblioteca.setLatitud(rs.getDouble("latitud"));
                    biblioteca.setLongitud(rs.getDouble("longitud"));
                    //biblioteca.setIdPoblacio(rs.getInt("id_poblacio"));


                    listBiblioteques.add(biblioteca);

                    return listBiblioteques;
                }

                con.desconectar();
        }
        catch (Exception e){
            System.out.println("ConsultarBiblioteques Error :" + e.getMessage());
        }

        return null;
    }

    /**
     * Metode per poder consultar una biblioteca en la base de datos
     * @param idBiblio Identificador de la biblioteca
     * @return Objeto Biblioteca
     */
    public Biblioteca findBiblioteca(int idBiblio) {

        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();;
            String sql = "SELECT * FROM biblioteca WHERE id_biblioteca = "+idBiblio;
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next())
            {
                Biblioteca biblioteca = new Biblioteca();
                biblioteca.setIdBiblioteca(rs.getInt("id_biblioteca"));
                //todo falta la poblacio

                biblioteca.setNomBiblioteca(rs.getString("nom_biblio"));
                biblioteca.setLatitud(rs.getDouble("latitud"));
                biblioteca.setLongitud(rs.getDouble("longitud"));
                //biblioteca.setIdPoblacio(rs.getInt("id_poblacio"));


                return biblioteca;
            }

            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("FindBiblioteca Error :" + e.getMessage());
        }
        return null;
    }
}
