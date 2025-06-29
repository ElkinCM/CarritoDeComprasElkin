package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.vista.Carrito.CarritoAnadirView;
import ec.edu.ups.vista.Producto.ProductoAnadirView;
import ec.edu.ups.vista.Producto.ProductoEliminarView;
import ec.edu.ups.vista.Producto.ProductoListaView;
import ec.edu.ups.vista.Producto.ProductoModificarView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductoController {

    private ProductoAnadirView productoAnadirView;
    private ProductoListaView productoListaView;
    private ProductoEliminarView productoEliminarView;
    private ProductoModificarView productoModificarView;
    private CarritoAnadirView carritoAnadirView;
    private final ProductoDAO productoDAO;

    public ProductoController(ProductoDAO productoDAO, CarritoAnadirView carritoAnadirView, ProductoModificarView productoModificarView, ProductoEliminarView productoEliminarView, ProductoListaView productoListaView, ProductoAnadirView productoAnadirView) {
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.productoModificarView = productoModificarView;
        this.productoEliminarView = productoEliminarView;
        this.productoListaView = productoListaView;
        this.productoAnadirView = productoAnadirView;
        configurarAñadirEventos();
        configurarListaEventos();
        configurarEliminarEventos();
        configurarModificarEventos();
        configurarCarritoEventos();
    }

    public ProductoAnadirView getProductoAnadirView() {
        return productoAnadirView;
    }

    public void setProductoAnadirView(ProductoAnadirView productoAnadirView) {
        this.productoAnadirView = productoAnadirView;

    }

    public ProductoListaView getProductoListaView() {
        return productoListaView;
    }

    public void setProductoListaView(ProductoListaView productoListaView) {
        this.productoListaView = productoListaView;
    }

    public ProductoEliminarView getProductoEliminarView() {
        return productoEliminarView;
    }

    public void setProductoEliminarView(ProductoEliminarView productoEliminarView) {
        this.productoEliminarView = productoEliminarView;
    }

    public ProductoModificarView getProductoModificarView() {
        return productoModificarView;
    }

    public void setProductoModificarView(ProductoModificarView productoModificarView) {
        this.productoModificarView = productoModificarView;
    }

    private void configurarAñadirEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
    }
    private void configurarListaEventos(){
        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });
        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarProductos();
            }
        });

    }

    private void configurarEliminarEventos() {

        productoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(productoEliminarView.getTxtNombre().getText());
                Producto productoEncontrado = productoDAO.buscarPorCodigo(codigo);
                if (productoEncontrado == null) {
                    productoEliminarView.mostrarMensaje("El producto no existe");
                } else {
                    productoEliminarView.mostrarMensaje("El producto " + productoEncontrado.getNombre() + " fue eliminado correctamente");
                    productoDAO.eliminar(codigo);
                    productoEliminarView.getTxtNombre().setText("");
                    productoEliminarView.mostrarProductos(productoDAO.listarTodos());
                }
            }
        });
    }

    private void configurarModificarEventos() {

        productoModificarView.getBtnBuscar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo  = Integer.parseInt(productoModificarView.getTxtCodigo().getText());
                Producto productoEncontrado = productoDAO.buscarPorCodigo(codigo);
                if(productoEncontrado == null) {
                    productoModificarView.mostrarMensaje("El producto no existe");
                }
                else {
                    productoModificarView.getTxtCodigo().setText(String.valueOf(productoEncontrado.getCodigo()));
                    productoModificarView.getTxtNombre().setText(productoEncontrado.getNombre());
                    productoModificarView.getTxtPrecio().setText(String.valueOf(productoEncontrado.getPrecio()));
                    productoModificarView.getBtnModificar().setEnabled(true);
                    productoModificarView.getBtnBuscar().setEnabled(false);
                    productoModificarView.getBtnModificar().addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int respueta = JOptionPane.showConfirmDialog(null,"¿Desea modificar este producto?","Confirmar",JOptionPane.YES_NO_OPTION);
                            if (respueta == JOptionPane.YES_OPTION) {
                                productoDAO.eliminar(codigo);
                                productoDAO.crear(new Producto(Integer.parseInt(productoModificarView.getTxtCodigo().getText()),productoModificarView.getTxtNombre().getText(),Double.parseDouble(productoModificarView.getTxtPrecio().getText())));
                                productoModificarView.mostrarMensaje("Producto modificado correctamente");
                                productoModificarView.getBtnModificar().setEnabled(false);
                                productoModificarView.getBtnBuscar().setEnabled(true);
                                productoModificarView.getTxtPrecio().setText("");
                                productoModificarView.getTxtNombre().setText("");
                                productoModificarView.getTxtCodigo().setText("");
                                productoModificarView.getTxtPrecio().setEnabled(false);
                                productoModificarView.getTxtNombre().setEnabled(false);
                            }
                        }
                    });
                }
            }
        });


    }


    private void configurarCarritoEventos() {
        carritoAnadirView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProductoPorCodigo();
            }
        });
    }

    private void buscarProducto() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productos = productoDAO.buscarPorNombre(nombre);
        productoListaView.getTxtBuscar().setText("");
        productoListaView.mostrarProductos(productos);
    }
    private void mostrarProductos() {
        productoListaView.mostrarProductos(productoDAO.listarTodos());
        productoEliminarView.mostrarProductos(productoDAO.listarTodos());
    }
    private void guardarProducto() {
        int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
        String nombre = productoAnadirView.getTxtNombre().getText();
        double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

        productoDAO.crear(new Producto(codigo, nombre, precio));
        productoAnadirView.mostrarMensaje("Producto guardado correctamente");
        productoAnadirView.limpiarCampos();
        productoAnadirView.mostrarProductos(productoDAO.listarTodos());
    }



    private void buscarProductoPorCodigo(){
        int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
        productoModificarView.getTxtCodigo().setEnabled(false);
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto == null){
            carritoAnadirView.mostrarMensaje("No se han encontrado el producto");
            carritoAnadirView.getTxtNombre().setText("");
            carritoAnadirView.getTxtPrecio().setText("");
        }else{
            carritoAnadirView.getTxtNombre().setText(producto.getNombre());
            carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
        }
    }


}