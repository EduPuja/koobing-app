package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Usuari;

import java.sql.ResultSet;
import java.sql.Statement;

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
            Statement con = ConnexioMYSQL.connexioMYSQL();


            String query ="INSERT INTO `usuari`( `avatar`, `nom`, `cognom`, `data_naix`, `email`, `password`) " +
                    "VALUES ('"+usuari.getAvatar()+"','"+usuari.getNom()+"','"+usuari.getCognom()+"','"+usuari.getDataNaix()+"','"+usuari.getEmail()+"','"+usuari.getPassword()+") ";

            if(con.executeUpdate(query) == 1)
            {
                System.out.println("Usuari insertat successfully");
            }
            else System.out.println("Usuari not inserted"); con.close();
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
            Statement con = ConnexioMYSQL.connexioMYSQL();

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
            Statement con = ConnexioMYSQL.connexioMYSQL();
            String query = "DELETE FROM `usuari` WHERE `dni` = '" + dniUsuari + "'";

            if(con.executeUpdate(query) == 1)
            {
                System.out.println("Usuari eliminat correctament");
            }
            else System.out.println("La baixa no s'ha completat ");
            con.close();
        }
        catch (Exception e)
        {
            System.out.println("El usuari no se ha pogut eliminar");
        }
    }


    /**
     * Metode per consultar els usuaris de la base de dades
     */
    public void consultarUsuari()
    {
        //todo consultar usuari

        try
        {
            Statement con = ConnexioMYSQL.connexioMYSQL();
            String query = "select * from usuari";
            ResultSet rs = con.executeQuery(query);
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
            con.close();
        }
        catch (Exception e)
        {
            System.out.println("No se han trobat usuaris ;( ");

        }
    }
}
