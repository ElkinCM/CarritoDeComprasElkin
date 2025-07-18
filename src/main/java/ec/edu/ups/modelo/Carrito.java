package ec.edu.ups.modelo;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Clase que representa un carrito de compras asociado a un usuario.
 * Permite agregar, eliminar productos, y calcular subtotales, IVA y total.
 */
public class Carrito {

    /**
     * Código único del carrito.
     */
    private int codigo;

    /**
     * Contador estático para asignar códigos únicos automáticamente.
     */
    private static int contador = 1;

    /**
     * Fecha en la que se creó el carrito.
     */
    private GregorianCalendar fecha;

    /**
     * Lista de ítems (productos con cantidad) del carrito.
     */
    private final List<ItemCarrito> items;

    /**
     * Usuario propietario del carrito.
     */
    private Usuario usuario;

    /**
     * Constructor que crea un carrito asociado a un usuario específico.
     * Inicializa la lista de items, asigna código único y fecha actual.
     *
     * @param usuario Usuario propietario del carrito.
     */
    public Carrito(Usuario usuario) {
        this.items = new ArrayList<>();
        this.codigo = contador++;
        this.fecha = new GregorianCalendar();
        this.usuario = usuario;
    }

    /**
     * Constructor que crea un carrito sin asignar un usuario.
     * Inicializa la lista de items, asigna código único y fecha actual.
     */
    public Carrito() {
        this.items = new ArrayList<>();
        this.codigo = contador++;
        this.fecha = new GregorianCalendar();
    }

    /**
     * Obtiene el código del carrito.
     *
     * @return Código único del carrito.
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Establece un código para el carrito.
     *
     * @param codigo Nuevo código a asignar.
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtiene la fecha de creación del carrito como cadena formateada (dd/MM/yyyy).
     *
     * @return Fecha en formato "día/mes/año".
     */
    public String getFecha() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(fecha.getTime());
    }

    /**
     * Devuelve la fecha de creación del carrito como objeto Date.
     *
     * @return Fecha de creación.
     */
    public Date setFecha() {
        return fecha.getTime();
    }

    /**
     * Establece la fecha de creación del carrito.
     *
     * @param fecha Nueva fecha (GregorianCalendar).
     */
    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }

    /**
     * Agrega un producto al carrito con una cantidad específica.
     * Si el producto ya existe, incrementa la cantidad.
     *
     * @param producto Producto a agregar.
     * @param cantidad Cantidad del producto.
     */
    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        ItemCarrito item = new ItemCarrito(producto, cantidad);
        items.add(item);
    }

    /**
     * Elimina un producto del carrito dado su código.
     *
     * @param codigoProducto Código del producto a eliminar.
     */
    public void eliminarProducto(int codigoProducto) {
        items.removeIf(item -> item.getProducto().getCodigo() == codigoProducto);
    }

    /**
     * Calcula el subtotal del carrito sumando los subtotales de cada ítem.
     *
     * @return Valor subtotal antes de impuestos.
     */
    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getSubtotal();
        }
        return subtotal;
    }

    /**
     * Limpia el carrito eliminando todos los ítems.
     */
    public void limpiarCarrito() {
        items.clear();
    }

    /**
     * Verifica si el carrito no tiene ningún ítem.
     *
     * @return true si el carrito está vacío, false si tiene al menos un ítem.
     */
    public boolean ningun() {
        return items.isEmpty();
    }

    /**
     * Calcula el valor del IVA (12%) sobre el subtotal del carrito.
     *
     * @return Valor del IVA.
     */
    public double calcularIVA() {
        return calcularSubtotal() * 0.12;
    }

    /**
     * Calcula el total a pagar sumando subtotal más IVA.
     *
     * @return Total a pagar.
     */
    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

    /**
     * Devuelve una copia de la lista de ítems del carrito.
     * Esto evita modificaciones externas a la lista original.
     *
     * @return Lista de ítems del carrito.
     */
    public List<ItemCarrito> obtenerItems() {
        return new ArrayList<>(items);
    }

    /**
     * Obtiene el usuario propietario del carrito.
     *
     * @return Usuario asociado.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Asigna un usuario propietario al carrito.
     *
     * @param usuario Usuario a asignar.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el contador estático actual usado para asignar códigos.
     *
     * @return Valor del contador.
     */
    public static int getContador() {
        return contador;
    }

    /**
     * Establece el valor del contador estático para códigos de carrito.
     *
     * @param contador Nuevo valor del contador.
     */
    public static void setContador(int contador) {
        Carrito.contador = contador;
    }

    /**
     * Obtiene la fecha de creación como objeto Date.
     *
     * @return Fecha de creación.
     */
    public Date getFechaCrear() {
        return fecha.getTime();
    }
}
