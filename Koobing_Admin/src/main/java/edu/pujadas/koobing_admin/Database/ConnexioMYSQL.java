package edu.pujadas.koobing_admin.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
public class ConnexioMYSQL
{
    /**
     * Metode que s'utiliza per poder connectarte a la base de dades MYSQL
     * @return Statment if successful null if not successful
     */
    public static Statement connexioMYSQL()
    {
        try
        {
            String url = "jdbc:mysql://localhost:3306/koobing_app";
            //nom usuari
            String user = "root";
            //contrassenya
            String password = "";
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            System.out.println("Connection successful! ");

            return statement;
        }
        catch (Exception e)
        {
           // e.printStackTrace();
            System.out.println("Error al conectar en la base de dades :( ");

        }
        return  null;
    }
}
