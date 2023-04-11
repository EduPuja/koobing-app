package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.Usuari;

import java.sql.Statement;

public class GestioUsuari implements OperacionCRUD
{
    Statement con = ConnexioMYSQL.connexioMYSQL();
    @Override
    public void crear()
    {
        // todo insertar usuari
        try
        {

        }
        catch (Exception e)
        {
            System.out.println("El usuari no se ha pogut insertar ;(");
        }
    }

    @Override
    public void modificar()
    {
        //todo modificar usuari
        try
        {

        }
        catch (Exception e)
        {
            System.out.println("Hi ha hagut una error a la modificacio");
        }
    }

    @Override
    public void eliminar()
    {
        //todo eliminar usuari

        try
        {

        }
        catch (Exception e)
        {
            System.out.println("El usuari no se ha pogut eliminar");
        }
    }

    @Override
    public void consultar()
    {
        //todo consultar usuari

        try
        {

        }
        catch (Exception e)
        {
            System.out.println("No se han trobat usuaris ;( ");
        }
    }
}
