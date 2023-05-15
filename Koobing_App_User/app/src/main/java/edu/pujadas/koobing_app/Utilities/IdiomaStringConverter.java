package edu.pujadas.koobing_app.Utilities;

import edu.pujadas.koobing_admin.Models.Idioma;
import javafx.util.StringConverter;

public class IdiomaStringConverter extends StringConverter<Idioma> {
    @Override
    public String toString(Idioma idioma) {
        if(idioma!=null)
        {
            return idioma.getNomIdioma();
        }
        else return null;
    }

    @Override
    public Idioma fromString(String s) {
        return null;
    }

    public int getIdIdioma(Idioma idioma)
    {
        if (idioma!=null)
        {
            return idioma.getIdIdioma();
        }
        else return -1;
    }
}
