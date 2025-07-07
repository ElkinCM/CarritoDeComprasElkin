package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class LoginView extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panelSecundario;
    private JTextField txtUsername;
    private JPasswordField psfContraseña;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JButton btnOlvidoContraseña;
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JLabel lblTitulo;
    private JButton btnSalir;

    private JMenuBar menuBar;
    private JMenu menuIdiomas;
    private JMenuItem menuItemEspañol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemFrances;

    private final MensajeInternacionalizacionHandler Internacionalizar;
    private Locale locale;

    public LoginView(MensajeInternacionalizacionHandler internacionalizar) {
        this.Internacionalizar = internacionalizar;
        setTitle(internacionalizar.get("login.app.menu"));
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,300);
        setLocationRelativeTo(null);
        setResizable(false);

        btnRegistrarse.setIcon(new ImageIcon(getClass().getResource("/crear.png")));
        btnIniciarSesion.setIcon(new ImageIcon(getClass().getResource("/iniciarSesion.png")));
        btnSalir.setIcon(new ImageIcon(getClass().getResource("/salir.png")));

        btnSalir.addActionListener(e -> dispose());

        menuBar = new JMenuBar();
        menuIdiomas = new JMenu(internacionalizar.get("idiomas.menu"));
        menuIdiomas.setIcon(new ImageIcon(getClass().getResource("/idioma.png")));

        menuItemEspañol = new JMenuItem(internacionalizar.get("etiqueta.idioma.Español"));
        menuItemIngles = new JMenuItem(internacionalizar.get("etiqueta.idioma.Ingles"));
        menuItemFrances = new JMenuItem(internacionalizar.get("etiqueta.idioma.Frances"));

        menuIdiomas.add(menuItemEspañol);
        menuIdiomas.add(menuItemIngles);
        menuIdiomas.add(menuItemFrances);
        menuBar.add(menuIdiomas);
        setJMenuBar(menuBar);

        menuItemEspañol.addActionListener(e -> {
            Internacionalizar.setLenguaje("es", "EC");
            actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        });

        menuItemIngles.addActionListener(e -> {
            Internacionalizar.setLenguaje("en", "US");
            actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        });

        menuItemFrances.addActionListener(e -> {
            Internacionalizar.setLenguaje("fr", "FR");
            actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        });

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    public void actualizarIdioma(String language, String country) {
        Internacionalizar.setLenguaje(language, country);
        lblTitulo.setText(Internacionalizar.get("login.app.titulo"));
        lblUsuario.setText(Internacionalizar.get("etiqueta.usuario"));
        lblContraseña.setText(Internacionalizar.get("etiqueta.contrasenia"));
        btnIniciarSesion.setText(Internacionalizar.get("boton.iniciar.sesion"));
        btnRegistrarse.setText(Internacionalizar.get("boton.registrarse"));
        btnOlvidoContraseña.setText(Internacionalizar.get("boton.contraseña.olvidada"));
        btnSalir.setText(Internacionalizar.get("boton.salir"));
        menuIdiomas.setText(Internacionalizar.get("idiomas.menu"));
        menuItemEspañol.setText(Internacionalizar.get("etiqueta.idioma.Español"));
        menuItemIngles.setText(Internacionalizar.get("etiqueta.idioma.Ingles"));
        menuItemFrances.setText(Internacionalizar.get("etiqueta.idioma.Frances"));
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public JPasswordField getPsfContraseña() {
        return psfContraseña;
    }

    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    public JButton getBtnOlvidoContraseña() {
        return btnOlvidoContraseña;
    }

    public JMenuItem getMenuItemEspañol() {
        return menuItemEspañol;
    }

    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;

    }

    public JMenuItem getMenuItemFrances() {
        return menuItemFrances;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
