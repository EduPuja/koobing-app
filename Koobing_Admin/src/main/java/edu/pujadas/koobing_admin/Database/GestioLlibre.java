package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

// Gestio Llibre done ✅
public class GestioLlibre
{


    /**
     * Metode per crear un llibre
     * @param llibre Objecte llibre
     */
    public void crearLlibre(Llibre llibre)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();

            String sql = "INSERT INTO `llibre`(`ISBN`, `id_autor`, `id_editor`, `id_idioma`, `id_genere`, `titol`, `versio`, `data_publi`, `stock`) " +
                    "VALUES ('"+llibre.getISBN()+"','"+llibre.getAutor().getIdAutor()+"','"+llibre.getEditor().getIdEditorial()+"','"+llibre.getIdioma().getIdIdioma()+"','"+llibre.getGenere().getIdGenere()+"','"+llibre.getTitol()+"','"+llibre.getVersio()+"','"+llibre.getDataPubli()+"','"+llibre.getStock()+"')";

            if(stat.executeUpdate(sql) ==1)
            {
                System.out.println("Llibre insertado correctamente");
            }
            else System.out.println("Llibre no insertado correctamente");

            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error LLibre:"+ e.getMessage());
        }
    }

    /**
     * Metode per modificar un de la base de dades
     * @param llibre Objecte llibre
     */
    public void modificarLlibre(Llibre llibre)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
           String sql = "UPDATE `llibre` SET `id_autor`='"+llibre.getAutor().getIdAutor()+"',`id_editor`='"+llibre.getEditor().getIdEditorial()+"'," +
                   "`id_idioma`='"+llibre.getIdioma().getIdIdioma()+"',`id_genere`='"+llibre.getGenere().getIdGenere()+"',`titol`='"+llibre.getTitol()+"',`versio`='"+llibre.getVersio()+"',`" +
                   "data_publi`='"+llibre.getDataPubli()+"',`stock`='"+llibre.getStock()+"' WHERE ISBN= "+llibre.getISBN();
            if(stat.executeUpdate(sql) ==1)
            {
                System.out.println("Llibre modificat correctament");
            }

            else System.out.println("Llibre not updated");
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error LLibre Modify :"+ e.getMessage());
        }
    }

    /**
     * Metode per poder eliminar un llibre de la base de dades
     * @param ISBN long ISBN del llibre a eliminar
     */
    public void eliminarLlibre(long ISBN)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();


            if(this.existeLlibre(ISBN))
            {
                String sql = "DELETE FROM `llibre` WHERE ISBN='"+ISBN+"'";
                if(stat.executeUpdate(sql) == 1)
                {
                    System.out.println("Llibre eliminado correctamente");
                }
                else System.out.println("Llibre no eliminado correctamente");
                con.desconectar();
            }
            else{
                System.out.println("Llibre Not found");
                con.desconectar();
            }

        }
        catch (Exception e)
        {
            System.out.println("Error in Eliminar LLibre:"+ e.getMessage());

        }
    }

    /**
     * Metode que et consulta tots els llibres de la base de dades qu tenen el stock mes gran que 0
     * @return ArrayList de llibres
     */
    public ArrayList<Llibre> consultarLlibresAmbStock()
    {
        ArrayList<Llibre> llibresList = new ArrayList<Llibre>();


        //gestions
        GestioAutor gestioAutor = new GestioAutor();
        GestioEditorial gestioEditorial = new GestioEditorial();
        GestioIdioma gestioIdioma = new GestioIdioma();
        GestioGenere gestioGenere = new GestioGenere();
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();



            Statement stat = con.conectar();


            String query2 = "SELECT * from llibre where stock >0";


            ResultSet rs = stat.executeQuery(query2);

            while (rs.next())
            {
                Llibre llibre = new Llibre();
                // isbn
                llibre.setISBN(rs.getLong("ISBN"));

                //buscant el autor , editorials, idioma and genere per poder crear un objeto de cada

                Autor autor = gestioAutor.findAutor(rs.getInt("id_autor"));
                Editorial editor =gestioEditorial.findEditorial(rs.getInt("id_editor"));
                Idioma idioma = gestioIdioma.findIdioma(rs.getInt("id_idioma"));
                Genere genere = gestioGenere.findGenere(rs.getInt("id_genere"));

                //afegint tot les dades el objecte llibre
                llibre.setAutor(autor);
                llibre.setEditor(editor);
                llibre.setIdioma(idioma);
                llibre.setGenere(genere);

                //afegint les dades que falten
                llibre.setTitol(rs.getString("titol"));
                llibre.setVersio(rs.getInt("versio"));
                llibre.setDataPubli(rs.getDate("data_publi"));

                llibre.setStock(rs.getInt("stock"));

                //afegint tots els llibres al arraylist
                llibresList.add(llibre);
            }


            con.desconectar();

            return llibresList;

        }
        catch (Exception e)
        {
            System.out.println("Hi ha hagut algun error" + e.getMessage());
        }
        return null;
    }

    /**
     * Metode per consultar els llibres que el seu stock = 0
     * @return arraylist de llibres amb stock 0
     */
    public ArrayList<Llibre> consultarLlibresOutStock()
    {
        ArrayList<Llibre> llibresList = new ArrayList<Llibre>();


        //gestions
        GestioAutor gestioAutor = new GestioAutor();
        GestioEditorial gestioEditorial = new GestioEditorial();
        GestioIdioma gestioIdioma = new GestioIdioma();
        GestioGenere gestioGenere = new GestioGenere();
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String query = "SELECT * from llibre where stock =0";

            ResultSet rs = stat.executeQuery(query);

            while (rs.next())
            {
                Llibre llibre = new Llibre();
                // isbn
                llibre.setISBN(rs.getLong("ISBN"));

                //buscant el autor , editorials, idioma and genere per poder crear un objeto de cada

                Autor autor = gestioAutor.findAutor(rs.getInt("id_autor"));
                Editorial editor =gestioEditorial.findEditorial(rs.getInt("id_editor"));
                Idioma idioma = gestioIdioma.findIdioma(rs.getInt("id_idioma"));
                Genere genere = gestioGenere.findGenere(rs.getInt("id_genere"));

                //afegint tot les dades el objecte llibre
                llibre.setAutor(autor);
                llibre.setEditor(editor);
                llibre.setIdioma(idioma);
                llibre.setGenere(genere);

                //afegint les dades que falten
                llibre.setTitol(rs.getString("titol"));
                llibre.setVersio(rs.getInt("versio"));
                llibre.setDataPubli(rs.getDate("data_publi"));
                llibre.setStock(rs.getInt("stock"));

                //afegint tots els llibres al arraylist
                llibresList.add(llibre);
            }


            con.desconectar();

            return llibresList;

        }
        catch (Exception e)
        {
            System.out.println("Error consultat llibres wihtout stock: " + e.getMessage());
        }
        return null;
    }





    /**
     * Metode que et retorna nomes els 10 primers llibres de la base de dades
     * @return ArrayList dels 10 primers llibres
     */
    public ArrayList<Llibre> conusltar10Llibres()
    {
        ArrayList<Llibre> llibresList = new ArrayList<Llibre>();


        //gestions
        GestioAutor gestioAutor = new GestioAutor();
        GestioEditorial gestioEditorial = new GestioEditorial();
        GestioIdioma gestioIdioma = new GestioIdioma();
        GestioGenere gestioGenere = new GestioGenere();


        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();



           Statement stat = con.conectar();

            String query2 = "SELECT * from llibre where stock >0 limit 10";


            ResultSet rs = stat.executeQuery(query2);

            while (rs.next())
            {
                Llibre llibre = new Llibre();
                // isbn
                llibre.setISBN(rs.getLong("ISBN"));



                Autor autor = gestioAutor.findAutor(rs.getInt("id_autor"));
                Editorial editor =gestioEditorial.findEditorial(rs.getInt("id_editor"));
                Idioma idioma = gestioIdioma.findIdioma(rs.getInt("id_idioma"));
                Genere genere = gestioGenere.findGenere(rs.getInt("id_genere"));

                //afegint tot les dades el objecte llibre
                llibre.setAutor(autor);
                llibre.setEditor(editor);
                llibre.setIdioma(idioma);
               llibre.setGenere(genere);

                //afegint les dades que falten
                llibre.setTitol(rs.getString("titol"));
                llibre.setVersio(rs.getInt("versio"));
                llibre.setDataPubli(rs.getDate("data_publi"));
                llibre.setStock(rs.getInt("stock"));

                //afegint tots els llibres al arraylist
                llibresList.add(llibre);
            }


            con.desconectar();

            return llibresList;

        }
        catch (Exception e)
        {
            System.out.println("Hi ha hagut algun error" + e.getMessage());
        }
        return null;
    }

    /**
     * Metode per trobar un llibre en concret de la base de dades
     * @param ISBN numero de llibre
     * @return Objecte Llibre
     */
    public Llibre findLlibreByISBN(long ISBN)
    {

        try {

            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();

            String query = "SELECT * from llibre WHERE ISBN='"+ISBN+"'";
            ResultSet rs = stat.executeQuery(query);

            if(rs.next())
            {
                Llibre llibre = new Llibre();

                llibre.setISBN(rs.getLong("ISBN"));

                // buscant la informacio gracies els gestiors
                Autor autor = new GestioAutor().findAutor(rs.getInt("id_autor"));
                Editorial editor =new GestioEditorial().findEditorial(rs.getInt("id_editor"));
                Idioma idioma = new GestioIdioma().findIdioma(rs.getInt("id_idioma"));
                Genere genere = new GestioGenere().findGenere(rs.getInt("id_genere"));

                //construinet el llibre
                llibre.setAutor(autor);
                llibre.setEditor(editor);
                llibre.setIdioma(idioma);
                llibre.setGenere(genere);
                llibre.setTitol(rs.getString("titol"));
                llibre.setVersio(rs.getInt("versio"));
                llibre.setDataPubli(rs.getDate("data_publi"));

                return llibre;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public boolean existeLlibre(long ISBN) {
        boolean existe = false;
        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql = "SELECT COUNT(*) AS count FROM llibre WHERE ISBN='"+ISBN+"'";
            ResultSet result = stat.executeQuery(sql);
            if(result.next()) {
                int count = result.getInt("count");
                if(count > 0) {
                    existe = true;
                }
            }
            con.desconectar();
        } catch(Exception e) {
            System.out.println("Error al comprobar si el libro existe: " + e.getMessage());
        }
        return existe;
    }


    /**
     * Metode per comprovar el estat de la reserva del llibre
     * @param ISBN numero de llibre
     * @return retrna -1 en cas d'error , 1 en cas de que "no tornat" , 2
     */
    public int getEstadoLlibre(long ISBN) {

        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql = "SELECT id_estat from reserva where  ISBN = " + ISBN;
            ResultSet result = stat.executeQuery(sql);
            if(result.next())
            {
                int valorEstat = result.getInt("id_estat");
                return valorEstat;


            }
            con.desconectar();
        } catch(Exception e) {
            System.out.println("Error al comprobar si el libro existe: " + e.getMessage());

        }
        return -1;
    }

    public boolean canBeDeleted(long isbn)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            String sql = "SELECt count(id_estat) from reserva where ISBN ="+isbn;
            Statement stat = con.conectar();
            ResultSet rs = stat.executeQuery(sql);

            if(rs.next())
            {
                int count = rs.getInt("count(id_estat)");
                if (count >0)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }


        }
        catch (Exception e)
        {
            System.out.println("Error Checking the delete value :" +e.getMessage());
        }
        return false;
    }


}
