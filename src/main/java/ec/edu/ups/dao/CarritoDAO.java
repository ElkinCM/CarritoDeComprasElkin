package ec.edu.ups.dao;

import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.List;

/**
 * Interfaz para las operaciones de acceso a datos relacionadas con la entidad {@link Carrito}.
 * Define los métodos CRUD y consultas específicas para manipular carritos de compra.
 */
public interface CarritoDAO {

    /**
     * Crea un nuevo carrito en el almacenamiento.
     *
     * @param carrito el carrito a ser creado.
     */
    void crear(Carrito carrito);

    /**
     * Busca un carrito por su código único.
     *
     * @param codigo el código identificador del carrito.
     * @return el carrito correspondiente al código, o {@code null} si no se encuentra.
     */
    Carrito buscarPorCodigo(int codigo);

    /**
     * Actualiza los datos de un carrito existente.
     *
     * @param carrito el carrito con los datos actualizados.
     */
    void actualizar(Carrito carrito);

    /**
     * Elimina un carrito del almacenamiento según su código.
     *
     * @param codigo el código del carrito a eliminar.
     */
    void eliminar(int codigo);

    /**
     * Lista todos los carritos almacenados.
     *
     * @return una lista de todos los carritos.
     */
    List<Carrito> listarTodos();

    /**
     * Busca todos los carritos asociados a un usuario específico.
     *
     * @param usuario el usuario cuyos carritos se desean recuperar.
     * @return una lista de carritos pertenecientes al usuario.
     */
    List<Carrito> buscarPorUsuario(Usuario usuario);
}
