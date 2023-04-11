package edu.pujadas.koobing_admin.Database;

import java.sql.Statement;

public class GestioUsuari implements OperacionCRUD
{
    Statement con = ConnexioMYSQL.connexioMYSQL();
    @Override
    public void crear()
    {
        // todo insertar usuari
    }

    @Override
    public void modificar()
    {
        //todo modificar usuari
    }

    @Override
    public void eliminar()
    {
        //todo eliminar usuari
    }

    @Override
    public void consultar()
    {
        //todo consultar usuari
    }
}
