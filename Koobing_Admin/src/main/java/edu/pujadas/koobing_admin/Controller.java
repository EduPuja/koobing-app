package edu.pujadas.koobing_admin;

import edu.pujadas.koobing_admin.Database.ConnexioMYSQL;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Statement;

public class Controller
{
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick()
    {
        // debug
        // Statement statment =ConnexioMYSQL.connexioMYSQL();

        welcomeText.setText("Welcome to JavaFX Application!");
    }
}