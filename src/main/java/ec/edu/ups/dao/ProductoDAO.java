package ec.edu.ups.dao;

import ec.edu.ups.modelo.Producto;

import java.util.List;

/**
 * Interfaz que define las operaciones de acceso a datos para la entidad {@link Producto}.
 * Proporciona los métodos necesarios para gestionar productos en el sistema.
 */
public interface ProductoDAO {

    /**
     * Crea un nuevo producto en el sistema.
     *
     * @param producto el producto a ser registrado.
     */
    void crear(Producto producto);

    /**
     * Busca un producto utilizando su código único.
     *
     * @param codigo el código identificador del producto.
     * @return el producto correspondiente al código, o {@code null} si no se encuentra.
     */
    Producto buscarPorCodigo(int codigo);

    /**
     * Busca productos que contengan el nombre especificado (total o parcialmente).
     *
     * @param nombre el nombre o parte del nombre del producto.
     * @return una lista de productos cuyo nombre coincida con el parámetro proporcionado.
     */
    List<Producto> buscarPorNombre(String nombre);

    /**
     * Actualiza la información de un producto existente.
     *
     * @param producto el producto con los datos actualizados.
     */
    void actualizar(Producto producto);

    /**
     * Elimina un producto identificado por su código.
     *
     * @param codigo el código del producto a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Lista todos los productos registrados en el sistema.
     *
     * @return una lista de todos los productos.
     */
    List<Producto> listarTodos();
}
