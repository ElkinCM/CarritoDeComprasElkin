package ec.edu.ups.dao.impl.DAOCarrito;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementación en memoria del DAO para la entidad {@link Carrito}.
 * Almacena los objetos en una lista local simulando el comportamiento de una base de datos.
 *
 * @author
 */
public class CarritoDAOMemoria implements CarritoDAO {

    private List<Carrito> listaCarritos;

    /**
     * Constructor por defecto que inicializa la lista de carritos.
     */
    public CarritoDAOMemoria() {
        this.listaCarritos = new ArrayList<>();
    }

    /**
     * Crea un nuevo carrito y lo agrega a la lista.
     *
     * @param carrito El objeto {@link Carrito} que se desea agregar.
     */
    @Override
    public void crear(Carrito carrito) {
        listaCarritos.add(carrito);
    }

    /**
     * Actualiza un carrito existente en la lista.
     * Busca el carrito por su código y lo reemplaza.
     *
     * @param carrito El objeto {@link Carrito} con los datos actualizados.
     */
    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < listaCarritos.size(); i++) {
            if (listaCarritos.get(i).getCodigo() == carrito.getCodigo()) {
                listaCarritos.set(i, carrito);
                break;
            }
        }
    }

    /**
     * Elimina un carrito de la lista según su código.
     *
     * @param codigo El código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = listaCarritos.iterator();
        while (iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if (carrito.getCodigo() == codigo) {
                iterator.remove();
                break;
            }
        }
    }

    /**
     * Busca un carrito por su código.
     *
     * @param codigo El código del carrito que se desea buscar.
     * @return El carrito correspondiente al código o {@code null} si no se encuentra.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : listaCarritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    /**
     * Retorna una lista con todos los carritos almacenados.
     *
     * @return Lista de todos los objetos {@link Carrito}.
     */
    @Override
    public List<Carrito> listarTodos() {
        return listaCarritos;
    }

    /**
     * Busca todos los carritos asociados a un usuario específico.
     *
     * @param usuario El objeto {@link Usuario} cuyos carritos se desean buscar.
     * @return Lista de carritos pertenecientes al usuario.
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> carritoUsuario = new ArrayList<>();
        for (Carrito carrito : listaCarritos) {
            if (carrito.getUsuario().equals(usuario)) {
                carritoUsuario.add(carrito);
            }
        }
        return carritoUsuario;
    }
}
