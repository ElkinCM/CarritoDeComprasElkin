package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos para la entidad {@link Usuario}.
 * Proporciona métodos para gestionar usuarios dentro del sistema.
 */
public interface UsuarioDAO {

    /**
     * Autentica a un usuario mediante su nombre de usuario y contraseña.
     *
     * @param username  el nombre de usuario.
     * @param password  la contraseña del usuario.
     * @return el usuario autenticado si las credenciales son válidas, o {@code null} si no coinciden.
     */
    Usuario autenticar(String username, String password);

    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param usuario el usuario a registrar.
     */
    void crear(Usuario usuario);

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username el nombre de usuario a buscar.
     * @return el usuario correspondiente al nombre, o {@code null} si no se encuentra.
     */
    Usuario buscarPorUsuario(String username);

    /**
     * Elimina un usuario identificado por su nombre de usuario.
     *
     * @param username el nombre de usuario del usuario a eliminar.
     */
    void eliminar(String username);

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param usuario el usuario con los datos actualizados.
     */
    void actualizar(Usuario usuario);

    /**
     * Lista todos los usuarios con rol de administrador.
     *
     * @return una lista de usuarios con rol {@link Rol#ADMINISTRADOR}.
     */
    List<Usuario> listarAdministradores();

    /**
     * Lista todos los usuarios con rol de usuario común.
     *
     * @return una lista de usuarios con rol {@link Rol#USUARIO}.
     */
    List<Usuario> listarUsuarios();

    /**
     * Lista los usuarios que tienen un rol específico.
     *
     * @param rol el rol por el cual filtrar.
     * @return una lista de usuarios con el rol especificado.
     */
    List<Usuario> listarRol(Rol rol);

    /**
     * Lista todos los usuarios registrados en el sistema.
     *
     * @return una lista con todos los usuarios.
     */
    List<Usuario> listarTodos();
}
