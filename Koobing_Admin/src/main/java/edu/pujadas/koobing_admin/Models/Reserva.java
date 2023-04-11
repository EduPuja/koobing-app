package edu.pujadas.koobing_admin.Models;

import java.time.LocalDateTime;
import java.util.function.LongConsumer;

public class Reserva
{
    int idReserva;
    Usuari usuari;
    Treballador treballador;
    Biblioteca biblio;
    Llibre llibre;
    LocalDateTime dataHoraReserva;
    LocalDateTime dataHoraEntrega;

    public Reserva(int idReserva, Usuari usuari, Treballador treballador, Biblioteca biblio, Llibre llibre, LocalDateTime dataHoraReserva, LocalDateTime dataHoraEntrega)
    {
        this.idReserva = idReserva;
        this.usuari = usuari;
        this.treballador = treballador;
        this.biblio = biblio;
        this.llibre = llibre;
        this.dataHoraReserva = dataHoraReserva;
        this.dataHoraEntrega = dataHoraEntrega;
    }

    public int getIdReserva()
    {
        return idReserva;
    }

    public void setIdReserva(int idReserva)
    {
        this.idReserva = idReserva;
    }

    public Usuari getUsuari()
    {
        return usuari;
    }

    public void setUsuari(Usuari usuari)
    {
        this.usuari = usuari;
    }

    public Treballador getTreballador()
    {
        return treballador;
    }

    public void setTreballador(Treballador treballador)
    {
        this.treballador = treballador;
    }

    public Biblioteca getBiblio()
    {
        return biblio;
    }

    public void setBiblio(Biblioteca biblio)
    {
        this.biblio = biblio;
    }

    public Llibre getLlibre()
    {
        return llibre;
    }

    public void setLlibre(Llibre llibre)
    {
        this.llibre = llibre;
    }

    public LocalDateTime getDataHoraReserva()
    {
        return dataHoraReserva;
    }

    public void setDataHoraReserva(LocalDateTime dataHoraReserva)
    {
        this.dataHoraReserva = dataHoraReserva;
    }

    public LocalDateTime getDataHoraEntrega()
    {
        return dataHoraEntrega;
    }

    public void setDataHoraEntrega(LocalDateTime dataHoraEntrega)
    {
        this.dataHoraEntrega = dataHoraEntrega;
    }
}
