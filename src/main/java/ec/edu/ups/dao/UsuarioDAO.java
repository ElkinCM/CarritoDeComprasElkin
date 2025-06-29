package ec.edu.ups.dao;

import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

public interface UsuarioDAO {
    Usuario autenticar(String username, String password);

    void crear(Usuario usuario);

    Usuario buscarPorUsuario(String username);

    void eliminar(String username);

    void actualizar(Usuario usuario);

    List<Usuario> listarAdministradores();

    List<Usuario> listarUsuarios();

    List<Usuario> listarRol(Rol rol);

    List<Usuario> listarTodos();
}
