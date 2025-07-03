package ec.edu.ups.vista.Producto;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductoEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnEliminar;
    private JButton BtnBuscar;
    private JLabel lblTitulo;
    private JLabel lblNombre;
    private JLabel lblCodigo;
    private JLabel lblPrecio;
    private JTextField txtCodigo;

    private final MensajeInternacionalizacionHandler mensaje;

    public ProductoEliminarView(MensajeInternacionalizacionHandler mensaje) {
        super(mensaje.get("prod.eliminar.titulo.app"),true,true,false,true);
    this.mensaje = mensaje;

    setContentPane(panelPrincipal);
    setSize(500,500);

    actualizartexto();
    }

    private void actualizartexto() {
        setTitle(mensaje.get("prod.eliminar.titulo.app"));

        lblTitulo.setText(mensaje.get("prod.eliminar.titulo"));
        lblCodigo.setText(mensaje.get("global.codigo"));
        lblNombre.setText(mensaje.get("global.nombre"));
        lblPrecio.setText(mensaje.get("global.precio"));
        txtCodigo.setToolTipText(mensaje.get("prod.top.codigo"));
        BtnBuscar.setText(mensaje.get("btn.buscar"));
        btnEliminar.setText(mensaje.get("btn.eliminar"));
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JButton getBtnBuscar() {
        return BtnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.BtnBuscar = btnBuscar;
    }

    public JLabel getLblCodigo() {
        return lblCodigo;
    }

    public void setLblCodigo(JLabel lblCodigo) {
        this.lblCodigo = lblCodigo;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JLabel getLblPrecio() {
        return lblPrecio;
    }

    public void setLblPrecio(JLabel lblPrecio) {
        this.lblPrecio = lblPrecio;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public JLabel getLblTitulo() {
        return lblTitulo;
    }

    public void setLblTitulo(JLabel lblTitulo) {
        this.lblTitulo = lblTitulo;
    }

    public void limpiarCampos(){
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCodigo.setEnabled(true);
    }

    public void mostrarMensaje(String textoMensaje) {
        JOptionPane.showMessageDialog(this, textoMensaje, mensaje.get("confirm.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

}

