package ec.edu.ups.vista.Registrarse;

import ec.edu.ups.modelo.Genero;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class RegistrarView extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtUsuario;
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JLabel lblContraseña2;
    private JButton BtnRegistrar;
    private JPasswordField contraseña;
    private JPasswordField contraseña2;
    private JLabel lblTitulo;
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JButton BtnAtras;
    private JSpinner spinnerEdad;
    private JComboBox<Genero> cbxGenero;
    private JLabel lblNombre;
    private JLabel lblEdad;
    private JLabel lblGenero;
    private JLabel lblEmail;
    private JLabel lblTelefono;

    private final MensajeInternacionalizacionHandler mensaje;

    public RegistrarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        cbxGenero.addItem(Genero.MASCULINO);
        cbxGenero.addItem(Genero.FEMENINO);
        cbxGenero.addItem(Genero.OTROS);
        setContentPane(panelPrincipal);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        spinnerEdad.setModel(new SpinnerNumberModel(18, 18, 100, 1));

        actualizarTexto();
        actualizarcbx();
    }

    public void actualizarTexto() {
        setTitle(mensaje.get("login.btn.reg"));
        lblTitulo.setText(mensaje.get("login.btn.reg"));
        lblUsuario.setText(mensaje.get("global.nuevoUsr"));
        lblContraseña.setText(mensaje.get("global.pass"));
        lblContraseña2.setText(mensaje.get("register.app.rep"));
        BtnRegistrar.setText(mensaje.get("btn.registrar"));
        lblEdad.setText(mensaje.get("global.edad"));
        lblGenero.setText(mensaje.get("global.genero"));
        lblNombre.setText(mensaje.get("global.nombre"));
        lblTelefono.setText(mensaje.get("global.telefono"));
        lblEmail.setText(mensaje.get("global.email"));
        cbxGenero.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value == Genero.MASCULINO) {
                    label.setText(mensaje.get("global.genero.masculino"));
                } else if (value == Genero.FEMENINO) {
                    label.setText(mensaje.get("global.genero.femenino"));
                } else if (value == Genero.OTROS) {
                    label.setText(mensaje.get("global.genero.otro"));
                }

                return label;
            }
        });
    }

    public void actualizarcbx() {
        cbxGenero.removeAllItems();
        for (Genero genero : Genero.values()) {
            cbxGenero.addItem(genero);
        }
        cbxGenero.setSelectedIndex(-1);
    }

    // Getters en una sola línea
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtTelefono() { return txtTelefono; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JButton getBtnAtras() { return BtnAtras; }
    public JSpinner getSpinnerEdad() { return spinnerEdad; }
    public JComboBox<Genero> getCbxGenero() { return cbxGenero; }
    public JTextField getTxtUsuario() { return txtUsuario; }
    public JPasswordField getContraseña() { return contraseña; }
    public JPasswordField getContraseña2() { return contraseña2; }
    public JLabel getLblUsuario() { return lblUsuario; }
    public JButton getBtnRegistrar() { return BtnRegistrar; }

    // Setters
    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public void setTxtTelefono(JTextField txtTelefono) {
        this.txtTelefono = txtTelefono;
    }

    public void setTxtEmail(JTextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public void setBtnAtras(JButton btnAtras) {
        BtnAtras = btnAtras;
    }

    public void setSpinnerEdad(JSpinner spinnerEdad) {
        this.spinnerEdad = spinnerEdad;
    }

    public void setCbxGenero(JComboBox<Genero> cbxGenero) {
        this.cbxGenero = cbxGenero;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    public void setContraseña(JPasswordField contraseña) {
        this.contraseña = contraseña;
    }

    public void setContraseña2(JPasswordField contraseña2) {
        this.contraseña2 = contraseña2;
    }

    public void setLblUsuario(JLabel lblUsuario) {
        this.lblUsuario = lblUsuario;
    }

    public void setBtnRegistrar(JButton btnRegistrar) {
        BtnRegistrar = btnRegistrar;
    }


    public void mostrarMensajeTraducido(String mensajeTraducido) {
        String titulo = mensaje.get("confirm.app.titulo");
        JOptionPane.showMessageDialog(this, mensajeTraducido, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        contraseña.setText("");
        contraseña2.setText("");
    }


}
