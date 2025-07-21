package ec.edu.ups;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;

/**
 * Vista principal del menú de la aplicación.
 * Esta clase extiende de {@link JFrame} y representa la ventana principal con un menú
 * que permite gestionar productos, carritos, usuarios e idiomas.
 * Incluye soporte para internacionalización y cambio dinámico de idioma.
 */
public class MenuPrincipalView extends JFrame {

    // Barra de menú principal
    private JMenuBar menubar;

    // Menús principales
    private JMenu menuCarrito;
    private JMenu menuProducto;
    private JMenu menuUsuario;
    private JMenu menuIdiomas;
    private JMenu menuSalir;

    // Items del menú Producto
    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemBuscarProducto;
    private JMenuItem menuItemModificarProducto;

    // Items del menú Carrito
    private JMenuItem menuItemCrearCarrito;
    private JMenuItem menuItemEliminarCarrito;
    private JMenuItem menuItemBuscarCarrito;
    private JMenuItem menuItemModificarCarrito;

    // Items del menú Usuario
    private JMenuItem menuItemCrearUsuario;
    private JMenuItem menuItemEliminarUsuario;
    private JMenuItem menuItemBuscarUsuario;
    private JMenuItem menuItemModificarUsuario;

    // Items del menú Idiomas
    private JMenuItem menuItemEspañol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemFrances;

    // Items del menú Salir
    private JMenuItem menuItemCerrarSesion;
    private JMenuItem menuItemSalir;

    // Componente principal tipo escritorio para agregar ventanas internas
    private MiDesktop Midesktop;

    // Handler para la internacionalización de mensajes y etiquetas
    private MensajeInternacionalizacionHandler Internacionalizar;

    // Configuración regional actual
    private Locale locale;

