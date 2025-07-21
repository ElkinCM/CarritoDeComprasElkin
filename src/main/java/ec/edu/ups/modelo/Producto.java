package ec.edu.ups.modelo;

/**
 * Clase que representa un producto disponible en el sistema.
 *
 * Cada producto tiene un código único, un nombre y un precio. El sistema puede asignar el código automáticamente
 * utilizando un contador estático o bien recibirlo manualmente mediante constructor o setter.
 *
 * Esta clase es útil en sistemas de ventas, catálogos o carritos de compra.
 *
 * @author
 */
public class Producto {

    /** Código único del producto. */
    private int codigo;

    /** Nombre del producto. */
    private String nombre;

    /** Precio del producto. */
    private double precio;

    /** Contador estático para generar códigos automáticos. */
    private static int contador = 1;

    /**
     * Constructor vacío. Útil para frameworks o serialización.
     */
    public Producto() {
    }

    /**
     * Constructor completo para crear un producto con código, nombre y precio específicos.
     *
     * @param codigo Código único del producto.
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     */
    public Producto(int codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Constructor que asigna automáticamente el código usando un contador estático.
     *
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     */
    public Producto(String nombre, double precio) {
        this.codigo = contador++;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Establece el código del producto manualmente.
     *
     * @param codigo Código del producto.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre Nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio Precio del producto.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el código del producto.
     *
     * @return Código del producto.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return Nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return Precio del producto.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Obtiene el valor actual del contador estático.
     * Este método no es comúnmente necesario a menos que se quiera verificar o reiniciar el contador.
     *
     * @return Valor actual del contador.
     */
    public int getContador() {
        return contador;
    }

    /**
     * Establece un nuevo valor para el contador estático.
     * Útil para pruebas o reinicio de numeración.
     *
     * @param contador Nuevo valor para el contador.
     */
    public void setContador(int contador) {
        Producto.contador = contador;
    }

    /**
     * Retorna una representación en cadena del producto, incluyendo código, nombre y precio.
     *
     * @return Cadena representativa del producto.
     */
    @Override
    public String toString() {
        return codigo + " - " + nombre + " - $" + precio;
    }
}
