package ec.edu.ups;

import ec.edu.ups.controlador.CarritoController;
import ec.edu.ups.controlador.PreguntaController;
import ec.edu.ups.controlador.ProductoController;
import ec.edu.ups.controlador.UsuarioController;
import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.dao.impl.*;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Rol;
import ec.edu.ups.modelo.Usuario;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.vista.*;
import ec.edu.ups.vista.Carrito.*;
import ec.edu.ups.vista.Producto.ProductoAnadirView;
import ec.edu.ups.vista.Producto.ProductoEliminarView;
import ec.edu.ups.vista.Producto.ProductoListaView;
import ec.edu.ups.vista.Producto.ProductoModificarView;
import ec.edu.ups.vista.Registrarse.RecuperarView;
import ec.edu.ups.vista.Registrarse.RegistrarView;
import ec.edu.ups.vista.Usuario.UsuarioCrearView;
import ec.edu.ups.vista.Usuario.UsuarioEliminarView;
import ec.edu.ups.vista.Usuario.UsuarioListarView;
import ec.edu.ups.vista.Usuario.UsuarioModificarView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class Main {
    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MensajeInternacionalizacionHandler Internacionalizar = new MensajeInternacionalizacionHandler("es", "EC");
                GuardarArchivo guardarArchivo = new GuardarArchivo(Internacionalizar);

                guardarArchivo.getBtnMemoria().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        guardarArchivo.setVisible(false);
                        UsuarioDAO usuarioDAO = new UsuarioDAOMemoria();
                        ProductoDAO productoDAO = new ProductoDAOMemoria();
                        CarritoDAO carritoDAO = new CarritoDAOMemoria();
                        PreguntaDAO preguntaDAO = new PreguntaDAOMemoria();
                        iniciarApp(usuarioDAO, productoDAO, carritoDAO, preguntaDAO, Internacionalizar);
                    }
                });

                guardarArchivo.getBtnArchivos().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        guardarArchivo.setVisible(false);
                        String ruta = guardarArchivo.RutaArchivo();
                        UsuarioDAO usuarioDAO = new UsuarioDAOArchivoTexto(ruta);
                        ProductoDAO productoDAO = new ProductoDAOArchivoBinario(ruta);
                        CarritoDAO carritoDAO = new CarritoDAOArchivoTexto(ruta);
                        PreguntaDAO preguntaDAO = new PreguntaDAOArchivoBinario(ruta);
                        iniciarApp(usuarioDAO, productoDAO, carritoDAO, preguntaDAO, Internacionalizar);
                    }
                });

                guardarArchivo.getMenuItemEspañol().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Internacionalizar.setLenguaje("es", "EC");
                        guardarArchivo.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                    }
                });

                guardarArchivo.getMenuItemIngles().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Internacionalizar.setLenguaje("en", "US");
                        guardarArchivo.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                    }
                });

                guardarArchivo.getMenuItemFrances().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Internacionalizar.setLenguaje("fr", "FR");
                        guardarArchivo.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                    }
                });
            }
        });
    }

    private static void iniciarApp(UsuarioDAO usuarioDAO, ProductoDAO productoDAO, CarritoDAO carritoDAO,
                                   PreguntaDAO preguntaDAO, MensajeInternacionalizacionHandler Internacionalizar) {
        LoginView loginView = new LoginView(Internacionalizar);
        RegistrarView registrarView = new RegistrarView(Internacionalizar);
        RecuperarView recuperarView = new RecuperarView(Internacionalizar);
        UsuarioController usuarioController = new UsuarioController(loginView, usuarioDAO, Internacionalizar);
        PreguntaController preguntaController = new PreguntaController(usuarioDAO, loginView, registrarView, preguntaDAO, recuperarView, Internacionalizar);

        loginView.getMenuItemEspañol().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Internacionalizar.setLenguaje("es", "EC");
                usuarioController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                preguntaController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
            }
        });
        loginView.getMenuItemIngles().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Internacionalizar.setLenguaje("en", "US");
                usuarioController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                preguntaController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
            }
        });
        loginView.getMenuItemFrances().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Internacionalizar.setLenguaje("fr", "FR");
                usuarioController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                preguntaController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
            }
        });


        registrarView.getMenuItemEspañol().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Internacionalizar.setLenguaje("es", "EC");
                usuarioController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                preguntaController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
            }
        });
        registrarView.getMenuItemIngles().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Internacionalizar.setLenguaje("en", "US");
                usuarioController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                preguntaController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
            }
        });
        registrarView.getMenuItemFrances().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Internacionalizar.setLenguaje("fr", "FR");
                usuarioController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                preguntaController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
            }
        });
        recuperarView.getMenuItemEspañol().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Internacionalizar.setLenguaje("es", "EC");
                usuarioController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                preguntaController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
            }
        });
        recuperarView.getMenuItemIngles().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Internacionalizar.setLenguaje("en", "US");
                usuarioController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                preguntaController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
            }
        });
        recuperarView.getMenuItemFrances().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Internacionalizar.setLenguaje("fr", "FR");
                usuarioController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                preguntaController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
            }
        });
        loginView.setVisible(true);

        loginView.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {

                super.windowClosed(e);
                Usuario usuarioAutenticado = usuarioController.getUsuario();

                if (usuarioAutenticado != null) {


                    MenuPrincipalView menuPrincipalView = new MenuPrincipalView(Internacionalizar);
                    menuPrincipalView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());

                    //Instanciar vistas del producto

                    ProductoAnadirView productoAnadirView = new ProductoAnadirView(Internacionalizar);
                    ProductoListaView productoListaView = new ProductoListaView(Internacionalizar);
                    ProductoModificarView productoModificarView = new ProductoModificarView(Internacionalizar);
                    ProductoEliminarView productoEliminarView = new ProductoEliminarView(Internacionalizar);

                    //Instancias vistas del carrito

                    CarritoAnadirView carritoAnadirView = new CarritoAnadirView(Internacionalizar);
                    CarritoEliminarView carritoEliminarView = new CarritoEliminarView(Internacionalizar);
                    CarritoListarView carritoListarView = new CarritoListarView(Internacionalizar);
                    CarritoListarMisItemsView carritoListarMisItemsView = new CarritoListarMisItemsView(Internacionalizar);
                    CarritoModificarView carritoModificarView = new CarritoModificarView(Internacionalizar);
                    menuPrincipalView.getMidesktop().add(carritoListarMisItemsView);


                    //Instancias vistas del usuario

                    UsuarioCrearView usuarioCrearView = new UsuarioCrearView(Internacionalizar);
                    UsuarioEliminarView usuarioEliminarView = new UsuarioEliminarView(Internacionalizar);
                    UsuarioListarView usuarioListarView = new UsuarioListarView(Internacionalizar);
                    UsuarioModificarView usuarioModificarView = new UsuarioModificarView(Internacionalizar);

                    // Instanciar controladores

                    ProductoController productoController = new ProductoController(productoAnadirView, productoListaView, productoEliminarView, productoModificarView, productoDAO, Internacionalizar);

                    CarritoController carritoController = new CarritoController(carritoDAO, productoDAO, carritoAnadirView, carritoModificarView,
                            carritoEliminarView, carritoListarView, carritoListarMisItemsView, Internacionalizar);

                    UsuarioController usuarioController = new UsuarioController(usuarioCrearView, usuarioEliminarView,
                            usuarioListarView, usuarioModificarView, usuarioAutenticado, usuarioDAO, Internacionalizar);

                    menuPrincipalView.mostrarMensaje(Internacionalizar.get("app.bienvenida") + ": " + usuarioAutenticado.getUsername());
                    if (usuarioAutenticado.getRol().equals(Rol.USUARIO)) {
                        menuPrincipalView.desactivar();
                    }

                    // Configurar eventos de la vista del menu principal

                    //PRODUCTO

                    menuPrincipalView.getMenuItemCrearProducto().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!productoAnadirView.isVisible()) {
                                productoAnadirView.setVisible(true);
                                menuPrincipalView.getMidesktop().add(productoAnadirView);
                            }
                        }
                    });

                    menuPrincipalView.getMenuItemBuscarProducto().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!productoListaView.isVisible()) {
                                productoListaView.setVisible(true);
                                menuPrincipalView.getMidesktop().add(productoListaView);
                            }
                        }
                    });

                    menuPrincipalView.getMenuItemModificarProducto().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!productoModificarView.isVisible()) {
                                productoModificarView.setVisible(true);
                                menuPrincipalView.getMidesktop().add(productoModificarView);
                            }
                        }
                    });

                    menuPrincipalView.getMenuItemEliminarProducto().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!productoEliminarView.isVisible()) {
                                productoEliminarView.setVisible(true);
                                menuPrincipalView.getMidesktop().add(productoEliminarView);
                            }
                        }
                    });

                    //CARRITO

                    menuPrincipalView.getMenuItemCrearCarrito().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!carritoAnadirView.isVisible()) {
                                carritoAnadirView.setVisible(true);
                                menuPrincipalView.getMidesktop().add(carritoAnadirView);
                            }
                        }
                    });

                    menuPrincipalView.getMenuItemEliminarCarrito().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!carritoEliminarView.isVisible()) {
                                carritoEliminarView.setVisible(true);
                                menuPrincipalView.getMidesktop().add(carritoEliminarView);
                            }
                        }
                    });

                    menuPrincipalView.getMenuItemBuscarCarrito().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!carritoListarView.isVisible()) {
                                carritoListarView.setVisible(true);
                                menuPrincipalView.getMidesktop().add(carritoListarView);
                            }
                        }
                    });

                    menuPrincipalView.getMenuItemModificarCarrito().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!carritoModificarView.isVisible()) {
                                carritoModificarView.setVisible(true);
                                menuPrincipalView.getMidesktop().add(carritoModificarView);
                            }
                        }
                    });

                    //USUARIO

                    menuPrincipalView.getMenuItemCrearUsuario().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!usuarioCrearView.isVisible()) {
                                usuarioCrearView.setVisible(true);
                                menuPrincipalView.getMidesktop().add(usuarioCrearView);
                            }
                        }
                    });

                    menuPrincipalView.getMenuItemEliminarUsuario().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!usuarioEliminarView.isVisible()) {
                                usuarioEliminarView.setVisible(true);
                                menuPrincipalView.getMidesktop().add(usuarioEliminarView);
                            }
                        }
                    });

                    menuPrincipalView.getMenuItemBuscarUsuario().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!usuarioListarView.isVisible()) {
                                usuarioListarView.setVisible(true);
                                menuPrincipalView.getMidesktop().add(usuarioListarView);
                            }
                        }
                    });

                    menuPrincipalView.getMenuItemModificarUsuario().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!usuarioModificarView.isVisible()) {
                                usuarioModificarView.setVisible(true);
                                menuPrincipalView.getMidesktop().add(usuarioModificarView);
                            }
                        }
                    });

                    //INTERNACIONALIZACION

                    menuPrincipalView.getMenuItemEspañol().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Internacionalizar.setLenguaje("es", "EC");
                            menuPrincipalView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                            productoController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                            carritoController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                            usuarioController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                        }
                    });

                    menuPrincipalView.getMenuItemIngles().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Internacionalizar.setLenguaje("en", "US");
                            menuPrincipalView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                            productoController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                            carritoController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                            usuarioController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());

                        }
                    });

                    menuPrincipalView.getMenuItemFrances().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Internacionalizar.setLenguaje("fr", "FR");
                            menuPrincipalView.actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                            productoController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                            carritoController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                            usuarioController.cambiarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
                        }
                    });


                    menuPrincipalView.getMenuItemCerrarSesion().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            menuPrincipalView.setVisible(false);
                            loginView.setVisible(true);
                            Carrito.setContador(Carrito.getContador() - 1);
                            loginView.getPsfContraseña().setText("");
                        }
                    });

                    menuPrincipalView.getMenuItemSalir().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
                }
            }
        });
    }
}
