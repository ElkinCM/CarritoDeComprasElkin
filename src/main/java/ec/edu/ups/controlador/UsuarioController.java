package ec.edu.ups.controlador;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.LoginView;
import ec.edu.ups.vista.Usuario.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.List;

public class UsuarioController {
    private LoginView loginView;
    private UsuarioCrearView usuarioCrearView;
    private UsuarioEliminarView usuarioEliminarView;
    private UsuarioListarView usuarioListarView;
    private UsuarioModificarView usuarioModificarView;

    private Usuario usuario;
    private UsuarioDAO usuarioDAO;
    private final MensajeInternacionalizacionHandler Internacionalizar;

    public UsuarioController(UsuarioCrearView usuarioCrearView, UsuarioEliminarView usuarioEliminarView,
                             UsuarioListarView usuarioListarView, UsuarioModificarView usuarioModificarView, Usuario usuario,
                             UsuarioDAO usuarioDAO, MensajeInternacionalizacionHandler internacionalizar) {
        this.usuarioCrearView = usuarioCrearView;
        this.usuarioEliminarView = usuarioEliminarView;
        this.usuarioListarView = usuarioListarView;
        this.usuarioModificarView = usuarioModificarView;
        this.usuario = usuario;
        this.usuarioDAO = usuarioDAO;
        this.Internacionalizar = internacionalizar;

        configurarAnadirUsu();
        configurarModificarUsu();
        configurarEliminarUsu();
        configurarListarUsu();
        actualizarIdioma();
    }

    public UsuarioController(LoginView loginView, UsuarioDAO usuarioDAO, MensajeInternacionalizacionHandler internacionalizar){
        this.loginView = loginView;
        this.usuarioDAO = usuarioDAO;
        this.Internacionalizar = internacionalizar;

        configurarLogin();
    }

    private void configurarLogin() {
        loginView.getBtnIniciarSesion().addActionListener(e -> manejarInicioSesion());
        loginView.getBtnSalir().addActionListener(e -> salirDelSistema());
    }

    private void manejarInicioSesion() {
        String username = loginView.getTxtUsername().getText().trim();
        String password = loginView.getPsfContraseña().getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            loginView.mostrarMensaje(Internacionalizar.get("mensaje.login.campos.vacios"));
            return;
        }

        usuario = usuarioDAO.autenticar(username, password);

        if (usuario == null) {
            loginView.mostrarMensaje(Internacionalizar.get("mensaje.login.incorrecto"));
        } else {
            loginView.dispose();
        }

