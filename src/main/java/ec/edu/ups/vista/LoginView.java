package ec.edu.ups.vista;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class LoginView extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panelSecundario;
    private JTextField txtUsername;
    private JPasswordField psfContraseña;
    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private JButton btnOlvidoContraseña;
    private JLabel lblUsuario;
    private JLabel lblContraseña;
    private JLabel lblTitulo;

    private final MensajeInternacionalizacionHandler mensaje;

    public LoginView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        actualizarTexto();
    }

    private void actualizarTexto() {
        setTitle(mensaje.get("login.app.titulo"));
        lblUsuario.setText(mensaje.get("global.usuario"));
        lblContraseña.setText(mensaje.get("global.pass"));
        btnIniciarSesion.setText(mensaje.get("login.app.titulo"));
        btnRegistrarse.setText(mensaje.get("login.btn.reg"));
        btnOlvidoContraseña.setText(mensaje.get("login.app.olvido"));
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JPasswordField getPsfContraseña() {
        return psfContraseña;
    }

    public void setPsfContraseña(JPasswordField psfContraseña) {
        this.psfContraseña = psfContraseña;
    }

    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public void setBtnIniciarSesion(JButton btnIniciarSesion) {
        this.btnIniciarSesion = btnIniciarSesion;
    }

    public JButton getBtnRegistrarse() {
        return btnRegistrarse;
    }

    public void setBtnRegistrarse(JButton btnRegistrarse) {
        this.btnRegistrarse = btnRegistrarse;
    }

    public JButton getBtnOlvidoContraseña() {
        return btnOlvidoContraseña;
    }

    public void setBtnOlvidoContraseña(JButton btnOlvidoContraseña) {
        this.btnOlvidoContraseña = btnOlvidoContraseña;
    }

    public void mostrarMensaje(String mensajes) {
        JOptionPane.showMessageDialog(this, mensajes, mensaje.get("confirm.app.titulo"), JOptionPane.INFORMATION_MESSAGE);
    }
}