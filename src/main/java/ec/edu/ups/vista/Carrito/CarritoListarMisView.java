package ec.edu.ups.vista.Carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.GregorianCalendar;
import java.util.List;

public class CarritoListarMisView extends JInternalFrame {
    private JTable tblCarritos;
    private JButton btnListar;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTable tblDetalles;
    private JPanel panelPrincipal;

    private DefaultTableModel modeloCarritos;
    private DefaultTableModel modeloDetalles;

    public CarritoListarMisView() {
        super("Mis Carritos de Compra", true, true, false, true); // Título claro
        setSize(600, 400);
        setContentPane(panelPrincipal);

        btnListar.setText("Ver Mis Carritos");
        btnBuscar.setText("Ver Detalles");

        configurarTablas();
    }

    private void configurarTablas() {
        modeloCarritos = new DefaultTableModel();
        modeloCarritos.setColumnIdentifiers(new Object[]{"Código", "Fecha", "N° Items", "Total"});
        tblCarritos.setModel(modeloCarritos);

        modeloDetalles = new DefaultTableModel();
        modeloDetalles.setColumnIdentifiers(new Object[]{"Producto", "Cantidad", "Precio Unit.", "Subtotal"});
        tblDetalles.setModel(modeloDetalles);
    }

    public void mostrarCarritos(List<Carrito> carritos) {
        modeloCarritos.setRowCount(0); // Limpiar tabla de carritos
        modeloDetalles.setRowCount(0); // Limpiar tabla de detalles

        for (Carrito carrito : carritos) {
            modeloCarritos.addRow(new Object[]{
                    carrito.getCodigo(),
                    String.format("%02d/%02d/%d",
                            carrito.getFecha().get(GregorianCalendar.DAY_OF_MONTH),
                            carrito.getFecha().get(GregorianCalendar.MONTH) + 1,
                            carrito.getFecha().get(GregorianCalendar.YEAR)),
                    carrito.obtenerItems().size(),
                    String.format("%.2f", carrito.calcularTotal())
            });
        }
    }


    public void mostrarDetalles(Carrito carrito) {
        modeloDetalles.setRowCount(0);

        if (carrito != null) {
            for (ItemCarrito item : carrito.obtenerItems()) {
                modeloDetalles.addRow(new Object[]{
                        item.getProducto().getNombre(),
                        item.getCantidad(),
                        String.format("%.2f", item.getProducto().getPrecio()),
                        String.format("%.2f", item.getSubtotal())
                });
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public JTable getTblCarritos() {
        return tblCarritos;
    }
    public JButton getBtnListar() {
        return btnListar;
    }
    public JTextField getTxtCodigo() {
        return txtCodigo;
    }
    public JButton getBtBuscar() {
        return btnBuscar;
    }
    public JTable getTblDetalles() {
        return tblDetalles;
    }
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
}
