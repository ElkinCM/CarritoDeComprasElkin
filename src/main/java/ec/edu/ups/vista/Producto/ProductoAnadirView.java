package ec.edu.ups.vista.Producto;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class ProductoAnadirView extends JInternalFrame {

    private JPanel panelPrincipal;
    private JTextField txtPrecio;
    private JTextField txtNombre;
    private JTextField txtCodigo;
    private JButton btnAceptar;
    private JButton btnLimpiar;
    private JLabel lblCodigo;
    private JLabel lblNombre;
    private JLabel lblPrecio;
    private JLabel lblTitulo;

    private MensajeInternacionalizacionHandler Internacionalizar;
    private Locale locale;

    public ProductoAnadirView(MensajeInternacionalizacionHandler internacionalizar) {
        super(internacionalizar.get("producto.anadir.menu"), true, true, true, true);
        this.Internacionalizar = internacionalizar;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);

        setFrameIcon(new ImageIcon(getClass().getResource("/crear.png")));
        btnAceptar.setIcon(new ImageIcon(getClass().getResource("/aceptar.png")));
        btnLimpiar.setIcon(new ImageIcon(getClass().getResource("/limpiar.png")));

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vaciarCampo();
            }
        });

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    public void actualizarIdioma(String language, String country) {
        Internacionalizar.setLenguaje(language, country);
        lblTitulo.setText(Internacionalizar.get("producto.anadir.titulo"));
        lblCodigo.setText(Internacionalizar.get("etiqueta.codigo"));
        lblNombre.setText(Internacionalizar.get("etiqueta.nombre"));
        lblPrecio.setText(Internacionalizar.get("etiqueta.precio"));

        btnAceptar.setText(Internacionalizar.get("boton.aceptar"));
        btnLimpiar.setText(Internacionalizar.get("boton.limpiar"));
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTextField getTxtPrecio() {
        return txtPrecio;
    }

    public void setTxtPrecio(JTextField txtPrecio) {
        this.txtPrecio = txtPrecio;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public void setTxtCodigo(JTextField txtCodigo) {
        this.txtCodigo = txtCodigo;
    }

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    public void setBtnLimpiar(JButton btnLimpiar) {
        this.btnLimpiar = btnLimpiar;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void vaciarCampo() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
    }
}

