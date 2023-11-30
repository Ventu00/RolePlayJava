package forms;

import javax.swing.*;
import java.awt.*;
public class Hasganado {
    private JPanel panel1;
    private JPanel hasganado;
    private forms.PanelJuego panelJuego;

    public Hasganado() {
        JFrame frame = new JFrame("Game Over");
        frame.setPreferredSize(new Dimension(700, 500));
        panel1.setPreferredSize(new Dimension(700, 500));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        frame.setVisible(true);
        panel1.setVisible(true);
        frame.add(panel1);


    }
}
