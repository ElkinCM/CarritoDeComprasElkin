package ec.edu.ups.vista.Usuario;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Locale;

public class UsuarioListarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JButton btnListar;
    private JTable tblUsuarios;
    private JLabel lblListado;
    private JTextField txtUsuario;
    private JButton btnBuscar;
    private DefaultTableModel tableModel;

    private MensajeInternacionalizacionHandler mensaje;
    private Locale locale;
    private List<Usuario> listaActual;

    public UsuarioListarView(MensajeInternacionalizacionHandler mensaje) {
        super("", true, true, false, true);
        this.mensaje = mensaje;

        setContentPane(panelPrincipal);
        setSize(600, 400);

        configurarTabla();
        actualizarTextos();
    }

    private void configurarTabla() {
        tableModel = new DefaultTableModel();
        tblUsuarios.setModel(tableModel);
    }

    public void actualizarTextos() {
        this.locale = new Locale(mensaje.get("locale.lang"), mensaje.get("locale.country"));
        setTitle(mensaje.get("usr.listar.titulo.app"));
        lblListado.setText(mensaje.get("menu.usr.listar"));
        btnListar.setText(mensaje.get("menu.usr.listar"));
        btnBuscar.setText(mensaje.get("btn.buscar"));
        txtUsuario.setToolTipText(mensaje.get("msg.usr.buscarUsername.vacio"));

        tableModel.setColumnIdentifiers(new Object[]{
                mensaje.get("global.usuario"),
                mensaje.get("global.rol")
        });

        mostrarUsuarios(listaActual);
    }

    public void mostrarUsuarios(List<Usuario> usuarios) {
        this.listaActual = usuarios;
        tableModel.setRowCount(0);
        if (usuarios == null) return;

        for (Usuario usuario : usuarios) {
            String rolTraducido = "";
            if (usuario.getRol() == Rol.ADMINISTRADOR) {
                rolTraducido = mensaje.get("global.rol.admin");
            } else if (usuario.getRol() == Rol.USUARIO) {
                rolTraducido = mensaje.get("global.rol.user");
            }

            tableModel.addRow(new Object[]{
                    usuario.getUsername(),
                    rolTraducido
            });
        }
    }

    public JButton getBtnListar() {
        return btnListar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg, mensaje.get("confirm.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}
