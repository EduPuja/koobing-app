package edu.pujadas.koobing_admin.Models;

import javafx.scene.image.Image;

import java.time.LocalDate;

public class Usuari extends Persona
{

    public Usuari(String dni, Image avatar, String nom, String cognom, LocalDate dataNaix, String email, String password)
    {
        super(dni, avatar, nom, cognom, dataNaix, email, password);
    }


}