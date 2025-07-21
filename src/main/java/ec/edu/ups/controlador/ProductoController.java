package ec.edu.ups.controlador;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.Producto.ProductoAnadirView;
import ec.edu.ups.vista.Producto.ProductoEliminarView;
import ec.edu.ups.vista.Producto.ProductoListaView;
import ec.edu.ups.vista.Producto.ProductoModificarView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;
/**
 * Controlador de productos que gestiona la interacción entre las vistas de productos
 * y la lógica del modelo. Permite añadir, modificar, eliminar y listar productos.
 * También permite la actualización del idioma de las vistas.
 *
 */
public class ProductoController {

    private ProductoAnadirView productoAnadirView;
    private ProductoListaView productoListaView;
    private ProductoEliminarView productoEliminarView;
    private ProductoModificarView productoModificarView;

    private final ProductoDAO productoDAO;
    private final MensajeInternacionalizacionHandler Internacionalizar;
    private Locale locale;
    /**
     * Constructor del controlador que inicializa las vistas y configura sus eventos.
     *
     * @param productoAnadirView Vista para añadir productos.
     * @param productoListaView Vista para listar productos.
     * @param productoEliminarView Vista para eliminar productos.
     * @param productoModificarView Vista para modificar productos.
     * @param productoDAO DAO para el acceso a datos de productos.
     * @param internacionalizar Manejador de internacionalización de mensajes.
     */
    public ProductoController(ProductoAnadirView productoAnadirView, ProductoListaView productoListaView,
                              ProductoEliminarView productoEliminarView, ProductoModificarView productoModificarView,
                              ProductoDAO productoDAO, MensajeInternacionalizacionHandler internacionalizar) {
        this.productoAnadirView = productoAnadirView;
        this.productoListaView = productoListaView;
        this.productoEliminarView = productoEliminarView;
        this.productoModificarView = productoModificarView; // <--- Aquí la asignas
        this.productoDAO = productoDAO;
        this.Internacionalizar = internacionalizar;

        configurarAnadirPro();
        configurarModificarPro();  // Ahora productoModificarView no es null
        configurarEliminarPro();
        configurarListarPro();
        actualizarIdioma();
    }

