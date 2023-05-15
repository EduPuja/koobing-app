package edu.pujadas.koobing_app.Database;

import edu.pujadas.koobing_app.Database.ConnexioMYSQL;
import edu.pujadas.koobing_app.Models.Idioma;

import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.Statement;
import java.util.ArrayList;


public class GestioIdioma
{
    public void crearIdioma(Idioma idioma)
    {
        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat  = con.conectar();
            String sql = "INSERT INTO idioma (nom_idioma) values ( '"+idioma.getNomIdioma()+"')";
            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("Idioma inserted successfully");
            }
            else System.out.println("Idioma not inserted");
            con.desconectar();
        }
        catch (Exception e) {
            System.out.println("Error inserting Idioma: " +e.getMessage());
        }
    }

    public void eliminarIdioma(int idIdioma)
    {
        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql = "DELETE FROM idioma WHERE id_idioma = "+idIdioma;

            if(stat.executeUpdate(sql) == 1)
            {
                System.out.println("Idioma eliminat correctament");
            }
            else System.out.println("Idioma not deleted");
            con.desconectar();
        }
        catch (Exception e) {
            System.out.println("Error deleting a idioma : " +e.getMessage());
        }
    }
    public void modificarIdioma(Idioma i)
    {
        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement statement = con.conectar();
            String sql = "UPDATE idioma SET nom_idioma = '"+i.getNomIdioma()+"' WHERE id_idioma = "+i.getIdIdioma();

            if(statement.executeUpdate(sql) == 1)
            {
                System.out.println("Idioma actualizat correctament");
            }
            else System.out.println("Idioma not updated correctly");
            con.desconectar();
        }
        catch (Exception e) {
            System.out.println("Error modfy the idioma :" +e.getMessage());
        }
    }
    public ArrayList<Idioma> consultarIdiomes()
    {
        ArrayList<Idioma> idiomas = new ArrayList<>();

        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM idioma");
            while (rs.next())
            {
                idiomas.add(new Idioma(rs.getInt("id_idioma"),rs.getString("nom_idioma")));
            }
            con.desconectar();
            return idiomas;
        }
        catch (Exception e)
        {
            System.out.println("Error consultar idiomes: " + e.getMessage());
        }

        return null;
    }

    /**
     * Metodo que busca el idioma d'una base de datos ✅'
     * @param idIdioma identificador de la base de datos
     * @return Idioma object
     */
    public Idioma findIdioma(int idIdioma)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery("SELECT * FROM idioma WHERE id_idioma =" + idIdioma);
            if (rs.next())
            {
                Idioma idioma =new Idioma(rs.getInt("id_idioma"),rs.getString("nom_idioma"));
                con.desconectar();
                return idioma;
            }
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error consultar idioma: " + e.getMessage());
        }
        return null;
    }

  public boolean isIdiomaInBook(int idIdioma)
  {
      try
      {
          ConnexioMYSQL con = new ConnexioMYSQL();
          Statement stat = con.conectar();

          String query = "SELECT COUNT(*) AS count FROM llibre WHERE id_idioma="+ idIdioma;
          ResultSet rs = stat.executeQuery(query);
          if(rs.next())
          {
              int count = rs.getInt("count");
              if(count>0)
              {
                  return true;
              }
          }
      }
      catch (Exception e)
      {
          System.out.println("Error buscat idioma en el llibre");
      }

      return false;
  }

}
