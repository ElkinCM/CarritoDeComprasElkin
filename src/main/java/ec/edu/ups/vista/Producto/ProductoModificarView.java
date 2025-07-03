package ec.edu.ups.vista.Producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

public class ProductoModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JButton btnModificar;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JLabel lblTitulo;

    private MensajeInternacionalizacionHandler mensaje;

    public ProductoModificarView(MensajeInternacionalizacionHandler mensaje) {
        super("", true, true, false, true);
        this.mensaje = mensaje;

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);

        // Cargar íconos si están disponibles
        URL urlBuscar = getClass().getResource("/search.png");
        URL urlModificar = getClass().getResource("/edit.png");

        if (urlBuscar != null) btnBuscar.setIcon(new ImageIcon(urlBuscar));
        if (urlModificar != null) btnModificar.setIcon(new ImageIcon(urlModificar));

        actualizarTextos();
    }

    public void actualizarTextos() {
        setTitle(mensaje.get("prod.modificar.titulo.app"));
        lblTitulo.setText(mensaje.get("prod.modificar.titulo.app"));
        lblCodigo.setText(mensaje.get("global.codigo"));
        lblNombre.setText(mensaje.get("global.nombre"));
        lblPrecio.setText(mensaje.get("global.precio"));
        txtCodigo.setToolTipText(mensaje.get("prod.top.codigo"));
        btnBuscar.setText(mensaje.get("btn.buscar"));
        btnModificar.setText(mensaje.get("btn.modificar"));
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void mostrarMensaje(String mensajeTexto) {
        JOptionPane.showMessageDialog(this, mensajeTexto, mensaje.get("confirm.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}
