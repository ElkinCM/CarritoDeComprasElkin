package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
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
    private final MensajeInternacionalizacionHandler mensaje;

    public ProductoController(ProductoDAO productoDAO, CarritoAnadirView carritoAnadirView, ProductoModificarView productoModificarView,
                              ProductoEliminarView productoEliminarView, ProductoListaView productoListaView,
                              ProductoAnadirView productoAnadirView, MensajeInternacionalizacionHandler mensaje) {
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.productoModificarView = productoModificarView;
        this.productoEliminarView = productoEliminarView;
        this.productoListaView = productoListaView;
        this.productoAnadirView = productoAnadirView;
        this.mensaje = mensaje;
    }
    private void configurarEventos() {
        productoAnadirView.getBtnAceptar().addActionListener(e -> guardarProducto());
        productoAnadirView.getBtnLimpiar().addActionListener(e -> productoAnadirView.limpiarCampos());

        productoListaView.getBtnBuscar().addActionListener(e -> buscarProductoPorNombre());
        productoListaView.getBtnListar().addActionListener(e -> listarTodosLosProductos());

        productoEliminarView.getBtnBuscar().addActionListener(e -> buscarProductoParaEliminar());
        productoEliminarView.getBtnEliminar().addActionListener(e -> eliminarProducto());

        productoModificarView.getBtnBuscar().addActionListener(e -> buscarProductoParaModificar());
        productoModificarView.getBtnModificar().addActionListener(e -> modificarProducto());

        carritoAnadirView.getBtnBuscar().addActionListener(e -> buscarProductoParaCarrito());
    }

    private void guardarProducto() {
        try {
            int codigo = Integer.parseInt(productoAnadirView.getTxtCodigo().getText());
            String nombre = productoAnadirView.getTxtNombre().getText();
            double precio = Double.parseDouble(productoAnadirView.getTxtPrecio().getText());

            if (nombre.isEmpty()) {
                productoAnadirView.mostrarMensaje(mensaje.get("msg.usr.error.camposVacios"));
                return;
            }

            productoDAO.crear(new Producto(codigo, nombre, precio));
            productoAnadirView.mostrarMensaje(mensaje.get("msg.prod.guardado"));
            productoAnadirView.limpiarCampos();
        } catch (NumberFormatException ex) {
            productoAnadirView.mostrarMensaje(mensaje.get("msg.car.codInvalido"));
        }
    }

    private void buscarProductoPorNombre() {
        String nombre = productoListaView.getTxtBuscar().getText();
        List<Producto> productos = productoDAO.buscarPorNombre(nombre);
        productoListaView.mostrarProductos(productos);
    }

    private void listarTodosLosProductos() {
        productoListaView.mostrarProductos(productoDAO.listarTodos());
    }

    private void buscarProductoParaEliminar() {
        try {
            int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null) {
                productoEliminarView.mostrarMensaje(mensaje.get("msg.noEncontrado"));
            } else {
                productoEliminarView.getTxtNombre().setText(producto.getNombre());
                productoEliminarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
                productoEliminarView.getTxtCodigo().setEnabled(false);
                productoEliminarView.getBtnEliminar().setEnabled(true);
            }
        } catch (NumberFormatException ex) {
            productoEliminarView.mostrarMensaje(mensaje.get("msg.car.codInvalido"));
        }
    }

    private void eliminarProducto() {
        String mensajeConfirmacion = mensaje.get("confirm.prod.elim");
        int respuesta = JOptionPane.showConfirmDialog(productoEliminarView, mensajeConfirmacion, mensaje.get("confirm.app.titulo"), JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            int codigo = Integer.parseInt(productoEliminarView.getTxtCodigo().getText());
            productoDAO.eliminar(codigo);
            productoEliminarView.mostrarMensaje(mensaje.get("msg.prod.eliminado"));
            productoEliminarView.limpiarCampos();
        }
    }

    private void buscarProductoParaModificar() {
        try {
            int codigo = Integer.parseInt(productoModificarView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null) {
                productoModificarView.mostrarMensaje(mensaje.get("msg.noEncontrado"));
            } else {
                productoModificarView.getTxtNombre().setText(producto.getNombre());
                productoModificarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
                productoModificarView.getBtnModificar().setEnabled(true);
                productoModificarView.getBtnBuscar().setEnabled(false);
                productoModificarView.getTxtCodigo().setEditable(false);
            }
        } catch (NumberFormatException ex) {
            productoModificarView.mostrarMensaje(mensaje.get("msg.car.codInvalido"));
        }
    }

    private void modificarProducto() {
        String mensajeConfirmacion = mensaje.get("confirm.prod.mod");
        int respuesta = JOptionPane.showConfirmDialog(productoModificarView, mensajeConfirmacion, mensaje.get("confirm.app.titulo"), JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                int codigo = Integer.parseInt(productoModificarView.getTxtCodigo().getText());
                String nombre = productoModificarView.getTxtNombre().getText();
                double precio = Double.parseDouble(productoModificarView.getTxtPrecio().getText());

                if (nombre.trim().isEmpty()) {
                    productoModificarView.mostrarMensaje(mensaje.get("msg.usr.error.camposVacios"));
                    return;
                }

                Producto productoActualizado = new Producto(codigo, nombre, precio);
                productoDAO.actualizar(productoActualizado);

                productoModificarView.mostrarMensaje(mensaje.get("msg.prod.modificado"));
                productoModificarView.getTxtCodigo().setText("");
                productoModificarView.getTxtNombre().setText("");
                productoModificarView.getTxtPrecio().setText("");
                productoModificarView.getBtnModificar().setEnabled(false);
                productoModificarView.getBtnBuscar().setEnabled(true);
                productoModificarView.getTxtCodigo().setEditable(true);

            } catch (NumberFormatException ex) {
                productoModificarView.mostrarMensaje(mensaje.get("msg.car.codInvalido"));
            }
        }
    }

    private void buscarProductoParaCarrito() {
        try {
            int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
            Producto producto = productoDAO.buscarPorCodigo(codigo);

            if (producto == null) {
                carritoAnadirView.mostrarMensaje(mensaje.get("msg.noEncontrado"));
                carritoAnadirView.getTxtNombre().setText("");
                carritoAnadirView.getTxtPrecio().setText("");
            } else {
                carritoAnadirView.getTxtNombre().setText(producto.getNombre());
                carritoAnadirView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
            }
        } catch (NumberFormatException ex) {
            carritoAnadirView.mostrarMensaje(mensaje.get("msg.car.codInvalido"));
        }
    }

}