package edu.pujadas.koobing_admin.Models;

import javafx.scene.image.Image;

import java.time.LocalDate;

public class Persona
{

    private int id;
    private String dni;
    private Image avatar;
    private String nom;
    private String cognom;
    private LocalDate dataNaix;
    private String email;
    private String password;


    public Persona(int id,String dni, Image avatar, String nom, String cognom, LocalDate dataNaix, String email, String password)
    {
        this.id = id;
        this.dni=dni;
        this.avatar = avatar;
        this.nom = nom;
        this.cognom = cognom;
        this.dataNaix = dataNaix;
        this.email = email;
        this.password = password;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDni()
    {
        return dni;
    }

    public void setDni(String dni)
    {
        this.dni = dni;
    }

    public Image getAvatar()
    {
        return avatar;
    }

    public void setAvatar(Image avatar)
    {
        this.avatar = avatar;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getCognom()
    {
        return cognom;
    }

    public void setCognom(String cognom)
    {
        this.cognom = cognom;
    }

    public LocalDate getDataNaix()
    {
        return dataNaix;
    }

    public void setDataNaix(LocalDate dataNaix)
    {
        this.dataNaix = dataNaix;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
