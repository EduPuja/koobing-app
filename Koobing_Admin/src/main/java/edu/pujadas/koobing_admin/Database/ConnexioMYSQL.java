package edu.pujadas.koobing_admin.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class ConnexioMYSQL
{
    private Connection conexion;
    private final String url = "jdbc:mysql://localhost:3306/koobing_app";
    private final String usuario = "root";
    private final String password = "";

    /**
     * Metode per poder connectarte a la base de dates
     * url = "http://localhost:3306/koobing_app"
     * usuario = "root"
     * contraseña = " "
     */
    public Statement conectar()
    {
        try
        {
            conexion = DriverManager.getConnection(url, usuario, password);
           // System.out.println("Conexión establecida correctamente ✓");
            return conexion.createStatement();
        }
        catch (SQLException ex) {
            System.out.println("Ha ocurrido un error al conectar con la base de datos: " + ex.getMessage());
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
