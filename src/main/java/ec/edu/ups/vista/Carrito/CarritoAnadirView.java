package ec.edu.ups.vista.Carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Locale;

public class CarritoAnadirView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JPanel txt;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JTable tblItems;
    private JButton btnGuardar;
    private JButton btnBuscar;
    private JButton btnAñadir;
    private JButton btnLimpiar;
    private JComboBox<String> cbxCantidad;
    private DefaultTableModel modelo;
    private Carrito carritoActual;
    private Locale locale;
    private final MensajeInternacionalizacionHandler mensajes;

    public CarritoAnadirView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.lang"), mensajes.get("locale.country"));

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 550);
        setClosable(true);
        setIconifiable(true);

        modelo = new DefaultTableModel();
        tblItems.setModel(modelo);

        cargarDatos();
        actualizarTextos();
    }

    private void cargarDatos() {
        cbxCantidad.removeAllItems();
        for (int i = 1; i <= 20; i++) {
            cbxCantidad.addItem(String.valueOf(i));
        }
    }

    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.lang"), mensajes.get("locale.country"));

        setTitle(mensajes.get("car.crear.titulo.app"));
        if (panelPrincipal != null) {
            Component[] components = panelPrincipal.getComponents();
            for (Component c : components) {
                if (c instanceof JLabel) {
                    JLabel label = (JLabel) c;
                    String name = label.getName();
                    if (name != null) {
                        switch (name) {
                            case "lblTitulo" -> label.setText(mensajes.get("car.crear.titulo"));
                            case "lblCodigo" -> label.setText(mensajes.get("global.codigo"));
                            case "lblNombre" -> label.setText(mensajes.get("global.nombre"));
                            case "lblPrecio" -> label.setText(mensajes.get("global.precio"));
                            case "lblCantidad" -> label.setText(mensajes.get("global.cantidad"));
                            case "lblSubtotal" -> label.setText(mensajes.get("global.subtotal"));
                            case "lblIVA" -> label.setText(mensajes.get("global.iva"));
                            case "lblTotal" -> label.setText(mensajes.get("global.total"));
                        }
                    }
                }
            }
        }

        txtCodigo.setToolTipText(mensajes.get("prod.top.codigo"));
        btnBuscar.setText(mensajes.get("btn.buscar"));
        btnAñadir.setText(mensajes.get("btn.añadir"));
        btnGuardar.setText(mensajes.get("btn.guardar"));
        btnLimpiar.setText(mensajes.get("btn.limpiar"));
        cbxCantidad.setToolTipText(mensajes.get("car.top.cantidad"));

        Object[] columnas = {
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.subtotal")
        };
        modelo.setColumnIdentifiers(columnas);

        mostrarCarrito(carritoActual);
    }

    public void mostrarCarrito(Carrito carrito) {
        this.carritoActual = carrito;
        modelo.setRowCount(0);
        if (carrito != null) {
            for (ItemCarrito item : carrito.obtenerItems()) {
                modelo.addRow(new Object[]{
                        item.getProducto().getCodigo(),
                        item.getProducto().getNombre(),
                        FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                        item.getCantidad(),
                        FormateadorUtils.formatearMoneda(item.getSubtotal(), locale)
                });
            }
            txtSubtotal.setText(FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale));
            txtIVA.setText(FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale));
            txtTotal.setText(FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale));
        } else {
            txtSubtotal.setText("");
            txtIVA.setText("");
            txtTotal.setText("");
        }
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JTextField getTxtSubtotal() {
        return txtSubtotal;
    }

    public void setTxtSubtotal(JTextField txtSubtotal) {
        this.txtSubtotal = txtSubtotal;
    }

    public JTextField getTxtIVA() {
        return txtIVA;
    }

    public void setTxtIVA(JTextField txtIVA) {
        this.txtIVA = txtIVA;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JButton getBtnAñadir() {
        return btnAñadir;
    }

    public void setBtnAñadir(JButton btnAñadir) {
        this.btnAñadir = btnAñadir;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    public JComboBox<String> getCbxCantidad() {
        return cbxCantidad;
    }

    public void setCbxCantidad(JComboBox<String> cbxCantidad) {
        this.cbxCantidad = cbxCantidad;
    }

    public JTable getTblItems() {
        return tblItems;
    }

    public void setTblItems(JTable tblItems) {
        this.tblItems = tblItems;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("confirm.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}
