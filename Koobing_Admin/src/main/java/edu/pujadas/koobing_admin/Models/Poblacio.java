package edu.pujadas.koobing_admin.Models;

public class Poblacio
{
    int idPoblacio;
    String nomPoble;

    public Poblacio(int idPoblacio, String nomPoble)
    {
        this.idPoblacio = idPoblacio;
        this.nomPoble = nomPoble;
    }

    public int getIdPoblacio()
    {
        return idPoblacio;
    }

    public void setIdPoblacio(int idPoblacio)
    {
        this.idPoblacio = idPoblacio;
    }

    public String getNomPoble()
    {
        return nomPoble;
    }

    public void setNomPoble(String nomPoble)
    {
        this.nomPoble = nomPoble;
    }
}