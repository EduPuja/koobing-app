
package edu.pujadas.koobing_app.Utilities;
import edu.pujadas.koobing_admin.Models.Treballador;

public class TrabajadorSingleton {
    private static TrabajadorSingleton instance = null;
    private Treballador trabajador;

    private TrabajadorSingleton() {}

    public static TrabajadorSingleton getInstance() {
        if (instance == null) {
            instance = new TrabajadorSingleton();
        }
        return instance;
    }

    public void setTrabajador(Treballador trabajador) {
        this.trabajador = trabajador;
    }

    public Treballador getTrabajador() {
        return this.trabajador;
    }
}


