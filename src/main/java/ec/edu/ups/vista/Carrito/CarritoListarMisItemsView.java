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

public class CarritoListarMisItemsView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTable tblCarritos;
    private JTextField txtCodigo;
    private JLabel lblTitulo;
    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler Internacionalizar;
    private Locale locale;


    public CarritoListarMisItemsView (MensajeInternacionalizacionHandler Internacionalizar) {
        super(Internacionalizar.get("carrito.listar.menu"), true, true, true, true);
        this.Internacionalizar = Internacionalizar;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setFrameIcon(new ImageIcon(getClass().getResource("/carrito.png")));
        modelo = new DefaultTableModel();
        tblCarritos.setModel(modelo);

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    public void cargarDatosCarrito(Carrito carrito) {
        modelo.setRowCount(0);

        txtCodigo.setText(String.valueOf(carrito.getCodigo()));
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

    public void actualizarIdioma(String language, String country) {
        Internacionalizar.setLenguaje(language, country);
        this.locale = new Locale(Internacionalizar.get("locale.lang"), Internacionalizar.get("locale.country"));
        Object[] fila = {Internacionalizar.get("codigo.tabla"),Internacionalizar.get("nombre.tabla"),
                Internacionalizar.get("precio.tabla"),Internacionalizar.get("cantidad.tabla")};
        modelo.setColumnIdentifiers(fila);
        lblTitulo.setText(Internacionalizar.get("carrito.listar.titulo"));
        setTitle(Internacionalizar.get("carrito.listar.menu"));
    }

    public JTable getTblCarritos() {
        return tblCarritos;
    }
    public DefaultTableModel getModelo() { return modelo; }
}
