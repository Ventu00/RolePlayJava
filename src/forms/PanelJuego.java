package forms;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.io.IOException;
        import java.nio.file.Files;
        import java.nio.file.Paths;
        import java.nio.file.StandardOpenOption;
        import java.sql.*;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Random;
public class PanelJuego {
    int maxoro = 4;

     int[] orocontador = {0};
    private Personatge mago;
    private Personatge sacerdot;
    private Personatge guerrer;
    private Enemic enemic;
    private Enemic enemic2;
    private Enemic enemic3;

    private JPanel panelTitle;
   public JFrame frame;
    private List<String> listaObjetos;

    private int vidas;
    private int oro;

    private JLabel vidasLabel;
    private JLabel oroLabel;

    private JLabel salida;
    private forms.Hasperdido hasperdido;
    private forms.Hasganado hasganado;


    ImageIcon fondoMenu;
    private JLabel nombreLabel;
    private JLabel objetosLabel;
    private String mitraN = "Mitra";
    private String espadaN = "Espada";
    private String pocionN = "Pocion";
    private boolean tiene = false;
    private Enemic enemic4;
    private Enemic enemic5;
    private Enemic enemic6;
    private JLabel labelTime;
    private int seconds = 0;
    private String dades;

    /**
     * Panel de juego que muestra el entorno de juego y los elementos gráficos.
     *
     * @param tipoPersonaje El tipo de personaje del jugador.
     * @param nom El nombre del jugador.
     * @param personatge El personaje del jugador.
     */
    public PanelJuego(String tipoPersonaje, String nom, Personatge personatge) {
        frame = new JFrame("Panel de juego");
        frame.setPreferredSize(new Dimension(1000, 650));
        frame.setSize(new Dimension(1000, 650));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setBackground(Color.black);

        listaObjetos = new ArrayList<String>();
        objetosLabel = new JLabel();

        ImageIcon fondoIcon = new ImageIcon("src/images/dungeon/tile001.png");
        Image fondoImagen = fondoIcon.getImage();

        JPanel panelJuego = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Dibujar el fondo del panel de juego
                int ancho = getWidth();
                int alto = getHeight();
                int anchoDibujo = 34;
                int altoDibujo = 34;

                for (int x = 0; x < ancho; x += anchoDibujo) {
                    for (int y = 0; y < alto; y += altoDibujo) {
                        g.drawImage(fondoImagen, x, y, anchoDibujo, altoDibujo, this);
                    }
                }

                // Dibujar los enemigos si existen
                if (enemic != null) {
                    g.drawImage(enemic.getImagen().getImage(), enemic.getX(), enemic.getY(), null);
                    g.drawImage(enemic2.getImagen().getImage(), enemic2.getX(), enemic2.getY(), null);
                    g.drawImage(enemic3.getImagen().getImage(), enemic3.getX(), enemic3.getY(), null);

                    g.drawImage(enemic4.getImagen().getImage(), enemic4.getX(), enemic4.getY(), null);
                    g.drawImage(enemic5.getImagen().getImage(), enemic5.getX(), enemic5.getY(), null);
                    g.drawImage(enemic6.getImagen().getImage(), enemic6.getX(), enemic6.getY(), null);
                }
            }
        };


        panelJuego.setLayout(null);
        listaObjetos = new ArrayList<String>();
        oro(panelJuego);
        mitra(panelJuego);
        pocion(panelJuego);
        espada(panelJuego);
        salida(panelJuego, tipoPersonaje, nom, orocontador, maxoro);


