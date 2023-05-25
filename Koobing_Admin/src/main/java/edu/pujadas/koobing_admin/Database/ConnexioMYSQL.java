package edu.pujadas.koobing_admin.Database;

import javafx.scene.control.Alert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnexioMYSQL {
    private Connection conexion;
    Properties prop = new Properties();
    InputStream input = null;




    public Statement conectar() {
        Alert wrong = new Alert(Alert.AlertType.ERROR);
        try {

            String filename = "C:/koobing_app_config/config.properties";
            input = new FileInputStream(filename);

            prop.load(input);


            String usuario = prop.getProperty("user");
            String password = prop.getProperty("password");
            String host = prop.getProperty("host");
            String puerto = prop.getProperty("port");


            String url = "jdbc:mysql://" + host + ":" + puerto + "/koobing_app";

            conexion = DriverManager.getConnection(url, usuario, password);
            return conexion.createStatement();
        }
        catch (Exception ex) {
            wrong.setTitle("Error al conectar amb el servidor");
            wrong.setHeaderText("La connexio no s'ha establert correctament");
            wrong.setContentText("Prova d'obrir el servidor de BASE DE DADES");
            wrong.show();
            return null;
        }
    }

    /**
     * Método para desconectar de la base de datos
     */
    public void desconectar() {
        try {
            if (conexion != null) {
                conexion.close();
                //System.out.println("Conexión cerrada correctamente \u2713");
            }
        } catch (SQLException ex) {
            System.out.println("Ha ocurrido un error al cerrar la conexión con la base de datos: " + ex.getMessage());
        }
    }

    public Connection getConexion() {
        return conexion;
    }
}
