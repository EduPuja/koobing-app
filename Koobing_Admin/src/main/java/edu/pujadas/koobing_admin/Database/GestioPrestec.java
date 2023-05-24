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
            String sql = "INSERT INTO `reserva`( `ISBN`, `id_usuari`, `id_treballador`, `data_inici`, `data_fi`, `id_estat`) VALUES ('"+prestec.getLlibre().getISBN()+"'," +
                    "'"+prestec.getUsuari().getId()+"','"+prestec.getTreballador().getId()+"','"+prestec.getDataInici()+"','"+prestec.getDataFI()+"','"+prestec.getEstat()+"')";
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


    /**
     * Metode quee modifia el estat de la reserva a la base de dades
     * @param prestec objecte de tipus prestec
     */
    public void modificarEstatReserva(Prestec prestec)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String query = "UPDATE `reserva` SET `id_estat`='"+prestec.getEstat()+"' WHERE id_prestec ="+prestec.getIdReserva();
            if(stat.executeUpdate(query) == 1)
            {
                System.out.println("El estat s'ha modificat correctament");
            }
            else{
                System.out.println("EL estat no s'ha modificat ");
            }

            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error al modificar el estat reserva: " +e.getMessage());
        }
    }


    /**
     * Metode per eliminar de la base de dades una reserva
     * @param idReserva idetificador de la reserva
     */
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


    /**
     * Metode per consultat totes les reseves de la base de dades
     * @return un arraylist de tipus prestec
     */
    public ArrayList<Prestec> consultarReserves()
    {
        ArrayList<Prestec> listPrestec = new ArrayList<>();


        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();


            String query = "SELECT * FROM reserva";

            ResultSet rs = stat.executeQuery(query);

            while (rs.next())
            {
                Prestec prestec = new Prestec();
                prestec.setIdReserva(rs.getInt("id_prestec"));
                prestec.setLlibre(new GestioLlibre().findLlibreByISBN(rs.getLong("ISBN")));
                prestec.setUsuari(new GestioUsuari().findUserID(rs.getInt("id_usuari")));
                prestec.setTreballador(new GestioTreballador().findTreballador(rs.getInt("id_treballador")));
                prestec.setDataInici(rs.getDate("data_inici"));
                prestec.setDataFI(rs.getDate("data_fi"));
                prestec.setEstat(rs.getInt("id_estat"));

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

    /**
     * Metode que et retonra totala inforamacio depenent del estat en que estigui
     * @param idEstat
     */
    public ArrayList<Prestec> consultarReservesByEstat(int idEstat)
    {     ArrayList<Prestec> listPrestec = new ArrayList<>();


        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();


            String query = "SELECT * FROM reserva where id_estat= "+idEstat;

            ResultSet rs = stat.executeQuery(query);

            while (rs.next())
            {
                Prestec prestec = new Prestec();
                prestec.setIdReserva(rs.getInt("id_prestec"));
                prestec.setLlibre(new GestioLlibre().findLlibreByISBN(rs.getLong("ISBN")));
                prestec.setUsuari(new GestioUsuari().findUserID(rs.getInt("id_usuari")));
                prestec.setTreballador(new GestioTreballador().findTreballador(rs.getInt("id_treballador")));
                prestec.setDataInici(rs.getDate("data_inici"));
                prestec.setDataFI(rs.getDate("data_fi"));
                prestec.setEstat(rs.getInt("id_estat"));

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

    /**
     * Metode per buscar una reserva per el id
     * @param idReserva  identificador de la reserva
     * @return una objtece de tipus reserva
     */
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
                prestec.setEstat(rs.getInt("id_estat"));

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


  
}
