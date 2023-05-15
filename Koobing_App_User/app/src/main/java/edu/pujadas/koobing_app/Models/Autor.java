package edu.pujadas.koobing_app.Models;

import java.sql.Date;

public class Autor
{
    int idAutor;
    String nomAutor;
    Date dataNaixAutor;

    public Autor()
    {
    }

    public Autor(int idAutor, String nomAutor, Date dataNaixAutor)
    {
        this.idAutor = idAutor;
        this.nomAutor = nomAutor;
        this.dataNaixAutor = dataNaixAutor;
    }

    public int getIdAutor()
    {
        return idAutor;
    }

    public void setIdAutor(int idAutor)
    {
        this.idAutor = idAutor;
    }

    public String getNomAutor()
    {
        return nomAutor;
    }

    public void setNomAutor(String nomAutor)
    {
        this.nomAutor = nomAutor;
    }

    public Date getDataNaixAutor()
    {
        return dataNaixAutor;
    }

    public void setDataNaixAutor(Date dataNaixAutor)
    {
        this.dataNaixAutor = dataNaixAutor;
    }
}
