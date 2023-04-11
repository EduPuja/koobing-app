package edu.pujadas.koobing_admin.Models;

import javafx.scene.image.Image;

import java.time.LocalDate;

public class Usuari extends Persona
{

    public Usuari(int idUsuari, Image avatar, String nom, String cognom, LocalDate dataNaix, String email, String password)
    {
        super(idUsuari, avatar, nom, cognom, dataNaix, email, password);
    }


}
