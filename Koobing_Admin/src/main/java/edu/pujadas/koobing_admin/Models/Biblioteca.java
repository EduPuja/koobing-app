package edu.pujadas.koobing_admin.Models;

public class Biblioteca
{
    int idBiblioteca;
    String nomPoblacio;
    Poblacio poblacio;
    double latitud;
    double longitud;

    public Biblioteca(int idBiblioteca, String nomPoblacio, Poblacio poblacio, double latitud, double longitud)
    {
        this.idBiblioteca = idBiblioteca;
        this.nomPoblacio = nomPoblacio;
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

    public String getNomPoblacio()
    {
        return nomPoblacio;
    }

    public void setNomPoblacio(String nomPoblacio)
    {
        this.nomPoblacio = nomPoblacio;
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
