package edu.pujadas.koobing_app.Models;

public class Editorial
{
    int idEditorial;
    String nomEditor;

    public Editorial()
    {
    }

    public Editorial(int idEditorial, String nomEditor)
    {
        this.idEditorial = idEditorial;
        this.nomEditor = nomEditor;
    }

    public int getIdEditorial()
    {
        return idEditorial;
    }

    public void setIdEditorial(int idEditorial)
    {
        this.idEditorial = idEditorial;
    }

    public String getNomEditor()
    {
        return nomEditor;
    }

    public void setNomEditor(String nomEditor)
    {
        this.nomEditor = nomEditor;
    }
}