    /**
     * Constructor que inicializa la ventana principal del menú.
     * Configura la barra de menú, los ítems, los íconos y establece el idioma inicial.
     *
     * @param Internacionalizar handler para obtener mensajes internacionalizados.
     */
    public MenuPrincipalView(MensajeInternacionalizacionHandler Internacionalizar) {
        this.Internacionalizar = Internacionalizar;
        Midesktop = new MiDesktop();

        setTitle(Internacionalizar.get("app.titulo"));

        // Inicialización y configuración de menús y items con texto internacionalizado e íconos
        menubar = new JMenuBar();

        menuCarrito = new JMenu(Internacionalizar.get("menu.carrito"));
        menuProducto = new JMenu(Internacionalizar.get("menu.producto"));
        menuUsuario = new JMenu(Internacionalizar.get("menu.usuario"));
        menuIdiomas = new JMenu(Internacionalizar.get("menu.idiomas"));
        menuSalir = new JMenu(Internacionalizar.get("menu.salir"));

        menuCarrito.setIcon(new ImageIcon(getClass().getResource("/carrito.png")));
        menuProducto.setIcon(new ImageIcon(getClass().getResource("/producto.png")));
        menuUsuario.setIcon(new ImageIcon(getClass().getResource("/usuario_listar.png")));
        menuIdiomas.setIcon(new ImageIcon(getClass().getResource("/idioma.png")));
        menuSalir.setIcon(new ImageIcon(getClass().getResource("/salir.png")));

        // Items del menú Producto
        menuItemCrearProducto = new JMenuItem(Internacionalizar.get("producto.anadir.menu"));
        menuItemEliminarProducto = new JMenuItem(Internacionalizar.get("producto.eliminar.menu"));
        menuItemBuscarProducto = new JMenuItem(Internacionalizar.get("producto.listar.menu"));
        menuItemModificarProducto = new JMenuItem(Internacionalizar.get("producto.modificar.menu"));

        menuItemCrearProducto.setIcon(new ImageIcon(getClass().getResource("/crear.png")));
        menuItemEliminarProducto.setIcon(new ImageIcon(getClass().getResource("/eliminar.png")));
        menuItemBuscarProducto.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        menuItemModificarProducto.setIcon(new ImageIcon(getClass().getResource("/modificar.png")));

        // Items del menú Carrito
        menuItemCrearCarrito = new JMenuItem(Internacionalizar.get("carrito.anadir.menu"));
        menuItemEliminarCarrito = new JMenuItem(Internacionalizar.get("carrito.eliminar.menu"));
        menuItemBuscarCarrito = new JMenuItem(Internacionalizar.get("carrito.listar.menu"));
        menuItemModificarCarrito = new JMenuItem(Internacionalizar.get("carrito.modificar.menu"));

        menuItemCrearCarrito.setIcon(new ImageIcon(getClass().getResource("/crear.png")));
        menuItemEliminarCarrito.setIcon(new ImageIcon(getClass().getResource("/eliminar.png")));
        menuItemBuscarCarrito.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        menuItemModificarCarrito.setIcon(new ImageIcon(getClass().getResource("/modificar.png")));

        // Items del menú Usuario
        menuItemCrearUsuario = new JMenuItem(Internacionalizar.get("usuario.crear.menu"));
        menuItemEliminarUsuario = new JMenuItem(Internacionalizar.get("usuario.eliminar.menu"));
        menuItemBuscarUsuario = new JMenuItem(Internacionalizar.get("usuario.listar.menu"));
        menuItemModificarUsuario = new JMenuItem(Internacionalizar.get("usuario.modificar.menu"));

        menuItemCrearUsuario.setIcon(new ImageIcon(getClass().getResource("/crear.png")));
        menuItemEliminarUsuario.setIcon(new ImageIcon(getClass().getResource("/eliminar.png")));
        menuItemBuscarUsuario.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        menuItemModificarUsuario.setIcon(new ImageIcon(getClass().getResource("/modificar.png")));

        // Items del menú Idiomas
        menuItemEspañol = new JMenuItem(Internacionalizar.get("etiqueta.idioma.Español"));
        menuItemIngles = new JMenuItem(Internacionalizar.get("etiqueta.idioma.Ingles"));
        menuItemFrances = new JMenuItem(Internacionalizar.get("etiqueta.idioma.Frances"));

        // Items del menú Salir
        menuItemSalir = new JMenuItem(Internacionalizar.get("salir.item.menu"));
        menuItemCerrarSesion = new JMenuItem(Internacionalizar.get("cerrarsesion.item.menu"));
        menuItemSalir.setIcon(new ImageIcon(getClass().getResource("/salir.png")));
        menuItemCerrarSesion.setIcon(new ImageIcon(getClass().getResource("/salir.png")));

        // Agregar menús a la barra de menú
        menubar.add(menuProducto);
        menubar.add(menuCarrito);
        menubar.add(menuUsuario);
        menubar.add(menuIdiomas);
        menubar.add(menuSalir);

        // Agregar items a menú Producto
        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemBuscarProducto);
        menuProducto.add(menuItemModificarProducto);

