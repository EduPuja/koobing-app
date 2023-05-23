package edu.pujadas.koobing_app.Models;



import java.sql.Date;


public class Reserva
{
    int idReserva;

    Usuari usuari;
    Treballador treballador;
    Biblioteca biblioteca;
    Llibre llibre;
    Date dataInici;
    Date dataFI;

    public Reserva()
    {
    }

    public Reserva(int idReserva, Usuari usuari, Treballador treballador, Biblioteca biblioteca, Llibre llibre, Date dataInici, Date dataFI) {
        this.idReserva = idReserva;
        this.usuari = usuari;
        this.treballador = treballador;
        this.biblioteca = biblioteca;
        this.llibre = llibre;
        this.dataInici = dataInici;
        this.dataFI = dataFI;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public Treballador getTreballador() {
        return treballador;
    }

    public void setTreballador(Treballador treballador) {
        this.treballador = treballador;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    public Llibre getLlibre() {
        return llibre;
    }

    public void setLlibre(Llibre llibre) {
        this.llibre = llibre;
    }

    public Date getDataInici() {
        return dataInici;
    }

    public void setDataInici(Date dataInici) {
        this.dataInici = dataInici;
    }

    public Date getDataFI() {
        return dataFI;
    }

    public void setDataFI(Date dataFI) {
        this.dataFI = dataFI;
    }
}
