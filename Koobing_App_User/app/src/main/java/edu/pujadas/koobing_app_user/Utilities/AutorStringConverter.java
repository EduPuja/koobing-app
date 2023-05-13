package edu.pujadas.koobing_app_user.Utilities;

import edu.pujadas.koobing_admin.Models.Autor;
import javafx.util.StringConverter;

public class AutorStringConverter extends StringConverter<Autor>
{
    /**
     * Metode per convertir un autor amb el nom
     * @param autor Objecte autor
     * @return el nom del autor
     */
    @Override
    public String toString(Autor autor) {

        if(autor !=null)
        {
            return autor.getNomAutor();
        }
        else {
            return null;
        }



    }

    @Override
    public Autor fromString(String s) {

        return null;
    }

    /**
     * Metode que et retorna el id del autor
     * @param autor object autor
     * @return id , -1 si no el troba
     */
    public int getIdAutor(Autor autor)
    {
        if(autor !=null)
        {
            return autor.getIdAutor();
        }
        else
        {
            return -1;
        }

    }
}
