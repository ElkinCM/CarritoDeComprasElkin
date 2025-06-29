package ec.edu.ups.vista;

import  ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class MenuPrincipalView extends JFrame {
    private JMenuBar menubar;
    private JMenu menuCarrito;
    private JMenu menuProducto;
    private JMenu menuUsuario;
    private JMenu menuAdministrador;
    private JMenu menuIdiomas;
    private JMenuItem menuItemEliminarProducto;
    private JMenuItem menuItemModificarProducto;
    private JMenuItem menuItemActualizarProducto;
    private JMenuItem menuItemCargarProducto;
    private JDesktopPane desktop;
    private JMenuItem menuItemAñadirCarrito;
    private JMenuItem menuItemListarCarritos;
    private JMenuItem menuItemEliminarCarrito;
    private JMenuItem menuItemModificarCarrito;
    private JMenuItem menuItemListarCarritoMis;
    private JMenuItem menuItemCrearUsuario;
    private JMenuItem menuItemEliminarUsuario;
    private JMenuItem menuItemListarUsuarios;
    private JMenuItem menuItemModificarUsuario;
    private JMenuItem menuItemModificarMisUsuario;
    private JMenuItem menuItemCerrarSesión;
    private JMenuItem menuItemEspañol;
    private JMenuItem menuItemIngles;
    private JMenuItem menuItemNoruego;


    public JDesktopPane getDesktop() {
        return desktop;
    }

    public void setDesktop(JDesktopPane desktop) {
        this.desktop = desktop;
    }

    public MenuPrincipalView() {
        desktop = new JDesktopPane();
        menubar = new JMenuBar();
        menuCarrito = new JMenu("Carrito");
        menuProducto = new JMenu("Producto");
        menuUsuario = new JMenu("Usuario");
        menuAdministrador = new JMenu("Administrador");
        menuIdiomas = new JMenu("Idiomas");

        menuItemEliminarProducto = new JMenuItem("Eliminar");
        menuItemModificarProducto = new JMenuItem("Modificar");
        menuItemActualizarProducto = new JMenuItem("Actualizar");
        menuItemCargarProducto = new JMenuItem("Cargar");
        menuItemAñadirCarrito = new JMenuItem("Carrito Añadir");
        menuItemCerrarSesión = new JMenuItem("Cerrar Sesión");
        menuItemListarCarritos = new JMenuItem("Listar Carritos");
        menuItemEliminarCarrito = new JMenuItem("Eliminar Carrito");
        menuItemModificarCarrito = new JMenuItem("Modificar Carrito");
        menuItemListarCarritoMis = new JMenuItem("Listar Mis Carritos");
        menuItemCrearUsuario = new JMenuItem("Crear Usuario");
        menuItemEliminarUsuario = new JMenuItem("Eliminar Usuario");
        menuItemListarUsuarios = new JMenuItem("Listar Usuarios");
        menuItemModificarUsuario = new JMenuItem("Modificar Usuario");
        menuItemModificarMisUsuario = new JMenuItem("Modifica tu Usuario");
        menuItemEspañol = new JMenuItem("Español");
        menuItemIngles = new JMenuItem("Ingles");
        menuItemNoruego = new JMenuItem("Frances");


        menubar.add(menuUsuario);
        menuUsuario.add(menuItemCerrarSesión);
        menuUsuario.add(menuItemModificarMisUsuario);
        menubar.add(menuAdministrador);
        menuAdministrador.add(menuItemCrearUsuario);
        menuAdministrador.add(menuItemEliminarUsuario);
        menuAdministrador.add(menuItemListarUsuarios);
        menuAdministrador.add(menuItemModificarUsuario);
        menubar.add(menuProducto);
        menuProducto.add(menuItemEliminarProducto);
        menuProducto.add(menuItemModificarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemCargarProducto);
        menubar.add(menuCarrito);
        menuCarrito.add(menuItemAñadirCarrito);
        menuCarrito.add(menuItemListarCarritoMis);
        menuCarrito.add(menuItemListarCarritos);
        menuCarrito.add(menuItemEliminarCarrito);
        menuCarrito.add(menuItemModificarCarrito);
        menubar.add(menuIdiomas);
        menuIdiomas.add(menuItemEspañol);
        menuIdiomas.add(menuItemIngles);
        menuIdiomas.add(menuItemNoruego);

        setJMenuBar(menubar);
        setContentPane(desktop);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema De Carrito De Compras");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JMenuItem getMenuItemModificarMisUsuario() {
        return menuItemModificarMisUsuario;
    }

    public void setMenuItemModificarMisUsuario(JMenuItem menuItemModificarMisUsuario) {
        this.menuItemModificarMisUsuario = menuItemModificarMisUsuario;
    }

    public JMenu getMenuIdiomas() {
        return menuIdiomas;
    }

    public void setMenuIdiomas(JMenu menuIdiomas) {
        this.menuIdiomas = menuIdiomas;
    }

    public JMenuItem getMenuItemListarCarritoMis() {
        return menuItemListarCarritoMis;
    }

    public void setMenuItemListarCarritoMis(JMenuItem menuItemListarCarritoMis) {
        this.menuItemListarCarritoMis = menuItemListarCarritoMis;
    }

    public JMenuItem getMenuItemEspañol() {
        return menuItemEspañol;
    }

    public void setMenuItemEspañol(JMenuItem menuItemEspañol) {
        this.menuItemEspañol = menuItemEspañol;
    }

    public JMenuItem getMenuItemIngles() {
        return menuItemIngles;
    }

    public void setMenuItemIngles(JMenuItem menuItemIngles) {
        this.menuItemIngles = menuItemIngles;
    }

    public JMenuItem getMenuItemNoruego() {
        return menuItemNoruego;
    }

    public void setMenuItemNoruego(JMenuItem menuItemNoruego) {
        this.menuItemNoruego = menuItemNoruego;
    }

    public JMenuItem getMenuItemAñadirCarrito() {
        return menuItemAñadirCarrito;
    }

    public void setMenuItemAñadirCarrito(JMenuItem menuItemAñadirCarrito) {
        this.menuItemAñadirCarrito = menuItemAñadirCarrito;
    }

    public JMenuItem getMenuItemEliminarProducto() {
        return menuItemEliminarProducto;
    }

    public void setMenuItemEliminarProducto(JMenuItem menuItemEliminarProducto) {
        this.menuItemEliminarProducto = menuItemEliminarProducto;
    }

    public JMenuItem getMenuItemModificarProducto() {
        return menuItemModificarProducto;
    }

    public void setMenuItemModificarProducto(JMenuItem menuItemModificarProducto) {
        this.menuItemModificarProducto = menuItemModificarProducto;
    }

    public JMenuItem getMenuItemActualizarProducto() {
        return menuItemActualizarProducto;
    }

    public void setMenuItemActualizarProducto(JMenuItem menuItemActualizarProducto) {
        this.menuItemActualizarProducto = menuItemActualizarProducto;
    }

    public JMenuItem getMenuItemCargarProducto() {
        return menuItemCargarProducto;
    }

    public void setMenuItemCargarProducto(JMenuItem menuItemCargarProducto) {
        this.menuItemCargarProducto = menuItemCargarProducto;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public JMenuItem getMenuItemCerrarSesión() {
        return menuItemCerrarSesión;
    }

    public void setMenuItemCerrarSesión(JMenuItem menuItemCerrarSesión) {
        this.menuItemCerrarSesión = menuItemCerrarSesión;
    }

    public JMenuItem getMenuItemListarCarritos() {
        return menuItemListarCarritos;
    }

    public void setMenuItemListarCarritos(JMenuItem menuItemListarCarritos) {
        this.menuItemListarCarritos = menuItemListarCarritos;
    }

    public JMenu getMenuCarrito() {
        return menuCarrito;
    }

    public void setMenuCarrito(JMenu menuCarrito) {
        this.menuCarrito = menuCarrito;
    }

    public JMenu getMenuProducto() {
        return menuProducto;
    }

    public void setMenuProducto(JMenu menuProducto) {
        this.menuProducto = menuProducto;
    }

    public JMenu getMenuUsuario() {
        return menuUsuario;
    }

    public void setMenuUsuario(JMenu menuUsuario) {
        this.menuUsuario = menuUsuario;
    }

    public JMenuBar getMenubar() {
        return menubar;
    }

    public void setMenubar(JMenuBar menubar) {
        this.menubar = menubar;
    }

    public JMenu getMenuAdministrador() {
        return menuAdministrador;
    }

    public void setMenuAdministrador(JMenu menuAdministrador) {
        this.menuAdministrador = menuAdministrador;
    }

    public JMenuItem getMenuItemEliminarCarrito() {
        return menuItemEliminarCarrito;
    }

    public void setMenuItemEliminarCarrito(JMenuItem menuItemEliminarCarrito) {
        this.menuItemEliminarCarrito = menuItemEliminarCarrito;
    }

    public JMenuItem getMenuItemModificarCarrito() {
        return menuItemModificarCarrito;
    }

    public void setMenuItemModificarCarrito(JMenuItem menuItemModificarCarrito) {
        this.menuItemModificarCarrito = menuItemModificarCarrito;
    }

    public JMenuItem getMenuItemCrearUsuario() {
        return menuItemCrearUsuario;
    }

    public void setMenuItemCrearUsuario(JMenuItem menuItemCrearUsuario) {
        this.menuItemCrearUsuario = menuItemCrearUsuario;
    }

    public JMenuItem getMenuItemEliminarUsuario() {
        return menuItemEliminarUsuario;
    }

    public void setMenuItemEliminarUsuario(JMenuItem menuItemEliminarUsuario) {
        this.menuItemEliminarUsuario = menuItemEliminarUsuario;
    }

    public JMenuItem getMenuItemListarUsuarios() {
        return menuItemListarUsuarios;
    }

    public void setMenuItemListarUsuarios(JMenuItem menuItemListarUsuarios) {
        this.menuItemListarUsuarios = menuItemListarUsuarios;
    }

    public JMenuItem getMenuItemModificarUsuario() {
        return menuItemModificarUsuario;
    }

    public void setMenuItemModificarUsuario(JMenuItem menuItemModificarUsuario) {
        this.menuItemModificarUsuario = menuItemModificarUsuario;
    }

    public void desactivar() {
        getMenuProducto().setVisible(false);
        getMenuAdministrador().setVisible(false);
        getMenuItemEliminarCarrito().setEnabled(false);
        getMenuItemModificarCarrito().setEnabled(false);
        getMenuItemListarCarritos().setEnabled(false);
        getMenuItemEliminarProducto().setEnabled(false);
        getMenuItemModificarProducto().setEnabled(false);
        getMenuItemActualizarProducto().setEnabled(false);
        getMenuItemCargarProducto().setEnabled(false);
    }
}
