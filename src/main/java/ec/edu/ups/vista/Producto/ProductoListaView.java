package ec.edu.ups.vista.Producto;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.List;
import java.util.Locale;

public class ProductoListaView extends JInternalFrame {
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JLabel lblBuscar;
    private JTable tblProductos;
    private JPanel panelPrincipal;
    private JButton btnListar;
    private JLabel lblTitulo;
    private DefaultTableModel modelo;
    private Locale locale;
    private List<Producto> productosActuales;

    private MensajeInternacionalizacionHandler mensaje;

    public ProductoListaView(MensajeInternacionalizacionHandler mensaje) {
        super("", true, true, true, true); // Minimizable, maximizable, etc.
        this.mensaje = mensaje;
        this.locale = new Locale(mensaje.get("locale.lang"), mensaje.get("locale.country"));

        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 500);

        // Iconos
        URL urlBuscar = getClass().getResource("/search.png");
        URL urlListar = getClass().getResource("/list.png");
        if (urlBuscar != null) btnBuscar.setIcon(new ImageIcon(urlBuscar));
        if (urlListar != null) btnListar.setIcon(new ImageIcon(urlListar));

        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        actualizarTextos();
    }

    public void actualizarTextos() {
        this.locale = new Locale(mensaje.get("locale.lang"), mensaje.get("locale.country"));
        setTitle(mensaje.get("prod.listar.titulo.app"));

        lblTitulo.setText(mensaje.get("prod.listar.titulo"));
        lblBuscar.setText(mensaje.get("btn.buscar") + ":");
        txtBuscar.setToolTipText(mensaje.get("prod.top.nombre"));
        btnBuscar.setText(mensaje.get("btn.buscar"));
        btnListar.setText(mensaje.get("prod.listar.titulo.app"));

        Object[] columnas = {
                mensaje.get("global.codigo"),
                mensaje.get("global.nombre"),
                mensaje.get("global.precio")
        };
        modelo.setColumnIdentifiers(columnas);
        mostrarProductos(productosActuales);
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JLabel getLblBuscar() {
        return lblBuscar;
    }

    public void setLblBuscar(JLabel lblBuscar) {
        this.lblBuscar = lblBuscar;
    }

    public JTable getTblProductos() {
        return tblProductos;
    }

    public void setTblProductos(JTable tblProductos) {
        this.tblProductos = tblProductos;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public void mostrarProductos(List<Producto> productos) {
        productosActuales = productos;
        if (productos == null) return;
        modelo.setRowCount(0); // Limpiar tabla
        for (Producto producto : productos) {
            Object[] fila = {
                    producto.getCodigo(),
                    producto.getNombre(),
                    FormateadorUtils.formatearMoneda(producto.getPrecio(), locale)
            };
            modelo.addRow(fila);
        }
    }
}
