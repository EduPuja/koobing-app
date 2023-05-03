package edu.pujadas.koobing_admin.Models;

import edu.pujadas.koobing_admin.Database.PasswordUtils;
import javafx.scene.image.Image;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;

public class Persona {

    private int id;
    private String dni;
    private Blob avatar;
    private String nom;
    private String cognom;
    private Date dataNaix;
    private String email;
    private String password;

    public Persona() {

    }


    public Persona(int id, String dni, Blob avatar, String nom, String cognom, Date dataNaix, String email, String password) {
        this.id = id;
        this.dni = dni;
        this.avatar = avatar;
        this.nom = nom;
        this.cognom = cognom;
        this.dataNaix = dataNaix;
        this.email = email;
        //crido el metode setPassword perque aixi me la encrpiti directament
        setPassword(password);
        // this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Blob getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public Date getDataNaix() {
        return dataNaix;
    }

    public void setDataNaix(Date dataNaix) {
        this.dataNaix = dataNaix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;

    }


    public void setPassword(String password) {
        this.password = password;
    }
}


