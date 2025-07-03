package ec.edu.ups.vista.Carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CarritoListarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTable tblCarritos;
    private JButton btnListar;
    private JTable tblDetalles;
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JLabel lblDetalles;

    private DefaultTableModel modelo;
    private DefaultTableModel modeloDetalles;

    private List<Carrito> listaActual;
    private Carrito carritoActual;

    private MensajeInternacionalizacionHandler mensaje;
    private Locale locale;

    public CarritoListarView(MensajeInternacionalizacionHandler mensaje) {
        super("", true, true, false, true);
        this.mensaje = mensaje;
        this.locale = new Locale(mensaje.get("locale.lang"), mensaje.get("locale.country"));

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        // Íconos opcionales
        URL urlListar = getClass().getResource("/list.png");
        URL urlBuscar = getClass().getResource("/search.png");

        if (urlListar != null) btnListar.setIcon(new ImageIcon(urlListar));
        if (urlBuscar != null) btnBuscar.setIcon(new ImageIcon(urlBuscar));

        // Modelos de tablas
        modelo = new DefaultTableModel();
        tblCarritos.setModel(modelo);

        modeloDetalles = new DefaultTableModel();
        tblDetalles.setModel(modeloDetalles);

        actualizarTextos();
    }

    public void actualizarTextos() {
        this.locale = new Locale(mensaje.get("locale.lang"), mensaje.get("locale.country"));

        setTitle(mensaje.get("car.listar.titulo.app"));
        if (lblTitulo != null) lblTitulo.setText(mensaje.get("car.listar.titulo.app"));
        if (lblCodigo != null) lblCodigo.setText(mensaje.get("global.codigo") + ":");
        if (lblDetalles != null) lblDetalles.setText(mensaje.get("global.detalles"));

        txtCodigo.setToolTipText(mensaje.get("car.top.codigo"));

        btnBuscar.setText(mensaje.get("btn.buscar"));
        btnListar.setText(mensaje.get("menu.car.listar"));

        Object[] columnas = {
                mensaje.get("global.codigo"),
                mensaje.get("global.usuario"),
                mensaje.get("global.fecha"),
                mensaje.get("global.item"),
                mensaje.get("global.subtotal"),
                mensaje.get("global.iva"),
                mensaje.get("global.total")
        };
        modelo.setColumnIdentifiers(columnas);

        Object[] columnasDetalles = {
                mensaje.get("global.codigo"),
                mensaje.get("global.nombre"),
                mensaje.get("global.precio"),
                mensaje.get("global.cantidad"),
                mensaje.get("global.subtotal")
        };
        modeloDetalles.setColumnIdentifiers(columnasDetalles);

        mostrarDetallesCarrito(carritoActual);
        mostrarCarritos(listaActual);
    }

    public void mostrarCarritos(List<Carrito> carritos) {
        this.listaActual = carritos;
        modelo.setRowCount(0);
        if (carritos == null) return;

        limpiarTablaDetalles();

        for (Carrito carrito : carritos) {
            Object[] fila = {
                    carrito.getCodigo(),
                    carrito.getUsuario() != null ? carrito.getUsuario().getUsername() : "N/A",
                    carrito.getFecha() != null ? FormateadorUtils.formatearFecha(carrito.getFecha().getTime(), locale) : "N/A",
                    carrito.obtenerItems().size(),
                    FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale),
                    FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale),
                    FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale)
            };
            modelo.addRow(fila);
        }
    }

    public void mostrarDetallesCarrito(Carrito carrito) {
        this.carritoActual = carrito;
        limpiarTablaDetalles();

        if (carrito != null) {
            for (ItemCarrito item : carrito.obtenerItems()) {
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

    public void limpiarTablaDetalles() {
        modeloDetalles.setRowCount(0);
    }

    public void mostrarMensaje(String mensajes) {
        JOptionPane.showMessageDialog(this, mensajes, mensaje.get("confirm.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    // Getters & Setters
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtBuscar() {
        return btnBuscar;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JTable getTblCarritos() {
        return tblCarritos;
    }

    public JTable getTblDetalles() {
        return tblDetalles;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public DefaultTableModel getModeloDetalles() {
        return modeloDetalles;
    }

    public void setModeloDetalles(DefaultTableModel modeloDetalles) {
        this.modeloDetalles = modeloDetalles;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public void setBtBuscar(JButton btBuscar) {
        this.btnBuscar = btBuscar;
    }

    public void setTblCarritos(JTable tblCarritos) {
        this.tblCarritos = tblCarritos;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public void setTblDetalles(JTable tblDetalles) {
        this.tblDetalles = tblDetalles;
    }
}
