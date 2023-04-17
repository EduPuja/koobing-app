package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Usuari;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class GestioUsuari
{


    /**
     * Metode que inserta un usuari a la base de dates
     * @param usuari objecte usuari que és creat a memória
     */
    public void crearUsuari(Usuari usuari)
    {
        // todo insertar usuari
        try
        {   //connexio usuari

            ConnexioMYSQL con = new ConnexioMYSQL();

            Statement stat = con.conectar();


            String query ="INSERT INTO `usuari`( `avatar`, `nom`, `cognom`, `data_naix`, `email`, `password`) " +
                    "VALUES ('"+usuari.getAvatar()+"','"+usuari.getNom()+"','"+usuari.getCognom()+"','"+usuari.getDataNaix()+"','"+usuari.getEmail()+"','"+usuari.getPassword()+") ";

            if(stat.executeUpdate(query) == 1)
            {
                System.out.println("Usuari insertat successfully");
            }
            else System.out.println("Usuari not inserted"); con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("El usuari no se ha pogut insertar ;(");
        }
    }


    public void modificarUsuari()
    {
        //todo modificar usuari
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();

            Statement stat = con.conectar();

        }
        catch (Exception e)
        {
            System.out.println("Hi ha hagut una error a la modificacio");
        }
    }

    /**
     * Metode per eliminar un usuari de la base de dades
     * @param dniUsuari li passes un DNI d'usuari
     */
    public void eliminarUsuari(String dniUsuari)
    {
        //todo eliminar usuari

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
            System.out.println("El usuari no se ha pogut eliminar");
        }
    }


    /**
     * Metode per consultar els usuaris de la base de dades
     */
    public void consultarUsuaris()
    {
        //todo consultar usuari

        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();

            Statement stat = con.conectar();


            String query = "select * from usuari";
            ResultSet rs = stat.executeQuery(query);



            while (rs.next())
            {
                System.out.println("Id_usuari: " +rs.getInt("id_usuari"));
                System.out.println("DNI: " +rs.getString("dni"));
                System.out.println("NOM: " +rs.getString("nom"));
                System.out.println("COGNOM: " +rs.getString("cognom"));
                System.out.println("Data Naix: " +rs.getDate("data_naix"));
                System.out.println("Email: " +rs.getString("email"));
                System.out.println("Password: " +rs.getString("password"));
            }
           con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("No se han trobat usuaris ;( ");


        }
    }


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
            System.out.println("Hi ha hagut un error " + e.getMessage());

            //e.printStackTrace();
        }

        return null;
    }

}
