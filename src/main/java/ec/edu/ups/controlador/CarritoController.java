package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.Carrito.*;
import ec.edu.ups.vista.Carrito.CarritoListarMisView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class CarritoController {

    private Carrito carrito;
    private final CarritoDAO carritoDAO;
    private final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private final CarritoListarView carritoListarView;
    private final CarritoModificarView carritoModificarView;
    private final CarritoEliminarView carritoEliminarView;
    private final CarritoListarMisView carritoListarMisView;
    private final Usuario usuarioLogueado;
    private final MensajeInternacionalizacionHandler mensaje;
    private final Locale locale;
    private Carrito carritoSeleccionadoParaModificar;

    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO, CarritoAnadirView carritoAñadirView,
                             CarritoListarView carritoListarView, CarritoModificarView carritoModificarView, CarritoEliminarView carritoEliminarView,
                             CarritoListarMisView carritoListarMisView, Usuario usuarioLogueado, MensajeInternacionalizacionHandler mensaje) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAñadirView;
        this.carritoListarView = carritoListarView;
        this.carritoModificarView = carritoModificarView;
        this.carritoEliminarView = carritoEliminarView;
        this.carritoListarMisView = carritoListarMisView;
        this.usuarioLogueado = usuarioLogueado;
        this.mensaje = mensaje;

        this.locale = new Locale(mensaje.get("locale.lang"), mensaje.get("locale.country"));

        iniciarNuevoCarrito();
        configurarEventosEnVistas();
    }

    private void iniciarNuevoCarrito() {
        this.carrito = new Carrito();
        this.carrito.setUsuario(this.usuarioLogueado);
    }

    public void configurarEventosEnVistas() {
        carritoAnadirView.getBtnAñadir().addActionListener(e -> añadirProducto());
        carritoAnadirView.getBtnGuardar().addActionListener(e -> guardarCarrito());
        carritoAnadirView.getBtnLimpiar().addActionListener(e -> limpiarCarrito());

        carritoListarView.getBtnListar().addActionListener(e -> listarTodosLosCarritos());
        carritoListarView.getBtBuscar().addActionListener(e ->  buscarYMostrarDetalles());

        carritoModificarView.getBtnBuscar().addActionListener(e -> buscarCarritoParaModificar());
        carritoModificarView.getBtnModificar().addActionListener(e -> {
            try {
                guardarModificacionCarrito();
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        });
        carritoEliminarView.getBtnBuscar().addActionListener(e -> buscarCarritoParaEliminar());
        carritoEliminarView.getBtnEliminar().addActionListener(e -> eliminarCarrito());

        carritoListarMisView.getBtnListar().addActionListener(e -> listarMisCarritos());
        carritoListarMisView.getBtnBuscar().addActionListener(e->buscarYMostrarDetallesMis());
    }

    private void listarMisCarritos() {
        List<Carrito> carritos = carritoDAO.buscarPorUsuario(usuarioLogueado);
        if (carritos.isEmpty()) {
            carritoListarMisView.mostrarMensaje(mensaje.get("msg.car.noHay"));
        }
        carritoListarMisView.mostrarCarritos(carritos);
    }

    private void eliminarCarrito() {
        int respuesta = JOptionPane.showConfirmDialog(carritoEliminarView, mensaje.get("confirm.car.elim")+ carritoSeleccionadoParaModificar.getCodigo() + "?", mensaje.get("confirm.app.titulo"), JOptionPane.YES_NO_OPTION);
        if(respuesta==JOptionPane.YES_OPTION){
            carritoDAO.eliminar(carritoSeleccionadoParaModificar.getCodigo());
            carritoEliminarView.mostrarMensaje(mensaje.get("msg.car.eliminado"));
            limpiarVistaEliminar();
        }
    }

    private void buscarCarritoParaEliminar() {
        String codigoStr = carritoEliminarView.getTxtCodigo().getText();
        if (codigoStr.isEmpty()) {
            carritoEliminarView.mostrarMensaje(mensaje.get("msg.car.codRequerido"));
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            this.carritoSeleccionadoParaModificar = carritoDAO.buscarPorCodigo(codigo);

            if (carritoSeleccionadoParaModificar != null) {
                carritoEliminarView.getTxtUsuario().setText(carritoSeleccionadoParaModificar.getUsuario().getUsername());
                carritoEliminarView.getTxtFecha().setText(FormateadorUtils.formatearFecha(carritoSeleccionadoParaModificar.getFecha().getTime(), locale));
                carritoEliminarView.mostrarItemsCarrito(carritoSeleccionadoParaModificar);

                carritoEliminarView.getTxtUsuario().setEditable(false);
                carritoEliminarView.getBtnBuscar().setEnabled(false);
                carritoEliminarView.getBtnEliminar().setEnabled(true);

            } else {
                carritoEliminarView.mostrarMensaje(mensaje.get("msg.car.noEncontrado") + " " + codigo);
                limpiarVistaEliminar();
            }
        } catch (NumberFormatException e) {
            carritoEliminarView.mostrarMensaje(mensaje.get("msg.car.codInvalido"));
        }
    }

    private void buscarCarritoParaModificar() {
        String codigoStr = carritoModificarView.getTxtCodigo().getText().trim();
        if (codigoStr.isEmpty()) {
            carritoModificarView.mostrarMensaje(mensaje.get("msg.car.codRequerido"));
            return;
        }
        try {
            int codigo = Integer.parseInt(codigoStr);
            this.carritoSeleccionadoParaModificar = carritoDAO.buscarPorCodigo(codigo);

            if (carritoSeleccionadoParaModificar != null) {
                carritoModificarView.getTxtUsuario().setText(carritoSeleccionadoParaModificar.getUsuario().getUsername());
                carritoModificarView.getTxtFecha().setText(FormateadorUtils.formatearFecha(carritoSeleccionadoParaModificar.getFecha().getTime(), locale));
                carritoModificarView.mostrarItemsCarrito(carritoSeleccionadoParaModificar);

                carritoModificarView.getTxtCodigo().setEditable(false);
                carritoModificarView.getBtnBuscar().setEnabled(false);
                carritoModificarView.getBtnModificar().setEnabled(true);
            } else {
                carritoModificarView.mostrarMensaje(mensaje.get("msg.car.noEncontrado") + " " + codigo);
                limpiarVistaModificar();
            }
        } catch (NumberFormatException e) {
            carritoModificarView.mostrarMensaje(mensaje.get("msg.car.codInvalido"));
        }
    }

    private void guardarModificacionCarrito() throws ParseException {
        if (carritoSeleccionadoParaModificar == null) {
            carritoModificarView.mostrarMensaje(mensaje.get("msg.car.noSeleccionado"));
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(
                carritoModificarView, mensaje.get("confirm.car.mod") + " " + carritoSeleccionadoParaModificar.getCodigo() + "?",
                mensaje.get("confirm.app.titulo"),
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {
            carritoDAO.actualizar(carritoSeleccionadoParaModificar);
            carritoModificarView.mostrarMensaje(mensaje.get("msg.car.guardado"));
            limpiarVistaModificar();
        }
    }

    private void limpiarVistaModificar() {
        carritoModificarView.getTxtCodigo().setText("");
        carritoModificarView.getTxtUsuario().setText("");
        carritoModificarView.getTxtFecha().setText("");
        carritoModificarView.mostrarItemsCarrito(null); // Limpia la tabla
        carritoModificarView.getTxtCodigo().setEditable(true);
        carritoModificarView.getBtnBuscar().setEnabled(true);
        carritoModificarView.getBtnModificar().setEnabled(false);
        this.carritoSeleccionadoParaModificar = null;
    }

    private void limpiarVistaEliminar() {
        carritoEliminarView.getTxtCodigo().setText("");
        carritoEliminarView.getTxtUsuario().setText("");
        carritoEliminarView.getTxtFecha().setText("");
        carritoEliminarView.mostrarItemsCarrito(null);
        carritoEliminarView.getTxtCodigo().setEditable(true);
        carritoEliminarView.getBtnBuscar().setEnabled(true);
        carritoEliminarView.getBtnEliminar().setEnabled(false);
        this.carritoSeleccionadoParaModificar = null;
    }

    private void buscarYMostrarDetallesMis() {
        try {
            int codigo = Integer.parseInt(carritoListarMisView.getTxtCodigo().getText());
            Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

            if (carritoEncontrado != null && carritoEncontrado.getUsuario().equals(usuarioLogueado)) {
                carritoListarMisView.mostrarDetalles(carritoEncontrado);
            } else {
                carritoListarMisView.mostrarMensaje(mensaje.get("msg.car.noEncontrado") + " " + codigo);
            }
        } catch (NumberFormatException ex) {
            carritoListarMisView.mostrarMensaje(mensaje.get("msg.car.codInvalido"));
        }
    }


    private void buscarYMostrarDetalles() {
        String codigoStr = carritoListarView.getTxtCodigo().getText();
        if (codigoStr.isEmpty()) {
            carritoListarView.mostrarMensaje(mensaje.get("msg.car.codRequerido"));
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

            if (carritoEncontrado != null) {
                carritoListarView.mostrarDetallesCarrito(carritoEncontrado);
            } else {
                carritoListarView.mostrarMensaje(mensaje.get("msg.car.noEncontrado") + " " + codigo);
            }
        } catch (NumberFormatException e) {
            carritoListarView.mostrarMensaje(mensaje.get("msg.car.codInvalido"));
        }
    }


    private void listarTodosLosCarritos() {
        List<Carrito> carritos = carritoDAO.listarTodos();
        if (carritos.isEmpty()) {
            carritoListarView.mostrarMensaje(mensaje.get("msg.car.noHay"));
        }
        carritoListarView.mostrarCarritos(carritos);
    }

    private void guardarCarrito() {
        if (carrito.obtenerItems().isEmpty()) {
            carritoAnadirView.mostrarMensaje(mensaje.get("msg.car.vacio"));
            return;
        }
        carritoDAO.crear(carrito);
        carritoAnadirView.mostrarMensaje(mensaje.get("msg.car.guardado") + " " + usuarioLogueado.getUsername());

        iniciarNuevoCarrito();
        cargarProductosEnTabla();
        mostrarTotales();
        carritoAnadirView.getTxtCodigo().setText("");
        carritoAnadirView.getTxtNombre().setText("");
        carritoAnadirView.getTxtPrecio().setText("");
    }

    private void añadirProducto() {
        try {
            Producto producto = productoDAO.buscarPorCodigo(Integer.parseInt(carritoAnadirView.getTxtCodigo().getText()));
            if (producto == null) {
                carritoAnadirView.mostrarMensaje(mensaje.get("msg.noEncontrado"));
                return;
            }
            int cantidad = carritoAnadirView.getCbxCantidad().getSelectedIndex() + 1;
            if (cantidad <= 0) {
                carritoAnadirView.mostrarMensaje(mensaje.get("msg.car.cantInvalida"));
                return;
            }

            carrito.agregarProducto(producto, cantidad);
            cargarProductosEnTabla();
            mostrarTotales();
        } catch (NumberFormatException ex) {
            carritoAnadirView.mostrarMensaje(mensaje.get("msg.car.codInvalido"));
        }
    }

    private void limpiarCarrito() {
        int respuesta = JOptionPane.showConfirmDialog(carritoAnadirView, mensaje.get("confirm.car.vaciar"), mensaje.get("confirm.app.titulo"), JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            iniciarNuevoCarrito();
            cargarProductosEnTabla();
            mostrarTotales();
            carritoAnadirView.getTxtCodigo().setText("");
            carritoAnadirView.getTxtNombre().setText("");
            carritoAnadirView.getTxtPrecio().setText("");
        }
    }

    private void cargarProductosEnTabla() {
        List<ItemCarrito> items = carrito.obtenerItems();
        DefaultTableModel modelo = (DefaultTableModel) carritoAnadirView.getTblItems().getModel();
        modelo.setRowCount(0);
        for (ItemCarrito item : items) {
            modelo.addRow(new Object[]{
                    item.getProducto().getCodigo(),
                    item.getProducto().getNombre(),
                    item.getProducto().getPrecio(),
                    item.getCantidad(),
                    item.getSubtotal()
            });
        }
    }

    private void mostrarTotales() {
        carritoAnadirView.getTxtSubtotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), locale));
        carritoAnadirView.getTxtIVA().setText(FormateadorUtils.formatearMoneda(carrito.calcularIVA(), locale));
        carritoAnadirView.getTxtTotal().setText(FormateadorUtils.formatearMoneda(carrito.calcularTotal(), locale));
    }
}
