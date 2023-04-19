package edu.pujadas.koobing_admin.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class ConnexioMYSQL
{
    private Connection conexion;

    // Servidor institut : jdbc:mysql://192.168.2.143:3306/koobing_app
    //servidor home : 
    private final String url = "jdbc:mysql://192.168.2.143:3306/koobing_app";
    private final String usuario = "admin";
    private final String password = "";


    public Statement conectar()
    {
        try
        {
            conexion = DriverManager.getConnection(url, usuario, password);
            //System.out.println("Conexión establecida correctamente ✓");
            return conexion.createStatement();
        }
        catch (SQLException ex) {
            //System.out.println("Ha ocurrido un error al conectar con la base de datos: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
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
}
