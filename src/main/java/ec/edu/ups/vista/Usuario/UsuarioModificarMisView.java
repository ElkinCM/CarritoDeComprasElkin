package ec.edu.ups.vista.Usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class UsuarioModificarMisView extends JInternalFrame {
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JTextField txtContraseña;
    private JButton btnModificar;
    private JTextField txtUsuario;
    private JPanel panelPrincipal;

    private MensajeInternacionalizacionHandler mensaje;

    public UsuarioModificarMisView(MensajeInternacionalizacionHandler mensaje){
        super(mensaje.get("usr.modificar.titulo.app"), true, true, false, true);
        this.mensaje = mensaje;
        setContentPane(panelPrincipal);
        setSize(600,400);

        lblUsuario.setText(mensaje.get("global.usuario") + ":");
        lblContraseña.setText(mensaje.get("global.pass") + ":");
        btnModificar.setText(mensaje.get("btn.modificar"));

        txtUsuario.setEditable(false);
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    public JLabel getLblContraseña() {
        return lblContraseña;
    }

    public void setLblContraseña(JLabel lblContraseña) {
        this.lblContraseña = lblContraseña;
    }

    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    public void setTxtContraseña(JTextField txtContraseña) {
        this.txtContraseña = txtContraseña;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public void setBtnModificar(JButton btnModificar) {
        this.btnModificar = btnModificar;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public void mostrarMensaje(String mensajeTexto) {
        JOptionPane.showMessageDialog(this, mensajeTexto, mensaje.get("confirm.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public void limpiarCampos() {
        txtContraseña.setText("");
        btnModificar.setEnabled(false);
        txtUsuario.setEditable(true);
    }
}
