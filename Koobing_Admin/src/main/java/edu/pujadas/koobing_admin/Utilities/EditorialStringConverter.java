package edu.pujadas.koobing_admin.Utilities;

import edu.pujadas.koobing_admin.Models.Editorial;
import javafx.util.StringConverter;

public class EditorialStringConverter extends StringConverter<Editorial> {
    @Override
    public String toString(Editorial editorial) {
        try
        {
            if(editorial != null)
            {
                return editorial.getNomEditor();
            }
        }
        catch (Exception e)
        {
            System.out.println("Error converting TO STRIGN Editorial: " +e.getMessage());
        }

        return null;
    }

    @Override
    public Editorial fromString(String s) {
        return null;
    }

    public int getIdEditor(Editorial editor)
    {
        try
        {
            if(editor !=null)
            {
                return editor.getIdEditorial();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return -1;
    }
}
