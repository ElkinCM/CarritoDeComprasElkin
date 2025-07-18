package ec.edu.ups.modelo;

/**
 * Enumeración que representa los roles que puede tener un usuario dentro del sistema.
 *
 * Los roles permiten controlar el acceso y comportamiento de los usuarios en diferentes partes
 * de la aplicación según sus permisos.
 *
 * <ul>
 *   <li>{@link #ADMINISTRADOR} - Tiene acceso completo a las funcionalidades del sistema.</li>
 *   <li>{@link #USUARIO} - Tiene acceso limitado según su perfil de usuario final.</li>
 * </ul>
 *
 * Esta enumeración puede ser utilizada para asignar permisos, controlar vistas, o restringir acciones.
 *
 * @author
 */
public enum Rol {

    /**
     * Rol de administrador del sistema.
     * Tiene privilegios elevados, como gestionar usuarios, productos, y configuraciones.
     */
    ADMINISTRADOR,

    /**
     * Rol de usuario estándar.
     * Puede interactuar con funcionalidades básicas, como comprar productos o gestionar su cuenta.
     */
    USUARIO
}
