package edu.pujadas.koobing_admin.Models;

import java.time.LocalDate;

public class Llibre
{
    int ISBN;
    Autor autor;
    Editorial editor;
    Idioma idioma;
    Genere genere;
    String titol;
    int versio;
    LocalDate dataPubli;

    public Llibre(int ISBN, Autor autor, Editorial editor, Idioma idioma, Genere genere, String titol, int versio, LocalDate dataPubli)
    {
        this.ISBN = ISBN;
        this.autor = autor;
        this.editor = editor;
        this.idioma = idioma;
        this.genere = genere;
        this.titol = titol;
        this.versio = versio;
        this.dataPubli = dataPubli;
    }

    public int getISBN()
    {
        return ISBN;
    }

    public void setISBN(int ISBN)
    {
        this.ISBN = ISBN;
    }

    public Autor getAutor()
    {
        return autor;
    }

    public void setAutor(Autor autor)
    {
        this.autor = autor;
    }

    public Editorial getEditor()
    {
        return editor;
    }

    public void setEditor(Editorial editor)
    {
        this.editor = editor;
    }

    public Idioma getIdioma()
    {
        return idioma;
    }

    public void setIdioma(Idioma idioma)
    {
        this.idioma = idioma;
    }

    public Genere getGenere()
    {
        return genere;
    }

    public void setGenere(Genere genere)
    {
        this.genere = genere;
    }

    public String getTitol()
    {
        return titol;
    }

    public void setTitol(String titol)
    {
        this.titol = titol;
    }

    public int getVersio()
    {
        return versio;
    }

    public void setVersio(int versio)
    {
        this.versio = versio;
    }

    public LocalDate getDataPubli()
    {
        return dataPubli;
    }

    public void setDataPubli(LocalDate dataPubli)
    {
        this.dataPubli = dataPubli;
    }
}