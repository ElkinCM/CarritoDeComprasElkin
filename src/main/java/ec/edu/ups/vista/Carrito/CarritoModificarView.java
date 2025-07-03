package ec.edu.ups.vista.Carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Locale;

public class CarritoModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextField txtUsuario;
    private JTextField txtFecha;
    private JTable tblItems;
    private JLabel lblCodigo;
    private JLabel lblUsuario;
    private JLabel lblFecha;
    private JLabel lblItems;
    private JButton btnModificar;
    private JLabel lblTitulo;
    private DefaultTableModel modeloDetalles;
    private Carrito carritoActual;
    private MensajeInternacionalizacionHandler mensaje;
    private Locale locale;

    public CarritoModificarView(MensajeInternacionalizacionHandler mensaje) {
        super("", true, true, false, true);
        this.mensaje = mensaje;
        this.locale = new Locale(mensaje.get("locale.lang"), mensaje.get("locale.country"));
        setContentPane(panelPrincipal);
        setSize(600, 600);

        modeloDetalles = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        tblItems.setModel(modeloDetalles);

        configurarListeners();
        actualizarTextos();

        btnModificar.setEnabled(false);

    }

    public void actualizarTextos() {
        this.locale = new Locale(mensaje.get("locale.lang"), mensaje.get("locale.country"));

        setTitle(mensaje.get("usr.modificar.titulo.app"));
        lblTitulo.setText(mensaje.get("usr.modificar.titulo.app"));
        lblCodigo.setText(mensaje.get("global.codigo"));
        lblUsuario.setText(mensaje.get("global.usuario"));
        lblFecha.setText(mensaje.get("global.fecha"));
        lblItems.setText(mensaje.get("global.item"));

        txtCodigo.setToolTipText(mensaje.get("car.top.codigo"));

        btnModificar.setText(mensaje.get("btn.modificar"));
        btnBuscar.setText(mensaje.get("btn.buscar"));

        Object[] columnas = {
                mensaje.get("global.codigo"),
                mensaje.get("global.nombre"),
                mensaje.get("global.precio"),
                mensaje.get("global.cantidad"),
                mensaje.get("global.subtotal")
        };
        modeloDetalles.setColumnIdentifiers(columnas);
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

    private void configurarListeners() {
        modeloDetalles.addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) {
                actualizarSubtotalF(e.getFirstRow());
            }
        });
    }

    private void actualizarSubtotalF(int fila) {
        if (carritoActual != null) {
            try {
                int codigoProducto = (int) modeloDetalles.getValueAt(fila, 0);
                int nuevaCantidad = Integer.parseInt(modeloDetalles.getValueAt(fila, 3).toString());

                carritoActual.actualizarCantidadProducto(codigoProducto, nuevaCantidad);

                ItemCarrito itemActualizado = encontrarItem(codigoProducto);
                if (itemActualizado != null) {
                    modeloDetalles.setValueAt(
                            FormateadorUtils.formatearMoneda(itemActualizado.getSubtotal(), locale),
                            fila, 4);
                }
            } catch (NumberFormatException ex) {
                mostrarMensaje(mensaje.get("mensaje.carrito.cantidadInvalida"));
            }
        }
    }

    private ItemCarrito encontrarItem(int codigoProducto) {
        if (carritoActual == null) return null;
        for (ItemCarrito item : carritoActual.obtenerItems()) {
            if (item.getProducto().getCodigo() == codigoProducto) {
                return item;
            }
        }
        return null;
    }

    public void mostrarMensaje(String mensajes) {
        JOptionPane.showMessageDialog(this, mensajes, mensaje.get("confirm.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}


