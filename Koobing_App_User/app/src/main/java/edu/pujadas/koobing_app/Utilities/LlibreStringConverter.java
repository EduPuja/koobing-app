package edu.pujadas.koobing_app.Utilities;

import edu.pujadas.koobing_admin.Models.Llibre;
import javafx.util.StringConverter;

public class LlibreStringConverter extends StringConverter<Llibre> {
    @Override
    public String toString(Llibre llibre) {
        if(llibre !=null)
        {
            return llibre.getTitol();
        }
        else return null;
    }

    @Override
    public Llibre fromString(String s) {
        return null;
    }

    /**
     * metode per que et retorni el identificador del llibre
     * @param llibre Objecte llibre
     * @return long si el id es correcte, en cas negatiu et retona -1
     */
    public long getISBNLlibre(Llibre llibre) {
        if(llibre !=null)
        {
            return llibre.getISBN();

        }
        return -1;
    }
}
