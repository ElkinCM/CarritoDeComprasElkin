package ec.edu.ups.vista.Registrarse;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class RecuperarView extends JFrame {
    private JPanel panelPrincipal;
    private JLabel lblPreguntas;
    private JButton btnSiguiente;
    private JButton btnRestablecer;
    private JPasswordField txtContraseña;
    private JLabel lblEnunciado;
    private JLabel lblTituloPregunta;
    private JMenuBar menubar;
    private JMenu menuIdiomas;

    private JMenuItem menuItemEspañol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemFrances;

    private MensajeInternacionalizacionHandler Internacionalizar;

    public RecuperarView(MensajeInternacionalizacionHandler internacionalizar) {
        super(internacionalizar.get("recuperar.menu"));
        this.Internacionalizar = internacionalizar;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        btnSiguiente.setIcon(new ImageIcon(RecuperarView.class.getResource("/siguiente.png")));

        menubar = new JMenuBar();
        menuIdiomas = new JMenu(internacionalizar.get("idiomas.menu"));
        menuIdiomas.setIcon(new ImageIcon(getClass().getResource("/idioma.png")));

        menuItemEspañol = new JMenuItem(internacionalizar.get("etiqueta.idioma.Español"));
        menuItemIngles = new JMenuItem(internacionalizar.get("etiqueta.idioma.Ingles"));
        menuItemFrances = new JMenuItem(internacionalizar.get("etiqueta.idioma.Frances"));

        menuIdiomas.add(menuItemEspañol);
        menuIdiomas.add(menuItemIngles);
        menuIdiomas.add(menuItemFrances);
        menubar.add(menuIdiomas);
        setJMenuBar(menubar);

        menuItemEspañol.addActionListener(e -> {
            internacionalizar.setLenguaje("es", "EC");
            actualizarIdioma(internacionalizar.getLocale().getLanguage(), internacionalizar.getLocale().getCountry());
        });
        menuItemIngles.addActionListener(e -> {
            internacionalizar.setLenguaje("en", "US");
            actualizarIdioma(Internacionalizar.getLocale().getLanguage(), internacionalizar.getLocale().getCountry());
        });
        menuItemFrances.addActionListener(e -> {
            internacionalizar.setLenguaje("fr", "FR");
            actualizarIdioma(internacionalizar.getLocale().getLanguage(), internacionalizar.getLocale().getCountry());
        });

        actualizarIdioma(internacionalizar.getLocale().getLanguage(), internacionalizar.getLocale().getCountry());
    }

    public void actualizarIdioma(String language, String country) {
        Internacionalizar.setLenguaje(language, country);
        lblTituloPregunta.setText(Internacionalizar.get("etiqueta.pregunta.titulo"));
        lblPreguntas.setText(Internacionalizar.get("etiqueta.pregunta"));
        btnRestablecer.setText(Internacionalizar.get("boton.restablecer"));
        btnSiguiente.setText(Internacionalizar.get("boton.siguiente"));
    }

    public void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JLabel getLblPreguntas() {
        return lblPreguntas;
    }

    public JLabel getLblEnunciado() {
        return lblEnunciado;
    }

    public JPasswordField getTxtContraseña() {
        return txtContraseña;
    }

    public JButton getBtnContinuar() {
        return btnSiguiente;
    }

    public JButton getBtnRestablecer() {
        return btnRestablecer;
    }

    public JMenuItem getMenuItemEspañol() {return menuItemEspañol; }

    public JMenuItem getMenuItemIngles() {return menuItemIngles; }

    public JMenuItem getMenuItemFrances() {return menuItemFrances; }

    public void setPanelPrincipal(JPanel panelPrincipal) { this.panelPrincipal = panelPrincipal; }

    public MensajeInternacionalizacionHandler getInternacionalizar() {
        return Internacionalizar;
    }

    public void setInternacionalizar(MensajeInternacionalizacionHandler internacionalizar) { this.Internacionalizar = internacionalizar; }

    public void habilitarCambioContrasenia() {
        lblPreguntas.setText(Internacionalizar.get("mensaje.nueva.contrasenia"));
        lblEnunciado.setText("");
        txtContraseña.setText("");

        txtContraseña.setEnabled(true);
        btnRestablecer.setEnabled(true);
        btnSiguiente.setEnabled(false);
    }

}
