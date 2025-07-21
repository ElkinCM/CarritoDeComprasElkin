package ec.edu.ups.dao.impl.DAOUsuario;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.*;

import java.io.*;
import java.util.*;

public class UsuarioDAOArchivoBinario implements UsuarioDAO {

    private final String rutaArchivo;

    public UsuarioDAOArchivoBinario(String rutaBase) {
        File directorio = new File(rutaBase, "CarritoCompras");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        this.rutaArchivo = new File(directorio, "usuarios.bin").getAbsolutePath();

        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                crear(new Usuario("0107113474", Rol.ADMINISTRADOR, "M@ura1", "Administrador", "admin@dominio.com", "1234567890", null));
            } catch (Exception e) {
                System.err.println("Error creando usuario por defecto: " + e.getMessage());
            }
        }
    }

    @Override
    public void crear(Usuario usuario) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            raf.seek(raf.length()); // Ir al final del archivo
            escribirUsuario(raf, usuario);
        } catch (IOException e) {
            System.out.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    private void escribirUsuario(RandomAccessFile raf, Usuario u) throws IOException {
        escribirCadena(raf, u.getUsername());
        escribirCadena(raf, u.getContrasenia());
        escribirCadena(raf, u.getRol().name());
        escribirCadena(raf, u.getNombre());
        escribirCadena(raf, u.getTelefono());
        escribirCadena(raf, u.getCorreo());

        raf.writeLong(u.getFechaNacimientoCalendar().getTimeInMillis());

        // Carritos
        List<Carrito> carritos = u.getCarritos();
        raf.writeInt(carritos.size());
        for (Carrito c : carritos) {
            raf.writeInt(c.getCodigo());
            escribirCadena(raf, c.getUsuario().getUsername());
            raf.writeLong(u.getFechaNacimientoCalendar().getTimeInMillis());

            List<ItemCarrito> items = c.obtenerItems();
            raf.writeInt(items.size());
            for (ItemCarrito item : items) {
                Producto p = item.getProducto();
                raf.writeInt(p.getCodigo());
                escribirCadena(raf, p.getNombre());
                raf.writeDouble(p.getPrecio());
                raf.writeInt(item.getCantidad());
            }
        }

        // Preguntas de seguridad
        List<RespuestaSegu> preguntas = u.getRespuestaSegu();
        raf.writeInt(preguntas.size());
        for (RespuestaSegu pr : preguntas) {
            Pregunta p = pr.getPregunta();
            raf.writeInt(p.getId());
            escribirCadena(raf, p.getTexto());
            escribirCadena(raf, pr.getRespuesta());
        }
    }

    private void escribirCadena(RandomAccessFile raf, String valor) throws IOException {
        byte[] datos = valor.getBytes("UTF-8");
        raf.writeInt(datos.length);
        raf.write(datos);
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario u : listarTodos()) {
            if (u.getUsername().equals(username) && u.getContrasenia().equals(contrasenia)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public Usuario buscarPorUsuario(String username) {
        for (Usuario u : listarTodos()) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = listarTodos();
        usuarios.removeIf(u -> u.getUsername().equals(username));
        guardarTodos(usuarios);
    }

    @Override
    public void actualizar(Usuario usuario) {
        List<Usuario> usuarios = listarTodos();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuario.getUsername())) {
                usuarios.set(i, usuario);
                break;
            }
        }
        guardarTodos(usuarios);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return listarRol(Rol.USUARIO);
    }

    @Override
    public List<Usuario> listarAdministradores() {
        return listarRol(Rol.ADMINISTRADOR);
    }

    @Override
    public List<Usuario> listarRol(Rol rol) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario u : listarTodos()) {
            if (u.getRol() == rol) {
                resultado.add(u);
            }
        }
        return resultado;
    }

    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();

        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                usuarios.add(leerUsuario(raf));
            }
        } catch (IOException e) {
            System.out.println("Error al leer usuarios: " + e.getMessage());
        }

        return usuarios;
    }

    private Usuario leerUsuario(RandomAccessFile raf) throws IOException {
        String username = leerCadena(raf);
        String password = leerCadena(raf);
        Rol rol = Rol.valueOf(leerCadena(raf));
        String nombre = leerCadena(raf);
        String telefono = leerCadena(raf);
        String correo = leerCadena(raf);
        long fechaMillis = raf.readLong();
        GregorianCalendar fechaNacimiento = new GregorianCalendar();
        fechaNacimiento.setTimeInMillis(fechaMillis);

        Usuario usuario = new Usuario(username, rol, password, nombre, correo, telefono, fechaNacimiento);

        int cantCarritos = raf.readInt();
        for (int i = 0; i < cantCarritos; i++) {
            int cod = raf.readInt();
            String userCarrito = leerCadena(raf);
            long fechaCarrito = raf.readLong();

            Carrito carrito = new Carrito(usuario);
            carrito.setCodigo(cod);

            GregorianCalendar fecha = new GregorianCalendar();
            fecha.setTimeInMillis(fechaCarrito);
            carrito.setFecha(fecha);

            carrito.setUsuario(usuario);

            int cantItems = raf.readInt();
            for (int j = 0; j < cantItems; j++) {
                int codProd = raf.readInt();
                String nombreProd = leerCadena(raf);
                double precioProd = raf.readDouble();
                int cantidad = raf.readInt();

                Producto prod = new Producto(codProd, nombreProd, precioProd);
                carrito.obtenerItems().add(new ItemCarrito(prod, cantidad));
            }

            usuario.getCarritos().add(carrito);
        }

        int cantPreguntas = raf.readInt();
        for (int i = 0; i < cantPreguntas; i++) {
            int id = raf.readInt();
            String texto = leerCadena(raf);
            String resp = leerCadena(raf);

            Pregunta p = new Pregunta(id, texto);
            usuario.getRespuestaSegu().add(new RespuestaSegu(p, resp));
        }

        return usuario;
    }

    private String leerCadena(RandomAccessFile raf) throws IOException {
        int longitud = raf.readInt();
        byte[] datos = new byte[longitud];
        raf.readFully(datos);
        return new String(datos, "UTF-8");
    }

    private void guardarTodos(List<Usuario> usuarios) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            raf.setLength(0); // borrar contenido previo
            for (Usuario u : usuarios) {
                escribirUsuario(raf, u);
            }
        } catch (IOException e) {
            System.out.println("Error al sobrescribir archivo binario: " + e.getMessage());
        }
    }
}
