package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Usuari;

import java.sql.Statement;

public class GestioUsuari
{


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


    public void eliminarUsuari()
    {
        //todo eliminar usuari

        try
        {
            Statement con = ConnexioMYSQL.connexioMYSQL();
        }
        catch (Exception e)
        {
            System.out.println("El usuari no se ha pogut eliminar");
        }
    }


    public void consultarUsuari()
    {
        //todo consultar usuari

        try
        {
            Statement con = ConnexioMYSQL.connexioMYSQL();
        }
        catch (Exception e)
        {
            System.out.println("No se han trobat usuaris ;( ");
        }
    }
}
