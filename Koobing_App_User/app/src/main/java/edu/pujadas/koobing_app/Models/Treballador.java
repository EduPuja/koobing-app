package edu.pujadas.koobing_app.Models;



import java.sql.Blob;
import java.sql.Date;


public class Treballador extends Persona
{
    private String numSegSocial;
    private boolean isAdmin;


    public Treballador()
    {

    }
    public Treballador(int idTreballador, String dniWork, Blob avatarWorker, String nomWorker, String cognomTreballador, Date dataNaixWorker, String emailWorker, String passwordWorker,
                       String numSegSocial, boolean isAdmin)
    {


        super(idTreballador,dniWork, avatarWorker, nomWorker, cognomTreballador, dataNaixWorker, emailWorker, passwordWorker);
        this.numSegSocial = numSegSocial;
        this.isAdmin = isAdmin;

    }

    public String getNumSegSocial()
    {
        return numSegSocial;
    }

    public void setNumSegSocial(String numSegSocial)
    {
        this.numSegSocial = numSegSocial;
    }


    /**
     * Metode que comprova si el valor boolean que li passen es true o false
     * @return en cas de que sigui true et retorna 1 si es true, 0 si es false.
     */
    public int isAdmin()
    {
        if(this.isAdmin)
        {
            return 1;
        }
        else return 0;

    }

    public void setAdmin(boolean admin)
    {
        isAdmin = admin;
    }
}
