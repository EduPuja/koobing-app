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
    public Treballador(int idTreballador, String dniWork, Blob avatarWorker, String nomWorker, String cognomTreballador, Date dataNaixWorker, String emailWorker, String passwordWorker,
                       String numSegSocial, boolean isAdmin)
    {


        super(idTreballador,dniWork, avatarWorker, nomWorker, cognomTreballador, dataNaixWorker, emailWorker, passwordWorker);
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
