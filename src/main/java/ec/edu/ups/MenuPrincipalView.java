package ec.edu.ups;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.util.Locale;

public class MenuPrincipalView extends JFrame {
    private JMenuBar menubar;
    private JMenu menuCarrito;
    private JMenu menuProducto;
    private JMenu menuUsuario;
    private JMenu menuIdiomas;
    private JMenu menuSalir;

    private JMenuItem menuItemCrearProducto;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemBuscarProducto;
    private JMenuItem menuItemModificarProducto;

    private JMenuItem menuItemCrearCarrito;
    private JMenuItem menuItemEliminarCarrito;
    private JMenuItem menuItemBuscarCarrito;
    private JMenuItem menuItemModificarCarrito;

    private JMenuItem menuItemCrearUsuario;
    private JMenuItem menuItemEliminarUsuario;
    private JMenuItem menuItemBuscarUsuario;
    private JMenuItem menuItemModificarUsuario;

    private JMenuItem menuItemEspañol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemFrances;
    private JMenuItem menuItemCerrarSesion;
    private JMenuItem menuItemSalir;

    private MiDesktop Midesktop;
    private MensajeInternacionalizacionHandler Internacionalizar;
    private Locale locale;

    public MenuPrincipalView(MensajeInternacionalizacionHandler Internacionalizar) {
        this.Internacionalizar = Internacionalizar;
        Midesktop = new MiDesktop();
        setTitle(Internacionalizar.get("app.titulo"));

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

        //Producto
        menuItemCrearProducto = new JMenuItem(Internacionalizar.get("producto.anadir.menu"));
        menuItemEliminarProducto = new JMenuItem(Internacionalizar.get("producto.eliminar.menu"));
        menuItemBuscarProducto = new JMenuItem(Internacionalizar.get("producto.listar.menu"));
        menuItemModificarProducto = new JMenuItem(Internacionalizar.get("producto.modificar.menu"));

        menuItemCrearProducto.setIcon(new ImageIcon(getClass().getResource("/crear.png")));
        menuItemEliminarProducto.setIcon(new ImageIcon(getClass().getResource("/eliminar.png")));
        menuItemBuscarProducto.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        menuItemModificarProducto.setIcon(new ImageIcon(getClass().getResource("/modificar.png")));

        //Carrito
        menuItemCrearCarrito = new JMenuItem(Internacionalizar.get("carrito.anadir.menu"));
        menuItemEliminarCarrito = new JMenuItem(Internacionalizar.get("carrito.eliminar.menu"));
        menuItemBuscarCarrito = new JMenuItem(Internacionalizar.get("carrito.listar.menu"));
        menuItemModificarCarrito = new JMenuItem(Internacionalizar.get("carrito.modificar.menu"));

        menuItemCrearCarrito.setIcon(new ImageIcon(getClass().getResource("/crear.png")));
        menuItemEliminarCarrito.setIcon(new ImageIcon(getClass().getResource("/eliminar.png")));
        menuItemBuscarCarrito.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        menuItemModificarCarrito.setIcon(new ImageIcon(getClass().getResource("/modificar.png")));

        //Usuario
        menuItemCrearUsuario = new JMenuItem(Internacionalizar.get("usuario.crear.menu"));
        menuItemEliminarUsuario = new JMenuItem(Internacionalizar.get("usuario.eliminar.menu"));
        menuItemBuscarUsuario = new JMenuItem(Internacionalizar.get("usuario.listar.menu"));
        menuItemModificarUsuario = new JMenuItem(Internacionalizar.get("usuario.modificar.menu"));

        menuItemCrearUsuario.setIcon(new ImageIcon(getClass().getResource("/crear.png")));
        menuItemEliminarUsuario.setIcon(new ImageIcon(getClass().getResource("/eliminar.png")));
        menuItemBuscarUsuario.setIcon(new ImageIcon(getClass().getResource("/buscar.png")));
        menuItemModificarUsuario.setIcon(new ImageIcon(getClass().getResource("/modificar.png")));

        //Idiomas
        menuItemEspañol = new JMenuItem(Internacionalizar.get("etiqueta.idioma.Español"));
        menuItemIngles = new JMenuItem(Internacionalizar.get("etiqueta.idioma.Ingles"));
        menuItemFrances = new JMenuItem(Internacionalizar.get("etiqueta.idioma.Frances"));

        menuItemSalir = new JMenuItem(Internacionalizar.get("salir.item.menu"));
        menuItemCerrarSesion = new JMenuItem(Internacionalizar.get("cerrarsesion.item.menu"));
        menuItemSalir.setIcon(new ImageIcon(getClass().getResource("/salir.png")));
        menuItemCerrarSesion.setIcon(new ImageIcon(getClass().getResource("/salir.png")));

        //Agregaciones
        menubar.add(menuProducto);
        menubar.add(menuCarrito);
        menubar.add(menuUsuario);
        menubar.add(menuIdiomas);
        menubar.add(menuSalir);

        menuProducto.add(menuItemCrearProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemBuscarProducto);
        menuProducto.add(menuItemModificarProducto);

        menuCarrito.add(menuItemCrearCarrito);
        menuCarrito.add(menuItemEliminarCarrito);
        menuCarrito.add(menuItemBuscarCarrito);
        menuCarrito.add(menuItemModificarCarrito);

        menuUsuario.add(menuItemCrearUsuario);
        menuUsuario.add(menuItemEliminarUsuario);
        menuUsuario.add(menuItemBuscarUsuario);
        menuUsuario.add(menuItemModificarUsuario);

        menuIdiomas.add(menuItemEspañol);
        menuIdiomas.add(menuItemIngles);
        menuIdiomas.add(menuItemFrances);

        menuSalir.add(menuItemSalir);
        menuSalir.add(menuItemCerrarSesion);

        setJMenuBar(menubar);
        setContentPane(Midesktop);
        Midesktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle(Internacionalizar.get("app.titulo"));
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);

        actualizarIdioma(Internacionalizar.getLocale().getLanguage(), Internacionalizar.getLocale().getCountry());
    }

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

    public void desactivar() {
        getMenuItemCrearProducto().setVisible(false);
        getMenuItemModificarProducto().setVisible(false);
        getMenuItemEliminarProducto().setEnabled(false);
        getMenuItemCrearUsuario().setEnabled(false);
        getMenuItemBuscarUsuario().setVisible(false);
    }
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

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
