package ec.edu.ups.vista.Usuario;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class UsuarioModificarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JButton btnBuscar;
    private JTextField txtContraseña;
    private JButton btnModificar;
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JLabel lblRol;
    private JComboBox<Rol> cbxRoles;
    private JTextField txtUsuario;
    private JLabel lblTitulo;

    private MensajeInternacionalizacionHandler mensaje;

    public UsuarioModificarView(MensajeInternacionalizacionHandler mensaje){
        super("", true, true, false, true);
        this.mensaje = mensaje;

        setContentPane(panelPrincipal);
        setSize(600,400);

        cargarRoles();
        actualizarTextos();
        limpiarCampos();
    }

    private void cargarRoles() {
        cbxRoles.removeAllItems();
        for (Rol rol : Rol.values()) {
            cbxRoles.addItem(rol);
        }
    }

    public void actualizarTextos() {
        setTitle(mensaje.get("usr.modificar.titulo.app"));

        lblUsuario.setText(mensaje.get("global.usuario") + ":");
        lblContraseña.setText(mensaje.get("global.pass") + ":");
        lblRol.setText(mensaje.get("global.rol") + ":");

        // En este diseño no existe lblTitulo, pero si lo agregas, usa esto:
        lblTitulo.setText(mensaje.get("usr.modificar.titulo.app"));

        btnBuscar.setText(mensaje.get("btn.buscar"));
        btnModificar.setText(mensaje.get("btn.modificar"));

        txtUsuario.setToolTipText(mensaje.get("msg.usr.buscar.vacio"));
        cbxRoles.setToolTipText(mensaje.get("usr.crear.rol"));
        txtContraseña.setToolTipText(mensaje.get("usr.crear.pass"));

        cbxRoles.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Rol) {
                    Rol rol = (Rol) value;
                    if (rol == Rol.ADMINISTRADOR) {
                        setText(mensaje.get("global.rol.admin"));
                    } else if (rol == Rol.USUARIO) {
                        setText(mensaje.get("global.rol.user"));
                    }
                }
                return this;
            }
        });
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

    public void mostrarMensaje(String mensajeTexto) {
        JOptionPane.showMessageDialog(this, mensajeTexto, mensaje.get("confirm.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }

    public JPanel getPanelPrincipal() { return panelPrincipal; }
    public JButton getBtnBuscar() { return btnBuscar; }
    public JTextField getTxtContraseña() { return txtContraseña; }
    public JButton getBtnModificar() { return btnModificar; }
    public JLabel getLblUsuario() { return lblUsuario; }
    public JLabel getLblContraseña() { return lblContraseña; }
    public JLabel getLblRol() { return lblRol; }
}
