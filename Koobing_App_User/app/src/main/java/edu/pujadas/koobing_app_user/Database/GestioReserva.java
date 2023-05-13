package edu.pujadas.koobing_app_user.Database;

import edu.pujadas.koobing_admin.Models.Reserva;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;



public class GestioReserva
{
    public void crearReserva(Reserva reserva)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement statement = con.conectar();
            String sql = "INSERT INTO `reserves`( `id_usuari`, `id_treballador`, `id_biblioteca`, `ISBN`, `data_inici`, `data_fi`) VALUES " +
                    "('"+reserva.getUsuari().getId()+"','"+reserva.getTreballador().getId()+"','"+reserva.getBiblio().getIdBiblioteca()+"','"+reserva.getLlibre().getISBN()+"','"+reserva.getDataInici()+"','"+reserva.getDataFI()+"') ";

            if(statement.executeUpdate(sql) == 1)
            {
                System.out.println("Reserva inserted successfuly!");
            }
            else System.out.println("Reserva not inseted :(");
        }
        catch (Exception e)
        {
            System.out.println("Crearte Reserva Error: "+e.getMessage());
        }
    }

    public void modificarReserva(Reserva reserva)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql ="UPDATE `reserves` SET `id_usuari`='"+reserva.getUsuari().getId()+"',`id_treballador`='"+reserva.getTreballador().getId()+"'," +
                    "`id_biblioteca`='"+reserva.getBiblio().getIdBiblioteca()+"',`ISBN`='"+reserva.getLlibre().getISBN()+"'," +
                    "`data_inici`='"+reserva.getDataInici()+"',`data_fi`='"+reserva.getDataFI()+"' WHERE id_reserva=" +reserva.getIdReserva();
            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("Reseva update successfully");
            }
            else System.out.println("Reserva not updated ;(");

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
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql="DELETE FROM `reserves` WHERE id_reserva =" +idReserva;
            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("Reserva deleted succesfully");
            }
            else System.out.println("Reserva not deleted :(");
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
                reserva.setDataInici(rs.getDate("data_inici"));
                reserva.setDataFI(rs.getDate("data_fi"));

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
