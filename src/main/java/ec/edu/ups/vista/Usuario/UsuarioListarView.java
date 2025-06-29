package ec.edu.ups.vista.Usuario;

import ec.edu.ups.modelo.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UsuarioListarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JButton btnListar;
    private JTable tblUsuarios;
    private JLabel lblListado;
    private JTextField txtUsuario;
    private JButton btnBuscar;
    private DefaultTableModel tableModel;

    public UsuarioListarView() {
        super("Listado de Usuarios", true, true, false, true);
        setContentPane(panelPrincipal);
        setSize(600, 400);

        lblListado.setText("Gestión de Usuarios");
        btnListar.setText("Listar Todos");
        btnBuscar.setText("Buscar");
        txtUsuario.setToolTipText("Ingrese el username a buscar");

        configurarTabla();
    }

    private void configurarTabla() {
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new Object[]{"Username", "Rol"});
        tblUsuarios.setModel(tableModel);
    }


    public void mostrarUsuarios(List<Usuario> usuarios) {
        tableModel.setRowCount(0);

        for (Usuario usuario : usuarios) {
            tableModel.addRow(new Object[]{
                    usuario.getUsername(),
                    usuario.getRol().toString()
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

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
