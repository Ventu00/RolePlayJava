package forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Personatge extends JLabel {
    private Hasperdido hasperdido;

    private ImageIcon imageIconUp;
    private ImageIcon imageIconDown;
    private ImageIcon imageIconLeft;
    private ImageIcon imageIconRight;
    private int vidas;
    private int oro;
    /**
     * Constructor de la clase Personatge.
     *
     * @param nom           El nombre del personaje.
     * @param vidas         El número de vidas del personaje.
     * @param oro           La cantidad de oro del personaje.
     * @param objectes      La lista de objetos del personaje.
     * @param tipoPersonaje El tipo de personaje ("Guerrer", "Mag" o "Sacerdot").
     */
    public Personatge(String nom, int vidas, int oro, List<String> objectes, String tipoPersonaje) {
        int anchoPersonaje = 64;
        int altoPersonaje = 64;

        // Cargar los GIFs según el tipo de personaje
        switch (tipoPersonaje) {
            case "Guerrer":
                imageIconUp = new ImageIcon("src/images/warrior/warrior_up.gif");
                imageIconDown = new ImageIcon("src/images/warrior/warrior_down.gif");
                imageIconLeft = new ImageIcon("src/images/warrior/warrior_left.gif");
                imageIconRight = new ImageIcon("src/images/warrior/warrior_right.gif");
                break;
            case "Mag":
                imageIconUp = new ImageIcon("src/images/wizard/wizard_up.gif");
                imageIconDown = new ImageIcon("src/images/wizard/wizard_down.gif");
                imageIconLeft = new ImageIcon("src/images/wizard/wizard_left.gif");
                imageIconRight = new ImageIcon("src/images/wizard/wizard_right.gif");
                break;
            case "Sacerdot":
                imageIconUp = new ImageIcon("src/images/priest/priest_up.gif");
                imageIconDown = new ImageIcon("src/images/priest/priest_down.gif");
                imageIconLeft = new ImageIcon("src/images/priest/priest_left.gif");
                imageIconRight = new ImageIcon("src/images/priest/priest_right.gif");
                break;
        }


        Image imagenUp = imageIconUp.getImage().getScaledInstance(anchoPersonaje, altoPersonaje, Image.SCALE_DEFAULT);
        imageIconUp = new ImageIcon(imagenUp);

        Image imagenDown = imageIconDown.getImage().getScaledInstance(anchoPersonaje, altoPersonaje, Image.SCALE_DEFAULT);
        imageIconDown = new ImageIcon(imagenDown);

        Image imagenLeft = imageIconLeft.getImage().getScaledInstance(anchoPersonaje, altoPersonaje, Image.SCALE_DEFAULT);
        imageIconLeft = new ImageIcon(imagenLeft);

        Image imagenRight = imageIconRight.getImage().getScaledInstance(anchoPersonaje, altoPersonaje, Image.SCALE_DEFAULT);
        imageIconRight = new ImageIcon(imagenRight);



        setIcon(imageIconDown);
        setLocation(0, 0);
        setBounds(0, 0, anchoPersonaje, altoPersonaje);

        this.vidas = vidas;

    }

    public Personatge(String nom) {
    }


    public void moverDerecha(int paso) {
        int x = getX() + paso;
        int limiteDerecho = getParent().getWidth() - getWidth();
        if (x > limiteDerecho) {
            x = limiteDerecho;
        }
        setLocation(x, getY());
        setIcon(imageIconRight);
    }

    public void moverIzquierda(int paso) {
        int x = getX() - paso;
        if (x < 0) {
            x = 0;
        }
        setLocation(x, getY());
        setIcon(imageIconLeft);
    }

    public void moverArriba(int paso) {
        int y = getY() - paso;
        if (y < 0) {
            y = 0;
        }
        setLocation(getX(), y);
        setIcon(imageIconUp);
    }

    public void moverAbajo(int paso) {
        int y = getY() + paso;
        int limiteAbajo = getParent().getHeight() - getHeight();
        if (y > limiteAbajo) {
            y = limiteAbajo;
        }
        setLocation(getX(), y);
        setIcon(imageIconDown);
    }

    private boolean haTenidoContacto = false;

    public void restarVida() {
        if (!haTenidoContacto) {
            vidas--;
            haTenidoContacto = true;
//restablecer variable
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    haTenidoContacto = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


    public void sumarvida(Personatge mago) {
        vidas++;
    }

    public int getVidas() {
        return vidas;
    }



    public int getOro() {
        return oro;
    }

    public int getVides() {
        return vidas;
    }

    public void setVides(int vides) {
        this.vidas = vides;
    }


    public class PersonajeKeyListener extends KeyAdapter {
        private int paso;

        public PersonajeKeyListener(int paso) {
            this.paso = paso;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    moverDerecha(paso);
                    break;
                case KeyEvent.VK_LEFT:
                    moverIzquierda(paso);
                    break;
                case KeyEvent.VK_UP:
                    moverArriba(paso);
                    break;
                case KeyEvent.VK_DOWN:
                    moverAbajo(paso);
                    break;
            }
        }
    }
}
