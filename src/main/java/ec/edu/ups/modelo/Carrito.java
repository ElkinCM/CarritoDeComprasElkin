package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class Carrito {

    private int codigo;
    private static int contador = 1;
    private GregorianCalendar fecha;
    private final List<ItemCarrito> items;
    private Usuario usuario;

    @Override
    public String toString() {
        return "Carrito{" +
                "codigo=" + codigo +
                ", fecha=" + (fecha != null ? fecha.getTime() : "N/A") +
                ", items=" + items.size() + " items" +
                ", subtotal=" + String.format("%.2f", calcularSubtotal()) +
                ", total=" + String.format("%.2f", calcularTotal()) +
                ", IVA=" + String.format("%.2f", calcularIVA()) +
                ", usuario=" + (usuario != null ? usuario.getUsername() : "N/A") +
                '}';
    }

    public Carrito() {
        this.items = new ArrayList<>();
        this.codigo = contador++;
        this.fecha = new GregorianCalendar();
    }


    public Carrito(int codigo, GregorianCalendar fecha, Usuario usuario) {
        this.items = new ArrayList<>();
        this.codigo = codigo;
        this.fecha = fecha;
        this.usuario = usuario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public GregorianCalendar getFecha() {
        return fecha;
    }

    public void setFecha(GregorianCalendar fecha) {
        this.fecha = fecha;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return; // Producto ya agregado, cantidad actualizada
            }
        }
        ItemCarrito item = new ItemCarrito(producto, cantidad);
        items.add(item);
    }

    public void eliminarProducto(int codigoProducto) {
        items.removeIf(item -> item.getProducto().getCodigo() == codigoProducto);
    }

    public void actualizarCantidadProducto(int codigoProducto, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            eliminarProducto(codigoProducto);
            return;
        }
        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo() == codigoProducto) {
                item.setCantidad(nuevaCantidad);
                return;
            }
        }
    }

    public double calcularSubtotal() {
        double currentSubtotal = 0;
        for (ItemCarrito item : items) {
            currentSubtotal += item.getSubtotal();
        }
        return currentSubtotal;
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
}

