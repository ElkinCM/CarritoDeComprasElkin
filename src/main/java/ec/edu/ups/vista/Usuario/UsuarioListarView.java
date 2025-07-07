package ec.edu.ups.vista.Usuario;

import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Locale;

public class UsuarioListarView extends JInternalFrame {
    private JPanel panelPrincipal;
    private JLabel lblTitulo;
    private JTextField txtNombre;
    private JButton btnListar;
    private JTable tblUsuarios;
    private JButton btnBuscar;
    private JLabel lblNombre;
    private DefaultTableModel tableModel;

    private MensajeInternacionalizacionHandler Internacionalizar;
    private DefaultTableModel modelo;
    private Locale locale;

    public UsuarioListarView(MensajeInternacionalizacionHandler internacionalizar) {
        super(internacionalizar.get("usuario.listar.menu"), true, true, true, true);
        this.Internacionalizar = internacionalizar;
        setContentPane(panelPrincipal);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setFrameIcon(new ImageIcon(getClass().getResource("/usuario_listar.png")));
        btnListar.setIcon(new ImageIcon(getClass().getResource("/listar.png")));
        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        modelo = new DefaultTableModel();
        tblUsuarios.setModel(modelo);

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    public void cargarDatosListaUsuario(List<Usuario> usuarios) {
        modelo.setRowCount(0);

        if (usuarios == null || usuarios.isEmpty()) {
            return;
        }

        for (Usuario usuario : usuarios) {
            String fechaNacimiento = (usuario.getFechaNacimientoCalendar() != null)
                    ? FormateadorUtils.formatearFecha(usuario.getFechaNacimientoCalendar().getTime(), Internacionalizar.getLocale())
                    : Internacionalizar.get("mensaje.sin.fecha");

            Object[] fila = {
                    usuario.getUsername(),
                    usuario.getContrasenia(),
                    usuario.getRol(),
                    usuario.getNombre(),
                    usuario.getCorreo(),
                    usuario.getTelefono(),
                    fechaNacimiento
            };

            modelo.addRow(fila);
        }
    }

    public void cargarDatosBusqueda(Usuario usuario) {
        modelo.setRowCount(0);

        if (usuario == null) {
            return;
        }

        Object[] fila = {
                usuario.getUsername() != null ? usuario.getUsername() : "",
                usuario.getContrasenia() != null ? usuario.getContrasenia() : "",
                usuario.getRol() != null ? usuario.getRol().toString() : ""
        };

        modelo.addRow(fila);
    }

    public void actualizarIdioma(String language, String country) {
        Internacionalizar.setLenguaje(language, country);
        lblTitulo.setText(Internacionalizar.get("usuario.listar.titulo"));
        lblNombre.setText(Internacionalizar.get("etiqueta.nombre"));

        btnListar.setText(Internacionalizar.get("boton.listar"));
        btnBuscar.setText(Internacionalizar.get("boton.buscar"));

        Object[] columnas = {
                Internacionalizar.get("etiqueta.usuario"),
                Internacionalizar.get("etiqueta.contrasenia"),
                Internacionalizar.get("etiqueta.rol"),
                Internacionalizar.get("etiqueta.nombre"),
                Internacionalizar.get("etiqueta.correo"),
                Internacionalizar.get("etiqueta.telefono"),
                Internacionalizar.get("etiqueta.fecha.nacimiento")
        };
        modelo.setColumnIdentifiers(columnas);
    }


    public JButton getBtnListar() {
        return btnListar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTable getTblUsuarios() {
        return tblUsuarios;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
