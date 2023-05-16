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
        }
        catch (Exception e)
        {
            System.out.println("Error insertinga book into a library: "+e.getMessage());
        }
    }

    public void modificarLlibreBiblioteca(Llibre llibre)
    {
        try

        {

        }
        catch (Exception e)
        {
            System.out.println("Error modifiy de book biblio " + e.getMessage());
        }
    }


    public void eliminarLlibreBiblioteca(Llibre llibre)
    {
        try
        {

        }
        catch (Exception e)
        {
            System.out.println("Error Deleting a book bilbiotec: " +e.getMessage());
        }
    }

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
        }
        catch (Exception e)
        {
            System.out.println("Error consulting relation between book and library: "+ e.getMessage());
        }

        return null;
    }
}
