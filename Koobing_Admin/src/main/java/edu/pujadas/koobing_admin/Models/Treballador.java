package edu.pujadas.koobing_admin.Models;

import javafx.scene.image.Image;

import java.sql.Blob;
import java.sql.Date;


public class Treballador extends Persona
{
    private String numSegSocial;
    private boolean isAdmin;


    public Treballador()
    {

    }
    public Treballador(int id, String dni, Blob avatar, String nom, String cognom, Date dataNaix, String email, String password,
                       String numSegSocial, boolean isAdmin)
    {

        super(id,dni, avatar, nom, cognom, dataNaix, email, password);
        this.numSegSocial = numSegSocial;
        this.isAdmin = isAdmin;

    }

    public String getNumSegSocial()
    {
        return numSegSocial;
    }

    public void setNumSegSocial(String numSegSocial)
    {
        this.numSegSocial = numSegSocial;
    }

    public boolean isAdmin()
    {
        return isAdmin;
    }

    public void setAdmin(boolean admin)
    {
        isAdmin = admin;
    }
}
