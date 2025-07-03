package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.RespuestaSegu;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.Registrarse.PreguntaModificarView;
import ec.edu.ups.vista.Registrarse.PreguntaRegistrarView;
import ec.edu.ups.vista.Registrarse.RegistrarView;
import ec.edu.ups.vista.Usuario.*;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsuarioController {
    private Usuario usuarioAutentificado;
    private Usuario usuarioTemp;
    private Usuario usuarioRecu;
    private final UsuarioCrearView usuarioCrearView;
    private final UsuarioDAO usuarioDAO;
    private final LoginView loginView;
    private final RegistrarView registrarView;
    private final PreguntaModificarView preguntaModificarView;
    private final PreguntaRegistrarView preguntaRegistrarView;
    private final UsuarioModificarView usuarioModificarView;
    private final UsuarioEliminarView usuarioEliminarView;
    private final UsuarioModificarMisView usuarioModificarMisView;
    private final UsuarioListarView usuarioListarView;
    private final MensajeInternacionalizacionHandler mensaje;

    public UsuarioController(UsuarioCrearView usuarioCrearView, UsuarioDAO usuarioDAO, LoginView logInView, RegistrarView registrarView,
                             PreguntaModificarView preguntaModificarView, PreguntaRegistrarView preguntaRegistrarView, UsuarioModificarView usuarioModificarView,
                             UsuarioEliminarView usuarioEliminarView, UsuarioModificarMisView usuarioModificarMisView, UsuarioListarView usuarioListarView,
                             MensajeInternacionalizacionHandler mensaje) {
        this.usuarioCrearView = usuarioCrearView;
        this.usuarioDAO = usuarioDAO;
        this.loginView = logInView;
        this.registrarView = registrarView;
        this.preguntaModificarView = preguntaModificarView;
        this.preguntaRegistrarView = preguntaRegistrarView;
        this.usuarioModificarView = usuarioModificarView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioModificarMisView = usuarioModificarMisView;
        this.usuarioListarView = usuarioListarView;
        this.mensaje = mensaje;

        configurarEventos();
    }

    private void configurarEventos() {
        loginView.getBtnIniciarSesion().addActionListener(e -> autentificar());
        loginView.getBtnRegistrarse().addActionListener(e -> {
            registrarView.setVisible(true);
            loginView.setVisible(false);
        });
        registrarView.getBtnRegistrar().addActionListener(e -> {
            procesarDatosDeRegistro();
        });
        preguntaRegistrarView.getBtnGuardar().addActionListener(e -> {
            guardarUsuarioConPreguntas();
        });
        loginView.getBtnOlvidoContraseña().addActionListener(e -> iniciarRecuperacionContraseña());
        preguntaModificarView.getBtnVerificar().addActionListener(e->verificarRespuestasYCambiarContraseña());
        usuarioCrearView.getBtnCrear().addActionListener(e -> crearUsuario());
        usuarioModificarView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaModificar());
        usuarioModificarView.getBtnModificar().addActionListener(e -> modificarUsuario());
        usuarioEliminarView.getBtnBuscar().addActionListener(e -> buscarUsuarioParaEliminar());
        usuarioEliminarView.getBtnEliminar().addActionListener(e -> eliminarUsuario());
        usuarioListarView.getBtnListar().addActionListener(e -> listarTodosLosUsuarios());
        usuarioListarView.getBtnBuscar().addActionListener(e -> buscarUsuarioPorUsername());

        usuarioModificarMisView.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                cargarDatosUsuarioActual();
            }
        });
        usuarioModificarMisView.getBtnModificar().addActionListener(e -> modificarMisDatos());
    }
    private void verificarRespuestasYCambiarContraseña() {
        if (usuarioRecu == null) return;

        Map<Pregunta, String> respuestasIngresadas = preguntaModificarView.obtenerRespuestasDigitadas();
        List<RespuestaSegu> respuestasGuardadas = usuarioRecu.getRespuestaSegu();

        boolean todasCorrectas = true;
        for (RespuestaSegu guardada : respuestasGuardadas) {
            String respuestaIngresada = respuestasIngresadas.get(guardada.getPre());
            if (!guardada.ResCorrecta(respuestaIngresada)) {
                todasCorrectas = false;
                break;
            }
        }

        if (todasCorrectas) {
            String nuevacontra = JOptionPane.showInputDialog(
                    preguntaModificarView,
                    mensaje.get("msg.preg.recuperar.exito")
            );

            if (nuevacontra != null && !nuevacontra.trim().isEmpty()) {
                usuarioRecu.setContrasenia(nuevacontra.trim());
                usuarioDAO.actualizar(usuarioRecu);
                JOptionPane.showMessageDialog(preguntaModificarView, mensaje.get("msg.pass.actualizada"));

                preguntaModificarView.dispose();
                preguntaModificarView.limpiarCampos();
                this.usuarioRecu= null;
            }
        } else {
            JOptionPane.showMessageDialog(
                    preguntaModificarView,
                    mensaje.get("msg.preg.recuperar.error"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void iniciarRecuperacionContraseña() {
        String username = JOptionPane.showInputDialog(
                loginView,
                mensaje.get("msg.preg.recuperar.instrucciones"),
                mensaje.get("preg.recuperar.titulo"),
                JOptionPane.QUESTION_MESSAGE
        );

        if (username == null || username.isEmpty()) {
            return;
        }

        this.usuarioRecu = usuarioDAO.buscarPorUsuario(username.trim());

        if (usuarioRecu == null) {
            loginView.mostrarMensaje(mensaje.get("msg.preg.recuperar.noUsuario"));
            return;
        }

        List<RespuestaSegu> respuestas = usuarioRecu.getRespuestaSegu();
        if (respuestas == null || respuestas.isEmpty()) {
            loginView.mostrarMensaje(mensaje.get("msg.preg.recuperar.sinPreguntas"));
            return;
        }

        preguntaModificarView.construirFormularioPreguntas(respuestas);
        preguntaModificarView.setVisible(true);
    }


    private void autentificar() {
        String username = loginView.getTxtUsername().getText();
        String contraseña = new String(loginView.getPsfContraseña().getPassword());

        usuarioAutentificado = usuarioDAO.autenticar(username, contraseña);
        if (usuarioAutentificado == null) {
            loginView.mostrarMensaje(mensaje.get("msg.usr.login.error"));
        } else {
            loginView.dispose();
        }
    }

    private void guardarUsuarioConPreguntas() {
        List<String> respuestasTexto = preguntaRegistrarView.getRespuestas();
        int respuestasDadas = 0;

        for (int i = 0; i < respuestasTexto.size(); i++) {
            String respuesta = respuestasTexto.get(i);
            if (respuesta != null && !respuesta.trim().isEmpty()) {
                respuestasDadas++;
            }
        }

        if (respuestasDadas < 3) {
            JOptionPane.showMessageDialog(
                    preguntaRegistrarView,
                    mensaje.get("msg.preg.minimoRequerido"),
                    mensaje.get("confirm.app.titulo"),
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        for (int i = 0; i < respuestasTexto.size(); i++) {
            String respuesta = respuestasTexto.get(i);
            if (respuesta != null && !respuesta.trim().isEmpty()) {
                int preguntaId = i + 1;
                String textoPregunta = mensaje.get("preg.seguridad." + preguntaId);
                Pregunta pregunta = new Pregunta(preguntaId, textoPregunta);
                usuarioTemp.addRespuesta(pregunta, respuesta);
            }
        }

        usuarioDAO.crear(usuarioTemp);

        JOptionPane.showMessageDialog(
                preguntaRegistrarView,
                mensaje.get("msg.usr.registrado"),
                mensaje.get("confirm.app.titulo"),
                JOptionPane.INFORMATION_MESSAGE
        );

        preguntaRegistrarView.dispose();
        registrarView.limpiarCampos();
        loginView.setVisible(true);
        this.usuarioTemp = null;
    }


    private void procesarDatosDeRegistro() {
        String username = registrarView.getTxtUsuario().getText().trim();
        String contraseña = new String(registrarView.getContraseña().getPassword());
        String repeContra = new String(registrarView.getContraseña2().getPassword());

        if (username.isEmpty() || contraseña.isEmpty()) {
            registrarView.mostrarMensajeTraducido(mensaje.get("msg.usr.registrar.datos"));
            return;
        }
        if (!contraseña.equals(repeContra)) {
            registrarView.mostrarMensajeTraducido(mensaje.get("msg.register.passNoCoincide"));
            return;
        }
        if (usuarioDAO.buscarPorUsuario(username) != null) {
            registrarView.mostrarMensajeTraducido(mensaje.get("msg.usr.error.nombreUsado"));
            return;
        }

        this.usuarioTemp = new Usuario(username, Rol.USUARIO, contraseña);
        preguntaRegistrarView.setVisible(true);
        registrarView.setVisible(false);
    }

    private void crearUsuario() {
        String username = usuarioCrearView.getTxtUsuario().getText().trim();
        String contraseña = usuarioCrearView.getTxtContraseña().getText().trim();
        Rol rolSeleccionado = (Rol) usuarioCrearView.getCbxRoles().getSelectedItem();

        if (username.isEmpty() || contraseña.isEmpty()) {
            usuarioCrearView.mostrarMensaje(mensaje.get("msg.usr.error.camposVacios"));
            return;
        }
        if (rolSeleccionado == null) {
            usuarioCrearView.mostrarMensaje(mensaje.get("msg.usr.error.rolNoSel"));
            return;
        }
        if (usuarioDAO.buscarPorUsuario(username) != null) {
            usuarioCrearView.mostrarMensaje(mensaje.get("msg.usr.error.nombreUsado"));
            return;
        }

        Usuario nuevoUsuario = new Usuario(username, rolSeleccionado, contraseña);
        usuarioDAO.crear(nuevoUsuario);
        usuarioCrearView.mostrarMensaje(mensaje.get("msg.usr.creado"));
        usuarioCrearView.limpiarCampos();
    }


    private void buscarUsuarioParaModificar() {
        String username = usuarioModificarView.getTxtUsuario().getText().trim();
        if (username.isEmpty()) {
            usuarioModificarView.mostrarMensaje(mensaje.get("msg.usr.buscar.vacio"));
            return;
        }
        Usuario usuario = usuarioDAO.buscarPorUsuario(username);
        if (usuario == null) {
            usuarioModificarView.mostrarMensaje(mensaje.get("msg.usr.buscar.noEncontrado"));
            return;
        }
        usuarioModificarView.getTxtContraseña().setText(usuario.getContrasenia());
        usuarioModificarView.getCbxRoles().setSelectedItem(usuario.getRol());
        usuarioModificarView.getTxtUsuario().setEditable(false);
        usuarioModificarView.getBtnBuscar().setEnabled(false);
        usuarioModificarView.getTxtContraseña().setEnabled(true);
        usuarioModificarView.getCbxRoles().setEnabled(true);
        usuarioModificarView.getBtnModificar().setEnabled(true);
    }

    private void modificarUsuario() {
        String username = usuarioModificarView.getTxtUsuario().getText();
        String nuevaContraseña = usuarioModificarView.getTxtContraseña().getText();
        Rol nuevoRol = (Rol) usuarioModificarView.getCbxRoles().getSelectedItem();

        if (nuevaContraseña.isEmpty()) {
            usuarioModificarView.mostrarMensaje(mensaje.get("msg.usr.mod.vacio"));
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(usuarioModificarView,
                mensaje.get("confirm.usr.mod"), mensaje.get("confirm.app.titulo"), JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            Usuario usuario = usuarioDAO.buscarPorUsuario(username);
            if (usuario == null) {
                usuarioModificarView.mostrarMensaje(mensaje.get("msg.usr.mod.noExiste"));
                usuarioModificarView.limpiarCampos();
                return;
            }
            usuario.setContrasenia(nuevaContraseña);
            usuario.setRol(nuevoRol);
            usuarioDAO.actualizar(usuario);
            usuarioModificarView.mostrarMensaje(mensaje.get("msg.usr.modMis.exito"));
            usuarioModificarView.limpiarCampos();
        }
    }

    private void buscarUsuarioParaEliminar() {
        String username = usuarioEliminarView.getTxtUsuario().getText().trim();
        if (username.isEmpty()) {
            usuarioEliminarView.mostrarMensaje(mensaje.get("msg.usr.buscar.vacio"));
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsuario(username);
        if (usuario == null) {
            usuarioEliminarView.mostrarMensaje(mensaje.get("msg.usr.buscar.noEncontrado"));
            return;
        }

        usuarioEliminarView.getTxtContraseña().setText(usuario.getContrasenia());
        usuarioEliminarView.getTxtRol().setText(
                usuario.getRol() == Rol.ADMINISTRADOR
                        ? mensaje.get("global.rol.admin")
                        : mensaje.get("global.rol.user")
        );
        usuarioEliminarView.getTxtUsuario().setEditable(false);
        usuarioEliminarView.getBtnBuscar().setEnabled(false);
        usuarioEliminarView.getBtnEliminar().setEnabled(true);
    }

    private void eliminarUsuario() {
        String username = usuarioEliminarView.getTxtUsuario().getText();
        int respuesta = JOptionPane.showConfirmDialog(usuarioEliminarView,
                mensaje.get("confirm.usr.elim"), mensaje.get("confirm.app.titulo"), JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            if (usuarioDAO.buscarPorUsuario(username) == null) {
                usuarioEliminarView.mostrarMensaje(mensaje.get("msg.usr.elim.noExiste"));
            } else {
                usuarioDAO.eliminar(username);
                usuarioEliminarView.mostrarMensaje(mensaje.get("msg.usr.elim.exito"));
            }
            usuarioEliminarView.limpiarCampos();
        }
    }

    private void listarTodosLosUsuarios() {
        usuarioListarView.mostrarUsuarios(usuarioDAO.listarUsuarios());
    }

    private void buscarUsuarioPorUsername() {
        String username = usuarioListarView.getTxtUsuario().getText().trim();
        if (username.isEmpty()) {
            usuarioListarView.mostrarMensaje(mensaje.get("msg.usr.buscarUsername.vacio"));
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorUsuario(username);
        if (usuario != null) {
            List<Usuario> lista = new ArrayList<>();
            lista.add(usuario);
            usuarioListarView.mostrarUsuarios(lista);
        } else {
            usuarioListarView.mostrarUsuarios(new ArrayList<>());
            usuarioListarView.mostrarMensaje(mensaje.get("msg.usr.buscarUsername.noEncontrado"));
        }
    }

    private void cargarDatosUsuarioActual() {
        if (usuarioAutentificado != null) {
            usuarioModificarMisView.getTxtUsuario().setText(usuarioAutentificado.getUsername());
            usuarioModificarMisView.getTxtUsuario().setEditable(true);
            usuarioModificarMisView.getBtnModificar().setEnabled(true);
            usuarioModificarMisView.getTxtContraseña().setText(usuarioAutentificado.getContrasenia());
        }
    }

    private void modificarMisDatos() {
        String nuevoUsername = usuarioModificarMisView.getTxtUsuario().getText().trim();
        String nuevaPassword = usuarioModificarMisView.getTxtContraseña().getText().trim();
        String usernameOriginal = usuarioAutentificado.getUsername();

        if (nuevoUsername.isEmpty() || nuevaPassword.isEmpty()) {
            usuarioModificarMisView.mostrarMensaje(mensaje.get("msg.usr.modMis.incompleto"));
            return;
        }

        if (!nuevoUsername.equals(usernameOriginal) && usuarioDAO.buscarPorUsuario(nuevoUsername) != null) {
            usuarioModificarMisView.mostrarMensaje(mensaje.get("msg.usr.error.nombreUsado"));
            return;
        }

        List<RespuestaSegu> respuestasGuardadas = usuarioAutentificado.getRespuestaSegu();
        if (respuestasGuardadas == null || respuestasGuardadas.isEmpty()) {
            usuarioModificarMisView.mostrarMensaje(mensaje.get("msg.preg.recuperar.sinPreguntas"));
            return;
        }

        JPanel panelPreguntas = new JPanel(new GridLayout(respuestasGuardadas.size() + 1, 2, 10, 5));
        panelPreguntas.add(new JLabel(mensaje.get("preg.recuperar.titulo")));
        panelPreguntas.add(new JLabel("")); // Espacio en blanco

        List<JTextField> camposDeRespuesta = new ArrayList<>();
        for (RespuestaSegu resp : respuestasGuardadas) {
            panelPreguntas.add(new JLabel(resp.getPre().getTexto()));
            JTextField campoRespuesta = new JTextField();
            camposDeRespuesta.add(campoRespuesta);
            panelPreguntas.add(campoRespuesta);
        }

        int resultado = JOptionPane.showConfirmDialog(
                usuarioModificarMisView,
                panelPreguntas,
                mensaje.get("confirm.app.titulo"),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (resultado == JOptionPane.OK_OPTION) {
            boolean todasCorrectas = true;
            for (int i = 0; i < respuestasGuardadas.size(); i++) {
                String respuestaIngresada = camposDeRespuesta.get(i).getText();
                if (!respuestasGuardadas.get(i).ResCorrecta(respuestaIngresada)) {
                    todasCorrectas = false;
                    break;
                }
            }

            if (todasCorrectas) {
                if (!usernameOriginal.equals(nuevoUsername)) {
                    usuarioDAO.eliminar(usernameOriginal);
                }
                usuarioAutentificado.setUsername(nuevoUsername);
                usuarioAutentificado.setContrasenia(nuevaPassword);
                usuarioDAO.crear(usuarioAutentificado);

                usuarioModificarMisView.mostrarMensaje(mensaje.get("msg.usr.modMis.exito"));
                usuarioModificarMisView.dispose();
            } else {
                usuarioModificarMisView.mostrarMensaje(mensaje.get("msg.preg.recuperar.error"));
            }
        }
    }

    public Usuario getUsuarioAutentificado() {
        return usuarioAutentificado;
    }
}
