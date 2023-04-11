package edu.pujadas.View;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller
{
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick()
    {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}