package ec.edu.ups.vista.Producto;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Locale;
import java.util.List;

public class ProductoListaView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtBuscar;
    private JTable tblProductos;
    private JButton btnBuscar;
    private JButton btnListar;
    private JLabel lblNombre;
    private JLabel lblTitulo;

    private DefaultTableModel modelo;
    private final MensajeInternacionalizacionHandler Internacionalizar;
    private Locale locale;

    public ProductoListaView(MensajeInternacionalizacionHandler internacionalizar) {
        super(internacionalizar.get("producto.listar.menu"), true, true, true, true);
        this.Internacionalizar = internacionalizar;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 500);

        setFrameIcon(new ImageIcon(getClass().getResource("/listar.png")));
        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        btnListar.setIcon(new ImageIcon(getClass().getResource("/listar.png")));

        modelo = new DefaultTableModel();
        tblProductos.setModel(modelo);

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    public void actualizarIdioma(String language, String country) {
        Internacionalizar.setLenguaje(language, country);

        lblTitulo.setText(Internacionalizar.get("producto.listar.titulo"));
        lblNombre.setText(Internacionalizar.get("etiqueta.nombre"));
        btnBuscar.setText(Internacionalizar.get("boton.buscar"));
        btnListar.setText(Internacionalizar.get("boton.listar"));

        Object[] columnas = {
                Internacionalizar.get("etiqueta.codigo"),
                Internacionalizar.get("etiqueta.nombre"),
                Internacionalizar.get("etiqueta.precio")
        };
        modelo.setColumnIdentifiers(columnas);
    }

    public void cargarDatosListaProducto(List<Producto> productos) {
        modelo.setRowCount(0);

        if (productos == null || productos.isEmpty()) {
            return;
        }

        for (Producto producto : productos) {
            if (producto != null) {
                modelo.addRow(new Object[]{
                        producto.getCodigo(),
                        producto.getNombre(),
                        FormateadorUtils.formatearMoneda(producto.getPrecio(), Internacionalizar.getLocale())
                });
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JTextField getTxtBuscar() {return txtBuscar;}

    public JTable getTblProductos() { return tblProductos; }

    public JButton getBtnBuscar() { return btnBuscar; }

    public JButton getBtnListar() { return btnListar; }

    public DefaultTableModel getModelo() { return modelo; }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
        this.tblProductos.setModel(modelo);
    }
}
