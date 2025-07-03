package ec.edu.ups.vista.Producto;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

public class ProductoAnadirView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtPrecio;
    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblTitulo;

    private MensajeInternacionalizacionHandler mensaje;

    public ProductoAnadirView(MensajeInternacionalizacionHandler mensaje) {
        super(mensaje.get("prod.crear.titulo.app"), true, true, true, true);
        this.mensaje = mensaje;

        URL urlAceptar = getClass().getResource("/check.png");
        URL urlLimpiar = getClass().getResource("/clean.png");

        if (urlAceptar != null) {
            btnAceptar.setIcon(new ImageIcon(urlAceptar));
        }

        if (urlLimpiar != null) {
            btnLimpiar.setIcon(new ImageIcon(urlLimpiar));
        }

        setContentPane(panelPrincipal);
        actualizarTextos();

        setSize(500, 500);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    public void actualizarTextos() {
        setTitle(mensaje.get("prod.crear.titulo.app"));
        lblTitulo.setText(mensaje.get("prod.crear.titulo"));
        lblCodigo.setText(mensaje.get("global.codigo"));
        lblNombre.setText(mensaje.get("global.nombre"));
        lblPrecio.setText(mensaje.get("global.precio"));

        txtCodigo.setToolTipText(mensaje.get("prod.top.codigo"));
        txtNombre.setToolTipText(mensaje.get("prod.top.nombre"));
        txtPrecio.setToolTipText(mensaje.get("prod.top.precio"));

        btnAceptar.setText(mensaje.get("btn.aceptar"));
        btnLimpiar.setText(mensaje.get("btn.limpiar"));
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, this.mensaje.get("confirm.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }

    public void mostrarProductos(List<Producto> productos) {
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }
}
