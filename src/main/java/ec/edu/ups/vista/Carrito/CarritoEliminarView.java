package ec.edu.ups.vista.Carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class CarritoEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTable tblItems;
    private JTextField txtUsuario;
    private JTextField txtFecha;
    private JButton btnEliminar;
    private JLabel lblCodigo;
    private JLabel lblUsuario;
    private JLabel lblFecha;
    private JLabel lblItems;
    private JLabel lblTitulo;
    private DefaultTableModel modeloDetalles;
    private Carrito carritoActual;

    private MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    public CarritoEliminarView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.lang"), mensajes.get("locale.country"));

        URL urlBuscar = getClass().getResource("/search.png");
        URL urlEliminar = getClass().getResource("/trash.png");

        setContentPane(panelPrincipal);

        if (btnBuscar != null && urlBuscar != null)
            btnBuscar.setIcon(new ImageIcon(urlBuscar));
        if (btnEliminar != null && urlEliminar != null)
            btnEliminar.setIcon(new ImageIcon(urlEliminar));

        setSize(600, 400);

        modeloDetalles = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        if (tblItems != null)
            tblItems.setModel(modeloDetalles);

        actualizarTextos();
    }

    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.lang"), mensajes.get("locale.country"));

        setTitle(mensajes.get("car.eliminar.titulo.app"));

        if (lblTitulo != null)
            lblTitulo.setText(mensajes.get("car.eliminar.titulo.app"));
        if (lblCodigo != null)
            lblCodigo.setText(mensajes.get("global.codigo"));
        if (lblUsuario != null)
            lblUsuario.setText(mensajes.get("global.usuario"));
        if (lblFecha != null)
            lblFecha.setText(mensajes.get("global.fecha"));
        if (lblItems != null)
            lblItems.setText(mensajes.get("global.item"));

        if (txtCodigo != null)
            txtCodigo.setToolTipText(mensajes.get("car.top.codigo"));

        if (btnBuscar != null)
            btnBuscar.setText(mensajes.get("btn.buscar"));
        if (btnEliminar != null)
            btnEliminar.setText(mensajes.get("btn.eliminar"));

        Object[] columnasDetalles = {
                mensajes.get("global.codigo"),
                mensajes.get("global.nombre"),
                mensajes.get("global.precio"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.subtotal")
        };
        modeloDetalles.setColumnIdentifiers(columnasDetalles);

        mostrarItemsCarrito(carritoActual);
    }

    public void mostrarItemsCarrito(Carrito carrito) {
        this.carritoActual = carrito;
        modeloDetalles.setRowCount(0);

        if (carrito != null) {
            List<ItemCarrito> items = carrito.obtenerItems();
            for (ItemCarrito item : items) {
                Object[] fila = {
                        item.getProducto().getCodigo(),
                        item.getProducto().getNombre(),
                        FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                        item.getCantidad(),
                        FormateadorUtils.formatearMoneda(item.getSubtotal(), locale)
                };
                modeloDetalles.addRow(fila);
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("confirm.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    // Getters y setters para los componentes que usarás en controlador:

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblItems() {
        return tblItems;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JTextField getTxtFecha() {
        return txtFecha;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public Carrito getCarritoActual() {
        return carritoActual;
    }
}
