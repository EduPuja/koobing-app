package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Prestec;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class GestioPrestec
{
    public void crearReserva(Prestec prestec)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement statement = con.conectar();
           /* String sql = "INSERT INTO `reserves`( `id_usuari`, `id_treballador`, `id_biblioteca`, `ISBN`, `data_inici`, `data_fi`,`estat`) VALUES " +
                    "('"+reserva.getUsuari().getId()+"','"+reserva.getTreballador().getId()+"','"+reserva.getBiblio().getIdBiblioteca()+"','"+reserva.getLlibre().getISBN()+"','"+reserva.getDataInici()+"','"+reserva.getDataFI()+"',"+reserva.isEstat()+") ";*/

            //String sql = "INSERT INTO `reserves`( `id_usuari`, `id_treballador`, `id_biblioteca`, `ISBN`, `data_inici`, `data_fi`, `estat`) VALUES ('"+ prestec.getUsuari().getId()+"','"+ prestec.getTreballador().getId()+"','"+ prestec.getBiblio()+"','"+ prestec.getLlibre().getISBN()+"','"+ prestec.getDataInici()+"','"+ prestec.getDataFI()+"','"+ prestec.isEstat()+"')";

            String sql = "";
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

    public void modificarReserva(Prestec prestec)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql ="";


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

    public ArrayList<Prestec> consultarReserves()
    {
        ArrayList<Prestec> listPrestec = new ArrayList<>();


        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();


            String query = "SELECT * FROM reserves WHERE estat = 0";

            ResultSet rs = stat.executeQuery(query);

            while (rs.next())
            {
                Prestec prestec = new Prestec();
                prestec.setIdReserva(rs.getInt("id_reserva"));
                prestec.setUsuari(new GestioUsuari().findUserID(rs.getInt("id_usuari")));
                prestec.setTreballador(new GestioTreballador().findTreballador(rs.getInt("id_treballador")));
                //prestec.setBiblio(new GestioBiblioteca().findBiblioteca(rs.getInt("id_biblioteca")));
                prestec.setLlibre(new GestioLlibre().findLlibreByISBN(rs.getLong("ISBN")));
                prestec.setDataInici(rs.getDate("data_inici"));
                prestec.setDataFI(rs.getDate("data_fi"));
                prestec.setEstat(rs.getBoolean("estat"));
                //addin the object into the arraylist
                listPrestec.add(prestec);
            }
            con.desconectar();

            return listPrestec;
        }
        catch (Exception e)
        {
            System.out.println("Error consutarReserves: " + e.getMessage());
        }

        return null;
    }

    public Prestec findReserva(int idReserva)
    {
        // RETORNA LA RESERVA INDEPENDENT DEL STAT
        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String query = "SELECT * FROM reserves where id_reserva = "+idReserva ;
            ResultSet rs = stat.executeQuery(query);

            if (rs.next())
            {
                Prestec prestec = new Prestec();
                prestec.setIdReserva(rs.getInt("id_reserva"));
                prestec.setUsuari(new GestioUsuari().findUserID(rs.getInt("id_usuari")));
                prestec.setTreballador(new GestioTreballador().findTreballador(rs.getInt("id_treballador")));
                //prestec.setBiblio(new GestioBiblioteca().findBiblioteca(rs.getInt("id_biblioteca")));
                prestec.setLlibre(new GestioLlibre().findLlibreByISBN(rs.getLong("ISBN")));
                prestec.setDataInici(rs.getDate("data_inici"));
                prestec.setDataFI(rs.getDate("data_fi"));
                prestec.setEstat(rs.getBoolean("estat"));

                con.desconectar();

                return prestec;

            }
       ;
        }
        catch (Exception e)
        {
            System.out.println("Find Reserva Error" + e.getMessage());
        }

        return null;
    }


    public void canviarEstat(int idPrestec)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String query = "UPDATE `reserves` SET `estat`='1' WHERE id_reserva= "+idPrestec;
            if(stat.executeUpdate(query) == 1)
            {
                System.out.println("Se ha actualizat el estat");
            }
            else System.out.println("Estat de la reserva no actualizat");
            con.desconectar();

        }
        catch (Exception e)
        {
            System.out.println("Error changing the state of Reserva: " + e.getMessage());
        }
    }
}
