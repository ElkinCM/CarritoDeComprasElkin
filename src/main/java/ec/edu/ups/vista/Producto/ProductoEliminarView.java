package ec.edu.ups.vista.Producto;

import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Locale;

public class ProductoEliminarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JLabel lblTitulo;
    private JLabel lblNombre;
    private JLabel lblCodigo;
    private JLabel lblPrecio;
    private JTextField txtCodigo;

    private final MensajeInternacionalizacionHandler Internacionalizar;
    private Locale locale;

    public ProductoEliminarView(MensajeInternacionalizacionHandler internacionalizar) {
        super(internacionalizar.get("producto.eliminar.menu"),true,true,false,true);
        Internacionalizar = internacionalizar;
        setContentPane(panelPrincipal);
        setSize(300,200);

        setFrameIcon(new ImageIcon(getClass().getResource("/eliminar.png")));
        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/eliminar.png")));
        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    public void actualizarIdioma(String language, String country) {
        Internacionalizar.setLenguaje(language, country);
        lblTitulo.setText(Internacionalizar.get("producto.eliminar.titulo"));
        lblCodigo.setText(Internacionalizar.get("etiqueta.codigo"));
        lblNombre.setText(Internacionalizar.get("etiqueta.nombre"));
        lblPrecio.setText(Internacionalizar.get("etiqueta.precio"));
        btnBuscar.setText(Internacionalizar.get("boton.buscar"));
        btnEliminar.setText(Internacionalizar.get("boton.eliminar"));
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
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
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

    public void vaciarCampo(){
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCodigo.setEnabled(true);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

}

