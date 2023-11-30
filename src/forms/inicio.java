package forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class inicio {
    private static Personatge personatge;
    private JPanel panelinicio;

    private forms.PanelJuego panelJuego;
    private JButton magButton;
    private JButton sacerdotButton;
    private JButton guerrerButton;
    private JPanel banerInicio;
    private JTextArea elGuerreroVioletaTextArea;
    private JTextArea elSacerdoteDeLaTextArea;

    private String tipoPersonaje;
    private static JFrame frame;
    String nom;

    public inicio(Personatge personatge) {

        panelinicio.setPreferredSize(new Dimension(1000,800));
        banerInicio.setPreferredSize(new Dimension(200,150));
        guerrerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new BorderLayout());
                JLabel imagen = new JLabel(new ImageIcon("src/images/warrior/warrior_right.gif"));
                panel.add(imagen, BorderLayout.WEST);
                JTextField textField = new JTextField(10);
                panel.add(textField, BorderLayout.CENTER);

                // Muestra el cuadro de diálogo con el panel personalizado
                int result = JOptionPane.showConfirmDialog(panelinicio, panel, "Ingresa el nom del Guerrer:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                     nom = textField.getText();
                    Guerrer guerrer = new Guerrer(nom);
                    tipoPersonaje = "Guerrer";
                    frame.setVisible(false);

                    panelJuego = new PanelJuego(tipoPersonaje, nom, personatge);
                    JOptionPane.getRootFrame().dispose();

                }
            }
        });

        magButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new BorderLayout());
                JLabel imagen = new JLabel(new ImageIcon("src/images/wizard/wizard_right.gif"));
                panel.add(imagen, BorderLayout.WEST);
                JTextField textField = new JTextField(10);
                panel.add(textField, BorderLayout.CENTER);

                // Muestra el cuadro de diálogo con el panel personalizado
                int result = JOptionPane.showConfirmDialog(panelinicio, panel, "Ingresa el nom del Mag:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    nom = textField.getText();
                    Mag mag = new Mag(nom);

                    tipoPersonaje = "Mag";
                    panelJuego = new PanelJuego(tipoPersonaje,nom, personatge);
                    frame.setVisible(false);


                }
            }
        });



        sacerdotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new BorderLayout());
                JLabel imagen = new JLabel(new ImageIcon("src/images/priest/priest_right.gif"));
                panel.add(imagen, BorderLayout.WEST);
                JTextField textField = new JTextField(10);
                panel.add(textField, BorderLayout.CENTER);

                // Muestra el cuadro de diálogo con el panel personalizado
                int result = JOptionPane.showConfirmDialog(panelinicio, panel, "Ingresa el nom del Sacerdot:", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                     nom = textField.getText();
                    Sacerdot sacerdot = new Sacerdot(nom);

                    tipoPersonaje = "Sacerdot";
                    panelJuego = new PanelJuego(tipoPersonaje, nom, personatge);
                    frame.setVisible(false);


                }
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("Repte Joc de Rol");
        frame.setContentPane(new inicio(personatge).panelinicio);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);

        frame.setLocation(400,100);
        frame.setVisible(true);
// Obtén la imagen de fondo
        ImageIcon imagenFondo = new ImageIcon("src/images/fondoJugar.jpg");

// Crea un JLabel con la imagen de fondo
        JLabel labelFondo = new JLabel(imagenFondo);
        labelFondo.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        frame.getContentPane().add(labelFondo);

        frame.getContentPane().setComponentZOrder(labelFondo, 0);

        Toolkit pantalla = Toolkit.getDefaultToolkit();
        Image icono = pantalla.getImage("src/images/politecnics.png");
        frame.setIconImage(icono);

    }
}
