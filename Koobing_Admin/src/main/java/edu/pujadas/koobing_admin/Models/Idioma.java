package edu.pujadas.koobing_admin.Models;

public class Idioma
{
    int idIdioma;
    String nomIdioma;

    public Idioma()
    {
    }

    public Idioma(int idIdioma, String nomIdioma)
    {
        this.idIdioma = idIdioma;
        this.nomIdioma = nomIdioma;
    }

    public int getIdIdioma()
    {
        return idIdioma;
    }

    public void setIdIdioma(int idIdioma)
    {
        this.idIdioma = idIdioma;
    }

    public String getNomIdioma()
    {
        return nomIdioma;
    }

    public void setNomIdioma(String nomIdioma)
    {
        this.nomIdioma = nomIdioma;
    }
}
