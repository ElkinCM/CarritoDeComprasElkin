package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarritoDAOMemoria implements CarritoDAO {
    private List<Carrito> listaCarritos;

    public CarritoDAOMemoria() {
        this.listaCarritos = new ArrayList<>(); // Inicializamos la lista aquí
    }

    @Override
    public void crear(Carrito carrito) {
        listaCarritos.add(carrito);
    }

    @Override
    public void actualizar(Carrito carrito) {
        for(int i = 0; i<listaCarritos.size();i++){
            if(listaCarritos.get(i).getCodigo() == carrito.getCodigo()){
                listaCarritos.set(i,carrito);
                break;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = listaCarritos.iterator();
        while(iterator.hasNext()){
            Carrito carrito = iterator.next();
            if (carrito.getCodigo()==codigo) {
                iterator.remove();
                break;
            }
        }
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : listaCarritos) {
            if(carrito.getCodigo() == codigo){ return carrito;}
        }
        return null;
    }

    @Override
    public List<Carrito> listarTodos() {
        return listaCarritos;
    }

    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> carritoUsuario = new ArrayList<>();
        for(Carrito carrito : listaCarritos){
            if(carrito.getUsuario().equals(usuario));
            carritoUsuario.add(carrito);
        }
        return carritoUsuario;
    }
}
