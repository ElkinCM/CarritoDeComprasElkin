package ec.edu.ups.vista.Usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;

public class UsuarioEliminarView extends JInternalFrame {

    private JLabel lblUsuario;
    private JTextField txtUsuario;
    private JLabel lblContraseña;
    private JTextField txtContraseña;
    private JButton btnBuscar;
    private JPanel panelPrincipal;
    private JLabel lblNombre;
    private JLabel lblTelefono;
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JLabel lblCorreo;
    private JLabel lblNacimiento;
    private JLabel lblDia;
    private JLabel lblMes;
    private JLabel lblAnio;
    private JComboBox cbxDia;
    private JComboBox cbxMes;
    private JTextField txtAnio; // ← CAMBIO aquí
    private JLabel lblTitulo;
    private JButton btnEliminar;

    private MensajeInternacionalizacionHandler Internacionalizar;
    private Locale locale;

    public UsuarioEliminarView(MensajeInternacionalizacionHandler internacionalizar) {
        super(internacionalizar.get("usuario.eliminar.menu"), true, true, true, true);
        this.Internacionalizar = internacionalizar;
        setSize(400, 450);
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setFrameIcon(new ImageIcon(getClass().getResource("/usuario_eliminar.png")));
        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        btnEliminar.setIcon(new ImageIcon(getClass().getResource("/eliminar.png")));

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    public void actualizarIdioma(String languge, String country) {
        Internacionalizar.setLenguaje(languge, country);
        lblTitulo.setText(Internacionalizar.get("usuario.eliminar.titulo"));
        lblUsuario.setText(Internacionalizar.get("etiqueta.usuario"));
        lblContraseña.setText(Internacionalizar.get("etiqueta.contrasenia"));
        lblNombre.setText(Internacionalizar.get("etiqueta.nombre"));
        lblCorreo.setText(Internacionalizar.get("etiqueta.correo"));
        lblTelefono.setText(Internacionalizar.get("etiqueta.telefono"));
        lblNacimiento.setText(Internacionalizar.get("etiqueta.fecha.nacimiento"));
        lblDia.setText(Internacionalizar.get("etiqueta.dia"));
        lblMes.setText(Internacionalizar.get("etiqueta.mes"));
        lblAnio.setText(Internacionalizar.get("etiqueta.anio"));

        btnBuscar.setText(Internacionalizar.get("boton.buscar"));
        btnEliminar.setText(Internacionalizar.get("boton.eliminar"));

        cargarDias(cbxDia);
        cargarMes(cbxMes);
    }

    private void cargarDias(JComboBox cbxDia) {
        cbxDia.removeAllItems();
        for (int i = 1; i <= 31; i++) {
            cbxDia.addItem(i);
        }
    }

    private void cargarMes(JComboBox cbxMes) {
        cbxMes.removeAllItems();
        String[] clavesMeses = {
                "mes.enero", "mes.febrero", "mes.marzo", "mes.abril", "mes.mayo", "mes.junio",
                "mes.julio", "mes.agosto", "mes.septiembre", "mes.octubre", "mes.noviembre", "mes.diciembre"
        };

        for (String clave : clavesMeses) {
            cbxMes.addItem(Internacionalizar.get(clave));
        }
    }

    public void vaciarCampo() {
        txtUsuario.setText("");
        txtContraseña.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtAnio.setText(""); // ← Limpia el campo de año
        if (cbxDia.getItemCount() > 0) cbxDia.setSelectedIndex(0);
        if (cbxMes.getItemCount() > 0) cbxMes.setSelectedIndex(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Getters

    public JTextField getTxtUsuario() { return txtUsuario; }
    public JTextField getTxtContraseña() { return txtContraseña; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtCorreo() { return txtCorreo; }
    public JTextField getTxtTelefono() { return txtTelefono; }
    public JTextField getTxtAnio() { return txtAnio; } // ← Getter corregido

    public JComboBox getCbxDia() { return cbxDia; }
    public JComboBox getCbxMes() { return cbxMes; }

    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnEliminar() { return btnEliminar; }
}
