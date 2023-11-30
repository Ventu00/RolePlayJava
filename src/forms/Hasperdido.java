package forms;

import javax.swing.*;
import java.awt.*;

public class Hasperdido {
    private JPanel hasperdido;
    private JButton jugarDeNuevoButton;
    private forms.PanelJuego panelJuego;

    private JFrame frame;
    public Hasperdido() {
        frame = new JFrame("Game Over");
        frame.setPreferredSize(new Dimension(700, 500));
        hasperdido.setPreferredSize(new Dimension(700, 500));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        frame.setVisible(true);
        hasperdido.setVisible(true);
        frame.add(hasperdido);


    }
}
