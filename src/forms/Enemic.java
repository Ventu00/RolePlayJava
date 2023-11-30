package forms;

import javax.swing.*;
import java.awt.*;

public class Enemic {
    private int x;
    private int y;
    private int direccionX;
    private int direccionY;
    private int velocidad;
    private ImageIcon[] imagenes;
    private int indiceImagen;
    private int anchoPantalla;
    private boolean subiendo;
    private boolean yendo;
    private int width;
    private int height;

    public Enemic(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.direccionX = 0;
        this.direccionY = 5;
        this.velocidad = 5;
        this.indiceImagen = 0;
        this.anchoPantalla = width;
        this.subiendo = false;
        this.yendo=false;
        this.width = 64;
        this.height = 64;

        // Cargar las imágenes del enemigo
        imagenes = new ImageIcon[4];
        imagenes[0] = new ImageIcon("src/images/skeleton/skeleton_right.gif");
        imagenes[1] = new ImageIcon("src/images/skeleton/skeleton_left.gif");
        imagenes[2] = new ImageIcon("src/images/skeleton/skeleton_up.gif");
        imagenes[3] = new ImageIcon("src/images/skeleton/skeleton_down.gif");
    }

    public void desaparecer(Object enemic){
        x = -getWidth();
        y = -getHeight();
    }
    public void mover() {
        // Actualizar la posición en función de la dirección y la velocidad
        x += direccionX * velocidad;

        if (subiendo) {
            y -= velocidad; // Mover hacia arriba
            indiceImagen = 2;
        } else {
            y += velocidad; // Mover hacia abajo
            indiceImagen = 3;
        }

        // Comprobar si ha llegado al borde inferior de la pantalla
        if (y + height >= 400) {
            y = 400 - height;
            cambiarDireccion();
            subiendo = true; // Comienza a subir
        }

        // Comprobar si ha llegado al borde superior
        if (y <= 0 && subiendo) {
            y = 0;
            cambiarDireccion();
            subiendo = false; // Deja de subir y  baja
        }
    }

    public void moverH() {
        // Actualizar la posición en función de la dirección y la velocidad
        y += direccionX * velocidad;

        if (yendo) {
            x -= velocidad;
            indiceImagen = 1;
        } else {
            x += velocidad;
            indiceImagen = 0;
        }

        // Comprobar si ha llegado al borde inferior de la pantalla y cambiar de dirección
        if (x + height >= 880) {
            x = 880 - height; // evitar que el enemigo se desplace más allá de la parte inferior
            cambiarDireccionH();
            yendo = true; // Comienza a subir
        }

        // Comprobar si ha llegado al borde superior de la pantalla y cambiar de dirección
        if (x <= 0 && yendo) {
            x = 0; // Evita que el enemigo se desplace más allá de la parte superior
            cambiarDireccionH();
            yendo = false; // Deja de subir y vuelve a bajar
        }
    }

    public void cambiarDireccionH() {
        // Invertir la dirección horizontal
        direccionX = -direccionX;
    }




    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), 10, 10);
    }



    private void cambiarDireccion() {
        direccionY = -direccionY; // Cambiar la dirección horizontal
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ImageIcon getImagen() {
        return imagenes[indiceImagen];
    }
    

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
