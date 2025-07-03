package ec.edu.ups.vista;

import ec.edu.ups.MiDesktop;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

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
    private MiDesktop desktop;
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
    private JMenuItem menuItemFrances;
    private MensajeInternacionalizacionHandler mensaje;

    public MenuPrincipalView(MensajeInternacionalizacionHandler mensaje ) {
        desktop = new MiDesktop(mensaje);
        menubar = new JMenuBar();
        menuCarrito = new JMenu();
        menuProducto = new JMenu();
        menuUsuario = new JMenu();
        menuAdministrador = new JMenu();
        menuIdiomas = new JMenu();

        menuItemEliminarProducto = new JMenuItem();
        menuItemModificarProducto = new JMenuItem();
        menuItemActualizarProducto = new JMenuItem();
        menuItemCargarProducto = new JMenuItem();
        menuItemAñadirCarrito = new JMenuItem();
        menuItemCerrarSesión = new JMenuItem();
        menuItemListarCarritos = new JMenuItem();
        menuItemEliminarCarrito = new JMenuItem();
        menuItemModificarCarrito = new JMenuItem();
        menuItemListarCarritoMis = new JMenuItem();
        menuItemCrearUsuario = new JMenuItem();
        menuItemEliminarUsuario = new JMenuItem();
        menuItemListarUsuarios = new JMenuItem();
        menuItemModificarUsuario = new JMenuItem();
        menuItemModificarMisUsuario = new JMenuItem();
        menuItemEspañol = new JMenuItem();
        menuItemIngles = new JMenuItem();
        menuItemFrances = new JMenuItem();

        actualizarTexto();

        menubar.add(menuUsuario);
        menuUsuario.add(menuItemModificarMisUsuario);
        menuUsuario.add(menuItemCerrarSesión);

        menubar.add(menuAdministrador);
        menuAdministrador.add(menuItemCrearUsuario);
        menuAdministrador.add(menuItemEliminarUsuario);
        menuAdministrador.add(menuItemListarUsuarios);
        menuAdministrador.add(menuItemModificarUsuario);

        menubar.add(menuProducto);
        menuProducto.add(menuItemCargarProducto);
        menuProducto.add(menuItemActualizarProducto);
        menuProducto.add(menuItemModificarProducto);
        menuProducto.add(menuItemEliminarProducto);


        menubar.add(menuCarrito);
        menuCarrito.add(menuItemAñadirCarrito);
        menuCarrito.add(menuItemListarCarritoMis);
        menuCarrito.add(menuItemListarCarritos);
        menuCarrito.add(menuItemEliminarCarrito);
        menuCarrito.add(menuItemModificarCarrito);

        menubar.add(menuIdiomas);
        menuIdiomas.add(menuItemEspañol);
        menuIdiomas.add(menuItemIngles);
        menuIdiomas.add(menuItemFrances);

        setJMenuBar(menubar);
        setContentPane(desktop);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actualizarTexto(){

        setTitle(mensaje.get("app.title"));
        desktop.actualizarTextos();

        menuProducto.setText(mensaje.get("menu.producto"));
        menuItemCargarProducto.setText(mensaje.get("menu.prod.crear"));
        menuItemEliminarProducto.setText(mensaje.get("menu.prod.eliminar"));
        menuItemModificarProducto.setText(mensaje.get("menu.prod.actualizar"));
        menuItemActualizarProducto.setText(mensaje.get("menu.prod.buscar"));

        menuCarrito.setText(mensaje.get("menu.carrito"));
        menuItemAñadirCarrito.setText(mensaje.get("menu.car.crear"));
        menuItemListarCarritos.setText(mensaje.get("menu.car.listar"));
        menuItemEliminarCarrito.setText(mensaje.get("menu.car.eliminar"));
        menuItemModificarCarrito.setText(mensaje.get("menu.car.actualizar"));
        menuItemListarCarritoMis.setText(mensaje.get("menu.car.listarMis"));

        menuAdministrador.setText(mensaje.get("menu.admin"));
        menuItemCrearUsuario.setText(mensaje.get("menu.usr.crear"));
        menuItemEliminarUsuario.setText(mensaje.get("menu.usr.eliminar"));
        menuItemListarUsuarios.setText(mensaje.get("menu.usr.listar"));
        menuItemModificarUsuario.setText(mensaje.get("menu.usr.modificar"));

        menuItemModificarMisUsuario.setText(mensaje.get("menu.usr.modificarMis"));
        menuItemCerrarSesión.setText(mensaje.get("menu.salir.cerrar"));

        menuIdiomas.setText(mensaje.get("menu.idiomas"));
        menuItemEspañol.setText(mensaje.get("menu.idioma.es"));
        menuItemIngles.setText(mensaje.get("menu.idioma.en"));
        menuItemFrances.setText(mensaje.get("menu.idioma.Fr"));

    }

    public JMenuItem getMenuItemModificarMisUsuario() {
        return menuItemModificarMisUsuario;
    }

    public void setMenuItemModificarMisUsuario(JMenuItem menuItemModificarMisUsuario) {
        this.menuItemModificarMisUsuario = menuItemModificarMisUsuario;
    }
    public MiDesktop getDesktop() {
        return desktop;
    }

    public void setDesktop(MiDesktop desktop) {
        this.desktop = desktop;
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

    public JMenuItem getMenuItemFrances() { return menuItemFrances; }

    public void setMenuItemFrances(JMenuItem menuItemFrances) { this.menuItemFrances = menuItemFrances; }

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
