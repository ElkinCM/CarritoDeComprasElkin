package ec.edu.ups.modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Usuario {
    private String username;
    private Rol rol;
    private String contrasenia;
    private String nombre;
    private String correo;
    private String telefono;
    private GregorianCalendar fechaNacimiento;
    private List<Carrito> carritos;
    private List<RespuestaSegu> respuestaSegu;

    // Constructor completo original
    public Usuario(String username, Rol rol, String contrasenia, String nombre, String correo, String telefono, GregorianCalendar fechaNacimiento) {
        this.username = username;
        this.rol = rol;
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.carritos = new ArrayList<>();
        this.respuestaSegu = new ArrayList<>();
    }

    // Constructor que recibía correo en lugar de username
    public Usuario(String correo, Rol rol, String contrasenia) {
        this.correo = correo;
        this.rol = rol;
        this.contrasenia = contrasenia;
        this.carritos = new ArrayList<>();
        this.respuestaSegu = new ArrayList<>();
    }

    public Usuario() {
        this.carritos = new ArrayList<>();
        this.respuestaSegu = new ArrayList<>();
    }

    // Getters y Setters
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public GregorianCalendar getFechaNacimientoCalendar() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(GregorianCalendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<RespuestaSegu> getRespuestaSegu() {
        return respuestaSegu;
    }

    public void setRespuestaSegu(List<RespuestaSegu> respuestaSegu) {
        this.respuestaSegu = respuestaSegu;
    }

    public void addRespuesta(Pregunta pregunta, String respuestaTexto) {
        if (this.respuestaSegu == null) {
            this.respuestaSegu = new ArrayList<>();
        }
        this.respuestaSegu.add(new RespuestaSegu(pregunta, respuestaTexto));
    }

    public List<Carrito> getCarritos() {
        return carritos;
    }

    public void setCarritos(List<Carrito> carritos) {
        this.carritos = carritos;
    }

    public void addCarrito(Carrito carrito) {
        this.carritos.add(carrito);
    }

    public String getFechaNacimiento() {
        if (fechaNacimiento == null) {
            return "Sin fecha";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fechaNacimiento.getTime());
    }

    public String getFechaNacimientoString() {
        return getFechaNacimiento();
    }

    public String getDiaNacimientoString() {
        return fechaNacimiento != null ? String.valueOf(fechaNacimiento.get(GregorianCalendar.DAY_OF_MONTH)) : "";
    }

    public String getMesNacimientoString() {
        return fechaNacimiento != null ? String.valueOf(fechaNacimiento.get(GregorianCalendar.MONTH) + 1) : "";
    }

    public String getAnioNacimientoString() {
        return fechaNacimiento != null ? String.valueOf(fechaNacimiento.get(GregorianCalendar.YEAR)) : "";
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", rol=" + rol +
                ", password='" + contrasenia + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento=" + getFechaNacimiento() +
                ", carritos=" + carritos +
                ", respuestaSegu=" + respuestaSegu +
                '}';
    }
}
