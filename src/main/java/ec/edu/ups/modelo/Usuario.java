package ec.edu.ups.modelo;

import ec.edu.ups.util.CedulaValidar;
import ec.edu.ups.util.ContrasenaValidar;

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

    // ===== CONSTRUCTORES =====

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

    public Usuario(String username) {
        this.username = username;
    }


    // ===== GETTERS Y SETTERS =====

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws CedulaValidar {
        if (!esCedulaEcuatorianaValida(username)) {
            throw new CedulaValidar("La cédula ingresada no es válida según el SRI.");
        }
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

    public void setContrasenia(String contrasenia) throws ContrasenaValidar{
        validarContrasenia(contrasenia);
        this.contrasenia = contrasenia;
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

    // ===== VALIDACIONES INTERNAS =====

    private boolean esCedulaEcuatorianaValida(String cedula) {
        if (cedula == null || !cedula.matches("\\d{10}")) return false;

        int provincia = Integer.parseInt(cedula.substring(0, 2));
        if (provincia < 1 || provincia > 24) return false;

        int tercerDigito = Character.getNumericValue(cedula.charAt(2));
        if (tercerDigito >= 6) return false;

        int sumaPar = 0, sumaImpar = 0;
        for (int i = 0; i < 9; i++) {
            int digito = Character.getNumericValue(cedula.charAt(i));
            if (i % 2 == 0) {
                digito *= 2;
                if (digito > 9) digito -= 9;
                sumaImpar += digito;
            } else {
                sumaPar += digito;
            }
        }

        int total = sumaPar + sumaImpar;
        int decenaSuperior = ((total + 9) / 10) * 10;
        int digitoVerificador = decenaSuperior - total;
        if (digitoVerificador == 10) digitoVerificador = 0;

        return digitoVerificador == Character.getNumericValue(cedula.charAt(9));
    }

    private void validarContrasenia(String password) throws ContrasenaValidar {
        if (password.length() < 6)
            throw new ContrasenaValidar("La contraseña debe tener al menos 6 caracteres");

        if (!password.matches(".*[A-Z].*"))
            throw new ContrasenaValidar("La contraseña debe contener al menos una letra mayúscula");

        if (!password.matches(".*[a-z].*"))
            throw new ContrasenaValidar("La contraseña debe contener al menos una letra minúscula");

        if (!password.matches(".*[@_.-].*"))
            throw new ContrasenaValidar("La contraseña debe contener al menos un carácter especial (@, _, -, .)");
    }
}
