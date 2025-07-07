package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios;

    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<>();
        // Usuarios por defecto
// Usar constructor completo o crear uno específico para username, rol y contrasenia
        crear(new Usuario("admin", Rol.ADMINISTRADOR, "12345", "Administrador", "admin@dominio.com", "1234567890", null));
        crear(new Usuario("usuario", Rol.USUARIO, "12345", "Usuario", "usuario@dominio.com", "0987654321", null));

    }

    @Override
    public Usuario autenticar(String username, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username) && usuario.getContrasenia().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public void crear(Usuario usuario) {
        if (buscarPorUsuario(usuario.getUsername()) == null) {
            usuarios.add(usuario);
        }
    }

    @Override
    public Usuario buscarPorUsuario(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

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

    @Override
    public void actualizar(Usuario usuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
    }

    @Override
    public List<Usuario> listarAdministradores() {
        return listarRol(Rol.ADMINISTRADOR);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return listarRol(Rol.USUARIO);
    }

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

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios); // Retorna copia por seguridad
    }
}
