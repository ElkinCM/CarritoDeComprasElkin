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
/**
 * Controlador que gestiona la lógica relacionada con los usuarios,
 * incluyendo creación, eliminación, modificación, listado e inicio de sesión.
 * También maneja la internacionalización de los mensajes en las vistas.
 */
public class UsuarioController {
    private LoginView loginView;
    private UsuarioCrearView usuarioCrearView;
    private UsuarioEliminarView usuarioEliminarView;
    private UsuarioListarView usuarioListarView;
    private UsuarioModificarView usuarioModificarView;

    private Usuario usuario;
    private UsuarioDAO usuarioDAO;
    private final MensajeInternacionalizacionHandler Internacionalizar;
    /**
     * Constructor principal del controlador para gestionar las operaciones de usuario.
     *
     * @param usuarioCrearView Vista para crear usuarios
     * @param usuarioEliminarView Vista para eliminar usuarios
     * @param usuarioListarView Vista para listar usuarios
     * @param usuarioModificarView Vista para modificar usuarios
     * @param usuario Objeto usuario actual
     * @param usuarioDAO Objeto DAO para acceder a los datos de usuario
     * @param internacionalizar Manejador de mensajes internacionalizados
     */
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
    /**
     * Constructor del controlador utilizado específicamente para manejar el inicio de sesión.
     *
     * @param loginView Vista de login
     * @param usuarioDAO DAO para autenticación
     * @param internacionalizar Manejador de mensajes internacionalizados
     */
    public UsuarioController(LoginView loginView, UsuarioDAO usuarioDAO, MensajeInternacionalizacionHandler internacionalizar){
        this.loginView = loginView;
        this.usuarioDAO = usuarioDAO;
        this.Internacionalizar = internacionalizar;

        configurarLogin();
    }
    /**
     * Configura los eventos de los botones de la vista de login.
     */
    private void configurarLogin() {
        loginView.getBtnIniciarSesion().addActionListener(e -> manejarInicioSesion());
        loginView.getBtnSalir().addActionListener(e -> salirDelSistema());
    }
    /**
     * Maneja el proceso de inicio de sesión, validando credenciales y mostrando mensajes.
     */
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
    /**
     * Limpia los campos del formulario de login después del intento de autenticación.
     */
    private void limpiarCamposLogIn() {
        loginView.getTxtUsername().setText("");
        loginView.getPsfContraseña().setText("");
    }
    /**
     * Solicita confirmación y cierra el sistema si el usuario acepta.
     */
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
    /**
     * Configura la lógica del botón "Crear" en la vista de creación de usuario.
     * Valida los campos, crea un nuevo usuario si los datos son válidos y muestra los mensajes correspondientes.
     */
    private void configurarAnadirUsu() {
        usuarioCrearView.getBtnCrear().addActionListener(e -> {
            String usuario = usuarioCrearView.getTxtUsuario().getText().trim();
            String contrasenia = usuarioCrearView.getTxtContraseña().getText().trim();
            String nombre = usuarioCrearView.getTxtNombre().getText().trim();
            String correo = usuarioCrearView.getTxtCorreo().getText().trim();
            String telefono = usuarioCrearView.getTxtTelefono().getText().trim();
            String anioStr = usuarioCrearView.getTxtAnio().getText().trim();

            Object diaObj = usuarioCrearView.getCbxDia().getSelectedItem();
            Object mesObj = usuarioCrearView.getCbxMes().getSelectedItem();

            if (usuario.isEmpty() || contrasenia.isEmpty() || nombre.isEmpty()
                    || correo.isEmpty() || telefono.isEmpty() || anioStr.isEmpty()
                    || diaObj == null || mesObj == null) {
                usuarioCrearView.mostrarMensaje(Internacionalizar.get("mensaje.completar.campos"));
                return;
            }

            Usuario existente = usuarioDAO.buscarPorUsuario(usuario);
            if (existente != null) {
                usuarioCrearView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.existe"));
                return;
            }

            int dia = (int) diaObj;
            int anio;
            try {
                anio = Integer.parseInt(anioStr);
            } catch (NumberFormatException ex) {
                usuarioCrearView.mostrarMensaje(Internacionalizar.get("mensaje.anio.invalido"));
                return;
            }

            String mesStr = mesObj.toString();
            int mes = convertirMesAInt(mesStr);

            GregorianCalendar fechaNacimiento = new GregorianCalendar(anio, mes, dia);

            Usuario nuevo = new Usuario();
            nuevo.setUsername(usuario);
            nuevo.setContrasenia(contrasenia);
            nuevo.setNombre(nombre);
            nuevo.setCorreo(correo);
            nuevo.setTelefono(telefono);
            nuevo.setFechaNacimiento(fechaNacimiento);
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
    /**
     * Convierte el nombre del mes a su valor entero correspondiente (0 = enero, ..., 11 = diciembre).
     *
     * @param mes Nombre del mes (en español).
     * @return Índice del mes como entero.
     */
    private int convertirMesAInt(String mes) {
        mes = mes.toLowerCase();
        switch (mes) {
            case "mes.enero": return 0;
            case "mes.febrero": return 1;
            case "mes.marzo": return 2;
            case "mes.abril": return 3;
            case "mes.mayo": return 4;
            case "mes.junio": return 5;
            case "mes.julio": return 6;
            case "mes.agosto": return 7;
            case "mes.septiembre": return 8;
            case "mes.octubre": return 9;
            case "mes.noviembre": return 10;
            case "mes.diciembre": return 11;
            default: return 0; // También puedes lanzar una excepción si prefieres
        }
    }
    /**
     * Configura los eventos para modificar un usuario, dependiendo de su rol.
     * Habilita búsqueda y actualización de datos.
     */
    private void configurarModificarUsu() {
        if (usuario.getRol() == Rol.USUARIO) {
            configurarVistaParaUsuarioModificar(usuario);
        }

        usuarioModificarView.getBtnBuscar().addActionListener(e -> buscarUsuarioNombreModificar());
        usuarioModificarView.getBtnModificar().addActionListener(e -> modificarUsuario());
    }
    /**
     * Configura los eventos para eliminar un usuario. Si el rol es USUARIO,
     * carga automáticamente los datos del usuario actual.
     */
    private void configurarEliminarUsu() {
        if (usuario.getRol() == Rol.USUARIO) {
            configurarVistaParaUsuario(usuario);
        }
        usuarioEliminarView.getBtnBuscar().addActionListener(e -> buscarUsuarioNombre());
        usuarioEliminarView.getBtnEliminar().addActionListener(e -> eliminarUsuario());
    }
    /**
     * Configura los eventos de búsqueda y listado general de usuarios en la vista de lista.
     */
    private void configurarListarUsu() {
        usuarioListarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = usuarioListarView.getTxtNombre().getText().trim();
                if (input.isEmpty()) {
                    usuarioListarView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.invalido"));
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
                usuarioListarView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.noregistrado"));
            } else {
                usuarioListarView.cargarDatosListaUsuario(usuarios);
            }
        });
    }
    /**
     * Actualiza el idioma de todas las vistas controladas por este controlador.
     * Utiliza el idioma y país configurado en el manejador de internacionalización.
     */
    public void actualizarIdioma() {
        if (usuarioCrearView != null) usuarioCrearView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        if (usuarioEliminarView != null) usuarioEliminarView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        if (usuarioListarView != null) usuarioListarView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        if (usuarioModificarView != null) usuarioModificarView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        if (loginView != null) loginView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }
    /**
     * Cambia el idioma de la aplicación según los parámetros proporcionados.
     *
     * @param lenguaje Código del idioma (por ejemplo, "es").
     * @param pais Código del país (por ejemplo, "EC").
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        Internacionalizar.setLenguaje(lenguaje, pais);
        actualizarIdioma();
    }
    /**
     * Configura la vista de eliminación para un usuario con rol USUARIO.
     * Carga sus datos y bloquea campos para evitar su edición.
     *
     * @param usuario Usuario actual a cargar.
     */
    private void configurarVistaParaUsuario(Usuario usuario) {
        deshabilitarBusqueda();
        cargarDatosEnCampos(usuario);
        bloquearCampos();
    }
    /**
     * Deshabilita la búsqueda manual en la vista de eliminación,
     * útil cuando se muestra información del usuario actual.
     */
    private void deshabilitarBusqueda() {
        usuarioEliminarView.getBtnBuscar().setEnabled(false);
        usuarioEliminarView.getBtnBuscar().setVisible(false);
        usuarioEliminarView.getTxtUsuario().setEnabled(false);
    }
    /**
     * Carga los datos de un usuario en los campos de la vista de eliminación.
     *
     * @param usuario Usuario cuyos datos serán cargados.
     */
    private void cargarDatosEnCampos(Usuario usuario) {
        usuarioEliminarView.getTxtUsuario().setText(usuario.getUsername());
        usuarioEliminarView.getTxtContraseña().setText(usuario.getContrasenia());
        usuarioEliminarView.getTxtNombre().setText(usuario.getNombre());
        usuarioEliminarView.getTxtTelefono().setText(usuario.getTelefono());
        usuarioEliminarView.getTxtCorreo().setText(usuario.getCorreo());
        usuarioEliminarView.getTxtAnio().setText(usuario.getAnioNacimientoString());

        seleccionarFechaNacimiento(usuario);
    }
    /**
     * Establece la fecha de nacimiento en los combo boxes (día y mes) para vista de eliminación.
     *
     * @param usuario Usuario con la fecha de nacimiento.
     */
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
    /**
     * Bloquea los combo boxes de fecha (día y mes) en la vista de eliminación.
     */
    private void bloquearCampos() {
        usuarioEliminarView.getCbxDia().setEnabled(false);
        usuarioEliminarView.getCbxMes().setEnabled(false);
    }
    /**
     * Busca un usuario por nombre de usuario en la vista de eliminación.
     * Muestra mensaje si no se encuentra o si el campo está vacío.
     */
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
    /**
     * Carga los datos del usuario en la vista de modificación si tiene rol USUARIO.
     *
     * @param usuario Usuario cuyos datos serán mostrados.
     */
    private void configurarVistaParaUsuarioModificar(Usuario usuario) {
        deshabilitarBusquedaModificar();
        cargarDatosEnCamposModificar(usuario);
        bloquearCamposModificar();
    }
    /**
     * Desactiva campos de búsqueda en la vista de modificación.
     */
    private void deshabilitarBusquedaModificar() {
        usuarioModificarView.getBtnBuscar().setEnabled(false);
        usuarioModificarView.getBtnBuscar().setVisible(false);
        usuarioModificarView.getTxtUsuario().setEnabled(false);
    }
    /**
     * Carga los datos del usuario en la vista de modificación.
     *
     * @param usuario Usuario cuyos datos serán mostrados.
     */
    private void cargarDatosEnCamposModificar(Usuario usuario) {
        usuarioModificarView.getTxtUsuario().setText(usuario.getUsername());
        usuarioModificarView.getTxtContraseña().setText(usuario.getContrasenia());
        usuarioModificarView.getTxtNombre().setText(usuario.getNombre());
        usuarioModificarView.getTxtTelefono().setText(usuario.getTelefono());
        usuarioModificarView.getTxtCorreo().setText(usuario.getCorreo());
        usuarioModificarView.getTxtAnio().setText(usuario.getAnioNacimientoString());

        seleccionarFechaNacimientoModificar(usuario);
    }
    /**
     * Establece la fecha de nacimiento en los combo boxes (día y mes) para vista de modificación.
     *
     * @param usuario Usuario con la fecha de nacimiento.
     */
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
    /**
     * Bloquea los combo boxes de fecha (día y mes) en la vista de modificación.
     */
    private void bloquearCamposModificar() {
        usuarioModificarView.getCbxDia().setEnabled(false);
        usuarioModificarView.getCbxMes().setEnabled(false);
    }
    /**
     * Busca un usuario en la base de datos desde la vista de modificación.
     * Muestra los datos si lo encuentra, o un mensaje de error si no.
     */
    private void buscarUsuarioNombreModificar() {
        String username = usuarioModificarView.getTxtUsuario().getText().trim();

        if (username.isEmpty()) {
            usuarioModificarView.mostrarMensaje(Internacionalizar.get("mensaje.completar.campos"));
            return;
        }

        Usuario usuarioEncontrado = usuarioDAO.buscarPorUsuario(username);

        if (usuarioEncontrado != null) {
            usuarioModificarView.getTxtContraseña().setText(usuarioEncontrado.getContrasenia());
        } else {
            usuarioModificarView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.noencontrado"));
        }
    }
    /**
     * Modifica un usuario existente con los datos ingresados en la vista de modificación.
     * Valida los datos ingresados antes de actualizar.
     */
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
    /**
     * Elimina un usuario de la base de datos tras confirmar con el usuario.
     * Muestra mensaje de éxito o error, y limpia los campos si es necesario.
     */
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
    /**
     * Retorna el usuario actual que está usando la aplicación.
     *
     * @return Usuario logueado actualmente.
     */
    public Usuario getUsuario(){
        return usuario;
    }

}
