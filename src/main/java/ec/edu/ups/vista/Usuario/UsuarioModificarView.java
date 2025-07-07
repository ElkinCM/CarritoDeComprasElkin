package ec.edu.ups.vista.Usuario;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Locale;

public class UsuarioModificarView extends JInternalFrame {
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JTextField txtUsuario;
    private JTextField txtContraseña;
    private JButton btnBuscar;
    private JPanel panelPrincipal;
    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JLabel lblCorreo;
    private JLabel lblTelefono;
    private JButton btnModificar;
    private JLabel lblNacimiento;
    private JComboBox cbxDia;
    private JComboBox cbxMes;
    private JLabel lblDia;
    private JLabel lblMes;
    private JLabel lblAnio;
    private JLabel lblTitulo;
    private JLabel lblNombre;
    private JTextField txtAnio;

    private final MensajeInternacionalizacionHandler Internacionalizar;
    private Locale locale;
    private DefaultTableModel modelo;

    public UsuarioModificarView(MensajeInternacionalizacionHandler internacionalizar){
        super(internacionalizar.get("usuario.modificar.menu"), true, true, true, true);
        this.Internacionalizar = internacionalizar;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400,450);

        setFrameIcon(new ImageIcon(getClass().getResource("/modificar.png")));
        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        btnModificar.setIcon(new ImageIcon(getClass().getResource("/modificar.png")));

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    public void actualizarIdioma(String language, String country) {
        Internacionalizar.setLenguaje(language, country);
        lblTitulo.setText(Internacionalizar.get("usuario.modificar.titulo"));

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
        btnModificar.setText(Internacionalizar.get("boton.modificar"));

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

    private void cargarAnios(JComboBox cbxAnio) {
        cbxAnio.removeAllItems();
        for (int i = 2000; i <= 2025; i++) {
            cbxAnio.addItem(i);
        }
    }

    public void  vaciarCampo() {
        txtUsuario.setText("");
        txtContraseña.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtAnio.setText("");
        if (cbxDia.getItemCount() > 0) cbxDia.setSelectedIndex(0);
        if (cbxMes.getItemCount() > 0) cbxMes.setSelectedIndex(0);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JTextField getTxtUsuario() { return txtUsuario; }
    public JTextField getTxtContraseña() { return txtContraseña; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtCorreo() { return txtCorreo; }
    public JTextField getTxtTelefono() { return txtTelefono; }
    public JTextField getTxtAnio() { return txtAnio; }

    public JButton getBtnBuscar() {return btnBuscar;}
    public JButton getBtnModificar() {return btnModificar;}

    public JComboBox getCbxDia() { return cbxDia; }
    public JComboBox getCbxMes() { return cbxMes; }
}
