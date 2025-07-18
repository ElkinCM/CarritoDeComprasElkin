package ec.edu.ups.modelo;

import ec.edu.ups.util.CedulaValidar;
import ec.edu.ups.util.ContrasenaValidar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Clase que representa un usuario en el sistema con sus atributos básicos,
 * como nombre, correo, teléfono, fecha de nacimiento, rol, y sus relaciones con carritos y respuestas a preguntas.
 * Contiene validaciones específicas para cédula ecuatoriana y contraseña.
 *
 * @author
 */
public class Usuario {

    /** Nombre de usuario, generalmente la cédula ecuatoriana */
    private String username;

    /** Rol que tiene el usuario dentro del sistema */
    private Rol rol;

    /** Contraseña del usuario */
    private String contrasenia;

    /** Nombre completo del usuario */
    private String nombre;

    /** Correo electrónico del usuario */
    private String correo;

    /** Número de teléfono del usuario */
    private String telefono;

    /** Fecha de nacimiento del usuario */
    private GregorianCalendar fechaNacimiento;

    /** Lista de carritos asociados al usuario */
    private List<Carrito> carritos;

    /** Lista de respuestas a preguntas de seguridad o seguimiento */
    private List<RespuestaSegu> respuestaSegu;

    // ===== CONSTRUCTORES =====

    /**
     * Constructor completo para crear un usuario con todos los atributos.
     *
     * @param username        nombre de usuario (cedula ecuatoriana)
     * @param rol             rol asignado al usuario
     * @param contrasenia     contraseña del usuario
     * @param nombre          nombre completo
     * @param correo          correo electrónico
     * @param telefono        teléfono de contacto
     * @param fechaNacimiento fecha de nacimiento
     */
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

    /**
     * Constructor para crear un usuario con correo, rol y contraseña.
     *
     * @param correo      correo electrónico
     * @param rol         rol del usuario
     * @param contrasenia contraseña del usuario
     */
    public Usuario(String correo, Rol rol, String contrasenia) {
        this.correo = correo;
        this.rol = rol;
        this.contrasenia = contrasenia;
        this.carritos = new ArrayList<>();
        this.respuestaSegu = new ArrayList<>();
    }

    /**
     * Constructor vacío que inicializa las listas de carritos y respuestas.
     */
    public Usuario() {
        this.carritos = new ArrayList<>();
        this.respuestaSegu = new ArrayList<>();
    }

    /**
     * Constructor que inicializa el usuario con el username (cédula).
     *
     * @param username nombre de usuario (cédula)
     */
    public Usuario(String username) {
        this.username = username;
    }

    // ===== GETTERS Y SETTERS =====