        limpiarCamposLogIn();
    }

    private void limpiarCamposLogIn() {
        loginView.getTxtUsername().setText("");
        loginView.getPsfContraseña().setText("");
    }

    private void salirDelSistema() {
        int confirmacion = JOptionPane.showConfirmDialog(
                null,
                Internacionalizar.get("mensaje.salir.confirmacion"),
                Internacionalizar.get("global.salir"),
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }


    private void configurarAnadirUsu() {
        usuarioCrearView.getBtnCrear().addActionListener(e -> {
            String usuario = usuarioCrearView.getTxtUsuario().getText().trim();
            String contrasenia = usuarioCrearView.getTxtContraseña().getText().trim();

            if (usuario.isEmpty() || contrasenia.isEmpty()) {
                usuarioCrearView.mostrarMensaje(Internacionalizar.get("mensaje.completar.campos"));
                return;
            }

            Usuario existente = usuarioDAO.buscarPorUsuario(usuario);
            if (existente != null) {
                usuarioCrearView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.existe"));
                return;
            }

            Usuario nuevo = new Usuario();
            nuevo.setUsername(usuario);
            nuevo.setContrasenia(contrasenia);
            nuevo.setRol(Rol.USUARIO);

            usuarioDAO.crear(nuevo);
            usuarioCrearView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.creado"));
            usuarioCrearView.vaciarCampo();
        });
        usuarioCrearView.getBtnSalir().addActionListener(e -> {
            if (usuarioCrearView.isVisible()) {
                usuarioCrearView.setVisible(false);
            }
        });
    }

    private void configurarModificarUsu() {
        if (usuario.getRol() == Rol.USUARIO) {
            configurarVistaParaUsuarioModificar(usuario);
        }

        usuarioModificarView.getBtnBuscar().addActionListener(e -> buscarUsuarioNombreModificar());
        usuarioModificarView.getBtnModificar().addActionListener(e -> modificarUsuario());
    }


    private void configurarEliminarUsu() {
        if (usuario.getRol() == Rol.USUARIO) {
            configurarVistaParaUsuario(usuario);
        }
        usuarioEliminarView.getBtnBuscar().addActionListener(e -> buscarUsuarioNombre());
        usuarioEliminarView.getBtnEliminar().addActionListener(e -> eliminarUsuario());
    }

    private void configurarListarUsu() {
        usuarioListarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = usuarioListarView.getTxtNombre().getText().trim();
                if (input.isEmpty()) {
                    usuarioListarView.mostrarMensaje("Por favor, ingrese un nombre de usuario.");
                    return;
                }
                Usuario usuario = usuarioDAO.buscarPorUsuario(input);
                if (usuario != null) {
                    usuarioListarView.cargarDatosBusqueda(usuario);
                } else {
                    usuarioListarView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.noencontrado"));
                }
            }
        });
        usuarioListarView.getBtnListar().addActionListener(e -> {
            List<Usuario> usuarios = usuarioDAO.listarTodos();

            if (usuarios == null || usuarios.isEmpty()) {
                usuarioListarView.mostrarMensaje("No hay usuarios registrados.");
            } else {
                usuarioListarView.cargarDatosListaUsuario(usuarios);
            }
        });
    }

    public void actualizarIdioma() {
        if (usuarioCrearView != null) usuarioCrearView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        if (usuarioEliminarView != null) usuarioEliminarView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        if (usuarioListarView != null) usuarioListarView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        if (usuarioModificarView != null) usuarioModificarView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        if (loginView != null) loginView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    public void cambiarIdioma(String lenguaje, String pais) {
        Internacionalizar.setLenguaje(lenguaje, pais);
        actualizarIdioma();
    }

    private void configurarVistaParaUsuario(Usuario usuario) {
        deshabilitarBusqueda();
        cargarDatosEnCampos(usuario);
        bloquearCampos();
    }

    private void deshabilitarBusqueda() {
        usuarioEliminarView.getBtnBuscar().setEnabled(false);
        usuarioEliminarView.getBtnBuscar().setVisible(false);
        usuarioEliminarView.getTxtUsuario().setEnabled(false);
    }

    private void cargarDatosEnCampos(Usuario usuario) {
        usuarioEliminarView.getTxtUsuario().setText(usuario.getUsername());
        usuarioEliminarView.getTxtContraseña().setText(usuario.getContrasenia());
        usuarioEliminarView.getTxtNombre().setText(usuario.getNombre());
        usuarioEliminarView.getTxtTelefono().setText(usuario.getTelefono());
        usuarioEliminarView.getTxtCorreo().setText(usuario.getCorreo());
        usuarioEliminarView.getTxtAnio().setText(usuario.getAnioNacimientoString());

        seleccionarFechaNacimiento(usuario);
    }

    private void seleccionarFechaNacimiento(Usuario usuario) {
        try {
            int dia = Integer.parseInt(usuario.getDiaNacimientoString());
            int mes = Integer.parseInt(usuario.getMesNacimientoString());
            usuarioEliminarView.getCbxDia().setSelectedItem(dia);
            usuarioEliminarView.getCbxMes().setSelectedIndex(mes);
        } catch (NumberFormatException e) {
            // en caso de error, se omite la selección
        }
    }

    private void bloquearCampos() {
        usuarioEliminarView.getCbxDia().setEnabled(false);
        usuarioEliminarView.getCbxMes().setEnabled(false);
    }

    private void buscarUsuarioNombre() {
        String username = usuarioEliminarView.getTxtUsuario().getText().trim();
        if (username.isEmpty()) {
            usuarioEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.completar.campos"));
            return;
        }

        Usuario encontrado = usuarioDAO.buscarPorUsuario(username);
        if (encontrado != null) {
            usuarioEliminarView.getTxtContraseña().setText(encontrado.getContrasenia());
        } else {
            usuarioEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.noencontrado"));
        }
    }

    private void configurarVistaParaUsuarioModificar(Usuario usuario) {
        deshabilitarBusquedaModificar();
        cargarDatosEnCamposModificar(usuario);
        bloquearCamposModificar();
    }

    private void deshabilitarBusquedaModificar() {
        usuarioModificarView.getBtnBuscar().setEnabled(false);
        usuarioModificarView.getBtnBuscar().setVisible(false);
        usuarioModificarView.getTxtUsuario().setEnabled(false);
    }

    private void cargarDatosEnCamposModificar(Usuario usuario) {
        usuarioModificarView.getTxtUsuario().setText(usuario.getUsername());
        usuarioModificarView.getTxtContraseña().setText(usuario.getContrasenia());
        usuarioModificarView.getTxtNombre().setText(usuario.getNombre());
        usuarioModificarView.getTxtTelefono().setText(usuario.getTelefono());
        usuarioModificarView.getTxtCorreo().setText(usuario.getCorreo());
        usuarioModificarView.getTxtAnio().setText(usuario.getAnioNacimientoString());

        seleccionarFechaNacimientoModificar(usuario);
    }

    private void seleccionarFechaNacimientoModificar(Usuario usuario) {
        try {
            int dia = Integer.parseInt(usuario.getDiaNacimientoString());
            int mes = Integer.parseInt(usuario.getMesNacimientoString());
            usuarioModificarView.getCbxDia().setSelectedItem(dia);
            usuarioModificarView.getCbxMes().setSelectedIndex(mes);
        } catch (NumberFormatException e) {
            // Silenciar error en caso de datos inválidos
        }
    }

    private void bloquearCamposModificar() {
        usuarioModificarView.getCbxDia().setEnabled(false);
        usuarioModificarView.getCbxMes().setEnabled(false);
    }

    private void buscarUsuarioNombreModificar() {
        String username = usuarioModificarView.getTxtUsuario().getText().trim();

        if (username.isEmpty()) {
            usuarioModificarView.mostrarMensaje("Ingrese un nombre de usuario.");
            return;
        }

        Usuario usuarioEncontrado = usuarioDAO.buscarPorUsuario(username);

        if (usuarioEncontrado != null) {
            usuarioModificarView.getTxtContraseña().setText(usuarioEncontrado.getContrasenia());
        } else {
            usuarioModificarView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.noencontrado"));
        }
    }

    private void modificarUsuario() {
        String username = usuarioModificarView.getTxtUsuario().getText().trim();
        String password = usuarioModificarView.getTxtContraseña().getText().trim();
        String nombre = usuarioModificarView.getTxtNombre().getText().trim();
        String telefono = usuarioModificarView.getTxtTelefono().getText().trim();
        String email = usuarioModificarView.getTxtCorreo().getText().trim();
        String anioStr = usuarioModificarView.getTxtAnio().getText().trim();

        if (username.isEmpty() || password.isEmpty() || nombre.isEmpty() ||
                telefono.isEmpty() || email.isEmpty() || anioStr.isEmpty()) {
            usuarioModificarView.mostrarMensaje(Internacionalizar.get("mensaje.completar.campos"));
            return;
        }

        int anio;
        try {
            anio = Integer.parseInt(anioStr);
        } catch (NumberFormatException ex) {
            usuarioModificarView.mostrarMensaje(Internacionalizar.get("mensaje.anio.invalido"));
            return;
        }

        Object diaSeleccionado = usuarioModificarView.getCbxDia().getSelectedItem();
        if (diaSeleccionado == null) {
            usuarioModificarView.mostrarMensaje(Internacionalizar.get("mensaje.fecha.dia.requerido"));
            return;
        }

        int dia = Integer.parseInt(diaSeleccionado.toString());
        int mes = usuarioModificarView.getCbxMes().getSelectedIndex();

        Usuario usuarioEncontrado = usuarioDAO.buscarPorUsuario(username);
        if (usuarioEncontrado != null) {
            usuarioEncontrado.setContrasenia(password);
            usuarioEncontrado.setNombre(nombre);
            usuarioEncontrado.setTelefono(telefono);
            usuarioEncontrado.setCorreo(email);
            usuarioEncontrado.setFechaNacimiento(new GregorianCalendar(anio, mes, dia));

            usuarioDAO.actualizar(usuarioEncontrado);
            usuarioModificarView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.modificado"));

            if (!usuario.getRol().equals(Rol.USUARIO)) {
                usuarioModificarView.vaciarCampo();
            }

            usuario = usuarioEncontrado;
        } else {
            usuarioModificarView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.noencontrado"));
        }
    }

    private void eliminarUsuario() {
        String username = usuarioEliminarView.getTxtUsuario().getText().trim();
        Usuario usuario = usuarioDAO.buscarPorUsuario(username);

        if (usuario == null) {
            usuarioEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.noencontrado"));
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                usuarioEliminarView,
                Internacionalizar.get("mensaje.usuario.confirmacion"),
                Internacionalizar.get("mensaje.confirmacion.titulo"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            usuarioDAO.eliminar(username);
            usuarioEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.eliminado"));

            if (usuario.getRol() == Rol.USUARIO) {
                usuarioEliminarView.dispose();
            } else {
                usuarioEliminarView.vaciarCampo();
            }
        } else {
            usuarioEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.cancelar"));
        }
    }

    public Usuario getUsuario(){
        return usuario;
    }

}