    /**
     * Configura el evento para añadir un nuevo producto.
     */
    private void configurarAnadirPro() {
        productoAnadirView.getBtnAceptar().addActionListener(e -> guardarProducto());
    }
    /**
     * Configura los eventos de búsqueda y modificación de productos.
     */
    private void configurarModificarPro() {
        productoModificarView.getBtnBuscar().addActionListener(e -> {
            String codigoTexto = productoModificarView.getTxtCodigo().getText().trim();

            if (codigoTexto.isEmpty()) {
                productoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.producto.codigo.vacio"));
                return;
            }

            try {
                int codigo = Integer.parseInt(codigoTexto);
                Producto producto = productoDAO.buscarPorCodigo(codigo);

                if (producto != null) {
                    productoModificarView.getTxtNombre().setText(producto.getNombre());
                    productoModificarView.getTxtPrecio().setText(String.valueOf(producto.getPrecio()));
                    productoModificarView.getTxtNombre().setEnabled(true);
                    productoModificarView.getTxtPrecio().setEnabled(true);
                } else {
                    productoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.producto.noencontrado"));
                }

            } catch (NumberFormatException ex) {
                productoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.producto.codigo.invalido"));
            }
        });
        productoModificarView.getBtnModificar().addActionListener(e -> {
            String codigoTexto = productoModificarView.getTxtCodigo().getText().trim();

            if (codigoTexto.isEmpty()) {
                productoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.producto.codigo.vacio"));
                return;
            }

            try {
                int codigo = Integer.parseInt(codigoTexto);
                actualizar(codigo);
                productoModificarView.vaciarCampo();
                productoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.producto.modificado"));
            } catch (NumberFormatException ex) {
                productoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.producto.codigo.invalido"));
            }
        });
    }
    /**
     * Configura los eventos de búsqueda y eliminación de productos.
     */
    private void configurarEliminarPro() {
        productoEliminarView.getBtnBuscar().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String codigoTexto = productoEliminarView.getTxtCodigo().getText().trim();

            if (codigoTexto.isEmpty()) {
                productoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.codigo.vacio"));
                return;
            }

            try {
                int codigo = Integer.parseInt(codigoTexto);
                Producto productoEncontrado = productoDAO.buscarPorCodigo(codigo);

                if (productoEncontrado != null) {
                    productoEliminarView.getTxtNombre().setText(productoEncontrado.getNombre());
                    productoEliminarView.getTxtPrecio().setText(
                            FormateadorUtils.formatearMoneda(productoEncontrado.getPrecio(), Internacionalizar.getLocale())
                    );
                    productoEliminarView.getTxtNombre().setEnabled(false);
                    productoEliminarView.getTxtPrecio().setEnabled(false);
                } else {
                    productoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.producto.noencontrado"));
                }
            } catch (NumberFormatException ex) {
                productoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.codigo.invalido"));
            }
        }
    });
        productoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigoTexto = productoEliminarView.getTxtCodigo().getText().trim();

                if (codigoTexto.isEmpty()) {
                    productoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.codigo.vacio"));
                    return;
                }

                int respuesta = JOptionPane.showConfirmDialog(
                        productoEliminarView,
                        Internacionalizar.get("mensaje.producto.confirmacion"),
                        Internacionalizar.get("mensaje.confirmacion.titulo"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    try {
                        int codigo = Integer.parseInt(codigoTexto);
                        eliminar(codigo);
                        productoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.producto.eliminado"));
                        productoEliminarView.vaciarCampo();
                    } catch (NumberFormatException ex) {
                        productoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.codigo.invalido"));
                    }
                } else {
                    productoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.cancelar"));
                }
            }
        });
    }
    /**
     * Configura los eventos para buscar productos por nombre o listar todos.
     */
    private void configurarListarPro(){
        productoListaView.getBtnBuscar().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nombreBusqueda = productoListaView.getTxtBuscar().getText().trim();

            if (nombreBusqueda.isEmpty()) {
                productoListaView.mostrarMensaje(Internacionalizar.get("mensaje.nombre.vacio"));
                return;
            }

            List<Producto> resultados = productoDAO.buscarPorNombre(nombreBusqueda);

            if (resultados.isEmpty()) {
                productoListaView.mostrarMensaje(Internacionalizar.get("mensaje.producto.noencontrado"));
            } else {
                productoListaView.cargarDatosListaProducto(resultados);
            }
        }
    });
        productoListaView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Producto> productos = productoDAO.listarTodos();
                if (productos == null || productos.isEmpty()) {
                    productoListaView.mostrarMensaje(Internacionalizar.get("mensaje.producto.noencontrado"));
                } else {
                    productoListaView.cargarDatosListaProducto(productos);
                }
                productoListaView.getTxtBuscar().setText(""); // Limpiar campo de búsqueda
            }
        });
    }
    /**
     * Guarda un nuevo producto en el sistema.
     */
    private void guardarProducto() {
        try {
            String codigoTexto = productoAnadirView.getTxtCodigo().getText().trim();
            String nombre = productoAnadirView.getTxtNombre().getText().trim();
            String precioTexto = productoAnadirView.getTxtPrecio().getText().trim();

            if (codigoTexto.isEmpty() || nombre.isEmpty() || precioTexto.isEmpty()) {
                productoAnadirView.mostrarMensaje(Internacionalizar.get("mensaje.campos.vacios"));
                return;
            }

            int codigo = Integer.parseInt(codigoTexto);
            double precio = Double.parseDouble(precioTexto);

            // Validar si código ya existe
            if (productoDAO.buscarPorCodigo(codigo) != null) {
                productoAnadirView.mostrarMensaje(Internacionalizar.get("mensaje.codigo.existe"));
                return;
            }

            Producto nuevoProducto = new Producto(codigo, nombre, precio);
            productoDAO.crear(nuevoProducto);

            productoAnadirView.mostrarMensaje(Internacionalizar.get("mensaje.producto.creado"));
            productoAnadirView.vaciarCampo();

        } catch (NumberFormatException e) {
            productoAnadirView.mostrarMensaje(Internacionalizar.get("mensaje.datos.invalidos"));
        }
    }

    /**
     * Actualiza un producto existente.
     *
     * @param codigo Código del producto a actualizar.
     */
    private void actualizar(int codigo) {
        Producto producto = productoDAO.buscarPorCodigo(codigo);

        if (producto == null) {
            productoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.producto.noencontrado"));
            return;
        }

        String nombre = productoModificarView.getTxtNombre().getText().trim();
        String precioTexto = productoModificarView.getTxtPrecio().getText().trim();

        if (nombre.isEmpty() || precioTexto.isEmpty()) {
            productoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.campos.vacios"));
            return;
        }

        try {
            double precio = Double.parseDouble(precioTexto);

            producto.setNombre(nombre);
            producto.setPrecio(precio);
            productoDAO.actualizar(producto);
        } catch (NumberFormatException e) {
            productoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.producto.precio.invalido"));
        }
    }
    /**
     * Elimina un producto por su código.
     *
     * @param codigo Código del producto a eliminar.
     */
    private void eliminar(int codigo) {
        productoDAO.eliminar(codigo);
    }
    /**
     * Actualiza el idioma de todas las vistas del controlador.
     */
    private void actualizarIdioma() {
        productoAnadirView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        productoListaView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        productoEliminarView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }
    /**
     * Cambia el idioma del sistema.
     *
     * @param lenguaje Código de lenguaje (por ejemplo, "es").
     * @param pais Código de país (por ejemplo, "EC").
     */
    public void cambiarIdioma(String lenguaje, String pais) {
        Internacionalizar.setLenguaje(lenguaje, pais);
        this.locale = Internacionalizar.getLocale();

        actualizarIdioma();
    }

}