package edu.pujadas.koobing_app.Utilites;

import edu.pujadas.koobing_app.Models.Usuari;

public class UsuarioSingleton {
    private static UsuarioSingleton instance;
    private Usuari usuario;


    public static synchronized UsuarioSingleton getInstance() {
        if (instance == null) {
            instance = new UsuarioSingleton();
        }
        return instance;
    }

    public void setUsuario(Usuari usuario) {
        this.usuario = usuario;
    }

    public Usuari getUsuario() {
        return this.usuario;
    }
}
