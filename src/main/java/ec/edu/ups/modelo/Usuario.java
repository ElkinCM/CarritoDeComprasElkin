package ec.edu.ups.modelo;

public class Usuario {
    private String username;
    private Rol rol;
    private String contrasenia;

    public Usuario(String username, Rol rol, String contrasenia) {
        this.username = username;
        this.rol = rol;
        this.contrasenia = contrasenia;
    }

    public Usuario() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String password) {
        this.contrasenia = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", rol=" + rol +
                ", password='" + contrasenia + '\'' +
                '}';
    }
}
