package edu.pujadas.koobing_admin.Models;

public class Genere
{
    int idGenere;
    String nomGenere;

    public Genere(int idGenere, String nomGenere)
    {
        this.idGenere = idGenere;
        this.nomGenere = nomGenere;
    }

    public int getIdGenere()
    {
        return idGenere;
    }

    public void setIdGenere(int idGenere)
    {
        this.idGenere = idGenere;
    }

    public String getNomGenere()
    {
        return nomGenere;
    }

    public void setNomGenere(String nomGenere)
    {
        this.nomGenere = nomGenere;
    }
}
