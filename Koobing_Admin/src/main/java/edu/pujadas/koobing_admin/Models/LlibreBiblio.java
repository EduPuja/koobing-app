package edu.pujadas.koobing_admin.Models;

public class LlibreBiblio {

    private int id;
    Llibre book;
    Biblioteca biblioteca;
    int stock ;

    public LlibreBiblio() {
    }

    public LlibreBiblio(int id, Llibre book, Biblioteca biblioteca, int stock) {
        this.id = id;
        this.book = book;
        this.biblioteca = biblioteca;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Llibre getBook() {
        return book;
    }

    public void setBook(Llibre book) {
        this.book = book;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
