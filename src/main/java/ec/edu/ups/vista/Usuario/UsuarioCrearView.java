package ec.edu.ups.vista.Usuario;

import ec.edu.ups.modelo.Rol;

import javax.swing.*;

public class UsuarioCrearView extends JInternalFrame {
    private JLabel lblUsuario;;
    private JLabel lblUsuarioA;
    private JLabel lblContraseña;
    private JLabel lblRol;
    private JTextField txtUsuario;
    private JTextField txtContraseña;
    private JButton btnCrear;
    private JComboBox cbxRoles;
    private JPanel panelPrincipal;

    public UsuarioCrearView() {
        super("",true,true,false,true);
        setSize(600,400);
        setContentPane(panelPrincipal);
        cargarRoles();
    }
    private void cargarRoles() {
        cbxRoles.removeAllItems();
        for (Rol rol : Rol.values()) {
            cbxRoles.addItem(rol);
        }
    }

    public JLabel getLblUsuario() {
        return lblUsuario;
    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }


    public JLabel getLblUsuarioA() {
        return lblUsuarioA;
    }

    public void setLblUsuarioA(JLabel lblUsuarioA) {
        this.lblUsuarioA = lblUsuarioA;
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

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    public void setTxtContraseña(JTextField txtContraseña) {
        this.txtContraseña = txtContraseña;
    }

    public JButton getBtnCrear() {
        return btnCrear;
    }

    public void setBtnCrear(JButton btnCrear) {
        this.btnCrear = btnCrear;
    }

    public JComboBox getCbxRoles() {
        return cbxRoles;
    }

    public void setCbxRoles(JComboBox cbxRoles) {
        this.cbxRoles = cbxRoles;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContraseña.setText("");
        cbxRoles.setSelectedIndex(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
