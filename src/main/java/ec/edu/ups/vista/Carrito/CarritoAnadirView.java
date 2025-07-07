package ec.edu.ups.vista.Carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CarritoAnadirView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JTable tblItems;
    private JButton btnGuardar;
    private JButton btnBuscar;
    private JButton btnAñadir;
    private JButton btnCancelar;
    private JComboBox<String> cbxCantidad;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblCantidad;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private JLabel lblTitulo;
    private DefaultTableModel modelo;

    private final MensajeInternacionalizacionHandler Internacionalizar;
    private List<ItemCarrito> carritos;


    public CarritoAnadirView(MensajeInternacionalizacionHandler internacionalizar) {
        super(internacionalizar.get("carrito.anadir.menu"),true,true,true,true);
        this.Internacionalizar = internacionalizar;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,500);
        setFrameIcon(new ImageIcon(getClass().getResource("/carrito.png")));
        modelo = new DefaultTableModel();
        tblItems.setModel(modelo);

        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        btnAñadir.setIcon(new ImageIcon(getClass().getResource("/crear.png")));
        btnGuardar.setIcon(new ImageIcon(getClass().getResource("/guardar.png")));
        btnCancelar.setIcon(new ImageIcon(getClass().getResource("/cancelar.png")));

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        cargarcbx();
    }

    public void cargarDatosCarrito(Carrito carrito) {
        modelo.setRowCount(0);
        List<ItemCarrito> items = carrito.obtenerItems();

        for (ItemCarrito item : items) {
            Producto producto = item.getProducto();
            String codigo = String.valueOf(producto.getCodigo());
            String nombre = producto.getNombre();
            String precio = FormateadorUtils.formatearMoneda(producto.getPrecio(), Internacionalizar.getLocale());
            int cantidad = item.getCantidad();

            Object[] fila = { codigo, nombre, precio, cantidad };

            modelo.addRow(fila);
        }
    }

    private void cargarcbx() {
        cbxCantidad.removeAllItems();
        for (int i = 1; i<=20; i++) {
            cbxCantidad.addItem(String.valueOf(i));
        }
    }

    public void vaciarCampo(){
        cbxCantidad.setSelectedIndex(0);
        modelo.setRowCount(0);
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtSubtotal.setText("");
        txtIVA.setText("");
        txtTotal.setText("");
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void actualizarIdioma(String language, String country) {
        Internacionalizar.setLenguaje(language, country);
        Object[] fila = {Internacionalizar.get("codigo.tabla"),Internacionalizar.get("nombre.tabla"),
                Internacionalizar.get("precio.tabla"),Internacionalizar.get("cantidad.tabla")};
        modelo.setColumnIdentifiers(fila);
        lblTitulo.setText(Internacionalizar.get("carrito.anadir.titulo"));
        lblCodigo.setText(Internacionalizar.get("etiqueta.codigo"));
        lblNombre.setText(Internacionalizar.get("etiqueta.nombre"));
        lblPrecio.setText(Internacionalizar.get("etiqueta.precio"));
        lblCantidad.setText(Internacionalizar.get("etiqueta.cantidad"));
        lblSubtotal.setText(Internacionalizar.get("etiqueta.subtotal"));
        lblIVA.setText(Internacionalizar.get("etiqueta.iva"));
        lblTotal.setText(Internacionalizar.get("etiqueta.total"));
        btnBuscar.setText(Internacionalizar.get("boton.buscar"));
        btnAñadir.setText(Internacionalizar.get("boton.anadir"));
        btnGuardar.setText(Internacionalizar.get("boton.guardar"));
        btnCancelar.setText(Internacionalizar.get("boton.cancelar"));
    }

    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtPrecio() { return txtPrecio; }
    public JTextField getTxtSubtotal() { return txtSubtotal; }
    public JTextField getTxtIVA() { return txtIVA; }
    public JTextField getTxtTotal() { return txtTotal; }
    public JTable getTblItems() { return tblItems; }
    public void setTblItems(JTable tblItems) { this.tblItems = tblItems; }
    public JButton getBtnGuardar() { return btnGuardar; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnAñadir() { return btnAñadir; }
    public JButton getBtnCancelar() { return btnCancelar; }
    public JComboBox<String> getCbxCantidad() { return cbxCantidad; }
    public JLabel getLblCodigo() { return lblCodigo; }
    public void setLblCodigo(JLabel lblCodigo) { this.lblCodigo = lblCodigo; }
    public JLabel getLblNombre() { return lblNombre; }
    public void setLblNombre(JLabel lblNombre) { this.lblNombre = lblNombre; }
    public void setModelo(DefaultTableModel modelo) { this.modelo = modelo; }
    public JLabel getLblTitulo() { return lblTitulo; }
    public void setLblTitulo(JLabel lblTitulo) { this.lblTitulo = lblTitulo; }

}



