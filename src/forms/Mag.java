package forms;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public  class Mag extends Personatge {
    private String nom;

    public Mag(String nom, int vidas, int oro, List<String> objectes, String tipoPersonaje) {
        super(nom, vidas, oro, objectes,tipoPersonaje);
    }

    public Mag(String nom) {
        super(nom);
    }


    public void getNom (String nombre) {
        this.getNombre(nombre);
    }

    private void getNombre(String nombre) {

    }

    public void setNom(String nombre) {
        this.setNombre(nombre);
    }

    private void setNombre(String nombre) {
    }

    @Override
    public ImageIcon getIcon() {
        return null;
    }
}
