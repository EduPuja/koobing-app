package edu.pujadas.koobing_app.Models;

import java.sql.Date;

public class Llibre
{

    Long ISBN;
    Autor autor;
    Editorial editor;
    Idioma idioma;
    Genere genere;
    String titol;
    int versio;
    Date dataPubli;
    int stock;

    public Llibre()
    {
    }

    public Llibre(Long ISBN, Autor autor, Editorial editor, Idioma idioma, Genere genere, String titol, int versio, Date dataPubli,int stock)
    {
        this.ISBN = ISBN;
        this.autor = autor;
        this.editor = editor;
        this.idioma = idioma;
        this.genere = genere;
        this.titol = titol;
        this.versio = versio;
        this.dataPubli = dataPubli;
        this.stock = stock;
    }

    public String getAllInfoBook()
    {
        return this.ISBN+" "+this.titol +" "+this.autor.getNomAutor() +" "+this.editor.getNomEditor() +" "+
                this.genere.getNomGenere() +" "+this.versio +" "+ this.dataPubli.toString();
    }

    public Long getISBN()
    {
        return ISBN;
    }

    public void setISBN(Long ISBN)
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

    public Date getDataPubli()
    {
        return dataPubli;
    }

    public void setDataPubli(Date dataPubli)
    {
        this.dataPubli = dataPubli;
    }


    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
