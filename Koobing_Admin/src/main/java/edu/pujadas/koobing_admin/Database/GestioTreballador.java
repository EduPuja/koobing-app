package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Treballador;
import edu.pujadas.koobing_admin.Utilities.PasswordUtilites;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//gestio worker done ✅
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
                    ", '"+treballador.getDataNaix()+"', '"+treballador.getEmail()+"', '"+ PasswordUtilites.encryptPassword(treballador.getPassword())+"', '"+treballador.getNumSegSocial()+"', '"+treballador.isAdmin()+"')";



            if(stat.executeUpdate(sql)==1)
            {
                System.out.println("El treballador se ha insertat correctamente");
            }
            else System.out.println("El treballador NO se ha insertat correctamente");
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("crearTreballador Error:" +e.getMessage());
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
            System.out.println("Modificar Treballador Error:" +e.getMessage());
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
            System.out.println("Eliminar Treballaddor Error:" + e.getMessage() );
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

            while (rs.next())
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
            System.out.println("Consultar Treballadors Error: " + e.getMessage());
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
                t.setNom(rs.getString("nom"));
                t.setDni(rs.getString("dni"));
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
            System.out.println("Find Worker Error: " + e.getMessage());
        }
        return null;
    }


    public boolean isTreballadorReserved(int idWorker)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();

            String query = "SELECT COUNT(*) AS count FROM reserves WHERE id_treballador="+idWorker;
            ResultSet rs = stat.executeQuery(query);
            if(rs.next())
            {
                int count = rs.getInt("count");
                if(count>0)
                {
                    return true;
                }
            }


        }
        catch (Exception e)
        {
            System.out.println("isWorkerReserved Error: " + e.getMessage());
        }

        return false;
    }


    public Treballador findWorkerByEmail(String email)
    {
       try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM treballador WHERE email='"+email+"'");

            if(rs.next())
            {
                Treballador t = new Treballador();
                t.setId(rs.getInt("id_treballador"));
                t.setAvatar(rs.getBlob("avatar"));
                t.setNom(rs.getString("nom"));
                t.setDni(rs.getString("dni"));
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
            System.out.println("Find Worker Error: " + e.getMessage());
        }
        return null;
    }




}