    /**
     * Obtiene el nombre de usuario (cédula).
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario (debe ser una cédula ecuatoriana válida).
     *
     * @param username nombre de usuario (cédula)
     * @throws CedulaValidar si la cédula no es válida
     */
    public void setUsername(String username) throws CedulaValidar {
        if (!esCedulaEcuatorianaValida(username)) {
            throw new CedulaValidar("La cédula ingresada no es válida según el SRI.");
        }
        this.username = username;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return rol
     */
    public Rol getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rol rol a asignar
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return contraseña
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Establece la contraseña del usuario, validando que cumpla con los requisitos.
     *
     * @param contrasenia contraseña a establecer
     * @throws ContrasenaValidar si la contraseña no cumple con las reglas
     */
    public void setContrasenia(String contrasenia) throws ContrasenaValidar {
        validarContrasenia(contrasenia);
        this.contrasenia = contrasenia;
    }

    /**
     * Obtiene el nombre completo del usuario.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre completo del usuario.
     *
     * @param nombre nombre completo
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param correo correo electrónico
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene el número de teléfono del usuario.
     *
     * @return teléfono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono del usuario.
     *
     * @param telefono número telefónico
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la fecha de nacimiento como objeto GregorianCalendar.
     *
     * @return fecha de nacimiento
     */
    public GregorianCalendar getFechaNacimientoCalendar() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento del usuario.
     *
     * @param fechaNacimiento fecha de nacimiento
     */
    public void setFechaNacimiento(GregorianCalendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene la lista de respuestas asociadas al usuario.
     *
     * @return lista de respuestas
     */
    public List<RespuestaSegu> getRespuestaSegu() {
        return respuestaSegu;
    }

    /**
     * Establece la lista de respuestas asociadas al usuario.
     *
     * @param respuestaSegu lista de respuestas
     */
    public void setRespuestaSegu(List<RespuestaSegu> respuestaSegu) {
        this.respuestaSegu = respuestaSegu;
    }

    /**
     * Añade una respuesta a la lista de respuestas del usuario.
     *
     * @param pregunta      pregunta asociada
     * @param respuestaTexto texto de la respuesta
     */
    public void addRespuesta(Pregunta pregunta, String respuestaTexto) {
        if (this.respuestaSegu == null) {
            this.respuestaSegu = new ArrayList<>();
        }
        this.respuestaSegu.add(new RespuestaSegu(pregunta, respuestaTexto));
    }

    /**
     * Obtiene la lista de carritos asociados al usuario.
     *
     * @return lista de carritos
     */
    public List<Carrito> getCarritos() {
        return carritos;
    }

    /**
     * Establece la lista de carritos asociados al usuario.
     *
     * @param carritos lista de carritos
     */
    public void setCarritos(List<Carrito> carritos) {
        this.carritos = carritos;
    }

    /**
     * Añade un carrito a la lista de carritos del usuario.
     *
     * @param carrito carrito a añadir
     */
    public void addCarrito(Carrito carrito) {
        this.carritos.add(carrito);
    }

    /**
     * Obtiene la fecha de nacimiento como String formateada "dd/MM/yyyy".
     *
     * @return fecha de nacimiento en formato String o "Sin fecha" si no está establecida
     */
    public String getFechaNacimiento() {
        if (fechaNacimiento == null) {
            return "Sin fecha";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fechaNacimiento.getTime());
    }

    /**
     * Obtiene la fecha de nacimiento en formato String.
     *
     * @return fecha de nacimiento en formato String
     */
    public String getFechaNacimientoString() {
        return getFechaNacimiento();
    }

    /**
     * Obtiene el día de nacimiento en formato String.
     *
     * @return día de nacimiento o cadena vacía si no está definida
     */
    public String getDiaNacimientoString() {
        return fechaNacimiento != null ? String.valueOf(fechaNacimiento.get(GregorianCalendar.DAY_OF_MONTH)) : "";
    }

    /**
     * Obtiene el mes de nacimiento en formato String (1-12).
     *
     * @return mes de nacimiento o cadena vacía si no está definida
     */
    public String getMesNacimientoString() {
        return fechaNacimiento != null ? String.valueOf(fechaNacimiento.get(GregorianCalendar.MONTH) + 1) : "";
    }

    /**
     * Obtiene el año de nacimiento en formato String.
     *
     * @return año de nacimiento o cadena vacía si no está definida
     */
    public String getAnioNacimientoString() {
        return fechaNacimiento != null ? String.valueOf(fechaNacimiento.get(GregorianCalendar.YEAR)) : "";
    }

    /**
     * Representación en String del objeto Usuario con todos sus atributos.
     *
     * @return representación en String
     */
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

    /**
     * Valida si una cédula ecuatoriana es válida según las reglas del SRI.
     *
     * @param cedula cédula a validar
     * @return true si la cédula es válida, false en caso contrario
     */
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

    /**
     * Valida que la contraseña cumpla con los requisitos mínimos:
     * longitud mínima, contener mayúsculas, minúsculas y caracteres especiales.
     *
     * @param password contraseña a validar
     * @throws ContrasenaValidar si la contraseña no cumple con los requisitos
     */
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
