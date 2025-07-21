package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;

public class GuardarArchivo extends JFrame {
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JButton btnMemoria;
    private JButton btnArchivos;
    private JButton btnBinario;
    private JFileChooser documentos;

    private MensajeInternacionalizacionHandler Internacionalizar;
    private Locale locale;
    private JMenuBar menubar;

    private JMenu menuIdiomas;

    private JMenuItem menuItemEspañol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemFrances;

    public GuardarArchivo(MensajeInternacionalizacionHandler internacionalizar) {
        super(internacionalizar.get("guardar.archivo.menu"));
        this.Internacionalizar = internacionalizar;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setContentPane(panelPrincipal);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

        documentos = new JFileChooser();
        documentos.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        menubar = new JMenuBar();
        menuIdiomas = new JMenu(internacionalizar.get("idiomas.menu"));
        menuIdiomas.setIcon(new ImageIcon(getClass().getResource("/idioma.png")));
        menuItemEspañol = new JMenuItem(internacionalizar.get("etiqueta.idioma.Español"));
        menuItemIngles = new JMenuItem(internacionalizar.get("etiqueta.idioma.Ingles"));
        menuItemFrances = new JMenuItem(internacionalizar.get("etiqueta.idioma.Frances"));

        menubar.add(menuIdiomas);

        menuIdiomas.add(menuItemEspañol);
        menuIdiomas.add(menuItemIngles);
        menuIdiomas.add(menuItemFrances);

        setJMenuBar(menubar);

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    public void actualizarIdioma(String language, String country) {
        locale = new Locale(language, country);
        lblTitulo.setText(Internacionalizar.get("guardar.archivo.titulo"));
        btnMemoria.setText(Internacionalizar.get("boton.memoria"));
        btnArchivos.setText(Internacionalizar.get("boton.archivos"));
        btnBinario.setText(Internacionalizar.get("boton.binario"));

        menuIdiomas.setText(Internacionalizar.get("idiomas.menu"));
        menuItemEspañol.setText(Internacionalizar.get("etiqueta.idioma.Español"));
        menuItemIngles.setText(Internacionalizar.get("etiqueta.idioma.Ingles"));
        menuItemFrances.setText(Internacionalizar.get("etiqueta.idioma.Frances"));
    }

    public JButton getBtnMemoria() {
        return btnMemoria;
    }

    public JButton getBtnArchivos() {
        return btnArchivos;
    }

    public JButton getBtnBinario() { return btnBinario; }

    public String RutaArchivo() {
        int seleccionar = documentos.showOpenDialog(this);
        if (seleccionar == JFileChooser.APPROVE_OPTION) {
            return documentos.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    public JMenuItem getMenuItemEspañol(){return menuItemEspañol;}
    public JMenuItem getMenuItemIngles(){return menuItemIngles;}
    public JMenuItem getMenuItemFrances(){return menuItemFrances;}
}
