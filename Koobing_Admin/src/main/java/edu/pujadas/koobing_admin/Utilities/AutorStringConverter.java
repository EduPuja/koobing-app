package edu.pujadas.koobing_admin.Utilities;

import edu.pujadas.koobing_admin.Models.Autor;
import javafx.util.StringConverter;

public class AutorStringConverter extends StringConverter<Autor>
{
    @Override
    public String toString(Autor autor) {

        try
        {
            if(autor !=null)
            {
                return autor.getNomAutor();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public Autor fromString(String s) {

        return null;
    }

    public int getIdAutor(Autor autor)
    {
        try
        {
            if(autor !=null)
            {
                return autor.getIdAutor();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;

    }
}
