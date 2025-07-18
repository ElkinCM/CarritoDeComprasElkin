package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria del DAO para la entidad {@link Usuario}.
 * Utiliza una lista local para almacenar objetos Usuario y simular una base de datos.
 * Permite operaciones CRUD, autenticación y filtrado por rol.
 * Se crean dos usuarios por defecto al instanciar la clase: un administrador y un usuario estándar.
 *
 * @author
 */
public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;

    /**
     * Constructor por defecto. Inicializa la lista de usuarios y crea usuarios por defecto:
     * un administrador y un usuario normal.
     */
    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<>();
        // Usuarios por defecto
        crear(new Usuario("admin", Rol.ADMINISTRADOR, "12345", "Administrador", "admin@dominio.com", "1234567890", null));
        crear(new Usuario("usuario", Rol.USUARIO, "12345", "Usuario", "usuario@dominio.com", "0987654321", null));
    }

    /**
     * Autentica a un usuario verificando el nombre de usuario y la contraseña.
     *
     * @param username Nombre de usuario.
     * @param password Contraseña.
     * @return El objeto {@link Usuario} autenticado o {@code null} si las credenciales no coinciden.
     */
    @Override
    public Usuario autenticar(String username, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getContrasenia().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Crea un nuevo usuario y lo agrega a la lista si no existe otro con el mismo nombre de usuario.
     *
     * @param usuario El objeto {@link Usuario} a agregar.
     */
    @Override
    public void crear(Usuario usuario) {
        if (buscarPorUsuario(usuario.getUsername()) == null) {
            usuarios.add(usuario);
        }
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario a buscar.
     * @return El usuario correspondiente o {@code null} si no se encuentra.
     */
    @Override
    public Usuario buscarPorUsuario(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Elimina un usuario de la lista según su nombre de usuario.
     *
     * @param username Nombre de usuario a eliminar.
     */
    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuario = iterator.next();
            if (usuario.getUsername().equals(username)) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param usuario El usuario con los datos actualizados.
     */
    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    /**
     * Lista todos los usuarios con el rol de administrador.
     *
     * @return Lista de usuarios con rol {@link Rol#ADMINISTRADOR}.
     */
    @Override
    public List<Usuario> listarAdministradores() {
        return listarRol(Rol.ADMINISTRADOR);
    }

    /**
     * Lista todos los usuarios con el rol de usuario normal.
     *
     * @return Lista de usuarios con rol {@link Rol#USUARIO}.
     */
    @Override
    public List<Usuario> listarUsuarios() {
        return listarRol(Rol.USUARIO);
    }

    /**
     * Lista todos los usuarios que tienen el rol especificado.
     *
     * @param rol El rol por el cual se desea filtrar.
     * @return Lista de usuarios que tienen el rol indicado.
     */
    @Override
    public List<Usuario> listarRol(Rol rol) {
        List<Usuario> usuariosRol = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario.getRol() == rol) {
                usuariosRol.add(usuario);
            }
        }
        return usuariosRol;
    }

    /**
     * Lista todos los usuarios almacenados.
     *
     * @return Lista de todos los usuarios (copia de la lista original).
     */
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios); // Retorna copia por seguridad
    }
}
