package ec.edu.ups.vista.Usuario;

import ec.edu.ups.modelo.Rol;

import javax.swing.*;

public class UsuarioModificarView extends JInternalFrame{
    private JPanel panelPrincipal;
    private JButton btnBuscar;
    private JTextField txtContraseña;
    private JButton btnModificar;
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JLabel lblRol;
    private JComboBox<Rol> cbxRoles;
    private JTextField txtUsuario;

    public UsuarioModificarView(){
        super("Modificar Usuario",true,true,false,true);
        setContentPane(panelPrincipal);
        setSize(600,400);

        lblUsuario.setText("Buscar Usuario:");
        lblContraseña.setText("Contraseña:");
        lblRol.setText("Rol:");
        btnBuscar.setText("Buscar");
        btnModificar.setText("Guardar Cambios");

        cargarRoles();
        limpiarCampos();
    }

    private void cargarRoles() {
        cbxRoles.removeAllItems();
        for (Rol rol : Rol.values()) {
            cbxRoles.addItem(rol);
        }
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JComboBox<Rol> getCbxRoles() {
        return cbxRoles;
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtContraseña.setText("");
        if (cbxRoles.getItemCount() > 0) {
            cbxRoles.setSelectedIndex(0);
        }
        btnModificar.setEnabled(false);
        txtContraseña.setEnabled(false);
        cbxRoles.setEnabled(false);
        txtUsuario.setEditable(true);
        btnBuscar.setEnabled(true);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JTextField getTxtContraseña() { return txtContraseña; }
    public JButton getBtnModificar() { return btnModificar; }
    public JLabel getLblUsuario() { return lblUsuario; }
    public JLabel getLblContraseña() { return lblContraseña; }
    public JLabel getLblRol() { return lblRol; }
}
