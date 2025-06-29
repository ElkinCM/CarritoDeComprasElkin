package ec.edu.ups.vista.Carrito;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CarritoModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JTextField txtUsuario;
    private JTextField txtFecha;
    private JTable tblItems;
    private JLabel lblCodigo;
    private JLabel lblUsuario;
    private JLabel lblFecha;
    private JLabel lblItems;
    private JButton btnModificar;
    private DefaultTableModel modeloDetalles;
    private Carrito carritoActual;

    public CarritoModificarView() {
        super("Modificar Carrito", true, true, false, true);
        setContentPane(panelPrincipal);
        setSize(600, 400);

        modeloDetalles = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        Object[] columnasDetalles = {"Cod. Producto", "Nombre", "Precio Unit.", "Cantidad", "Subtotal"};
        modeloDetalles.setColumnIdentifiers(columnasDetalles);
        tblItems.setModel(modeloDetalles);

        modeloDetalles.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    if (column == 3 && carritoActual != null) {
                        int codigoProducto = (int) modeloDetalles.getValueAt(row, 0);
                        int nuevaCantidad = Integer.parseInt(modeloDetalles.getValueAt(row, 3).toString());

                        carritoActual.actualizarCantidadProducto(codigoProducto, nuevaCantidad);

                        ItemCarrito itemActualizado = encontrarItem(codigoProducto);
                        if (itemActualizado != null) {
                            modeloDetalles.setValueAt(itemActualizado.getSubtotal(), row, 4);
                        }

                    }
                }
            }
        });

        btnModificar.setEnabled(false);
    }

    private ItemCarrito encontrarItem(int codigoProducto) {
        for (ItemCarrito item : carritoActual.obtenerItems()) {
            if (item.getProducto().getCodigo() == codigoProducto) {
                return item;
            }
        }
        return null;
    }

    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public void setPanelPrincipal(JPanel panelPrincipal) { this.panelPrincipal = panelPrincipal; }
    public JTextField getTxtCodigo() { return txtCodigo; }
    public void setTxtCodigo(JTextField txtCodigo) { this.txtCodigo = txtCodigo; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public void setBtnBuscar(JButton btnBuscar) { this.btnBuscar = btnBuscar; }
    public JTextField getTxtUsuario() { return txtUsuario; }
    public void setTxtUsuario(JTextField txtUsuario) { this.txtUsuario = txtUsuario; }
    public JTextField getTxtFecha() { return txtFecha; }
    public void setTxtFecha(JTextField txtFecha) { this.txtFecha = txtFecha; }
    public JTable getTblItems() { return tblItems; }
    public void setTblItems(JTable tblItems) { this.tblItems = tblItems; }
    public JLabel getLblCodigo() { return lblCodigo; }
    public void setLblCodigo(JLabel lblCodigo) { this.lblCodigo = lblCodigo; }
    public JLabel getLblUsuario() { return lblUsuario; }
    public void setLblUsuario(JLabel lblUsuario) { this.lblUsuario = lblUsuario; }
    public JLabel getLblFecha() { return lblFecha; }
    public void setLblFecha(JLabel lblFecha) { this.lblFecha = lblFecha; }
    public JLabel getLblItems() { return lblItems; }
    public void setLblItems(JLabel lblItems) { this.lblItems = lblItems; }
    public JButton getBtnModificar() { return btnModificar; }
    public void setBtnModificar(JButton btnModificar) { this.btnModificar = btnModificar; }


    public void mostrarItemsCarrito(Carrito carrito) {
        this.carritoActual = carrito; // Guardar la referencia al carrito
        modeloDetalles.setRowCount(0);

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

    public void mostrarMensaje(String s) {
        JOptionPane.showMessageDialog(this, s, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
