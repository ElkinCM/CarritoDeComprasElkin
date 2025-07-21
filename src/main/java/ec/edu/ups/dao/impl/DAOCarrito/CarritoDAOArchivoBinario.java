package ec.edu.ups.dao.impl.DAOCarrito;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.ItemCarrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class CarritoDAOArchivoBinario implements CarritoDAO {

    private final String rutaArchivo;
    private static final int LONGITUD_USERNAME = 20;
    private static final int LONGITUD_NOMBRE_PROD = 30;

    public CarritoDAOArchivoBinario(String rutaBase) {
        File carpeta = new File(rutaBase, "CarritoCompras");
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        rutaArchivo = new File(carpeta, "carrito.dat").getAbsolutePath();
    }

    @Override
    public void crear(Carrito carrito) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            raf.seek(raf.length());
            escribirCarrito(raf, carrito);
        } catch (IOException e) {
            System.out.println("Error al crear carrito: " + e.getMessage());
        }
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            while (true) {
                Carrito carrito = leerCarrito(raf);
                if (carrito == null) break; // fin de archivo
                if (carrito.getCodigo() == codigo) {
                    return carrito;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al buscar carrito: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void actualizar(Carrito carrito) {
        List<Carrito> carritos = listarTodos();
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            raf.setLength(0);
            for (Carrito c : carritos) {
                if (c.getCodigo() == carrito.getCodigo()) {
                    escribirCarrito(raf, carrito);
                } else {
                    escribirCarrito(raf, c);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar carrito: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int codigo) {
        List<Carrito> carritos = listarTodos();
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            raf.setLength(0);
            for (Carrito c : carritos) {
                if (c.getCodigo() != codigo) {
                    escribirCarrito(raf, c);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al eliminar carrito: " + e.getMessage());
        }
    }

    @Override
    public List<Carrito> listarTodos() {
        List<Carrito> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                lista.add(leerCarrito(raf));
            }
        } catch (IOException e) {
            System.out.println("Error al listar carritos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                Carrito carrito = leerCarrito(raf);
                if (carrito.getUsuario().getUsername().equals(usuario.getUsername())) {
                    lista.add(carrito);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al buscar carritos por usuario: " + e.getMessage());
        }
        return lista;
    }

    private void escribirCarrito(RandomAccessFile raf, Carrito carrito) throws IOException {
        raf.writeInt(carrito.getCodigo());

        String username = String.format("%-" + LONGITUD_USERNAME + "s", carrito.getUsuario().getUsername());
        raf.writeChars(username.substring(0, LONGITUD_USERNAME));

        raf.writeLong(carrito.getFechaCrear().getTime());

        List<ItemCarrito> items = carrito.obtenerItems();
        raf.writeInt(items.size());

        for (ItemCarrito item : items) {
            Producto p = item.getProducto();
            raf.writeInt(p.getCodigo());

            String nombre = String.format("%-" + LONGITUD_NOMBRE_PROD + "s", p.getNombre());
            raf.writeChars(nombre.substring(0, LONGITUD_NOMBRE_PROD));

            raf.writeDouble(p.getPrecio());
            raf.writeInt(item.getCantidad());
        }
    }

    private Carrito leerCarrito(RandomAccessFile raf) throws IOException {
        try {
            int codigo = raf.readInt();

            StringBuilder userBuilder = new StringBuilder();
            for (int i = 0; i < LONGITUD_USERNAME; i++) {
                userBuilder.append(raf.readChar());
            }
            String username = userBuilder.toString().trim();

            long fechaMillis = raf.readLong();
            GregorianCalendar fecha = new GregorianCalendar();
            fecha.setTimeInMillis(fechaMillis);

            Usuario usuario = new Usuario(username);
            Carrito carrito = new Carrito(usuario);
            carrito.setCodigo(codigo);
            carrito.setFecha(fecha);

            int cantidadItems = raf.readInt();
            for (int i = 0; i < cantidadItems; i++) {
                int codProducto = raf.readInt();

                StringBuilder nombreBuilder = new StringBuilder();
                for (int j = 0; j < LONGITUD_NOMBRE_PROD; j++) {
                    nombreBuilder.append(raf.readChar());
                }
                String nombre = nombreBuilder.toString().trim();

                double precio = raf.readDouble();
                int cantidad = raf.readInt();

                Producto producto = new Producto(codProducto, nombre, precio);
                carrito.agregarProducto(producto, cantidad);
            }

            return carrito;
        } catch (EOFException e) {
            // Llegamos al final del archivo, no hay más objetos
            return null;
        }
    }


}
