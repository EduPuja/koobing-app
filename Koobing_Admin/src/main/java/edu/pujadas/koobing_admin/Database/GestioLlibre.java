package edu.pujadas.koobing_admin.Database;

import edu.pujadas.koobing_admin.Models.*;

import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.Statement;
import java.util.ArrayList;

// Gestio Llibre done ✅
public class GestioLlibre
{


    /**
     * Metode per crear un llibre
     * @param l Objecte llibre
     */
    public void crearLlibre(Llibre l)
    {
        try
        {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();

            String sql = "INSERT INTO `llibre`(`ISBN`, `id_autor`, `id_editor`, `id_idioma`, `id_genere`, `titol`, `versio`, `data_publi`) " +
                    "VALUES ('"+l.getISBN()+"','"+l.getAutor().getIdAutor()+"','"+l.getEditor().getIdEditorial()+"','"+l.getIdioma().getIdIdioma()+"','"+l.getGenere().getIdGenere()+"','"+l.getTitol()+"','"+l.getVersio()+"','"+l.getDataPubli()+"')";

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
            String sql = "UPDATE `llibre` SET `ISBN`='"+llibre.getISBN()+"',`id_autor`='"+llibre.getAutor().getIdAutor()+"'," +
                    "`id_editor`='"+llibre.getEditor().getIdEditorial()+"',`id_idioma`='"+llibre.getIdioma().getIdIdioma()+"'," +
                    "`id_genere`='"+llibre.getGenere().getIdGenere()+"',`titol`='"+llibre.getTitol()+"',`versio`='"+llibre.getVersio()+"',`data_publi`='"+llibre.getDataPubli()+"' WHERE ISBN='"+llibre.getISBN()+"'";

            if(stat.executeUpdate(sql) ==1)
            {
                System.out.println("Llibre modificat correctament");
            }

            else System.out.println("Llibre not updated");
            con.desconectar();
        }
        catch (Exception e)
        {
            System.out.println("Error LLibre:"+ e.getMessage());
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
     * Metode que et consulta tots els llibres de la base de dades
     * @return ArrayList de llibres
     */
    public ArrayList<Llibre>consultarLlibres()
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


            String query2 = "SELECT * from llibre";


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

            /*String query = "SELECT l.isbn,a.nom_autor,e.nom_editorial,i.nom_idioma,g.descrip,l.titol,l.versio,l.data_publi FROM llibre l " +
                    "INNER JOIN autor a ON a.id_autor = l.id_autor INNER JOIN editorial e ON e.id_editorial = l.id_editor " +
                    "INNER JOIN idioma i ON i.id_idioma = l.id_idioma INNER JOIN genere g ON g.id_genere = l.id_genere  Limit 10";*/

            String query2 = "SELECT * from llibre limit 10";


            ResultSet rs = stat.executeQuery(query2);

            while (rs.next())
            {
                Llibre llibre = new Llibre();
                // isbn
                llibre.setISBN(rs.getLong("ISBN"));

                //buscant el autor , editorials, idioma and genere per poder crear un objeto de cada
                //System.out.println("Buscant Autors ... editorials etc..\n");

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
    public Llibre findLLibre(long ISBN)
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
     * Metode per comprobar si hi ha alguna reserva activa
     * @param ISBN
     * @return
     */
    public boolean hayReservasActivas(long ISBN) {
        boolean existe = false;
        try {
            ConnexioMYSQL con = new ConnexioMYSQL();
            Statement stat = con.conectar();
            String sql = "SELECT COUNT(*) AS count FROM reserves WHERE ISBN='"+ISBN+"'";
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
}
