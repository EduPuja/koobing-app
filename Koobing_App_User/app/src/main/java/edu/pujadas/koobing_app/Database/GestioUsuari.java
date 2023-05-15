package edu.pujadas.koobing_app.Database;

import edu.pujadas.koobing_app.Models.Usuari;
import edu.pujadas.koobing_app.Utilities.PasswordUtilites;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.SortedMap;


public class GestioUsuari
{


    /**
     * Metode que inserta un usuari a la base de dates
     * @param usuari objecte usuari que és creat a memória
     */
    public void crearUsuari(Usuari usuari)
    {

        try
        {   //connexio usuari

            ConnexioMYSQL con = new ConnexioMYSQL();

            Statement stat = con.conectar();


            String query ="INSERT INTO usuari(dni,avatar,nom,cognom,data_naix,email,password) VALUES " +
                    "('"+usuari.getDni()+"','"+usuari.getAvatar()+"','"+usuari.getNom()+"','"+usuari.getCognom()+"','"+usuari.getDataNaix()+"','"+usuari.getEmail()+"','"+ PasswordUtilites.encryptPassword(usuari.getPassword())+"')";

            if(stat.executeUpdate(query) == 1)
            {
                System.out.println("Usuari insertat successfully");
            }
            else System.out.println("Usuari not inserted"); con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Crear usuari error: "+e.getMessage());
        }
    }


    /**
     * Metode per modificar un usuar a la base de dades
     * @param usuari Objecte usuari que és modificat a memória
     */
    public void modificarUsuari(Usuari usuari)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();

            Statement stat = con.conectar();
            String query = "UPDATE `usuari` " +
                    "SET `id_usuari`="+usuari.getId()+",`dni`='"+usuari.getDni()+"'," +
                    "`avatar`='"+usuari.getAvatar()+"',`nom`='"+usuari.getNom()+"'," +
                    "`cognom`='"+usuari.getCognom()+"',`data_naix`='"+usuari.getDataNaix()+"'," +
                    "`email`='"+usuari.getEmail()+"',`password`='"+usuari.getPassword()+"' WHERE dni= '"+usuari.getDni()+"'";

            if(stat.executeUpdate(query) == 1)
            {
                System.out.println("Successfully updated");
            }
            else System.out.println("Wrong update :(");

        con.desconectar();

        }
        catch (Exception e)
        {
            System.out.println("Modificar usuari error: "+e.getMessage());
        }
    }

    /**
     * Metode per eliminar un usuari de la base de dades
     * @param dniUsuari li passes un DNI d'usuari
     */
    public void eliminarUsuari(String dniUsuari)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();

            Statement stat = con.conectar();
            String query = "DELETE FROM `usuari` WHERE `dni` = '" + dniUsuari + "'";

            if(stat.executeUpdate(query) == 1)
            {
                System.out.println("Usuari eliminat correctament");
            }
            else System.out.println("La baixa no s'ha completat ");
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Eliminar usuari error: "+e.getMessage());
        }
    }


    /**
     * Metode per consultar la taula dels usuaris de la base de dades
     * @return ArrayList dels usuaris
     */
    public ArrayList<Usuari> consultarUsuaris()
    {
        ArrayList<Usuari> listUsuaris  = new ArrayList<Usuari>();

        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();

            Statement stat = con.conectar();


            String query = "select * from usuari";
            ResultSet rs = stat.executeQuery(query);



            while (rs.next())
            {
                Usuari usuari = new Usuari();
                usuari.setId(rs.getInt("id_usuari"));
                usuari.setDni(rs.getString("dni"));
                usuari.setAvatar(rs.getBlob("avatar"));
                usuari.setNom(rs.getString("nom"));
                usuari.setCognom(rs.getString("cognom"));
                usuari.setDataNaix(rs.getDate("data_naix"));
                usuari.setEmail(rs.getString("email"));
                usuari.setPassword(rs.getString("password"));

                listUsuaris.add(usuari);

            }
           con.desconectar();
            return listUsuaris;
        }
        catch (Exception e)
        {
            System.out.println("Consultar usuaris Error: " + e.getMessage());
        }
        return null;
    }


    /**
     * Meode que et retorna NOMES els 10 primers usuaris de la base de dades
     * @return ArrayList dels 10  usuaris
     */
    public ArrayList<Usuari> consultar10Usuaris()
    {
        ArrayList<Usuari> usuarios = new ArrayList<>();
        try
        {
            
            ConnexioMYSQL con = new ConnexioMYSQL();

            Statement stat = con.conectar();
            String query = "select  * from usuari limit 10 ";
            ResultSet rs = stat.executeQuery(query);

            while (rs.next())
            {

                Usuari user = new Usuari();
                user.setId(rs.getInt("id_usuari"));

                user.setAvatar(rs.getBlob("avatar"));
                user.setDni(rs.getString("dni"));
                user.setNom(rs.getString("nom"));
                user.setCognom(rs.getString("cognom"));
                user.setDataNaix(rs.getDate("data_naix"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));

                usuarios.add(user);
            }

            con.desconectar();
            return usuarios;


        }
        catch (Exception e)
        {
            System.out.println("Consultar 10 users Error: " + e.getMessage());
        }

        return null;
    }

    /**
     * Metode per consultar nomes 1 usuari de la base de dades
     * @param dni dni del usuari
     * @return Objecte usuari
     */
    public Usuari findUser(String dni)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();

            Statement stat = con.conectar();
            String query = "select * from usuari where dni = '" + dni + "'";
            ResultSet rs = stat.executeQuery(query);

            if(rs.next())
            {
                Usuari user = new Usuari();
                user.setId(rs.getInt("id_usuari"));
                user.setDni(rs.getString("dni"));
                user.setAvatar(rs.getBlob("avatar"));
                user.setNom(rs.getString("nom"));
                user.setCognom(rs.getString("cognom"));
                user.setDataNaix(rs.getDate("data_naix"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));

                return user;
            }

            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Find User Error :(" + e.getMessage());
        }

        return null;
    }

    /**
     * Metode per consultar nomes 1 usuari de la base de dades
     * @param idUsuari dni del usuari
     * @return Objecte usuari
     */
    public Usuari findUserID(int idUsuari)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();

            Statement stat = con.conectar();
            String query = "select * from usuari where id_usuari = " + idUsuari ;
            ResultSet rs = stat.executeQuery(query);

            if(rs.next())
            {
                Usuari user = new Usuari();
                user.setId(rs.getInt("id_usuari"));
                user.setDni(rs.getString("dni"));
                user.setAvatar(rs.getBlob("avatar"));
                user.setNom(rs.getString("nom"));
                user.setCognom(rs.getString("cognom"));
                user.setDataNaix(rs.getDate("data_naix"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));

                return user;
            }

            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Find User Error :(" + e.getMessage());
        }

        return null;
    }

    public boolean isUserReserved(int idUsuari)
    {
        boolean existe = false;
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql = "SELECT COUNT(*) AS count FROM reserves WHERE id_usuari="+idUsuari;
            ResultSet result = stat.executeQuery(sql);
            if(result.next()) {
                int count = result.getInt("count");
                if(count > 0) {
                    existe = true;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("UsuariReserver Error: " + e.getMessage());
        }

        return existe;
    }


}
