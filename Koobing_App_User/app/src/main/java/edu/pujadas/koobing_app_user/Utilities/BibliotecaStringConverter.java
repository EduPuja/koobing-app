package edu.pujadas.koobing_app_user.Utilities;

import edu.pujadas.koobing_admin.Models.Biblioteca;
import javafx.util.StringConverter;

public class BibliotecaStringConverter extends StringConverter<Biblioteca> {
    @Override
    public String toString(Biblioteca biblioteca) {
        if(biblioteca!=null)
        {
            return biblioteca.getNomBiblioteca();
        }
        else return null;
    }

    @Override
    public Biblioteca fromString(String s) {

        return null;
    }

    public int getIdBiblioteca(Biblioteca biblioteca) {
        if(biblioteca!=null)
        {
            return biblioteca.getIdBiblioteca();
        }
        return -1;
    }
}
