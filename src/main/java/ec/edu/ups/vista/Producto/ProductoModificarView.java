package ec.edu.ups.vista.Producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;

public class ProductoModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JButton btnGuardar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JLabel lblTitulo;

    private MensajeInternacionalizacionHandler Internacionalizar;
    private Locale locale;

    public ProductoModificarView(MensajeInternacionalizacionHandler internacionalizar) {
        super(internacionalizar.get("producto.modificar.menu"), true, true, true, true);
        this.Internacionalizar = internacionalizar;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        setFrameIcon(new ImageIcon(getClass().getResource("/modificar.png")));
        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        btnGuardar.setIcon(new ImageIcon(getClass().getResource("/guardar.png")));

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    public void actualizarIdioma(String language, String country) {
        Internacionalizar.setLenguaje(language, country);
        lblTitulo.setText(Internacionalizar.get("producto.modificar.titulo"));
        lblCodigo.setText(Internacionalizar.get("etiqueta.codigo"));
        lblNombre.setText(Internacionalizar.get("etiqueta.nombre"));
        lblPrecio.setText(Internacionalizar.get("etiqueta.precio"));
        btnBuscar.setText(Internacionalizar.get("boton.buscar"));
        btnGuardar.setText(Internacionalizar.get("boton.guardar"));
    }

    public void vaciarCampo() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnModificar() {
        return btnGuardar;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