/**
 * Crea y configura un personaje en función del tipo de personaje especificado.
 *
 * @param tipoPersonaje El tipo de personaje del jugador.
 * @param nom El nombre del jugador.
 */
        switch (tipoPersonaje) {
            case "Guerrer":
                vidas = 5;
                guerrer = new Personatge(nom, vidas, oro, listaObjetos, "Guerrer");
                guerrer.setLocation(0, 0);
                guerrer.setBounds(-18, -15, 64, 64);
                panelJuego.add(guerrer, 1);
                panelJuego.addKeyListener(guerrer.new PersonajeKeyListener(3));
                break;

            case "Mag":
                vidas = 3;
                mago = new Personatge(nom, vidas, oro, listaObjetos, "Mag");
                panelJuego.add(mago, 1);
                panelJuego.addKeyListener(mago.new PersonajeKeyListener(7));
                mago.setLocation(0, 0);
                mago.setBounds(-18, -15, 64, 64);
                panelJuego.setLayout(null);
                break;

            case "Sacerdot":
                vidas = 4;
                sacerdot = new Personatge(nom, vidas, oro, listaObjetos, "Sacerdot");
                panelJuego.add(sacerdot, 1);
                panelJuego.addKeyListener(sacerdot.new PersonajeKeyListener(5));
                sacerdot.setLocation(0, 0);
                sacerdot.setBounds(-18, -15, 64, 64);
                panelJuego.setLayout(null);
                break;
        }

        creacionDelmapaJuego(panelJuego,nom,tipoPersonaje);


        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (enemic == null) {
                    enemic = new Enemic(100, 0, panelJuego.getWidth());
                    enemic2 = new Enemic(430, 100, panelJuego.getWidth());
                    enemic3 = new Enemic(700, 200, panelJuego.getWidth());

                    // Añadir tres nuevos enemigos con trayectoria horizontal
                    enemic4 = new Enemic(50, 100, panelJuego.getWidth());
                    enemic5 = new Enemic(100, 220, panelJuego.getWidth());
                    enemic6 = new Enemic(200, 410, panelJuego.getWidth());
                } else {
                    enemic.mover();
                    enemic2.mover();
                    enemic3.mover();
                    enemic4.moverH();
                    enemic5.moverH();
                    enemic6.moverH();
                }

                if (mago != null) {
                    if (enemic.getBounds().intersects(mago.getBounds()) ||
                            enemic2.getBounds().intersects(mago.getBounds()) ||
                            enemic3.getBounds().intersects(mago.getBounds()) ||
                            enemic4.getBounds().intersects(mago.getBounds()) ||
                            enemic5.getBounds().intersects(mago.getBounds()) ||
                            enemic6.getBounds().intersects(mago.getBounds())) {
                        for (int i = 0; i < listaObjetos.size(); i++) {
                            if (listaObjetos.get(i).equals("Pocion")) {
                                mago.sumarvida(mago);
                                vidas = mago.getVides();
                                vidasLabel.setText(" : " + vidas);
                                listaObjetos.remove(pocionN);
                                actualizarObjetosLabel();
                                mago.setBounds(-18, -15, 64, 64);
                            }
                        }
                        restarVida(mago,tipoPersonaje,seconds,vidas,oro,nom);
                    }
                }

                if (guerrer != null) {
                    if (enemic.getBounds().intersects(guerrer.getBounds()) ||
                            enemic2.getBounds().intersects(guerrer.getBounds()) ||
                            enemic3.getBounds().intersects(guerrer.getBounds()) ||
                            enemic4.getBounds().intersects(guerrer.getBounds()) ||
                            enemic5.getBounds().intersects(guerrer.getBounds()) ||
                            enemic6.getBounds().intersects(guerrer.getBounds())) {
                        boolean tieneEspada = false;
                        for (int i = 0; i < listaObjetos.size(); i++) {
                            if (listaObjetos.get(i).equals("Espada")) {
                                listaObjetos.remove(espadaN);
                                guerrer.sumarvida(guerrer);
                                vidas = guerrer.getVides();
                                vidasLabel.setText(" : " + vidas);
                                actualizarObjetosLabel();
                                tieneEspada = true;
                            }
                        }
                        restarVida(guerrer, tipoPersonaje, seconds, vidas, oro, nom);

                        if (tieneEspada) {
                            if (enemic.getBounds().intersects(guerrer.getBounds())) {
                                enemic = null;
                                enemic = new Enemic(-1000, -1000, 0);
                            } else if (enemic2.getBounds().intersects(guerrer.getBounds())) {
                                enemic2 = null;
                                enemic2 = new Enemic(-1000, -1000, 0);
                            } else if (enemic3.getBounds().intersects(guerrer.getBounds())) {
                                enemic3 = null;
                                enemic3 = new Enemic(-1000, -1000, 0);
                            } else if (enemic4.getBounds().intersects(guerrer.getBounds())) {
                                enemic4 = null;
                                enemic4 = new Enemic(-1000, -1000, 0);
                            } else if (enemic5.getBounds().intersects(guerrer.getBounds())) {
                                enemic5 = null;
                                enemic5 = new Enemic(-1000, -1000, 0);
                            } else if (enemic6.getBounds().intersects(guerrer.getBounds())) {
                                enemic6 = null;
                                enemic6 = new Enemic(-1000, -1000, 0);
                            }
                        }
                    }
                }

                if (sacerdot != null) {
                    if (enemic.getBounds().intersects(sacerdot.getBounds()) ||
                            enemic2.getBounds().intersects(sacerdot.getBounds()) ||
                            enemic3.getBounds().intersects(sacerdot.getBounds()) ||
                            enemic4.getBounds().intersects(sacerdot.getBounds()) ||
                            enemic5.getBounds().intersects(sacerdot.getBounds()) ||
                            enemic6.getBounds().intersects(sacerdot.getBounds())) {
                        for (int i = 0; i < listaObjetos.size(); i++) {
                            if (listaObjetos.get(i).equals("Mitra")) {
                                sacerdot.sumarvida(sacerdot);
                                vidas = sacerdot.getVides();
                                vidasLabel.setText(" : " + vidas);
                                listaObjetos.remove(mitraN);
                                actualizarObjetosLabel();
                                sacerdot.setBounds(-18, -15, 64, 64);

                            }
                        }
                        restarVida(sacerdot, tipoPersonaje, seconds, vidas, oro, nom);
                    }
                }

                panelJuego.repaint();
            }
        });

        timer.start();


    }

    /**
     * Crea y configura el mapa de juego en el panel de juego.
     *
     * @param panelJuego El panel de juego donde se mostrará el mapa.
     * @param nom El nombre del jugador.
     * @param tipoPersonaje El tipo de personaje del jugador.
     */
    private void creacionDelmapaJuego(JPanel panelJuego, String nom, String tipoPersonaje) {
        panelJuego.setOpaque(false);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(createBannerPanel(tipoPersonaje, nom), BorderLayout.NORTH);
        mainPanel.add(panelJuego, BorderLayout.CENTER);
        mainPanel.add(createWallPanel(), BorderLayout.SOUTH);
        mainPanel.add(createWallPanel(), BorderLayout.NORTH);

        mainPanel.add(createWallPanelLadosIzquierda(), BorderLayout.WEST);
        mainPanel.add(createWallPanelLadosDerecha(), BorderLayout.EAST);

        frame.getContentPane().add(mainPanel);

        panelJuego.requestFocusInWindow();
        panelJuego.setFocusable(true);
        panelJuego.requestFocus();
        frame.pack();
        frame.setVisible(true);
    }


    /**
     * Configura la salida en el panel de juego y verifica si el personaje ha alcanzado la salida.
     *
     * @param panelJuego El panel de juego donde se mostrará la salida.
     * @param tipoPersonaje El tipo de personaje del jugador.
     * @param nom El nombre del jugador.
     * @param orocontador Un array de enteros que almacena el contador de oro.
     * @param maxoro El máximo de oro necesario para alcanzar la salida.
     */
    private void salida(JPanel panelJuego, String tipoPersonaje, String nom, int[] orocontador, int maxoro) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/salida.png"));
        Image imagenRedimensionada = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon imagenReRedimensionada = new ImageIcon(imagenRedimensionada);
        JLabel imagenLabel = new JLabel(imagenReRedimensionada);

        // Obtén las dimensiones del panelJuego
        int panelAncho = 1000;
        int panelAlto = 580;

        // Establece las coordenadas x e y
        int x = panelAncho - imagenLabel.getPreferredSize().width;
        int y = panelAlto - imagenLabel.getPreferredSize().height;

        // Ubica el JLabel
        imagenLabel.setBounds(x, y, imagenLabel.getPreferredSize().width, imagenLabel.getPreferredSize().height);

        // Agrega el JLabel al panelJuego
        panelJuego.add(imagenLabel);
        final boolean[] Tocado = {false};
        Timer timer = new Timer(5, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Tocado[0] && (mago != null || guerrer != null || sacerdot != null)) {
                    if (mago != null && imagenLabel.getBounds().intersects(mago.getBounds()) && oro == 50 ||
                            guerrer != null && imagenLabel.getBounds().intersects(guerrer.getBounds()) && oro == 50 ||
                            sacerdot != null && imagenLabel.getBounds().intersects(sacerdot.getBounds()) && oro == 50) {
                        Tocado[0] = true;
                        if (Tocado[0]) {
                            hasganado = new Hasganado();
                            guardarEstadisticas(tipoPersonaje, seconds, vidas, oro, nom);
                            frame.setVisible(false);
                        }
                    }
                }
            }
        });
        timer.start();
    }

    /**
     * Crea una poción en el panel de juego y verifica si el personaje la recoge.
     *
     * @param panelJuego El panel de juego donde se mostrará la poción.
     */
    private void pocion(JPanel panelJuego) {
        ImageIcon imagenPocion = new ImageIcon(getClass().getResource("/images/dungeon/potion.png"));
        Image imagenRedimensionada = imagenPocion.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon imagenReRedimensionada = new ImageIcon(imagenRedimensionada);
        JLabel pocion = new JLabel(imagenReRedimensionada);

        Random random = new Random();
        int x = random.nextInt(800 - pocion.getWidth());
        int y = random.nextInt(450 - pocion.getHeight());
        pocion.setBounds(x, y, 64, 64);

        panelJuego.add(pocion);

        final boolean[] Tocado = {false};
        Timer timer = new Timer(5, e -> {
            if (!Tocado[0] && (mago != null || guerrer != null || sacerdot != null)) {
                if (mago != null && pocion.getBounds().intersects(mago.getBounds()) ||
                        guerrer != null && pocion.getBounds().intersects(guerrer.getBounds()) ||
                        sacerdot != null && pocion.getBounds().intersects(sacerdot.getBounds())) {
                    listaObjetos.add(pocionN);
                    Tocado[0] = true;
                    pocion.setVisible(false);
                    actualizarObjetosLabel();
                }
            }
        });
        timer.start();
    }

    /**
     * Crea una mitra en el panel de juego y verifica si el personaje la recoge.
     *
     * @param panelJuego El panel de juego donde se mostrará la mitra.
     */
    private void mitra(JPanel panelJuego) {
        ImageIcon imagenMitra = new ImageIcon(getClass().getResource("/images/dungeon/mitra.png"));
        Image imagenRedimensionada = imagenMitra.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon imagenReRedimensionada = new ImageIcon(imagenRedimensionada);
        JLabel mitra = new JLabel(imagenReRedimensionada);

        Random random = new Random();
        int x = random.nextInt(800 - mitra.getWidth());
        int y = random.nextInt(450 - mitra.getHeight());
        mitra.setBounds(x, y, 64, 64);

        panelJuego.add(mitra);
        final boolean[] Tocado = {false};

        Timer timer = new Timer(5, e -> {
            if (!Tocado[0] && (mago != null || guerrer != null || sacerdot != null)) {
                if (mago != null && mitra.getBounds().intersects(mago.getBounds()) ||
                        guerrer != null && mitra.getBounds().intersects(guerrer.getBounds()) ||
                        sacerdot != null && mitra.getBounds().intersects(sacerdot.getBounds())) {
                    listaObjetos.add(mitraN);
                    Tocado[0] = true;
                    mitra.setVisible(false);
                    actualizarObjetosLabel();
                }
            }
        });
        timer.start();
    }

    /**
     * Crea oro en el panel de juego y verifica si el personaje lo recoge.
     *
     * @param panelJuego El panel de juego donde se mostrará el oro.
     */
    private void oro(JPanel panelJuego) {
        ImageIcon imagenOro = new ImageIcon(getClass().getResource("/images/dungeon/dollar.png"));
        Image imagenRedimensionada = imagenOro.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon imagenReRedimensionada = new ImageIcon(imagenRedimensionada);

        Random random = new Random();
        List<JLabel> oroList = new ArrayList<>();
        final JLabel[] oro = {new JLabel(imagenReRedimensionada)};
        final int[] x = {random.nextInt(800 - oro[0].getWidth())};
        final int[] y = {random.nextInt(450 - oro[0].getHeight())};
        oro[0].setBounds(x[0], y[0], 64, 64);
        oro[0].setOpaque(false); // Permite que se detecten las colisiones
        oroList.add(oro[0]);
        panelJuego.add(oro[0]);

        Timer timer = new Timer(5, e -> {
            if (oro[0].isVisible() && (mago != null || guerrer != null || sacerdot != null)) {
                if (mago != null && oro[0].getBounds().intersects(mago.getBounds()) ||
                        guerrer != null && oro[0].getBounds().intersects(guerrer.getBounds()) ||
                        sacerdot != null && oro[0].getBounds().intersects(sacerdot.getBounds())) {
                    sumarOro(oroLabel);
                    oro[0].setVisible(false);
                    if (orocontador[0] < maxoro) {
                        oro[0] = new JLabel(imagenReRedimensionada);
                        x[0] = random.nextInt(800 - oro[0].getWidth());
                        y[0] = random.nextInt(450 - oro[0].getHeight());
                        oro[0].setBounds(x[0], y[0], 64, 64);
                        oro[0].setOpaque(false); // Permite que se detecten las colisiones
                        oroList.add(oro[0]);
                        panelJuego.add(oro[0]);
                        orocontador[0]++;
                    } else {
                        oro[0].setVisible(false);
                    }
                }
            }
        });
        timer.start();
    }

    private void sumarOro(JLabel oroLabel) {
        oro += 10;
        oroLabel.setText("  : " + oro);

    }

    private boolean personajeEnEsquinaInferiorDerecha() {
        int personajeX = mago.getX();
        int personajeY = mago.getY();
        int ventanaAncho = frame.getWidth();
        int ventanaAlto = frame.getHeight();

        return personajeX >= ventanaAncho - mago.getWidth() && personajeY >= ventanaAlto - mago.getHeight();
    }

    /**
     * Crea una espada en el panel de juego y verifica si el personaje la recoge.
     *
     * @param panelJuego El panel de juego donde se mostrará la espada.
     */
    private void espada(JPanel panelJuego) {
        ImageIcon imagenEspada = new ImageIcon(getClass().getResource("/images/dungeon/sword.png"));
        Image imagenRedimensionada = imagenEspada.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon imagenReRedimensionada = new ImageIcon(imagenRedimensionada);
        JLabel espada = new JLabel(imagenReRedimensionada);

        Random random = new Random();
        int x = random.nextInt(800 - espada.getWidth());
        int y = random.nextInt(450 - espada.getHeight());
        espada.setBounds(x, y, 64, 64);

        panelJuego.add(espada);
        final boolean[] Tocado = {false};

        Timer timer = new Timer(5, e -> {
            if (!Tocado[0] && (mago != null || guerrer != null || sacerdot != null)) {
                if (mago != null && espada.getBounds().intersects(mago.getBounds()) ||
                        guerrer != null && espada.getBounds().intersects(guerrer.getBounds()) ||
                        sacerdot != null && espada.getBounds().intersects(sacerdot.getBounds())) {
                    listaObjetos.add(espadaN);
                    Tocado[0] = true;
                    espada.setVisible(false);
                    actualizarObjetosLabel();
                }
            }
        });
        timer.start();
        actualizarObjetosLabel();
    }

    /**
     * Actualiza el texto del JLabel que muestra los objetos recolectados.
     */
    private void actualizarObjetosLabel() {
        StringBuilder sb = new StringBuilder("   Objetos: ");
        for (String objeto : listaObjetos) {
            sb.append(objeto).append(", ");
        }
        // Eliminar la coma adicional al final si hay objetos en la lista
        if (!listaObjetos.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length());
        }
        objetosLabel.setText(sb.toString());
    }

    public PanelJuego() {

    }
    /**
     * Crea un panel de banner personalizado para el personaje.
     *
     * @param tipoPersonaje El tipo de personaje.
     * @param nom           El nombre del personaje.
     * @return El panel de banner creado.
     */
    private JPanel createBannerPanel(String tipoPersonaje, String nom) {
        if (tipoPersonaje.equals("Mag")) {
            vidas = 3;
        } else if (tipoPersonaje.equals("Guerrer")) {
            vidas = 5;
        } else if (tipoPersonaje.equals("Sacerdot")) {
            vidas = 4;
        }

        panelTitle = new JPanel();
        panelTitle.setPreferredSize(new Dimension(frame.getWidth(), 70));
        panelTitle.setBackground(Color.BLACK);
        panelTitle.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelTime = new JLabel();
        labelTime.setText("TIME 0  ");
        labelTime.setForeground(Color.cyan);
        panelTitle.add(labelTime);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                labelTime.setText(" TIME  " + seconds + "   ");
            }
        });
        timer.start();

        nombreLabel = new JLabel(tipoPersonaje + ": " + nom + "  ");
        nombreLabel.setForeground(Color.white);
        nombreLabel.setFont(nombreLabel.getFont().deriveFont(24f));
        panelTitle.add(nombreLabel);

        ImageIcon imagencora = new ImageIcon(getClass().getResource("/images/dungeon/heart.png"));
        Image imagenRedimensionada = imagencora.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon imagenReRedimensionada = new ImageIcon(imagenRedimensionada);
        JLabel cora = new JLabel(imagenReRedimensionada);
        panelTitle.add(cora);
        vidasLabel = new JLabel(": " + vidas + "  ");
        vidasLabel.setForeground(Color.GREEN);
        vidasLabel.setFont(vidasLabel.getFont().deriveFont(24f));
        panelTitle.add(vidasLabel);

        ImageIcon imagenoro = new ImageIcon(getClass().getResource("/images/dungeon/dollar.png"));
        Image imagenRedimensionadaoro = imagenoro.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon imagenReRedimensionadaoros = new ImageIcon(imagenRedimensionadaoro);
        JLabel oros = new JLabel(imagenReRedimensionadaoros);
        panelTitle.add(oros);
        oroLabel = new JLabel(" : " + oro);
        oroLabel.setForeground(Color.yellow);
        oroLabel.setFont(vidasLabel.getFont().deriveFont(24f));
        panelTitle.add(oroLabel);

        objetosLabel = new JLabel("   Objetos: " + listaObjetos);
        objetosLabel.setForeground(Color.orange);
        objetosLabel.setFont(vidasLabel.getFont().deriveFont(24f));
        panelTitle.add(objetosLabel);

        return panelTitle;
    }

    /**
     * Resta una vida al personaje y actualiza la etiqueta de vidas en el banner.
     *
     * @param personatge    El personaje al que se le restará una vida.
     * @param tipoPersonaje El tipo de personaje.
     * @param seconds       El tiempo transcurrido en segundos.
     * @param vidas         La cantidad actual de vidas del personaje.
     * @param oro           La cantidad actual de oro del personaje.
     * @param nom           El nombre del personaje.
     */
    private void restarVida(Personatge personatge, String tipoPersonaje, int seconds, int vidas, int oro, String nom) {
        personatge.restarVida();
        this.vidas = personatge.getVidas();
        vidasLabel.setText(" : " + this.vidas + "   ");
        if (this.vidas <= 0) {
            hasperdido = new Hasperdido();
            frame.setVisible(false);

            guardarEstadisticas(tipoPersonaje, seconds, vidas, oro, nom);
        }
    }

    /**
     * Guarda las estadísticas del juego en un archivo y en una base de datos.
     *
     * @param tipoPersonaje El tipo de personaje.
     * @param seconds       El tiempo transcurrido en segundos.
     * @param vidas         La cantidad de vidas restantes.
     * @param oro           La cantidad de monedas de oro.
     * @param nom           El nombre del jugador.
     */
    private void guardarEstadisticas(String tipoPersonaje, int seconds, int vidas, int oro, String nom) {

        dades =
                "\n" +
                        "Jugador: " + nom + "\n" +
                        "Personatge: " + tipoPersonaje + "\n" +
                        "Temps: " + seconds + " segons " + " \n" +
                        "Número de vides: " + vidas + "\n" +
                        "Número de monedes d'or: " + oro + "\n" +
                        "    \n";

        String historial = "src/historialJoc.txt";

        String ranking = "src/recursos/temauno.txt";

        // Guardar les dades en el fitxer
        try {
            Files.write(Paths.get(historial), dades.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("Estadístiques guardades correctament.");

        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> lineas = leerHistorial();

        // Mostrar el historial en el marco (frame)
        //HistorialFrame historialFrame = new HistorialFrame();
        //historialFrame.mostrarHistorial(lineas, dades, nom, tipoPersonaje, seconds, vidas, oro);

        String db_url ="jdbc:mysql://localhost:3306/JOCROL";
        String user = "root";
        String paswd = "mysql";
        String query = "SELECT * FROM jocrol.partida where nom_user = ? ;";
        String insertQy = "insert into partida(nom_user,tipus_personatge,durada,vides_restants,monedes_or) values (?,?,?,?,?);";

        try {
            Connection con = DriverManager.getConnection(db_url,user,paswd);
            PreparedStatement ps = con.prepareStatement(insertQy);
            ps.setString(1,nom);
            ps.setString(2,tipoPersonaje);
            ps.setInt(3,seconds);
            ps.setInt(4,vidas);
            ps.setInt(5,oro);
            int addRows = ps.executeUpdate();
            if (addRows>0){
                System.out.println("Todo ok");
            }
            ps.close();

            Connection con2 = DriverManager.getConnection(db_url,user,paswd);
            PreparedStatement ps2 = con2.prepareStatement(query);
            ps2.setString(1,nom);
            ResultSet rs = ps2.executeQuery();
            while (rs.next()) {
                System.out.println(
                        rs.getInt(1) + "-" +
                                rs.getString(2) + "-" +
                                rs.getString(3) + "-" +
                                rs.getInt(4) + "-" +
                                rs.getInt(5) + "-" +
                                rs.getInt(6));

            }
            rs.close();
            ps2.close();
            con.close();
            con2.close();


        }catch (Exception e){
            System.out.println("Ha fallado la canoexion a la base de datos");
        }

    }

        public List<String> leerHistorial() {
        List<String> lineas = new ArrayList<>();

        try {
            lineas = Files.readAllLines(Paths.get("src/historialJoc.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineas;
    }
    public static class HistorialFrame extends JFrame {

        private JTable tabla;

        public HistorialFrame() {
            setTitle("Historial de Juego");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            tabla = new JTable();
            JScrollPane scrollPane = new JScrollPane(tabla);

            add(scrollPane, BorderLayout.CENTER);
            pack();
            setVisible(true);
        }

        /**public void mostrarHistorial(List<String> lineas, String dades, String nom, String tipoPersonaje, int seconds, int vidas, int oro) {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Jugador");
            model.addColumn("Personaje");
            model.addColumn("Tiempo (segundos)");
            model.addColumn("Vidas");
            model.addColumn("Monedas de oro");

            for (String linea : lineas) {
                String[] datos = linea.split("\n");

                if (datos.length >= 5) {
                     nom = datos[0].substring(datos[0].indexOf(":") + 2);
                     tipoPersonaje = datos[1].substring(datos[1].indexOf(":") + 2);
                     seconds = Integer.parseInt(datos[2].substring(datos[2].indexOf(":") + 2, datos[2].indexOf(" segundos")));
                    vidas = Integer.parseInt(datos[3].substring(datos[3].indexOf(":") + 2));
                     oro = Integer.parseInt(datos[4].substring(datos[4].indexOf(":") + 2));

                    model.addRow(new Object[]{nom, tipoPersonaje, seconds, vidas, oro});
                }
            }

            tabla.setModel(model);
        }*/

        public List<String> leerHistorial() {
            List<String> lineas = new ArrayList<>();

            try {
                lineas = Files.readAllLines(Paths.get("src/historialJoc.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return lineas;
        }
    }

    /**
     * Crea un panel de muro con baldosas en fila.
     *
     * @return El panel de muro con baldosas en fila.
     */
    private JPanel createWallPanel() {
        JPanel wallRow = new JPanel();
        wallRow.setLayout(new BoxLayout(wallRow, BoxLayout.X_AXIS)); // Layout horizontal
        wallRow.setBackground(Color.black);
        int anchoMuro = 30;
        int altoMuro = 30;

        ImageIcon wallIcon = new ImageIcon("src/images/dungeon/tile004.png");
        Image imagenMuro = wallIcon.getImage().getScaledInstance(anchoMuro, altoMuro, Image.SCALE_DEFAULT);
        wallIcon = new ImageIcon(imagenMuro);

        int largo = 73;

        for (int i = 0; i < largo; i++) {
            JLabel wallLabel = new JLabel(wallIcon);
            wallRow.add(wallLabel);
        }

        int panelWidth = anchoMuro * largo;
        int panelHeight = altoMuro;
        wallRow.setPreferredSize(new Dimension(panelWidth, panelHeight));

        return wallRow;
    }

    /**
     * Crea un panel de muro en el lado izquierdo.
     *
     * @return El panel de muro en el lado izquierdo.
     */
    private JPanel createWallPanelLadosIzquierda() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Layout vertical
        mainPanel.setBackground(Color.darkGray);

        int anchoMuro = 30;
        int altoMuro = 30;

        ImageIcon wallIcon = new ImageIcon("src/images/dungeon/tile004.png");
        Image imagenMuro = wallIcon.getImage().getScaledInstance(anchoMuro, altoMuro, Image.SCALE_DEFAULT);
        wallIcon = new ImageIcon(imagenMuro);

        int alturaDeseada = 15;

        JPanel wallPanel = new JPanel();
        wallPanel.setLayout(new BoxLayout(wallPanel, BoxLayout.Y_AXIS));
        wallPanel.setBackground(Color.darkGray);

        for (int i = 0; i < alturaDeseada; i++) {
            JLabel wallLabel = new JLabel(wallIcon);
            wallPanel.add(wallLabel, 0);
        }

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(wallPanel);

        return mainPanel;
    }

    /**
     * Crea un panel de muro en el lado derecho.
     *
     * @return El panel de muro en el lado derecho.
     */
    private JPanel createWallPanelLadosDerecha() {
        JPanel wallPanel = new JPanel();
        wallPanel.setLayout(new BoxLayout(wallPanel, BoxLayout.Y_AXIS)); // Layout vertical
        wallPanel.setBackground(Color.darkGray);

        int anchoMuro = 30;
        int altoMuro = 30;

        ImageIcon wallIcon = new ImageIcon("src/images/dungeon/tile004.png");
        Image imagenMuro = wallIcon.getImage().getScaledInstance(anchoMuro, altoMuro, Image.SCALE_DEFAULT);
        wallIcon = new ImageIcon(imagenMuro);

        int alturaDeseada = 15;

        for (int i = 0; i < alturaDeseada; i++) {
            JLabel wallLabel = new JLabel(wallIcon);
            wallPanel.add(wallLabel, 0);
        }
        return wallPanel;
    }


}
