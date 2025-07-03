package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String username;
    private Rol rol;
    private String contrasenia;
    private List<RespuestaSegu> respuestaSegu;

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

    public List<RespuestaSegu> getRespuestaSegu(){
        return respuestaSegu;
    }
    public void setRespuestaSegu(List<RespuestaSegu> respuestaSegu){
        this.respuestaSegu = respuestaSegu;
    }

    public void addRespuesta(Pregunta pregunta, String respuestaTexto) {
        if (this.respuestaSegu == null) {
            this.respuestaSegu = new ArrayList<>();
        }
        this.respuestaSegu.add(new RespuestaSegu(pregunta, respuestaTexto));
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
