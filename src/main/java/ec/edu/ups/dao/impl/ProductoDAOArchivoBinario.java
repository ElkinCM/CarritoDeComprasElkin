package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
/**
 * Implementación de {@link ProductoDAO} que almacena los productos
 * en un archivo binario con acceso aleatorio.
 * Cada producto contiene un código (int), un nombre (String de longitud fija)
 * y un precio (double).
 */
public class ProductoDAOArchivoBinario implements ProductoDAO {

    private final String rutaArchivo;
    private static final int LONGITUD_NOMBRE = 20;
    private static final int BYTES_POR_PRODUCTO = Integer.BYTES + (LONGITUD_NOMBRE * Character.BYTES) + Double.BYTES;
    /**
     * Constructor que inicializa el DAO y crea el archivo con productos de ejemplo si no existe.
     *
     * @param rutaBase Ruta base del directorio donde se almacenarán los archivos.
     */
    public ProductoDAOArchivoBinario(String rutaBase) {
        File directorio = new File(rutaBase, "CarritoCompras");
        if (!directorio.exists() && !directorio.mkdirs()) {
            System.out.println("Error al crear directorio: " + directorio.getAbsolutePath());
        }
        this.rutaArchivo = new File(directorio, "productos.dat").getAbsolutePath();

        inicializarArchivoConProductosEjemplo();
    }

    /**
     * Inicializa el archivo con 10 productos de ejemplo si no existe.
     */
    private void inicializarArchivoConProductosEjemplo() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
                crear(raf, new Producto("Cámara", 350.0));
                crear(raf, new Producto("Smartwatch", 220.0));
                crear(raf, new Producto("Altavoz Bluetooth", 180.0));
                crear(raf, new Producto("Disco Duro", 90.0));
                crear(raf, new Producto("Router WiFi", 130.0));
                crear(raf, new Producto("Drone", 800.0));
                crear(raf, new Producto("Consola", 400.0));
                crear(raf, new Producto("Proyector", 270.0));
                crear(raf, new Producto("Lámpara LED", 60.0));
                crear(raf, new Producto("Teclado Mecánico", 110.0));
            } catch (IOException e) {
                System.out.println("Error al crear archivo de productos con ejemplos: " + e.getMessage());
            }
        }
    }
    /**
     * Crea un nuevo producto en el archivo binario.
     *
     * @param producto Producto a registrar.
     */
    @Override
    public void crear(Producto producto) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            raf.seek(raf.length());
            crear(raf, producto);
        } catch (IOException e) {
            System.out.println("Error al crear producto: " + e.getMessage());
        }
    }
    /**
     * Escribe un producto en el archivo.
     *
     * @param raf      Archivo de acceso aleatorio.
     * @param producto Producto a escribir.
     * @throws IOException En caso de error de escritura.
     */
    private void crear(RandomAccessFile raf, Producto producto) throws IOException {
        raf.writeInt(producto.getCodigo());
        String nombre = producto.getNombre();
        if (nombre.length() > LONGITUD_NOMBRE) {
            nombre = nombre.substring(0, LONGITUD_NOMBRE);
        }
        nombre = String.format("%-" + LONGITUD_NOMBRE + "s", nombre);
        raf.writeChars(nombre);
        raf.writeDouble(producto.getPrecio());
    }
    /**
     * Busca un producto por su código.
     *
     * @param codigo Código del producto.
     * @return El producto correspondiente o {@code null} si no se encuentra.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        if (codigo <= 0) return null;

        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            long numRegistros = raf.length() / BYTES_POR_PRODUCTO;
            if (codigo > numRegistros) return null;

            raf.seek((codigo - 1) * BYTES_POR_PRODUCTO);
            int codigoLeido = raf.readInt();
            String nombre = leerNombre(raf);
            double precio = raf.readDouble();

            if (codigoLeido == codigo) {
                return new Producto(codigoLeido, nombre, precio);
            }
        } catch (IOException e) {
            System.out.println("Error al buscar producto por código: " + e.getMessage());
        }
        return null;
    }
    /**
     * Lee el nombre del producto desde el archivo.
     *
     * @param raf Archivo de acceso aleatorio.
     * @return Nombre del producto.
     * @throws IOException Si ocurre un error de lectura.
     */
    private String leerNombre(RandomAccessFile raf) throws IOException {
        StringBuilder sb = new StringBuilder(LONGITUD_NOMBRE);
        for (int i = 0; i < LONGITUD_NOMBRE; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }
    /**
     * Busca productos por su nombre (ignora mayúsculas y minúsculas).
     *
     * @param nombreBuscado Nombre del producto a buscar.
     * @return Lista de productos que coinciden exactamente con el nombre.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombreBuscado) {
        List<Producto> productos = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            long numRegistros = raf.length() / BYTES_POR_PRODUCTO;
            for (int i = 0; i < numRegistros; i++) {
                raf.seek(i * BYTES_POR_PRODUCTO);
                int codigo = raf.readInt();
                String nombre = leerNombre(raf);
                double precio = raf.readDouble();

                if (codigo != 0 && nombre.equalsIgnoreCase(nombreBuscado.trim())) {
                    productos.add(new Producto(codigo, nombre, precio));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al buscar productos por nombre: " + e.getMessage());
        }
        return productos;
    }
    /**
     * Actualiza un producto existente en el archivo binario.
     *
     * @param producto Producto actualizado.
     */
    @Override
    public void actualizar(Producto producto) {
        if (producto.getCodigo() <= 0) return;

        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            long numRegistros = raf.length() / BYTES_POR_PRODUCTO;
            if (producto.getCodigo() > numRegistros) return;

            raf.seek((producto.getCodigo() - 1) * BYTES_POR_PRODUCTO);
            raf.writeInt(producto.getCodigo());

            String nombre = producto.getNombre();
            if (nombre.length() > LONGITUD_NOMBRE) {
                nombre = nombre.substring(0, LONGITUD_NOMBRE);
            }
            nombre = String.format("%-" + LONGITUD_NOMBRE + "s", nombre);
            raf.writeChars(nombre);
            raf.writeDouble(producto.getPrecio());
        } catch (IOException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
        }
    }
    /**
     * Elimina lógicamente un producto del archivo estableciendo su código en 0.
     *
     * @param codigo Código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        if (codigo <= 0) return;

        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            long numRegistros = raf.length() / BYTES_POR_PRODUCTO;
            if (codigo > numRegistros) return;

            raf.seek((codigo - 1) * BYTES_POR_PRODUCTO);
            raf.writeInt(0); // Marca registro como eliminado
            raf.writeChars(String.format("%-" + LONGITUD_NOMBRE + "s", ""));
            raf.writeDouble(0.0);
        } catch (IOException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }

    /**
     * Lista todos los productos que no han sido eliminados (código distinto de 0).
     *
     * @return Lista de productos activos.
     */
    @Override
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            long numRegistros = raf.length() / BYTES_POR_PRODUCTO;
            for (int i = 0; i < numRegistros; i++) {
                raf.seek(i * BYTES_POR_PRODUCTO);
                int codigo = raf.readInt();
                String nombre = leerNombre(raf);
                double precio = raf.readDouble();

                if (codigo != 0) {
                    productos.add(new Producto(codigo, nombre, precio));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return productos;
    }
}
