package edu.pujadas.koobing_admin.Models;

import javafx.scene.image.Image;

import java.time.LocalDate;

public class Treballador extends Persona
{
    private String numSegSocial;
    private boolean isAdmin;
    public Treballador(int id, Image avatar, String nom, String cognom, LocalDate dataNaix, String email, String password,
                       String numSegSocial,boolean isAdmin)
    {

        super(id, avatar, nom, cognom, dataNaix, email, password);
        this.numSegSocial = numSegSocial;
        this.isAdmin = isAdmin;

    }
}
