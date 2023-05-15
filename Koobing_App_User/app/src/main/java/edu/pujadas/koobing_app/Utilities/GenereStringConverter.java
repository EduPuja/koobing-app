package edu.pujadas.koobing_app.Utilities;

import edu.pujadas.koobing_admin.Models.Genere;
import javafx.util.StringConverter;

public class GenereStringConverter extends StringConverter<Genere>
{

    @Override
    public String toString(Genere genere) {
        if(genere !=null)
        {
            return genere.getNomGenere();
        }
        else return null;
    }

    @Override
    public Genere fromString(String s) {
        return null;
    }

    /**
     * metode per tenir el id del genere
     * @param genere objecte gener
     * @return id genere , en cas que no -1
     */
    public int getIdGenere(Genere genere)
    {
        if(genere !=null)
        {
            return genere.getIdGenere();
        }
        else return -1;
    }
}
