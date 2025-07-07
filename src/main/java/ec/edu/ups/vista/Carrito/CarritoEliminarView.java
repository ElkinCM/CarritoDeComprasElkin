package ec.edu.ups.vista.Carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
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
    private JLabel lblTitulo;
    private DefaultTableModel modelo;

    private MensajeInternacionalizacionHandler Internacionalizar;

    public CarritoEliminarView(MensajeInternacionalizacionHandler Internacionalizar) {
        super(Internacionalizar.get("carrito.eliminar.menu"), true, true, true, true);
        this.Internacionalizar = Internacionalizar;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setFrameIcon(new ImageIcon(getClass().getResource("/carrito_eliminar.png")));
        modelo = new DefaultTableModel();
        tblItems.setModel(modelo);

        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/eliminar.png")));

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    public void cargarDatosCarrito(Carrito carrito) {
        modelo.setRowCount(0);
        List<ItemCarrito> items = carrito.obtenerItems();

        for (ItemCarrito item : items) {
            Producto producto = item.getProducto();
            String codigo = String.valueOf(producto.getCodigo());
            String nombre = producto.getNombre();
            String precioFormateado = FormateadorUtils.formatearMoneda(producto.getPrecio(), Internacionalizar.getLocale());
            int cantidad = item.getCantidad();

            Object[] fila = { codigo, nombre, precioFormateado, cantidad };

            modelo.addRow(fila);
        }
    }

    public void vaciarCampo(){
        modelo.setRowCount(0);
        txtCodigo.setText("");
        txtUsuario.setText("");
        txtFecha.setText("");
    }

    public void VaciarTabla() {
        modelo.setRowCount(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void actualizarIdioma(String languje, String country) {
        Internacionalizar.setLenguaje(languje, country);
        Object[] fila = {Internacionalizar.get("codigo.tabla"),Internacionalizar.get("nombre.tabla"),
                Internacionalizar.get("precio.tabla"), Internacionalizar.get("cantidad.tabla")};
        modelo.setColumnIdentifiers(fila);
        lblTitulo.setText(Internacionalizar.get("carrito.eliminar.titulo"));
        lblCodigo.setText(Internacionalizar.get("etiqueta.codigo"));
        lblUsuario.setText(Internacionalizar.get("etiqueta.usuario"));
        lblFecha.setText(Internacionalizar.get("etiqueta.fecha"));
        btnBuscar.setText(Internacionalizar.get("boton.buscar"));
        btnEliminar.setText(Internacionalizar.get("boton.eliminar"));
    }

    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtUsuario() { return txtUsuario; }
    public JTextField getTxtFecha() { return txtFecha; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnEliminar() { return btnEliminar; }
    public DefaultTableModel getModelo() { return modelo; }
    public void setModelo (DefaultTableModel modelo) { this.modelo = modelo; }
}
