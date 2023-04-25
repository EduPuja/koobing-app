package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Reserva;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


//todo falta create, mod , update
public class GestioReserva
{
    public void crearReserva(Reserva reserva)
    {
        try
        {

        }
        catch (Exception e)
        {
            System.out.println("Crearte Reserva Error: "+e.getMessage());
        }
    }

    public void modificarReserva(int idReserva)
    {
        try
        {

        }
        catch (Exception e)
        {
            System.out.println("Modificar Reserva Error: "+e.getMessage());
        }
    }

    public void eliminarReserva(int idReserva)
    {
        try
        {

        }
        catch (Exception e)
        {
            System.out.println("Eliminar Reserva Error: "+e.getMessage());
        }
    }

    public ArrayList<Reserva> consultarReserves()
    {
        ArrayList<Reserva> listReserva = new ArrayList<>();

        //gestiors

        GestioUsuari gestioUsuari = new GestioUsuari();
        GestioTreballador gestioTreballador = new GestioTreballador();

        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            /*String query="SELECT r.id_reserva,u.nom as nom_usuari,t.nom as nom_treballador,l.titol,b.nom_biblio" +
                    "FROM `reserves` r INNER JOIN usuari u on u.id_usuari=r.id_reserva" +
                    "INNER JOIN treballador t on t.id_treballador=r.id_treballador" +
                    "INNER JOIN llibre l on l.ISBN= r.ISBN" +
                    "INNER JOIN biblioteca b on b.id_biblioteca=r.id_biblioteca";*/

            String query = "SELECT * FROM reserves";

            ResultSet rs = stat.executeQuery(query);

            while (rs.next())
            {
                Reserva reserva = new Reserva();
                reserva.setIdReserva(rs.getInt("id_reserva"));
                reserva.setUsuari(new GestioUsuari().findUserID(rs.getInt("id_usuari")));
                reserva.setTreballador(new GestioTreballador().findTreballador(rs.getInt("id_treballador")));
                reserva.setBiblio(new GestioBiblioteca().findBiblioteca(rs.getInt("id_biblioteca")));
                reserva.setLlibre(new GestioLlibre().findLLibre(rs.getLong("ISBN")));
                reserva.setDataHoraReserva(rs.getTimestamp("data_hora_reserva"));
                reserva.setDataHoraEntrega(rs.getTimestamp("data_hora_entrega"));

                //addin the object into the arraylist
                listReserva.add(reserva);
            }
            con.desconectar();

            return listReserva;
        }
        catch (Exception e)
        {
            System.out.println("Error consutarReserves: " + e.getMessage());
        }

        return null;
    }

    public Reserva findReserva(int idReserva)
    {
        try {

        }
        catch (Exception e)
        {
            System.out.println("Find Reserva Error" + e.getMessage());
        }

        return null;
    }
}
