package ec.edu.ups;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;
import ec.edu.ups.vista.Carrito.*;
import ec.edu.ups.vista.Carrito.CarritoListarMisView;
import ec.edu.ups.vista.Producto.ProductoAnadirView;
import ec.edu.ups.vista.Producto.ProductoEliminarView;
import ec.edu.ups.vista.Producto.ProductoListaView;
import ec.edu.ups.vista.Producto.ProductoModificarView;
import ec.edu.ups.vista.Registrarse.PreguntaModificarView;
import ec.edu.ups.vista.Registrarse.PreguntaRegistrarView;
import ec.edu.ups.vista.Registrarse.RegistrarView;
import ec.edu.ups.vista.Usuario.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

public class Main {

    private static final UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
    private static final ProductoDAO productoDAO = new ProductoDAOMemoria();
    private static final CarritoDAO carritoDAO = new CarritoDAOMemoria();
    private static final MensajeInternacionalizacionHandler mensaje = new MensajeInternacionalizacionHandler("es","EC");
    private static MenuPrincipalView menuPrincipalView;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(Main::mostrarVentanaDeLogin);
    }

    public static void mostrarVentanaDeLogin() {
        LoginView loginView = new LoginView(mensaje);
        UsuarioCrearView usuarioCrearView = new UsuarioCrearView(mensaje);
        UsuarioModificarView usuarioModificarView = new UsuarioModificarView(mensaje);
        UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView(mensaje);
        UsuarioListarView usuarioListarView = new UsuarioListarView(mensaje);
        UsuarioModificarMisView usuarioModificarMisView = new UsuarioModificarMisView(mensaje);
        RegistrarView registrarView = new RegistrarView(mensaje);
        PreguntaRegistrarView preguntaRegistrarView = new PreguntaRegistrarView(mensaje);
        PreguntaModificarView preguntaModificarView = new PreguntaModificarView(mensaje);

        UsuarioController usuarioController = new UsuarioController(usuarioCrearView, usuarioDAO, loginView, registrarView, preguntaModificarView, preguntaRegistrarView, usuarioModificarView, usuarioEliminarView, usuarioModificarMisView, usuarioListarView, mensaje);

        loginView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Usuario usuarioAutenticado = usuarioController.getUsuarioAutentificado();
                if (usuarioAutenticado != null) {
                    iniciarAplicacionPrincipal(usuarioAutenticado, usuarioCrearView, usuarioModificarView,usuarioEliminarView,usuarioModificarMisView,usuarioListarView);
                } else {
                    System.exit(0);
                }
            }
        });

        loginView.setVisible(true);
    }

    public static void iniciarAplicacionPrincipal(Usuario usuarioAutenticado, UsuarioCrearView usuarioCrearView, UsuarioModificarView usuarioModificarView, UsuarioEliminarView usuarioEliminarView,UsuarioModificarMisView usuarioModificarMisView ,UsuarioListarView usuarioListarView) {
        MenuPrincipalView menuPrincipalView = new MenuPrincipalView(mensaje);
        ProductoModificarView productoModificarView = new ProductoModificarView(mensaje);
        ProductoEliminarView productoEliminarView = new ProductoEliminarView(mensaje);
        CarritoListarView carritoListarView = new CarritoListarView(mensaje);
        CarritoAnadirView carritoAnadirView = new CarritoAnadirView(mensaje);
        ProductoListaView productoListaView = new ProductoListaView(mensaje);
        ProductoAnadirView productoAnadirView = new ProductoAnadirView(mensaje);
        CarritoModificarView carritoModificarView = new CarritoModificarView(mensaje);
        CarritoEliminarView carritoEliminarView = new CarritoEliminarView(mensaje);
        CarritoListarMisView carritoListarMisView = new CarritoListarMisView(mensaje);



        CarritoController carritoController = new CarritoController(carritoDAO, productoDAO, carritoAnadirView, carritoListarView, carritoModificarView, carritoEliminarView, carritoListarMisView, usuarioAutenticado, mensaje);
        ProductoController productoController = new ProductoController(productoDAO, carritoAnadirView, productoModificarView, productoEliminarView, productoListaView, productoAnadirView, mensaje);

        menuPrincipalView.mostrarMensaje(mensaje.get("msg.usr.login.exito") + " " + usuarioAutenticado.getUsername());
        if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
            menuPrincipalView.desactivar();
        }

        configurarMenu(menuPrincipalView, carritoAnadirView, productoAnadirView, productoListaView, productoEliminarView,
                productoModificarView, carritoListarView, carritoModificarView, carritoEliminarView,carritoListarMisView,
                usuarioCrearView, usuarioModificarView,usuarioEliminarView,usuarioModificarMisView, usuarioListarView);

        menuPrincipalView.setVisible(true);
    }

    private static void cambiarIdioma(String idioma, String pais) {
        mensaje.setLenguaje(idioma, pais);
        if (menuPrincipalView != null) {
            menuPrincipalView.actualizarTexto();
        }

    }

    private static void configurarMenu(MenuPrincipalView principalView, CarritoAnadirView carritoAñadirView, ProductoAnadirView productoAnadirView,
                                       ProductoListaView productoListaView, ProductoEliminarView productoEliminarView,
                                       ProductoModificarView productoModificarView, CarritoListarView carritoListarView,
                                       CarritoModificarView carritoModificarView, CarritoEliminarView carritoEliminarView, CarritoListarMisView carritoListarMisView,
                                       UsuarioCrearView usuarioCrearView, UsuarioModificarView usuarioModificarView, UsuarioEliminarView usuarioEliminarView, UsuarioModificarMisView usuarioModificarMisView, UsuarioListarView usuarioListarView)

    {
        principalView.getMenuItemCerrarSesión().addActionListener(e -> {
            principalView.dispose();
            mostrarVentanaDeLogin();
        });


        principalView.getMenuItemAñadirCarrito().addActionListener(e -> {
            if (!carritoAñadirView.isVisible()) {
                principalView.getDesktop().add(carritoAñadirView);
                carritoAñadirView.setVisible(true);
            }
            carritoAñadirView.toFront();
        });
        principalView.getMenuItemListarCarritos().addActionListener(e -> {
            if (!carritoListarView.isVisible()) {
                principalView.getDesktop().add(carritoListarView);
                carritoListarView.setVisible(true);
            }
            carritoListarView.toFront();
        });
        principalView.getMenuItemModificarCarrito().addActionListener(e -> {
            if (!carritoModificarView.isVisible()) {
                principalView.getDesktop().add(carritoModificarView);
                carritoModificarView.setVisible(true);
            }
            carritoModificarView.toFront();
        });
        principalView.getMenuItemEliminarCarrito().addActionListener(e -> {
            if (!carritoEliminarView.isVisible()) {
                principalView.getDesktop().add(carritoEliminarView);
                carritoEliminarView.setVisible(true);
            }
            carritoEliminarView.toFront();
        });

        principalView.getMenuItemCargarProducto().addActionListener(e -> {
            if (!productoAnadirView.isVisible()) {
                principalView.getDesktop().add(productoAnadirView);
                productoAnadirView.setVisible(true);
            }
            productoAnadirView.toFront();
        });
        principalView.getMenuItemActualizarProducto().addActionListener(e -> {
            if (!productoListaView.isVisible()) {
                principalView.getDesktop().add(productoListaView);
                productoListaView.setVisible(true);
            }
            productoListaView.toFront();
        });
        principalView.getMenuItemEliminarProducto().addActionListener(e -> {
            if (!productoEliminarView.isVisible()) {
                principalView.getDesktop().add(productoEliminarView);
                productoEliminarView.setVisible(true);
            }
            productoEliminarView.toFront();
        });
        principalView.getMenuItemModificarProducto().addActionListener(e -> {
            if (!productoModificarView.isVisible()) {
                principalView.getDesktop().add(productoModificarView);
                productoModificarView.setVisible(true);
            }
            productoModificarView.toFront();
        });


        principalView.getMenuItemCrearUsuario().addActionListener(e -> {
            if (!usuarioCrearView.isVisible()) {
                principalView.getDesktop().add(usuarioCrearView);
                usuarioCrearView.setVisible(true);
            }
            usuarioCrearView.toFront();
        });
        principalView.getMenuItemModificarUsuario().addActionListener(e -> {
            if (!usuarioModificarView.isVisible()) {
                principalView.getDesktop().add(usuarioModificarView);
                usuarioModificarView.setVisible(true);
            }
            usuarioModificarView.toFront();
        });

        principalView.getMenuItemEliminarUsuario().addActionListener(e->{
            if(!usuarioEliminarView.isVisible()){
                principalView.getDesktop().add(usuarioEliminarView);
                usuarioEliminarView.setVisible(true);
            }
            usuarioEliminarView.toFront();
        });

        principalView.getMenuItemListarUsuarios().addActionListener(e->{
            if(!usuarioListarView.isVisible()){
                principalView.getDesktop().add(usuarioListarView);
                usuarioListarView.setVisible(true);
            }
            usuarioListarView.toFront();
        });

        principalView.getMenuItemListarCarritoMis().addActionListener(e->{
            if(!carritoListarMisView.isVisible()){
                principalView.getDesktop().add(carritoListarMisView);
                carritoListarMisView.setVisible(true);
            }
            carritoListarMisView.toFront();
        });

        principalView.getMenuItemModificarMisUsuario().addActionListener(e->{
            if(!usuarioModificarMisView.isVisible()){
                principalView.getDesktop().add(usuarioModificarMisView);
                usuarioModificarMisView.setVisible(true);
            }
            usuarioModificarMisView.toFront();
        });

        principalView.getMenuItemEspañol().addActionListener(e ->{
                    cambiarIdioma("es", "EC");
                    principalView.actualizarTexto();
                    carritoAñadirView.actualizarTextos();
                    carritoEliminarView.actualizarTextos();
                    carritoListarView.actualizarTextos();
                    carritoModificarView.actualizarTextos();
                });
        principalView.getMenuItemIngles().addActionListener(e ->
                cambiarIdioma("en", "US")

        );
        }
    }

