package edu.pujadas.koobing_admin.Utilities;

import edu.pujadas.koobing_admin.Models.Usuari;
import javafx.util.StringConverter;

public class UsuariStringConverter extends StringConverter<Usuari> {
    @Override
    public String toString(Usuari usuari) {
        if(usuari !=null)
        {
            return usuari.getDni()+" | "+usuari.getNom()+" "+usuari.getCognom();
        }
        return null;
    }

    @Override
    public Usuari fromString(String s) {
        return null;
    }

    /**
     * Metode per retornar el id del usuari en cas negatiu retorna -1
     * @param usuari objecte usuari
     * @return id del usuari
     */
    public int getIdUsuari(Usuari usuari) {
        if(usuari !=null)
        {
            return usuari.getId();
        }
        return -1;
    }
}
