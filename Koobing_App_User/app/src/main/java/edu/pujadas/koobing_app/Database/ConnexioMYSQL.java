package edu.pujadas.koobing_app.Database;


import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class ConnexioMYSQL extends AsyncTask<Void,Void,Statement>
{
    private Connection conexion = null;

    // ip insti :  192.168.19.0
    private final String url = "jdbc:mysql://192.168.19.0:3306/koobing_app";
    private final String usuario = "root";
    private final String password = "";


    public Statement conectar()
    {

        try
        {

            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexión establecida correctamente ✓");
            return conexion.createStatement();


        }
        catch (SQLException ex) {
            ex.printStackTrace();

        }

        return null;
    }
    /**
     * Metode per poder desconectar de la base de datos
     */
    public void desconectar()
    {
        try {
            if (conexion != null) {
                conexion.close();
                //System.out.println("Conexión cerrada correctamente \u2713");
            }
        } catch (SQLException ex) {
            System.out.println("Ha ocurrido un error al cerrar la conexión con la base de datos: " + ex.getMessage());
        }
    }

    public Connection getConexion()
    {
        return conexion;
    }

    @Override
    protected Statement doInBackground(Void... voids) {
       return  conectar();

    }
}
