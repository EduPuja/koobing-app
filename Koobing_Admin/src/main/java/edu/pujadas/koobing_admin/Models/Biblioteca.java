package edu.pujadas.koobing_admin.Models;

public class Biblioteca
{
    int idBiblioteca;
    String nomBiblioteca;
    Poblacio poblacio;
    double latitud;
    double longitud;

    public Biblioteca()
    {
    }

    public Biblioteca(int idBiblioteca, String nomBiblioteca, Poblacio poblacio, double latitud, double longitud)
    {
        this.idBiblioteca = idBiblioteca;
        this.nomBiblioteca = nomBiblioteca;
        this.poblacio = poblacio;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getIdBiblioteca()
    {
        return idBiblioteca;
    }

    public void setIdBiblioteca(int idBiblioteca)
    {
        this.idBiblioteca = idBiblioteca;
    }

    public String getNomBiblioteca()
    {
        return nomBiblioteca;
    }

    public void setNomBiblioteca(String nomBiblioteca)
    {
        this.nomBiblioteca = nomBiblioteca;
    }

    public Poblacio getPoblacio()
    {
        return poblacio;
    }

    public void setPoblacio(Poblacio poblacio)
    {
        this.poblacio = poblacio;
    }

    public double getLatitud()
    {
        return latitud;
    }

    public void setLatitud(double latitud)
    {
        this.latitud = latitud;
    }

    public double getLongitud()
    {
        return longitud;
    }

    public void setLongitud(double longitud)
    {
        this.longitud = longitud;
    }
}
