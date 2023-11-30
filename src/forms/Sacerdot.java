package forms;

import java.util.ArrayList;
import java.util.List;

public  class Sacerdot extends Personatge {
    private boolean sacerdot;

    public Sacerdot(String nom, int vidas, int oro, List<String> objectes, String tipoPersonaje) {
        super(nom, vidas, oro, objectes,tipoPersonaje);
    }

    public Sacerdot(String nom) {
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

}
