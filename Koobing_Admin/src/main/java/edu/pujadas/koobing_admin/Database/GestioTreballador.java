package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Treballador;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GestioTreballador
{

    /**
     * Metode que inserta un treballador a la base de dades
     * @param treballador Objecte Treballador
     */
    public void crearTreballador(Treballador treballador)
    {
        try {

            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql = "INSERT INTO treballador (id_treballador, avatar, dni,nom,cognom, data_naix, email,password, num_seg_social, isAdmin) " +
                    "VALUES ("+treballador.getId()+", '"+treballador.getAvatar()+"', '"+treballador.getDni()+"'"+", '"+treballador.getNom()+"', '"+treballador.getCognom()+"'"+
                    ", '"+treballador.getDataNaix()+"', '"+treballador.getEmail()+"', '"+treballador.getPassword()+"', '"+treballador.getNumSegSocial()+"', '"+treballador.isAdmin()+"')";


            if(stat.executeUpdate(sql)==1)
            {
                System.out.println("El treballador se ha insertat correctamente");
            }
            else System.out.println("El treballador NO se ha insertat correctamente");
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error: El treballador NO se ha insertat :C");
        }
    }

    /**
     * Metode per poder modifcar les dades d'un treballador.
     * Aquest només el pot utilizar si es un administrador
     * @param t Objecte Treballador
     */
    public  void modificarTreballador(Treballador t)
    {
        try {

            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql = "UPDATE treballador SET  dni='"+t.getDni()+"',nom='"+t.getNom()+"', cognom='"+t.getCognom()+"', data_naix='"+t.getDataNaix()+"', email='"+t.getEmail()+"', password='"+t.getPassword()+"', num_seg_social='"+t.getNumSegSocial()+"', isAdmin='"+t.isAdmin()+"' WHERE id_treballador="+t.getId();


            if(stat.executeUpdate(sql)==1)
            {
                System.out.println("El treballador se ha modificat correctamente");
            }
            else System.out.println("El treballador NO se ha modificat correctamente");
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error: El treballador NO se ha modificat :C");
        }
    }

    /**
     * Metode per eliminar un treballadro de la base de datos
     * @param idWorker identificador del treballador
     */
    public void eliminarTreballador(int idWorker)
    {
        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql = "DELETE FROM treballador WHERE id_treballador="+idWorker;

            if(stat.executeUpdate(sql)==1)
            {
                System.out.println("El treballador se ha eliminat correctamente");
            }
            else System.out.println("El treballador NO se ha eliminat correctamente");
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error: El Treballador no se ha eliminat" );
        }
    }

    /**
     * Metode que et mostre un arraylist de treballadors consultats desde la base de datos
     * @return Arraylist de treballadors
     */
    public ArrayList<Treballador> consultarTreballadors()
    {
        ArrayList<Treballador> listTreballs = new ArrayList<Treballador>();

        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM treballador ");

            if(rs.next())
            {
                Treballador t = new Treballador();
                t.setId(rs.getInt("id_treballador"));
                t.setAvatar(rs.getBlob("avatar"));
                t.setDni(rs.getString("dni"));
                t.setNom(rs.getString("nom"));
                t.setCognom(rs.getString("cognom"));
                t.setDataNaix(rs.getDate("data_naix"));
                t.setEmail(rs.getString("email"));
                t.setPassword(rs.getString("password"));
                t.setNumSegSocial(rs.getString("num_seg_social"));
                t.setAdmin(rs.getBoolean("isAdmin"));

                listTreballs.add(t);
            }
            con.desconectar();
            return listTreballs;
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }


    /**
     * Metode per buscar un unic treballador
     * @param idWorker identificador del treballador
     * @return un objecte Treballador montat
     */
    public Treballador findTreballador(int idWorker)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM treballador WHERE id_treballador="+idWorker);

            if(rs.next())
            {
                Treballador t = new Treballador();
                t.setId(rs.getInt("id_treballador"));
                t.setAvatar(rs.getBlob("avatar"));
                t.setNom(rs.getString("dni"));
                t.setCognom(rs.getString("cognom"));
                t.setDataNaix(rs.getDate("data_naix"));
                t.setEmail(rs.getString("email"));
                t.setPassword(rs.getString("password"));
                t.setNumSegSocial(rs.getString("num_seg_social"));
                t.setAdmin(rs.getBoolean("isAdmin"));

                return  t;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }


}
