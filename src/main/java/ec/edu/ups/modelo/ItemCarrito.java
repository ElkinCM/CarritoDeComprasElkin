package ec.edu.ups.modelo;

/**
 * Representa un ítem dentro del carrito de compras.
 * Contiene un producto y la cantidad de ese producto en el carrito.
 */
public class ItemCarrito {

    /**
     * Producto asociado a este ítem del carrito.
     */
    private Producto producto;

    /**
     * Cantidad del producto en el carrito.
     */
    private int cantidad;

    /**
     * Constructor por defecto.
     * Inicializa un ítem vacío sin producto ni cantidad.
     */
    public ItemCarrito() {
    }

    /**
     * Constructor que inicializa el ítem con un producto y una cantidad.
     *
     * @param producto Producto asociado al ítem.
     * @param cantidad Cantidad del producto.
     */
    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    /**
     * Establece el producto de este ítem.
     *
     * @param producto Producto a asignar.
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Establece la cantidad de producto en este ítem.
     *
     * @param cantidad Cantidad a asignar.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el producto asociado a este ítem.
     *
     * @return Producto del ítem.
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Obtiene la cantidad del producto en este ítem.
     *
     * @return Cantidad del producto.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Calcula el subtotal de este ítem multiplicando el precio del producto
     * por la cantidad.
     *
     * @return Subtotal para este ítem.
     */
    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    /**
     * Representación en texto del ítem, mostrando el producto,
     * la cantidad y el subtotal.
     *
     * @return String descriptivo del ítem.
     */
    @Override
    public String toString() {
        return producto.toString() + " x " + cantidad + " = $" + getSubtotal();
    }

}
