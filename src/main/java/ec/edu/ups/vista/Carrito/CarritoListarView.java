package ec.edu.ups.vista.Carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CarritoListarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTable tblCarritos;
    private JButton btnListar;
    private JLabel lblTitulo;
    private JLabel lblCodigo;
    private JButton btnDetallar;

    private DefaultTableModel modelo;
    private MensajeInternacionalizacionHandler Internacionalizar;

    public CarritoListarView(MensajeInternacionalizacionHandler Internacionalizar) {
        super(Internacionalizar.get("carrito.listar.menu"), true, true, true, true);
        this.Internacionalizar = Internacionalizar;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setFrameIcon(new ImageIcon(getClass().getResource("/carrito.png")));
        modelo = new DefaultTableModel();
        tblCarritos.setModel(modelo);
        
        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        btnListar.setIcon(new ImageIcon(getClass().getResource("/listar.png")));
        btnDetallar.setIcon(new ImageIcon(getClass().getResource("/detallar.png")));

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    public void cargarDatosCarritoLista(List<Carrito> carritos) {
        modelo.setRowCount(0);
        for (Carrito carrito : carritos) {
            String codigo = String.valueOf(carrito.getCodigo());
            String fecha = FormateadorUtils.formatearFecha(carrito.getFechaCrear(), Internacionalizar.getLocale());
            String usuario = carrito.getUsuario().getUsername();
            String subtotal = FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), Internacionalizar.getLocale());
            String iva = FormateadorUtils.formatearMoneda(carrito.calcularIVA(), Internacionalizar.getLocale());
            String total = FormateadorUtils.formatearMoneda(carrito.calcularTotal(), Internacionalizar.getLocale());

            Object[] fila = { codigo, fecha, usuario, subtotal, iva, total };

            modelo.addRow(fila);
        }
    }

    public void cargarDatosBuscar(Carrito carrito) {
        modelo.setRowCount(0);

        String codigo = String.valueOf(carrito.getCodigo());
        String fecha = FormateadorUtils.formatearFecha(carrito.getFechaCrear(), Internacionalizar.getLocale());
        String usuario = carrito.getUsuario().getUsername();
        String subtotal = FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), Internacionalizar.getLocale());
        String iva = FormateadorUtils.formatearMoneda(carrito.calcularIVA(), Internacionalizar.getLocale());
        String total = FormateadorUtils.formatearMoneda(carrito.calcularTotal(), Internacionalizar.getLocale());

        Object[] fila = { codigo, fecha, usuario, subtotal, iva, total };

        modelo.addRow(fila);
    }

    public void vaciarCampo() {
        modelo.setRowCount(0);
        txtCodigo.setText("");
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    public void actualizarIdioma(String language, String country) {
        Internacionalizar.setLenguaje(language, country);
        Object[] fila = {Internacionalizar.get("codigo.tabla"), Internacionalizar.get("fecha.tabla"), Internacionalizar.get("usuario.tabla"),
                Internacionalizar.get("etiqueta.subtotal"), Internacionalizar.get("etiqueta.iva"), Internacionalizar.get("etiqueta.total")};
        modelo.setColumnIdentifiers(fila);
        lblTitulo.setText(Internacionalizar.get("carrito.listar.titulo"));
        lblCodigo.setText(Internacionalizar.get("etiqueta.codigo"));
        btnBuscar.setText(Internacionalizar.get("boton.buscar"));
        btnListar.setText(Internacionalizar.get("boton.listar"));
        btnDetallar.setText(Internacionalizar.get("boton.detallar"));
    }

    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnListar() { return btnListar; }
    public JButton getBtnDetallar() { return btnDetallar; }
    public JTable getTblCarritos() { return tblCarritos; }
    public DefaultTableModel getModelo() { return modelo; }
    public JTextField getTxtCodigo() { return txtCodigo; }
}
