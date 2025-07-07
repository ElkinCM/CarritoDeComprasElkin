package ec.edu.ups.vista.Carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
public class CarritoModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextField txtUsuario;
    private JTextField txtFecha;
    private JLabel lblCodigo;
    private JLabel lblUsuario;
    private JLabel lblFecha;
    private JButton btnModificar;
    private JLabel lblTitulo;
    private JButton btnAnadir;
    private JButton btnEliminar;
    private JTextField txtSubtotal;
    private JTextField txtIVA;
    private JTextField txtTotal;
    private JLabel lblSubtotal;
    private JLabel lblIVA;
    private JLabel lblTotal;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler Internacionalizar;
    private JTable tblItems;


    public CarritoModificarView(MensajeInternacionalizacionHandler Internacionalizar) {
        super(Internacionalizar.get("carrito.modificar.menu"),true,true,true,true);
        this.Internacionalizar = Internacionalizar;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,500);
        setFrameIcon(new ImageIcon(getClass().getResource("/carrito.png")));
        modelo = new DefaultTableModel();

        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        btnAnadir.setIcon(new ImageIcon(getClass().getResource("/crear.png")));
        btnModificar.setIcon(new ImageIcon(getClass().getResource("/modificar.png")));
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
            String precio = FormateadorUtils.formatearMoneda(producto.getPrecio(), Internacionalizar.getLocale());
            int cantidad = item.getCantidad();

            Object[] fila = { codigo, nombre, precio, cantidad };

            modelo.addRow(fila);
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void vaciarCampo() {
        modelo.setRowCount(0);
        txtCodigo.setText("");
        txtUsuario.setText("");
        txtFecha.setText("");
        txtSubtotal.setText("");
        txtIVA.setText("");
        txtTotal.setText("");
    }

    public void actualizarIdioma(String language, String country) {
        Internacionalizar.setLenguaje(language, country);
        Object[] fila = {Internacionalizar.get("codigo.tabla"),Internacionalizar.get("nombre.tabla"),
                Internacionalizar.get("precio.tabla"),Internacionalizar.get("cantidad.tabla")};
        modelo.setColumnIdentifiers(fila);
        lblTitulo.setText(Internacionalizar.get("carrito.modificar.titulo"));
        lblCodigo.setText(Internacionalizar.get("etiqueta.codigo"));
        lblUsuario.setText(Internacionalizar.get("etiqueta.usuario"));
        lblFecha.setText(Internacionalizar.get("etiqueta.fecha"));
        btnBuscar.setText(Internacionalizar.get("boton.buscar"));
        btnAnadir.setText(Internacionalizar.get("boton.anadir"));
        btnModificar.setText(Internacionalizar.get("boton.modificar"));
        btnEliminar.setText(Internacionalizar.get("boton.eliminar"));
        lblSubtotal.setText(Internacionalizar.get("etiqueta.subtotal"));
        lblIVA.setText(Internacionalizar.get("etiqueta.iva"));
        lblTotal.setText(Internacionalizar.get("etiqueta.total"));
    }

    public JTextField getTxtCodigo() {return txtCodigo;}
    public JTextField getTxtUsuario() {return txtUsuario;}
    public JTextField getTxtFecha() {return txtFecha;}
    public JTextField getTxtSubtotal() {return txtSubtotal;}
    public JTextField getTxtIVA() {return txtIVA;}
    public JTextField getTxtTotal() {return txtTotal;}
    public JButton getBtnBuscar() {return btnBuscar;}
    public JButton getBtnAnadir() {return btnAnadir;}
    public JButton getBtnModificar() {return btnModificar;}
    public JButton getBtnEliminar() {return btnEliminar;}
    public DefaultTableModel getModelo() {return modelo;}
    public JTable getTblItems() {return tblItems;}
}



