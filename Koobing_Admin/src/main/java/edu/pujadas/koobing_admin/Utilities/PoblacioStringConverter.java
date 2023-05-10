package edu.pujadas.koobing_admin.Utilities;

import edu.pujadas.koobing_admin.Models.Poblacio;
import javafx.util.StringConverter;

public class PoblacioStringConverter extends StringConverter<Poblacio> {
    @Override
    public String toString(Poblacio poblacio) {
        if(poblacio !=null)
        {
            return poblacio.getNomPoble();
        }
        else return null;
    }

    @Override
    public Poblacio fromString(String s) {
        return null;
    }

    public int getIdPoblacio(Poblacio poblacio)
    {
        if(poblacio != null)
        {
            return  poblacio.getIdPoblacio();
        }
        else  return -1;
    }
}
