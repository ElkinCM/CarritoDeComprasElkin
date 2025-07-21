package ec.edu.ups.dao.impl.DAOProducto;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria del DAO para la entidad {@link Producto}.
 * Utiliza una lista local para almacenar productos simulando una base de datos.
 * Permite operaciones CRUD y búsqueda por nombre o código.
 */
public class ProductoDAOMemoria implements ProductoDAO {

    private List<Producto> productos;

    /**
     * Constructor por defecto.
     * Inicializa la lista de productos y agrega productos de ejemplo.
     */
    public ProductoDAOMemoria() {
        productos = new ArrayList<>();
        inicializarProductosEjemplo();
    }

    /**
     * Inicializa la lista con productos de ejemplo.
     */
    private void inicializarProductosEjemplo() {
        productos.add(new Producto(1, "Cámara", 350.0));
        productos.add(new Producto(2, "Smartwatch", 220.0));
        productos.add(new Producto(3, "Altavoz Bluetooth", 180.0));
        productos.add(new Producto(4, "Disco Duro", 90.0));
        productos.add(new Producto(5, "Router WiFi", 130.0));
        productos.add(new Producto(6, "Drone", 800.0));
        productos.add(new Producto(7, "Consola", 400.0));
        productos.add(new Producto(8, "Proyector", 270.0));
        productos.add(new Producto(9, "Lámpara LED", 60.0));
        productos.add(new Producto(10, "Teclado Mecánico", 110.0));
    }

    /**
     * Agrega un nuevo producto a la lista.
     * Lanza IllegalArgumentException si el producto es nulo,
     * tiene código inválido o el código ya existe.
     *
     * @param producto El objeto {@link Producto} que se desea agregar.
     */
    @Override
    public void crear(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        if (producto.getCodigo() <= 0) {
            throw new IllegalArgumentException("El código del producto debe ser mayor a 0.");
        }
        if (buscarPorCodigo(producto.getCodigo()) != null) {
            throw new IllegalArgumentException("Código ya existente. Por favor, ingrese otro código.");
        }
        productos.add(producto);
    }

    /**
     * Busca un producto por su código único.
     *
     * @param codigo El código del producto que se desea buscar.
     * @return El producto correspondiente al código, o {@code null} si no se encuentra.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }

    /**
     * Busca productos cuyos nombres comiencen con la cadena dada.
     *
     * @param nombre Nombre (o prefijo) del producto que se desea buscar.
     * @return Lista de productos cuyo nombre empieza con la cadena especificada.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().startsWith(nombre)) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }

    /**
     * Actualiza un producto existente en la lista.
     * Busca el producto por su código y lo reemplaza con el nuevo.
     *
     * @param producto El producto con los datos actualizados.
     */
    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                break;
            }
        }
    }

    /**
     * Elimina un producto de la lista según su código.
     *
     * @param codigo El código del producto que se desea eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    /**
     * Devuelve la lista completa de productos.
     *
     * @return Lista de todos los productos almacenados.
     */
    @Override
    public List<Producto> listarTodos() {
        return productos;
    }
}
