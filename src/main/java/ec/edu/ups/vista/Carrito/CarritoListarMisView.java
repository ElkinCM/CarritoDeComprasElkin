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

public class CarritoListarMisView extends JInternalFrame {
    private JTable tblCarritos;
    private JButton btnListar;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTable tblDetalles;
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JLabel lblListaCarrito;
    private JLabel lblCodigo;
    private Carrito carritoActual;
    private List<Carrito> listaActual;

    private DefaultTableModel modeloCarritos;
    private DefaultTableModel modeloDetalles;

    private MensajeInternacionalizacionHandler mensajes;
    private Locale locale;

    public CarritoListarMisView(MensajeInternacionalizacionHandler mensajes) {
        super("", true, true, false, true);
        this.mensajes = mensajes;
        this.locale = new Locale(mensajes.get("locale.language"), mensajes.get("locale.country"));

        setContentPane(panelPrincipal);
        setSize(600, 450);

        URL urlListar = getClass().getResource("/list.png");
        URL urlBuscar = getClass().getResource("/search.png");

        modeloCarritos = new DefaultTableModel();
        tblCarritos.setModel(modeloCarritos);

        modeloDetalles = new DefaultTableModel();
        tblDetalles.setModel(modeloDetalles);

        if (btnListar != null && urlListar != null)
            btnListar.setIcon(new ImageIcon(urlListar));
        if (btnBuscar != null && urlBuscar != null)
            btnBuscar.setIcon(new ImageIcon(urlBuscar));

        actualizarTextos();
    }

    public void actualizarTextos() {
        this.locale = new Locale(mensajes.get("locale.lang"), mensajes.get("locale.country"));

        setTitle(mensajes.get("menu.carrito.listarMis"));

        if (lblTitulo != null)
            lblTitulo.setText(mensajes.get("menu.carrito.listarMis"));
        if (lblListaCarrito != null)
            lblListaCarrito.setText(mensajes.get("menu.carrito.listar"));
        if (lblCodigo != null)
            lblCodigo.setText(mensajes.get("global.codigo") + ":");

        if (txtCodigo != null)
            txtCodigo.setToolTipText(mensajes.get("car.top.codigo"));

        if (btnListar != null)
            btnListar.setText(mensajes.get("menu.carrito.listarMis"));
        if (btnBuscar != null)
            btnBuscar.setText(mensajes.get("btn.buscar"));

        Object[] columnasCarritos = {
                mensajes.get("global.codigo"),
                mensajes.get("global.fecha"),
                mensajes.get("global.item"),
                mensajes.get("global.total")
        };
        modeloCarritos.setColumnIdentifiers(columnasCarritos);

        Object[] columnasDetalles = {
                mensajes.get("global.nombre"),
                mensajes.get("global.cantidad"),
                mensajes.get("global.precio"),
                mensajes.get("global.subtotal")
        };
        modeloDetalles.setColumnIdentifiers(columnasDetalles);

        if (listaActual != null) {
            mostrarCarritos(listaActual);
        }
        if (carritoActual != null) {
            mostrarDetalles(carritoActual);
        }
    }


    public void mostrarCarritos(List<Carrito> carritos) {
        this.listaActual = carritos;
        modeloCarritos.setRowCount(0);
        modeloDetalles.setRowCount(0);
        if (carritos == null) {
            return;
        }
        for (Carrito carrito : carritos) {
            modeloCarritos.addRow(new Object[]{
                    carrito.getCodigo(),
                    FormateadorUtils.formatearFecha(carrito.getFecha().getTime(), locale),
                    carrito.obtenerItems().size(),
                    FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale)
            });
        }
    }

    public void mostrarDetalles(Carrito carrito) {
        this.carritoActual = carrito;
        modeloDetalles.setRowCount(0);

        if (carrito != null) {
            for (ItemCarrito item : carrito.obtenerItems()) {
                modeloDetalles.addRow(new Object[]{
                        item.getProducto().getNombre(),
                        item.getCantidad(),
                        FormateadorUtils.formatearMoneda(item.getProducto().getPrecio(), locale),
                        FormateadorUtils.formatearMoneda(item.getSubtotal(), locale)
                });
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, mensajes.get("yesNo.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    // Getters para acceso desde controlador

    public JTable getTblCarritos() {
        return tblCarritos;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTable getTblDetalles() {
        return tblDetalles;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
