package edu.pujadas.koobing_app.Models;


import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;


public class Usuari extends Persona
{

    public Usuari()
    {

    }

    public Usuari(int id, String dni, String nom, String cognom, Date dataNaix, String email, String password) {
        super(id, dni, nom, cognom, dataNaix, email, password);
    }
}
