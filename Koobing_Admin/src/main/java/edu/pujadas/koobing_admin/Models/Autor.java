package edu.pujadas.koobing_admin.Models;

import java.time.LocalDate;

public class Autor
{
    int idAutor;
    String nomAutor;
    LocalDate dataNaixAutor;

    public Autor(int idAutor, String nomAutor, LocalDate dataNaixAutor)
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

    public LocalDate getDataNaixAutor()
    {
        return dataNaixAutor;
    }

    public void setDataNaixAutor(LocalDate dataNaixAutor)
    {
        this.dataNaixAutor = dataNaixAutor;
    }
}
