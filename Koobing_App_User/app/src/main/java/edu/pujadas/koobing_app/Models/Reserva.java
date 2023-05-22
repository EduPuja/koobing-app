package edu.pujadas.koobing_app.Models;

import java.sql.Date;
import java.sql.Timestamp;

public class Reserva
{
    int idReserva;

    int idUsuari;
    int idTreballador;
    int idBiblioteca;

    long ISBN;

    Date dataInici;
    Date dataFI;

    public Reserva()
    {
    }

    public Reserva(int idReserva, int idUsuari, int idTreballador, int idBiblioteca, long ISBN, Date dataInici, Date dataFI) {
        this.idReserva = idReserva;
        this.idUsuari = idUsuari;
        this.idTreballador = idTreballador;
        this.idBiblioteca = idBiblioteca;
        this.ISBN = ISBN;
        this.dataInici = dataInici;
        this.dataFI = dataFI;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdUsuari() {
        return idUsuari;
    }

    public void setIdUsuari(int idUsuari) {
        this.idUsuari = idUsuari;
    }

    public int getIdTreballador() {
        return idTreballador;
    }

    public void setIdTreballador(int idTreballador) {
        this.idTreballador = idTreballador;
    }

    public int getIdBiblioteca() {
        return idBiblioteca;
    }

    public void setIdBiblioteca(int idBiblioteca) {
        this.idBiblioteca = idBiblioteca;
    }

    public long getISBN() {
        return ISBN;
    }

    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
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