        // Agregar items a menú Carrito
        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemEliminarCarrito);
        menuCarrito.add(menuItemBuscarCarrito);
        menuCarrito.add(menuItemModificarCarrito);

        // Agregar items a menú Usuario
        menuUsuario.add(menuItemCrearUsuario);
        menuUsuario.add(menuItemEliminarUsuario);
        menuUsuario.add(menuItemBuscarUsuario);
        menuUsuario.add(menuItemModificarUsuario);

        // Agregar items a menú Idiomas
        menuIdiomas.add(menuItemEspañol);
        menuIdiomas.add(menuItemIngles);
        menuIdiomas.add(menuItemFrances);

        // Agregar items a menú Salir
        menuSalir.add(menuItemSalir);
        menuSalir.add(menuItemCerrarSesion);

        // Configuración final de la ventana
        setJMenuBar(menubar);
        setContentPane(Midesktop);
        Midesktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        // Actualiza el idioma de la interfaz según la configuración actual
        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

    /**
     * Actualiza el idioma de todos los textos de los menús y sus ítems
     * de acuerdo al lenguaje y país especificados.
     *
     * @param language código de idioma (ej. "es", "en", "fr").
     * @param country  código de país (ej. "EC", "US", "FR").
     */
    public void actualizarIdioma(String language, String country){
        locale = new Locale(language, country);

        menuItemCerrarSesion.setText(Internacionalizar.get("cerrar.sesion"));

        menuProducto.setText(Internacionalizar.get("menu.producto"));
        menuCarrito.setText(Internacionalizar.get("menu.carrito"));
        menuUsuario.setText(Internacionalizar.get("menu.usuario"));
        menuIdiomas.setText(Internacionalizar.get("menu.idiomas"));
        menuSalir.setText(Internacionalizar.get("menu.salir"));

        menuItemCrearProducto.setText(Internacionalizar.get("producto.anadir.menu"));
        menuItemEliminarProducto.setText(Internacionalizar.get("producto.eliminar.menu"));
        menuItemBuscarProducto.setText(Internacionalizar.get("producto.listar.menu"));
        menuItemModificarProducto.setText(Internacionalizar.get("producto.modificar.menu"));

        menuItemCrearCarrito.setText(Internacionalizar.get("carrito.anadir.menu"));
        menuItemEliminarCarrito.setText(Internacionalizar.get("carrito.eliminar.menu"));
        menuItemBuscarCarrito.setText(Internacionalizar.get("carrito.listar.menu"));
        menuItemModificarCarrito.setText(Internacionalizar.get("carrito.modificar.menu"));

        menuItemCrearUsuario.setText(Internacionalizar.get("usuario.crear.menu"));
        menuItemEliminarUsuario.setText(Internacionalizar.get("usuario.eliminar.menu"));
        menuItemBuscarUsuario.setText(Internacionalizar.get("usuario.listar.menu"));
        menuItemModificarUsuario.setText(Internacionalizar.get("usuario.modificar.menu"));

        menuItemEspañol.setText(Internacionalizar.get("etiqueta.idioma.Español"));
        menuItemIngles.setText(Internacionalizar.get("etiqueta.idioma.Ingles"));
        menuItemFrances.setText(Internacionalizar.get("etiqueta.idioma.Frances"));
    }

    /**
     * Desactiva o oculta ciertos elementos del menú para limitar acceso o funciones.
     */
    public void desactivar() {
        getMenuItemCrearProducto().setVisible(false);
        getMenuItemModificarProducto().setVisible(false);
        getMenuItemEliminarProducto().setEnabled(false);
        getMenuItemCrearUsuario().setEnabled(false);
        getMenuItemBuscarUsuario().setVisible(false);
    }

    /**
     * Muestra un mensaje emergente {@link JOptionPane} con el texto indicado.
     *
     * @param mensaje mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Getters para acceder a los elementos del menú

    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    public JMenuItem getMenuItemCrearProducto() {
        return menuItemCrearProducto;
    }

    public JMenuItem getMenuItemBuscarProducto() {
        return menuItemBuscarProducto;
    }

    public JMenuItem getMenuItemModificarProducto() {
        return menuItemModificarProducto;
    }

    public JMenuItem getMenuItemCrearCarrito() {
        return menuItemCrearCarrito;
    }

    public JMenuItem getMenuItemEliminarCarrito() {
        return menuItemEliminarCarrito;
    }

    public JMenuItem getMenuItemBuscarCarrito() {
        return menuItemBuscarCarrito;
    }

    public JMenuItem getMenuItemModificarCarrito() {
        return menuItemModificarCarrito;
    }

    public JMenuItem getMenuItemCrearUsuario() {
        return menuItemCrearUsuario;
    }

    public JMenuItem getMenuItemEliminarUsuario() {
        return menuItemEliminarUsuario;
    }

    public JMenuItem getMenuItemBuscarUsuario() {
        return menuItemBuscarUsuario;
    }

    public JMenuItem getMenuItemModificarUsuario() {
        return menuItemModificarUsuario;
    }

    public JMenuItem getMenuItemEspañol() {
        return menuItemEspañol;
    }

    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    public JMenuItem getMenuItemFrances() {
        return menuItemFrances;
    }

    public JMenuItem getMenuItemCerrarSesion() {
        return menuItemCerrarSesion;
    }

    public JMenuItem getMenuItemSalir() {
        return menuItemSalir;
    }

    public MiDesktop getMidesktop() {
        return Midesktop;
    }
}
