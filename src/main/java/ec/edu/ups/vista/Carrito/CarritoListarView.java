package ec.edu.ups.vista.Carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.GregorianCalendar;
import java.util.List;

public class CarritoListarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTable tblCarritos;
    private JButton btnListar;
    private JTable tblDetalles;
    private DefaultTableModel modelo;
    private DefaultTableModel modeloDetalles;

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

    public JTable getTblCarritos() {
        return tblCarritos;
    }

    public void setTblCarritos(JTable tblCarritos) {
        this.tblCarritos = tblCarritos;
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public void setBtnListar(JButton btnListar) {
        this.btnListar = btnListar;
    }

    public JTable getTable1() {
        return tblDetalles;
    }

    public void setTable1(JTable table1) {
        this.tblDetalles = table1;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public void setModelo(DefaultTableModel modelo) {
        this.modelo = modelo;
    }

    public CarritoListarView() {
        super("[ADMIN] Listar Carrito de compras", true, true, false, true);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);

        modelo = new DefaultTableModel();
        Object[] columnas = {"Código Carrito", "Usuario", "Fecha", "Cantidad Items", "Subtotal", "IVA", "Total"};
        modelo.setColumnIdentifiers(columnas);
        tblCarritos.setModel(modelo);

        modeloDetalles = new DefaultTableModel();
        Object[] columnasDetalles = {"Cod. Producto", "Nombre", "Precio Unit.", "Cantidad", "Subtotal"};
        modeloDetalles.setColumnIdentifiers(columnasDetalles);
        tblDetalles.setModel(modeloDetalles);
    }


    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtBuscar() {
        return btnBuscar;
    }

    public JButton getListarButton() {
        return btnListar;
    }

    public void mostrarCarritos(List<Carrito> carritos) {
        modelo.setRowCount(0);
        for (Carrito carrito : carritos) {
            Object[] fila = {
                    carrito.getCodigo(),
                    carrito.getUsuario() != null ? carrito.getUsuario().getUsername() : "N/A",
                    carrito.getFecha() != null ? String.format("%02d/%02d/%d", carrito.getFecha().get(GregorianCalendar.DAY_OF_MONTH), carrito.getFecha().get(GregorianCalendar.MONTH) + 1, carrito.getFecha().get(GregorianCalendar.YEAR)) : "N/A",
                    carrito.obtenerItems().size(),
                    String.format("%.2f", carrito.calcularSubtotal()),
                    String.format("%.2f", carrito.calcularIVA()),
                    String.format("%.2f", carrito.calcularTotal())
            };
            modelo.addRow(fila);
        }
    }


    public void mostrarDetallesCarrito(Carrito carrito) {
        limpiarTablaDetalles();

        if (carrito != null) {
            List<ItemCarrito> items = carrito.obtenerItems();
            for (ItemCarrito item : items) {
                Object[] fila = {
                        item.getProducto().getCodigo(),
                        item.getProducto().getNombre(),
                        item.getProducto().getPrecio(),
                        item.getCantidad(),
                        item.getSubtotal()
                };
                modeloDetalles.addRow(fila);
            }
        }
    }


    public void limpiarTablaDetalles() {
        modeloDetalles.setRowCount(0);
    }


    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
