package ec.edu.ups.vista.Registrarse;

import ec.edu.ups.modelo.Genero;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class RegistrarView extends JFrame {

    private JPanel panelPrincipal;

    private JTextField txtUsuario;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JTextField txtAnio;

    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JLabel lblNombre;
    private JLabel lblTelefono;
    private JLabel lblEmail;
    private JLabel lblFechaNacimiento;
    private JLabel lblGenero;
    private JLabel lblDia;
    private JLabel lblMes;
    private JLabel lblAnio;
    private JLabel lblPregunta;

    private JPasswordField txtContrasenia;
    private JPasswordField txtRespuesta;

    private JComboBox<Genero> cbxGenero;

    private JComboBox cbxDia;
    private JComboBox cbxMes;

    private JButton btnGuardar;
    private JButton btnRegistar;
    private JLabel lblEnunciado;
    private JLabel lbltituloPreguntas;

    private JMenuBar menubar;

    private JMenu menuIdiomas;

    private JMenuItem menuItemEspañol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemFrances;


    private final MensajeInternacionalizacionHandler Internacionalizar;
    private Locale locale;

    public RegistrarView(MensajeInternacionalizacionHandler internacionalizar) {
        super(internacionalizar.get("registrarse.app.menu"));
        this.Internacionalizar = internacionalizar;
        setContentPane(panelPrincipal);
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        btnRegistar.setIcon(new ImageIcon(getClass().getResource("/usuario_crear.png")));
        btnGuardar.setIcon(new ImageIcon(getClass().getResource("/guardar.png")));

        cbxGenero.addItem(Genero.MASCULINO);
        cbxGenero.addItem(Genero.FEMENINO);
        cbxGenero.addItem(Genero.OTROS);

        cbxDia.removeAllItems();
        for (int i = 1; i <= 31; i++) {
            cbxDia.addItem(i);
        }

        cbxMes.removeAllItems();
        String[] clavesMeses = {
                "mes.enero", "mes.febrero", "mes.marzo", "mes.abril", "mes.mayo", "mes.junio",
                "mes.julio", "mes.agosto", "mes.septiembre", "mes.octubre", "mes.noviembre", "mes.diciembre"
        };
        for (String clave : clavesMeses) {
            cbxMes.addItem(internacionalizar.get(clave));
        }

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
        locale = new Locale(language, country);

        lblTitulo.setText(Internacionalizar.get("registrarse.app.titulo"));
        lblNombre.setText(Internacionalizar.get("etiqueta.nombre"));
        lblUsuario.setText(Internacionalizar.get("etiqueta.usuario"));
        lblContraseña.setText(Internacionalizar.get("etiqueta.contrasenia"));
        lblTelefono.setText(Internacionalizar.get("etiqueta.telefono"));
        lblEmail.setText(Internacionalizar.get("etiqueta.correo"));
        lblFechaNacimiento.setText(Internacionalizar.get("etiqueta.fecha.nacimiento"));
        lblDia.setText(Internacionalizar.get("etiqueta.dia"));
        lblMes.setText(Internacionalizar.get("etiqueta.mes"));
        lblAnio.setText(Internacionalizar.get("etiqueta.anio"));
        lblGenero.setText(Internacionalizar.get("etiqueta.genero"));
        lblPregunta.setText(Internacionalizar.get("etiqueta.pregunta.titulo"));
        lblEnunciado.setText(Internacionalizar.get("etiqueta.enunciado"));

        menuIdiomas.setText(Internacionalizar.get("idiomas.menu"));
        menuItemEspañol.setText(Internacionalizar.get("etiqueta.idioma.Español"));
        menuItemIngles.setText(Internacionalizar.get("etiqueta.idioma.Ingles"));
        menuItemFrances.setText(Internacionalizar.get("etiqueta.idioma.Frances"));

        cbxGenero.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == Genero.MASCULINO) {
                    label.setText(Internacionalizar.get("etiqueta.genero.masculino"));
                } else if (value == Genero.FEMENINO) {
                    label.setText(Internacionalizar.get("etiqueta.genero.femenino"));
                } else if (value == Genero.OTROS) {
                    label.setText(Internacionalizar.get("etiqueta.genero.otro"));
                }
                return label;
            }
        });

        cbxMes.removeAllItems();
        String[] clavesMeses = {
                "mes.enero", "mes.febrero", "mes.marzo", "mes.abril", "mes.mayo", "mes.junio",
                "mes.julio", "mes.agosto", "mes.septiembre", "mes.octubre", "mes.noviembre", "mes.diciembre"
        };
        for (String clave : clavesMeses) {
            cbxMes.addItem(Internacionalizar.get(clave));
        }
    }

    public void vaciarCampo() {
        txtUsuario.setText("");
        txtContrasenia.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtAnio.setText("");
        txtRespuesta.setText("");
        cbxGenero.setSelectedIndex(-1);
        cbxDia.setSelectedIndex(0);
        cbxMes.setSelectedIndex(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JTextField getTxtUsuario() { return txtUsuario; }
    public JPasswordField getTxtContrasenia() { return txtContrasenia; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtTelefono() { return txtTelefono; }
    public JTextField getTxtCorreo() { return txtCorreo; }
    public JTextField getTxtAnio() { return txtAnio; }
    public JComboBox<Genero> getCbxGenero() { return cbxGenero; }
    public JComboBox getCbxDia() { return cbxDia; }
    public JComboBox getCbxMes() { return cbxMes; }
    public JPasswordField getTxtPregunta() { return txtRespuesta; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnRegistar() { return btnRegistar; }
    public JLabel getLblPreguntas() { return lblPregunta; }
    public JLabel getLblEnunciado() {return lblEnunciado; }
    public JMenuItem getMenuItemEspañol(){return menuItemEspañol;}
    public JMenuItem getMenuItemIngles(){return menuItemIngles;}
    public JMenuItem getMenuItemFrances(){return menuItemFrances;}

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
