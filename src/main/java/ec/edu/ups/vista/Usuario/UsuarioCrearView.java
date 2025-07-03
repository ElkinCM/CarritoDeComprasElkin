package ec.edu.ups.vista.Usuario;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class UsuarioCrearView extends JInternalFrame {
    private JLabel lblUsuarioA;
    private JLabel lblContraseña;
    private JLabel lblRol;
    private JTextField txtUsuario;
    private JTextField txtContraseña;
    private JButton btnCrear;
    private JComboBox<Rol> cbxRoles;
    private JPanel panelPrincipal;
    private JLabel lblTitulo;

    private MensajeInternacionalizacionHandler mensaje;

    public UsuarioCrearView(MensajeInternacionalizacionHandler mensaje) {
        super("", true, true, false, true);
        this.mensaje = mensaje;

        URL urlCrear = getClass().getResource("/plus.png");
        if (urlCrear != null) {
            btnCrear.setIcon(new ImageIcon(urlCrear));
        }

        setContentPane(panelPrincipal);
        setSize(600, 400);
        setClosable(true);
        setIconifiable(true);

        actualizarTextos();
        cargarRoles();
    }

    public void actualizarTextos() {
        setTitle(mensaje.get("usr.crear.titulo.app"));
        lblTitulo.setText(mensaje.get("usr.crear.titulo.app"));
        lblUsuarioA.setText(mensaje.get("global.usuario") + ": ");
        lblContraseña.setText(mensaje.get("global.pass") + ":");
        lblRol.setText(mensaje.get("global.rol") + ":");

        txtUsuario.setToolTipText(mensaje.get("usr.crear.nombre"));
        txtContraseña.setToolTipText(mensaje.get("usr.crear.pass"));
        cbxRoles.setToolTipText(mensaje.get("usr.crear.rol"));

        btnCrear.setText(mensaje.get("global.crear"));

        cbxRoles.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
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

    private void cargarRoles() {
        cbxRoles.removeAllItems();
        for (Rol rol : Rol.values()) {
            cbxRoles.addItem(rol);
        }
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public JTextField getTxtContraseña() {
        return txtContraseña;
    }

    public JButton getBtnCrear() {
        return btnCrear;
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
    }

    public void mostrarMensaje(String mensajeTexto) {
        JOptionPane.showMessageDialog(this, mensajeTexto, mensaje.get("confirm.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}
