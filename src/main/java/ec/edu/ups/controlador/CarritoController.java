package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.vista.Carrito.*;

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

    private Carrito carritoSeleccionadoParaModificar;

    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO, CarritoAnadirView carritoAñadirView, CarritoListarView carritoListarView, CarritoModificarView carritoModificarView, CarritoEliminarView carritoEliminarView, CarritoListarMisView carritoListarMisView, Usuario usuarioLogueado) {
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAñadirView;
        this.carritoListarView = carritoListarView;
        this.carritoModificarView = carritoModificarView;
        this.carritoEliminarView = carritoEliminarView;
        this.carritoListarMisView = carritoListarMisView;
        this.usuarioLogueado = usuarioLogueado;

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
        carritoListarView.getListarButton().addActionListener(e -> listarTodosLosCarritos());
        carritoListarView.getBtBuscar().addActionListener(e -> buscarYMostrarDetalles());

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
        carritoListarMisView.getBtBuscar().addActionListener(e->buscarYMostrarDetallesMis());
    }

    private void listarMisCarritos() {
        List<Carrito> carritos = carritoDAO.buscarPorUsuario(usuarioLogueado);
        if (carritos.isEmpty()) {
            carritoListarMisView.mostrarMensaje("No hay carritos registrados.");
        }
        carritoListarMisView.mostrarCarritos(carritos);
    }

    private void eliminarCarrito() {
        int respuesta = JOptionPane.showConfirmDialog(carritoEliminarView, "¿Está seguro de que desea eliminar el carrito " + carritoSeleccionadoParaModificar.getCodigo() + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if(respuesta==JOptionPane.YES_OPTION){
            carritoDAO.eliminar(carritoSeleccionadoParaModificar.getCodigo());
            carritoEliminarView.mostrarMensaje("Carrito eliminado con éxito.");
            limpiarVistaEliminar();
        }
    }

    private void buscarCarritoParaEliminar() {
        String codigoStr = carritoEliminarView.getTxtCodigo().getText();
        if (codigoStr.isEmpty()) {
            carritoEliminarView.mostrarMensaje("Por favor, ingrese un código de carrito para buscar.");
            return;
        }

        int codigo = Integer.parseInt(codigoStr);
        this.carritoSeleccionadoParaModificar = carritoDAO.buscarPorCodigo(codigo);

        if (carritoSeleccionadoParaModificar != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            carritoEliminarView.getTxtUsuario().setText(carritoSeleccionadoParaModificar.getUsuario().getUsername());
            carritoEliminarView.getTxtFecha().setText(sdf.format(carritoSeleccionadoParaModificar.getFecha().getTime()));
            carritoEliminarView.mostrarItemsCarrito(carritoSeleccionadoParaModificar);

            carritoEliminarView.getTxtUsuario().setEditable(false);
            carritoEliminarView.getTxtCodigo().setEditable(false);
            carritoEliminarView.getBtnBuscar().setEnabled(false);
            carritoEliminarView.getBtnEliminar().setEnabled(true);

        } else {
            carritoModificarView.mostrarMensaje("No se encontró ningún carrito con el código: " + codigo);
            limpiarVistaEliminar();
        }
    }


    private void buscarCarritoParaModificar() {
        String codigoStr = carritoModificarView.getTxtCodigo().getText().trim();
        if (codigoStr.isEmpty()) {
            carritoModificarView.mostrarMensaje("Por favor, ingrese un código de carrito para buscar.");
            return;
        }

        int codigo = Integer.parseInt(codigoStr);
        this.carritoSeleccionadoParaModificar = carritoDAO.buscarPorCodigo(codigo);

        if (carritoSeleccionadoParaModificar != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            carritoModificarView.getTxtUsuario().setText(carritoSeleccionadoParaModificar.getUsuario().getUsername());
            carritoModificarView.getTxtFecha().setText(sdf.format(carritoSeleccionadoParaModificar.getFecha().getTime()));
            carritoModificarView.mostrarItemsCarrito(carritoSeleccionadoParaModificar);

            carritoModificarView.getTxtUsuario().setEditable(false);
            carritoModificarView.getTxtCodigo().setEditable(false);
            carritoModificarView.getBtnBuscar().setEnabled(false);
            carritoModificarView.getBtnModificar().setEnabled(true);

        } else {
            carritoModificarView.mostrarMensaje("No se encontró ningún carrito con el código: " + codigo);
            limpiarVistaModificar();
        }

    }

    private void guardarModificacionCarrito() throws ParseException {
        if (carritoSeleccionadoParaModificar == null) {
            carritoModificarView.mostrarMensaje("No hay ningún carrito seleccionado para modificar.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(
                carritoModificarView,
                "¿Está seguro de que desea guardar los cambios en el carrito " + carritoSeleccionadoParaModificar.getCodigo() + "?",
                "Confirmar Modificación",
                JOptionPane.YES_NO_OPTION
        );

        if (respuesta == JOptionPane.YES_OPTION) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            GregorianCalendar nuevaFecha = new GregorianCalendar();
            nuevaFecha.setTime(sdf.parse(carritoModificarView.getTxtFecha().getText()));
            carritoSeleccionadoParaModificar.setFecha(nuevaFecha);



            carritoDAO.actualizar(carritoSeleccionadoParaModificar);
            carritoModificarView.mostrarMensaje("Carrito modificado con éxito.");

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
        String codigoStr = carritoListarMisView.getTxtCodigo().getText();
        if (codigoStr.isEmpty()) {
            carritoListarMisView.mostrarMensaje("Por favor, ingrese un código de carrito para buscar.");
            return;
        }

        int codigo = Integer.parseInt(codigoStr);
        Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

        if (carritoEncontrado != null) {
            carritoListarMisView.mostrarDetalles(carritoEncontrado);
        } else {
            carritoListarMisView.mostrarMensaje("No se encontró ningún carrito con el código: " + codigo);
        }

    }

    private void buscarYMostrarDetalles() {
        String codigoStr = carritoListarView.getTxtCodigo().getText();
        if (codigoStr.isEmpty()) {
            carritoListarView.mostrarMensaje("Por favor, ingrese un código de carrito para buscar.");
            return;
        }

        int codigo = Integer.parseInt(codigoStr);
        Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

        if (carritoEncontrado != null) {
            carritoListarView.mostrarDetallesCarrito(carritoEncontrado);
        } else {
            carritoListarView.mostrarMensaje("No se encontró ningún carrito con el código: " + codigo);
        }

    }

    private void listarTodosLosCarritos() {
        List<Carrito> carritos = carritoDAO.listarTodos();
        if (carritos.isEmpty()) {
            carritoListarView.mostrarMensaje("No hay carritos registrados.");
        }
        carritoListarView.mostrarCarritos(carritos);
    }

    private void guardarCarrito() {
        if (carrito.obtenerItems().isEmpty()) {
            carritoAnadirView.mostrarMensaje("El carrito está vacío. Añada productos antes de guardar.");
            return;
        }
        carritoDAO.crear(carrito);
        carritoAnadirView.mostrarMensaje("Carrito guardado correctamente para el usuario: " + usuarioLogueado.getUsername());
        System.out.println("Carritos guardados: " + carritoDAO.listarTodos());

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
                carritoAnadirView.mostrarMensaje("Producto no encontrado.");
                return;
            }
            int cantidad = carritoAnadirView.getCbxCantidad().getSelectedIndex() + 1;
            if (cantidad <= 0) {
                carritoAnadirView.mostrarMensaje("Seleccione una cantidad válida.");
                return;
            }

            carrito.agregarProducto(producto, cantidad);
            cargarProductosEnTabla();
            mostrarTotales();
        } catch (NumberFormatException ex) {
            carritoAnadirView.mostrarMensaje("El código del producto debe ser un número válido.");
        }
    }

    private void limpiarCarrito() {
        int respuesta = JOptionPane.showConfirmDialog(carritoAnadirView, "¿Desea vaciar el carrito actual?", "Confirmar", JOptionPane.YES_NO_OPTION);
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
        carritoAnadirView.getTxtSubtotal().setText(String.format("%.2f", carrito.calcularSubtotal()));
        carritoAnadirView.getTxtIVA().setText(String.format("%.2f", carrito.calcularIVA()));
        carritoAnadirView.getTxtTotal().setText(String.format("%.2f", carrito.calcularTotal()));
    }
}
