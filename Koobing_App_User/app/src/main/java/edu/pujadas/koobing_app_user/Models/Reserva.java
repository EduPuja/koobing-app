package edu.pujadas.koobing_app_user.Models;

import java.sql.Date;
import java.sql.Timestamp;

public class Reserva
{
    int idReserva;
    Usuari usuari;
    Treballador treballador;
    Biblioteca biblio;
    Llibre llibre;
    Date dataInici;
    Date dataFI;

    public Reserva()
    {
    }

    public Reserva(int idReserva, Usuari usuari, Treballador treballador, Biblioteca biblio, Llibre llibre, Date dataInici, Date dataFi)
    {
        this.idReserva = idReserva;
        this.usuari = usuari;
        this.treballador = treballador;
        this.biblio = biblio;
        this.llibre = llibre;
        this.dataInici = dataInici;
        this.dataFI = dataFi;
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
