package ec.edu.ups.vista.Usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.net.URL;

public class UsuarioEliminarView extends JInternalFrame {
    private JLabel lblUsuario;
    private JButton btnBuscar;
    private JTextField txtUsuario;
    private JLabel lblContraseña;
    private JLabel lblRol;
    private JTextField txtContraseña;
    private JButton btnEliminar;
    private JTextField txtRol;
    private JPanel paneltitulo;
    private JPanel panelPrincipal;

    private MensajeInternacionalizacionHandler mensaje;

    public UsuarioEliminarView(MensajeInternacionalizacionHandler mensaje) {
        super("", true, true, false, true);
        this.mensaje = mensaje;

        setSize(600, 400);
        setContentPane(panelPrincipal);

        // Aquí puedes cargar íconos si cuentas con ellos
        URL urlBuscar = getClass().getResource("/search.png");
        if (urlBuscar != null) {
            btnBuscar.setIcon(new ImageIcon(urlBuscar));
        }
        URL urlEliminar = getClass().getResource("/trash.png");
        if (urlEliminar != null) {
            btnEliminar.setIcon(new ImageIcon(urlEliminar));
        }

        actualizarTextos();
    }

    public void actualizarTextos() {
        setTitle(mensaje.get("usr.eliminar.titulo.app"));
        lblUsuario.setText(mensaje.get("global.usuario") + ":");
        lblContraseña.setText(mensaje.get("global.pass") + ":");
        lblRol.setText(mensaje.get("global.rol") + ":");
        txtUsuario.setToolTipText(mensaje.get("usr.crear.nombre"));
        btnBuscar.setText(mensaje.get("btn.buscar"));
        btnEliminar.setText(mensaje.get("btn.eliminar"));
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JLabel getLblContraseña() {
        return lblContraseña;
    }

    public void setLblContraseña(JLabel lblContraseña) {
        this.lblContraseña = lblContraseña;
    }

    public JLabel getLblRol() {
        return lblRol;
    }

    public void setLblRol(JLabel lblRol) {
        this.lblRol = lblRol;
    }

    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    public void setTxtContraseña(JTextField txtContraseña) {
        this.txtContraseña = txtContraseña;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public JTextField getTxtRol() {
        return txtRol;
    }

    public void setTxtRol(JTextField txtRol) {
        this.txtRol = txtRol;
    }

    public JPanel getPaneltitulo() {
        return paneltitulo;
    }

    public void setPaneltitulo(JPanel paneltitulo) {
        this.paneltitulo = paneltitulo;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public void mostrarMensaje(String mensajes) {
        JOptionPane.showMessageDialog(this, mensajes, mensaje.get("confirm.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContraseña.setText("");
        txtRol.setText("");
        btnEliminar.setEnabled(false);
        txtContraseña.setEnabled(false);
        txtRol.setEnabled(false);
        txtUsuario.setEditable(true);
        btnBuscar.setEnabled(true);
    }
}
