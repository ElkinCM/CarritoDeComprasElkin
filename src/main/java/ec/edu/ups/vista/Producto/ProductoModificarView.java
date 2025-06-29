package ec.edu.ups.vista.Producto;

import javax.swing.*;

public class ProductoModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnBuscar;
    private JLabel lblCodigo;
    private JTextField txtModificar;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JButton btnModificar;
    private JTextField txtNombre;
    private JTextField txtPrecio;


    public ProductoModificarView() {
        setContentPane(panelPrincipal);
        setTitle("Modificar Producto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500,500);
        setResizable(false);
        //setVisible(true);
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtModificar() {
        return txtModificar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }



    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
