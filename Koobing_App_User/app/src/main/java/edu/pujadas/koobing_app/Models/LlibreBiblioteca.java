package edu.pujadas.koobing_app.Models;

public class LlibreBiblioteca {

    private int id;

    private Llibre book;
    private Biblioteca biblioteca;
    private int stock;

    public LlibreBiblioteca(int id, Llibre book, Biblioteca biblioteca, int stock) {
        this.id = id;
        this.book = book;
        this.biblioteca = biblioteca;
        this.stock = stock;
    }

    public LlibreBiblioteca() {
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
