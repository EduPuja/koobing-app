package edu.pujadas.koobing_admin.Utilities;

import edu.pujadas.koobing_admin.Models.Autor;
import javafx.util.StringConverter;

public class AutorStringConverter extends StringConverter<Autor>
{
    @Override
    public String toString(Autor autor) {
        return autor.getNomAutor();
    }

    @Override
    public Autor fromString(String s) {
    
        return null;
    }

    public int getIdAutor(Autor autor)
    {
        return autor.getIdAutor();
    }
}
