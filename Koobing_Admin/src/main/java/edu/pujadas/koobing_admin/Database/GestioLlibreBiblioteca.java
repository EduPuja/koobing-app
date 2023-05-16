package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Biblioteca;
import edu.pujadas.koobing_admin.Models.Llibre;
import edu.pujadas.koobing_admin.Models.LlibreBiblio;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GestioLlibreBiblioteca {

    public void crearLlibreBiblioteca(Llibre llibre)
    {

    }

    public void modificarLlibreBiblioteca(Llibre llibre)
    {

    }


    public void eliminarLlibreBiblioteca(Llibre llibre)
    {

    }

    public ArrayList<LlibreBiblio> consultarLlibreBiblioteca()
    {
        try
        {
            //gestors
            GestioLlibre gestioLlibre = new GestioLlibre();
            GestioBiblioteca gestioBiblioteca = new GestioBiblioteca();
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
