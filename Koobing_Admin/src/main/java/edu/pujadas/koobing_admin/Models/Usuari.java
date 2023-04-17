package edu.pujadas.koobing_admin.Models;


import java.sql.Blob;
import java.sql.Date;


public class Usuari extends Persona
{

    public Usuari()
    {

    }
    public Usuari(int id, String dni, Blob avatar, String nom, String cognom, Date dataNaix, String email, String password)
    {
        super(id,dni, avatar, nom, cognom, dataNaix, email, password);
    }


}
