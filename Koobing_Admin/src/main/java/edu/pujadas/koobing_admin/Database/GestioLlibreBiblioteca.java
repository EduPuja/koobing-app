package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Biblioteca;
import edu.pujadas.koobing_admin.Models.Llibre;
import edu.pujadas.koobing_admin.Models.LlibreBiblio;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GestioLlibreBiblioteca {
    //gestors necessaris per trobar els objectes per els identificadors
    GestioLlibre gestioLlibre = new GestioLlibre();
    GestioBiblioteca gestioBiblioteca = new GestioBiblioteca();


    public void crearLlibreBiblioteca(LlibreBiblio llibreBiblio)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql = "INSERT INTO `biblio_llibre`(`ISBN`, `id_biblioteca`, `stock`) VALUES ('"+llibreBiblio.getBook().getISBN()+"',"+llibreBiblio.getBiblioteca().getIdBiblioteca()+","+llibreBiblio.getStock()+")";
            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("Insert biblio llibre correctly");
            }
            else System.out.println("Error inserting biblio llibre"); con.desconectar();;
        }
        catch (Exception e)
        {
            System.out.println("Error insertinga book into a library: "+e.getMessage());
        }
    }


    /**
     * Metode per actualizar en el stock o bé la relació del llibre i la biblioteca
     * @param llibreBiblio objecte
     */
    public void modificarLlibreBiblioteca(LlibreBiblio llibreBiblio)
    {
        try

        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stmt = con.conectar();
            String sql = "UPDATE `biblio_llibre` SET `ISBN`='"+llibreBiblio.getBook().getISBN()+"',`id_biblioteca`='"+llibreBiblio.getBiblioteca().getIdBiblioteca()+"',`stock`='"+llibreBiblio.getStock()+"' WHERE id="+llibreBiblio.getId();
            if(stmt.executeUpdate(sql) == 1 ){
                System.out.println("La relacio s'ha actualizat perfectament");
            }
            else System.out.println("No se ha actualizat correctament");
        }
        catch (Exception e)
        {
            System.out.println("Error modifiy de book biblio " + e.getMessage());
        }
    }


    /**
     * Metode per eliminar es valors de la relacio entre llibre i bilioceca
     * @param id identficador de tipus integer
     */
    public void eliminarLlibreBiblioteca(int id)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql = "DELETE FROM `biblio_llibre` WHERE  id= "+id;
            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("Llibre elimiant de la relacio correctament");
            }
            else System.out.println("Llibre not deleted from relation :( "); con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error Deleting a book bilbiotec: " +e.getMessage());
        }
    }


    /**
     * Metode que et retorna un llistat de tots els llibres que hi han en una bilbioteca
     * et dona tot el llistat dels llibres amb la bilbioteca i el seu stock
     * @return ArraList<LlibreBiblioteca>
     */
    public ArrayList<LlibreBiblio> consultarLlibreBiblioteca()
    {
        try
        {


          ArrayList<LlibreBiblio>  listBiblioBook=  new ArrayList<LlibreBiblio>();

            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String query ="SELECT * FROM biblio_llibre";
            ResultSet rs = stat.executeQuery(query);
            while (rs.next())
            {
                LlibreBiblio llibreBiblio = new LlibreBiblio();
                Llibre book = gestioLlibre.findLLibre(rs.getLong("ISBN"));
                Biblioteca b = gestioBiblioteca.findBiblioteca(rs.getInt("id_biblioteca"));
                llibreBiblio.setBook(book);
                llibreBiblio.setBiblioteca(b);

                listBiblioBook.add(llibreBiblio);
                return  listBiblioBook;

            }

            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error consulting relation between book and library: "+ e.getMessage());
        }


        return null;
    }
}
