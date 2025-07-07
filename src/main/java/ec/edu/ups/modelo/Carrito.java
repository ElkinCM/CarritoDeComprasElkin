package ec.edu.ups.modelo;

import java.text.SimpleDateFormat;
import java.util.*;

public class Carrito {

    private int codigo;
    private static int contador = 1;
    private GregorianCalendar fecha;
    private final List<ItemCarrito> items;
    private Usuario usuario;

    public Carrito(Usuario usuario) {
        this.items = new ArrayList<>();
        this.codigo = contador++;
        this.fecha = new GregorianCalendar();
        this.usuario = usuario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getFecha() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(fecha.getTime());
    }

    public Date setFecha() {
        return fecha.getTime();
    }
    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }

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

    public void eliminarProducto(int codigoProducto) {
        items.removeIf(item -> item.getProducto().getCodigo() == codigoProducto);
    }

    public double calcularSubtotal() {
        double Subtotal = 0;
        for (ItemCarrito item : items) {
            Subtotal += item.getSubtotal();
        }
        return Subtotal;
    }

    public void limpiarCarrito() {
        items.clear();
    }

    public boolean ningun() {
        return items.isEmpty();
    }

    public double calcularIVA() {
        return calcularSubtotal() * 0.12;
    }

    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

    public List<ItemCarrito> obtenerItems() {
        return new ArrayList<>(items);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Carrito.contador = contador;
    }

    public Date getFechaCrear() {
        return fecha.getTime();
    }
}

