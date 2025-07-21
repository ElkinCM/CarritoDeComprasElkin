package ec.edu.ups.controlador;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.*;
import ec.edu.ups.util.FormateadorUtils;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.Carrito.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;
/**
 * Controlador para gestionar las operaciones del carrito de compras.
 * Permite añadir, modificar, eliminar, buscar y listar carritos e ítems,
 * así como actualizar el idioma de la interfaz de usuario.
 *
 * Esta clase implementa el patrón MVC actuando como el controlador entre el modelo y las vistas.
 *
 * @author Elkin Chamba
 * @version 1.0
 */
public class CarritoController {

    private Carrito carrito;
    private Usuario usuario;
    private Locale locale;

    public final CarritoDAO carritoDAO;
    public final ProductoDAO productoDAO;
    private final CarritoAnadirView carritoAnadirView;
    private final CarritoModificarView carritoModificarView;
    private final CarritoEliminarView carritoEliminarView;
    private final CarritoListarView carritoListarView;
    private final CarritoListarMisItemsView carritoListarMisItemsView;

    private MensajeInternacionalizacionHandler Internacionalizar;
    /**
     * Constructor del controlador de carrito.
     *
     * @param carritoDAO DAO para operaciones de carrito.
     * @param productoDAO DAO para operaciones de producto.
     * @param carritoAnadirView Vista para añadir productos al carrito.
     * @param carritoModificarView Vista para modificar carritos.
     * @param carritoEliminarView Vista para eliminar carritos.
     * @param carritoListarView Vista para listar carritos.
     * @param carritoListarMisItemsView Vista para mostrar detalles del carrito.
     * @param internacionalizar Handler para mensajes internacionalizados.
     */
    public CarritoController(CarritoDAO carritoDAO, ProductoDAO productoDAO,
                             CarritoAnadirView carritoAnadirView,
                             CarritoModificarView carritoModificarView,
                             CarritoEliminarView carritoEliminarView,
                             CarritoListarView carritoListarView,
                             CarritoListarMisItemsView carritoListarMisItemsView,
                             MensajeInternacionalizacionHandler internacionalizar,
                             Usuario usuario) { // ← nuevo parámetro

        this.Internacionalizar = internacionalizar;
        this.carritoDAO = carritoDAO;
        this.productoDAO = productoDAO;
        this.carritoAnadirView = carritoAnadirView;
        this.carritoModificarView = carritoModificarView;
        this.carritoEliminarView = carritoEliminarView;
        this.carritoListarView = carritoListarView;
        this.carritoListarMisItemsView = carritoListarMisItemsView;

        this.usuario = usuario; // ← guarda el usuario
        this.carrito = new Carrito(usuario); // ← crea el carrito con usuario

        configurarAnadirCa();
        configurarModificarCa();
        configurarEliminarCa();
        configurarListarCa();
        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    /**
     * Configura los listeners para la vista de añadir carrito.
     */
    private void configurarAnadirCa() {
        carritoAnadirView.getBtnAñadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(carritoAnadirView.getTxtCodigo().getText());
                anadirEnCarrito(codigo);
            }
        });
        carritoAnadirView.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carritoDAO.crear(carrito);
                carritoAnadirView.mostrarMensaje(Internacionalizar.get("carrito.guardado.exito"));
                carritoAnadirView.vaciarCampo();
                carrito = new Carrito(usuario);
                carritoAnadirView.getBtnGuardar().setEnabled(false);
            }
        });
        carritoAnadirView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoCodigo = carritoAnadirView.getTxtCodigo().getText().trim();

                if (textoCodigo.isEmpty()) {
                    String mensaje = Internacionalizar.get("carrito.codigo.vacio"); // Asegúrate de tener esta clave en tus properties
                    carritoAnadirView.mostrarMensaje(mensaje);
                    return;
                }

                try {
                    int codigo = Integer.parseInt(textoCodigo);
                    buscarEnCarrito(codigo);
                } catch (NumberFormatException ex) {
                    String mensaje = Internacionalizar.get("carrito.codigo.no.numero"); // Asegúrate de tener esta clave también
                    carritoAnadirView.mostrarMensaje(mensaje);
                }
            }
        });

        carritoAnadirView.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carritoAnadirView.vaciarCampo();
                carritoAnadirView.getTxtCodigo().setEnabled(false);
                carrito.limpiarCarrito();
                carritoAnadirView.mostrarMensaje(Internacionalizar.get("carrito.cancelar.exito"));
                carritoAnadirView.getBtnGuardar().setEnabled(false);
            }
        });
    }
    /**
     * Configura los listeners para modificar un carrito existente.
     */
    private void configurarModificarCa() {
        carritoModificarView.getBtnAnadir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigoCarrito = Integer.parseInt(carritoModificarView.getTxtCodigo().getText());
                    Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigoCarrito);

                    if (carritoEncontrado == null) {
                        carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.noencontrado"));
                        return;
                    }

                    String mensajeProducto = Internacionalizar.get("mensaje.carrito.ingresar.producto");
                    String inputCodigoProducto = JOptionPane.showInputDialog(carritoModificarView, mensajeProducto);
                    if (inputCodigoProducto == null) return;

                    int codigoNuevoProducto = Integer.parseInt(inputCodigoProducto);

                    boolean productoYaExiste = carritoEncontrado.obtenerItems().stream()
                            .anyMatch(item -> item.getProducto().getCodigo() == codigoNuevoProducto);

                    if (productoYaExiste) {
                        carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.producto.ya.registrado.editar"));
                        return;
                    }

                    String mensajeCantidad = Internacionalizar.get("mensaje.carrito.ingresar.cantidad");
                    String inputCantidad = JOptionPane.showInputDialog(carritoModificarView, mensajeCantidad);
                    if (inputCantidad == null) return;

                    int cantidad = Integer.parseInt(inputCantidad);

                    Producto nuevoProducto = productoDAO.buscarPorCodigo(codigoNuevoProducto);
                    if (nuevoProducto == null) {
                        carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.producto.noencontrado"));
                        return;
                    }

                    carritoEncontrado.agregarProducto(nuevoProducto, cantidad);
                    carritoDAO.actualizar(carritoEncontrado);

                    carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.modificado"));
                    carritoModificarView.cargarDatosCarrito(carritoEncontrado);

                    carritoModificarView.getTxtSubtotal().setText(
                            FormateadorUtils.formatearMoneda(carritoEncontrado.calcularSubtotal(), Internacionalizar.getLocale())
                    );
                    carritoModificarView.getTxtIVA().setText(
                            FormateadorUtils.formatearMoneda(carritoEncontrado.calcularIVA(), Internacionalizar.getLocale())
                    );
                    carritoModificarView.getTxtTotal().setText(
                            FormateadorUtils.formatearMoneda(carritoEncontrado.calcularTotal(), Internacionalizar.getLocale())
                    );

                } catch (NumberFormatException ex) {
                    carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.error.numero.invalido"));
                }
            }
        });
        carritoModificarView.getBtnModificar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int filaSeleccionada = carritoModificarView.getTblItems().getSelectedRow();
                    if (filaSeleccionada == -1) {
                        carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.seleccionar.editar"));
                        return;
                    }

                    int codigoCarrito = Integer.parseInt(carritoModificarView.getTxtCodigo().getText());
                    Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigoCarrito);

                    if (carritoEncontrado == null) {
                        carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.noencontrado"));
                        return;
                    }

                    int codigoProductoSeleccionado = (Integer) carritoModificarView.getTblItems().getValueAt(filaSeleccionada, 0);

                    String inputCantidad = JOptionPane.showInputDialog(
                            carritoModificarView,
                            Internacionalizar.get("mensaje.carrito.ingresar.cantidad")
                    );
                    if (inputCantidad == null) return;

                    int nuevaCantidad = Integer.parseInt(inputCantidad);

                    boolean productoModificado = false;
                    for (ItemCarrito item : carritoEncontrado.obtenerItems()) {
                        if (item.getProducto().getCodigo() == codigoProductoSeleccionado) {
                            item.setCantidad(nuevaCantidad);
                            productoModificado = true;
                            break;
                        }
                    }

                    if (!productoModificado) {
                        carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.producto.noencontrado"));
                        return;
                    }

                    carritoDAO.actualizar(carritoEncontrado);

                    carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.modificado"));
                    carritoModificarView.cargarDatosCarrito(carritoEncontrado);

                    carritoModificarView.getTxtSubtotal().setText(
                            FormateadorUtils.formatearMoneda(carritoEncontrado.calcularSubtotal(), Internacionalizar.getLocale())
                    );
                    carritoModificarView.getTxtIVA().setText(
                            FormateadorUtils.formatearMoneda(carritoEncontrado.calcularIVA(), Internacionalizar.getLocale())
                    );
                    carritoModificarView.getTxtTotal().setText(
                            FormateadorUtils.formatearMoneda(carritoEncontrado.calcularTotal(), Internacionalizar.getLocale())
                    );

                } catch (NumberFormatException ex) {
                    carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.error.numero.invalido"));
                } catch (Exception ex) {
                    carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.error.general") + ": " + ex.getMessage());
                }
            }
        });
        carritoModificarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String inputCodigo = carritoModificarView.getTxtCodigo().getText();

                    if (inputCodigo.isEmpty()) {
                        carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.buscar.codigo"));
                        return;
                    }

                    int codigoCarrito = Integer.parseInt(inputCodigo);
                    Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigoCarrito);

                    if (carritoEncontrado == null) {
                        carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.noencontrado"));
                        return;
                    }

                    carritoModificarView.getTxtFecha().setText(
                            FormateadorUtils.formatearFecha(carritoEncontrado.getFechaCrear(), Internacionalizar.getLocale())
                    );
                    carritoModificarView.getTxtUsuario().setText(carritoEncontrado.getUsuario().getUsername());

                    if (carritoEncontrado.obtenerItems().isEmpty()) {
                        carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.vacio"));
                    } else {
                        carritoModificarView.cargarDatosCarrito(carritoEncontrado);
                    }

                    carritoModificarView.getTxtSubtotal().setText(
                            FormateadorUtils.formatearMoneda(carritoEncontrado.calcularSubtotal(), Internacionalizar.getLocale())
                    );
                    carritoModificarView.getTxtIVA().setText(
                            FormateadorUtils.formatearMoneda(carritoEncontrado.calcularIVA(), Internacionalizar.getLocale())
                    );
                    carritoModificarView.getTxtTotal().setText(
                            FormateadorUtils.formatearMoneda(carritoEncontrado.calcularTotal(), Internacionalizar.getLocale())
                    );

                } catch (NumberFormatException ex) {
                    carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.error.numero.invalido"));
                } catch (Exception ex) {
                    carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.error.general") + ": " + ex.getMessage());
                }
            }
        });
        carritoModificarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int filaSeleccionada = carritoModificarView.getTblItems().getSelectedRow();
                    if (filaSeleccionada == -1) {
                        carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.seleccionar.eliminar"));
                        return;
                    }

                    int confirmacion = JOptionPane.showConfirmDialog(
                            carritoModificarView,
                            Internacionalizar.get("mensaje.carrito.confirmacion.producto"),
                            Internacionalizar.get("mensaje.confirmacion.titulo"),
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (confirmacion != JOptionPane.YES_OPTION) {
                        carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.cancelar"));
                        return;
                    }

                    int codigoCarrito = Integer.parseInt(carritoModificarView.getTxtCodigo().getText());
                    Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigoCarrito);

                    if (carritoEncontrado == null) {
                        carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.noencontrado"));
                        return;
                    }

                    int codigoProductoAEliminar = (Integer) carritoModificarView.getTblItems().getValueAt(filaSeleccionada, 0);

                    carritoEncontrado.eliminarProducto(codigoProductoAEliminar);
                    carritoDAO.actualizar(carritoEncontrado);

                    carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.producto.eliminado"));
                    carritoModificarView.cargarDatosCarrito(carritoEncontrado);

                    carritoModificarView.getTxtSubtotal().setText(
                            FormateadorUtils.formatearMoneda(carritoEncontrado.calcularSubtotal(), Internacionalizar.getLocale())
                    );
                    carritoModificarView.getTxtIVA().setText(
                            FormateadorUtils.formatearMoneda(carritoEncontrado.calcularIVA(), Internacionalizar.getLocale())
                    );
                    carritoModificarView.getTxtTotal().setText(
                            FormateadorUtils.formatearMoneda(carritoEncontrado.calcularTotal(), Internacionalizar.getLocale())
                    );

                } catch (NumberFormatException ex) {
                    carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.error.numero.invalido"));
                } catch (Exception ex) {
                    carritoModificarView.mostrarMensaje(Internacionalizar.get("mensaje.error.general") + ": " + ex.getMessage());
                }
            }
        });
    }
    /**
     * Configura los listeners para eliminar carritos.
     */
    private void configurarEliminarCa() {
        carritoEliminarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String inputCodigo = carritoEliminarView.getTxtCodigo().getText().trim();

                    if (inputCodigo.isEmpty()) {
                        carritoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.buscar.codigo"));
                        return;
                    }

                    int codigoCarrito = Integer.parseInt(inputCodigo);

                    carritoEliminarView.VaciarTabla();
                    carritoEliminarView.vaciarCampo();

                    Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigoCarrito);

                    if (carritoEncontrado == null) {
                        carritoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.noencontrado"));
                        carritoEliminarView.getBtnEliminar().setEnabled(false);
                        return;
                    }

                    carritoEliminarView.getTxtCodigo().setText(String.valueOf(carritoEncontrado.getCodigo()));
                    carritoEliminarView.getTxtFecha().setText(
                            FormateadorUtils.formatearFecha(carritoEncontrado.getFechaCrear(), Internacionalizar.getLocale())
                    );
                    carritoEliminarView.getTxtUsuario().setText(carritoEncontrado.getUsuario().getUsername());

                    carritoEliminarView.cargarDatosCarrito(carritoEncontrado);
                    carritoEliminarView.getBtnEliminar().setEnabled(true);

                } catch (NumberFormatException ex) {
                    carritoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.error.numero.invalido"));
                } catch (Exception ex) {
                    carritoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.error.general") + ": " + ex.getMessage());
                }
            }
        });
        carritoEliminarView.getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String inputCodigo = carritoEliminarView.getTxtCodigo().getText().trim();

                    if (inputCodigo.isEmpty()) {
                        carritoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.buscar.codigo"));
                        return;
                    }

                    int codigoCarrito = Integer.parseInt(inputCodigo);

                    int confirmacion = JOptionPane.showConfirmDialog(
                            carritoEliminarView,
                            Internacionalizar.get("mensaje.carrito.confirmacion"),
                            Internacionalizar.get("mensaje.confirmacion.titulo"),
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (confirmacion != JOptionPane.YES_OPTION) {
                        carritoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.cancelar"));
                        return;
                    }

                    carritoDAO.eliminar(codigoCarrito);
                    carritoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.eliminado"));
                    carritoEliminarView.vaciarCampo();
                    carritoEliminarView.VaciarTabla();
                    carritoEliminarView.getBtnEliminar().setEnabled(false);

                } catch (NumberFormatException ex) {
                    carritoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.error.numero.invalido"));
                } catch (Exception ex) {
                    carritoEliminarView.mostrarMensaje(Internacionalizar.get("mensaje.error.general") + ": " + ex.getMessage());
                }
            }
        });
    }
    /**
     * Configura los listeners para listar carritos y ver detalles.
     */
    private void configurarListarCa() {
        carritoListarView.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String textoCodigo = carritoListarView.getTxtCodigo().getText().trim();

                    if (textoCodigo.isEmpty()) {
                        carritoListarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.buscar.codigo"));
                        return;
                    }

                    int codigo = Integer.parseInt(textoCodigo);
                    Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigo);

                    carritoListarView.vaciarCampo();

                    if (carritoEncontrado != null) {
                        carritoListarView.cargarDatosBuscar(carritoEncontrado);
                    } else {
                        carritoListarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.noencontrado"));
                    }

                } catch (NumberFormatException ex) {
                    carritoListarView.mostrarMensaje(Internacionalizar.get("mensaje.error.numero.invalido"));
                } catch (Exception ex) {
                    carritoListarView.mostrarMensaje(Internacionalizar.get("mensaje.error.general") + ": " + ex.getMessage());
                }
            }
        });
        carritoListarView.getBtnListar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carritoListarView.vaciarCampo();

                if (usuario == null) {
                    carritoListarView.mostrarMensaje(Internacionalizar.get("mensaje.usuario.noautenticado"));
                    return;
                }

                List<Carrito> carritos;
                if (usuario.getRol().equals(Rol.USUARIO)) {
                    carritos = carritoDAO.buscarPorUsuario(usuario);
                } else {
                    carritos = carritoDAO.listarTodos();
                }

                if (carritos == null || carritos.isEmpty()) {
                    carritoListarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.inexistentes"));
                    return;
                }

                carritoListarView.cargarDatosCarritoLista(carritos);
            }
        });

        carritoListarView.getBtnDetallar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int filaSeleccionada = carritoListarView.getTblCarritos().getSelectedRow();

                    if (filaSeleccionada == -1) {
                        carritoListarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.detalles.ver"));
                        return;
                    }

                    String valor = carritoListarView.getTblCarritos().getValueAt(filaSeleccionada, 0).toString();
                    int codigoCarrito = Integer.parseInt(valor);

                    Carrito carritoEncontrado = carritoDAO.buscarPorCodigo(codigoCarrito);

                    if (carritoEncontrado == null) {
                        carritoListarView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.noencontrado"));
                        return;
                    }

                    carritoListarMisItemsView.cargarDatosCarrito(carritoEncontrado);

                    if (!carritoListarMisItemsView.isVisible()) {
                        carritoListarMisItemsView.setVisible(true);
                    }

                } catch (Exception ex) {
                    carritoListarView.mostrarMensaje(Internacionalizar.get("mensaje.error.general") + ": " + ex.getMessage());
                }
            }
        });

    }
    /**
     * Verifica si un producto ya existe en el carrito.
     *
     * @param producto El producto a verificar.
     * @return true si ya está en el carrito, false si no.
     */
    private boolean existeProductoEnCarrito(Producto producto) {
        List<ItemCarrito> items = carrito.obtenerItems();

        for (ItemCarrito item : items) {
            Producto productoDelItem = item.getProducto();
            if (productoDelItem.getCodigo() == producto.getCodigo()) {
                return true;
            }
        }

        return false;
    }
    /**
     * Añade un producto al carrito actual.
     *
     * @param codigo Código del producto a añadir.
     */
    private void anadirEnCarrito(int codigo) {
        Producto productoEncontrado = productoDAO.buscarPorCodigo(codigo);

        if (productoEncontrado == null) {
            carritoAnadirView.mostrarMensaje(Internacionalizar.get("mensaje.producto.noencontrado"));
            return;
        }

        int cantidadSeleccionada = Integer.parseInt(carritoAnadirView.getCbxCantidad().getSelectedItem().toString());

        if (existeProductoEnCarrito(productoEncontrado)) {
            carritoAnadirView.mostrarMensaje(Internacionalizar.get("mensaje.carrito.producto.ya.registrado"));
            return;
        }

        carrito.agregarProducto(productoEncontrado, cantidadSeleccionada);
        carritoAnadirView.cargarDatosCarrito(carrito);

        carritoAnadirView.getTxtSubtotal().setText(
                FormateadorUtils.formatearMoneda(carrito.calcularSubtotal(), Internacionalizar.getLocale())
        );
        carritoAnadirView.getTxtIVA().setText(
                FormateadorUtils.formatearMoneda(carrito.calcularIVA(), Internacionalizar.getLocale())
        );
        carritoAnadirView.getTxtTotal().setText(
                FormateadorUtils.formatearMoneda(carrito.calcularTotal(), Internacionalizar.getLocale())
        );
        carritoAnadirView.getBtnGuardar().setEnabled(true);
    }
    /**
     * Busca un producto y lo muestra en la vista de añadir carrito.
     *
     * @param Codigo Código del producto a buscar.
     */
    private void buscarEnCarrito(int Codigo){
            Producto productoEncontrado = productoDAO.buscarPorCodigo(Codigo);

            if (productoEncontrado != null) {
                String codigoTexto = String.valueOf(productoEncontrado.getCodigo());
                String nombre = productoEncontrado.getNombre();
                String precioFormateado = FormateadorUtils.formatearMoneda(productoEncontrado.getPrecio(), Internacionalizar.getLocale());

                JTextField campoCodigo = carritoAnadirView.getTxtCodigo();
                JTextField campoNombre = carritoAnadirView.getTxtNombre();
                JTextField campoPrecio = carritoAnadirView.getTxtPrecio();

                campoCodigo.setText(codigoTexto);
                campoNombre.setText(nombre);
                campoPrecio.setText(precioFormateado);
            } else {
                String mensaje = Internacionalizar.get("carrito.codigo.invalido");
                carritoAnadirView.mostrarMensaje(mensaje);
            }
        }
    /**
     * Actualiza el idioma de todas las vistas del controlador.
     *
     * @param language Código de idioma (ej: "es", "en").
     * @param country Código de país (ej: "EC", "US").
     */
    public void actualizarIdioma(String language, String country) {
        this.locale = new Locale(language, country);
        carritoAnadirView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        carritoModificarView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        carritoEliminarView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        carritoListarView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
        carritoListarMisItemsView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }
    /**
     * Cambia el idioma de la aplicación y actualiza la vista.
     *
     * @param languge Código de idioma.
     * @param country Código de país.
     */
    public void cambiarIdioma(String languge, String country) {
        Internacionalizar.setLenguaje(languge, country);
        this.locale = Internacionalizar.getLocale();

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

}
