package ec.edu.ups.dao.impl.DAOProducto;

import ec.edu.ups.dao.ProductoDAO;
import ec.edu.ups.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOArchivoTexto implements ProductoDAO {

    private String rutaArchivo;

    public ProductoDAOArchivoTexto(String rutaBase) {
        File directorio = new File(rutaBase, "CarritoCompras");
        if (!directorio.exists() && !directorio.mkdirs()) {
            System.out.println("Error al crear directorio: " + directorio.getAbsolutePath());
        }
        this.rutaArchivo = new File(directorio, "productos.txt").getAbsolutePath();

        inicializarArchivoConProductosEjemplo();
    }

    private void inicializarArchivoConProductosEjemplo() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
                // Insertar 10 productos ejemplo
                bw.write("1 | Cámara | 350.0\n");
                bw.write("2 | Smartwatch | 220.0\n");
                bw.write("3 | Altavoz Bluetooth | 180.0\n");
                bw.write("4 | Disco Duro | 90.0\n");
                bw.write("5 | Router WiFi | 130.0\n");
                bw.write("6 | Drone | 800.0\n");
                bw.write("7 | Consola | 400.0\n");
                bw.write("8 | Proyector | 270.0\n");
                bw.write("9 | Lámpara LED | 60.0\n");
                bw.write("10 | Teclado Mecánico | 110.0\n");
            } catch (IOException e) {
                System.out.println("Error al crear archivo de productos con ejemplos: " + e.getMessage());
            }
        }
    }

    @Override
    public void crear(Producto producto) {
        if (producto == null || producto.getCodigo() <= 0) {
            System.out.println("Producto inválido.");
            return;
        }
        // Validar código único
        if (buscarPorCodigo(producto.getCodigo()) != null) {
            System.out.println("Código ya existente. Por favor, ingrese otro.");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            bw.write(formatearProducto(producto));
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al crear producto: " + e.getMessage());
        }
    }

    @Override
    public Producto buscarPorCodigo(int codigo) {
        if (codigo <= 0) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Producto p = parsearProducto(linea);
                if (p != null && p.getCodigo() == codigo) {
                    return p;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al buscar producto por código: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombreBuscado) {
        List<Producto> productos = new ArrayList<>();
        if (nombreBuscado == null) return productos;
        String nombreBuscadoTrim = nombreBuscado.trim().toLowerCase();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Producto p = parsearProducto(linea);
                if (p != null && p.getNombre().toLowerCase().contains(nombreBuscadoTrim)) {
                    productos.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al buscar productos por nombre: " + e.getMessage());
        }
        return productos;
    }

    @Override
    public void actualizar(Producto producto) {
        if (producto == null || producto.getCodigo() <= 0) return;

        List<Producto> productos = listarTodos();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, false))) {
            for (Producto p : productos) {
                if (p.getCodigo() == producto.getCodigo()) {
                    bw.write(formatearProducto(producto));
                } else {
                    bw.write(formatearProducto(p));
                }
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar producto: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int codigo) {
        if (codigo <= 0) return;

        List<Producto> productos = listarTodos();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, false))) {
            for (Producto p : productos) {
                if (p.getCodigo() != codigo) {
                    bw.write(formatearProducto(p));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Producto p = parsearProducto(linea);
                if (p != null) {
                    productos.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return productos;
    }

    // Helper: convierte Producto a línea texto
    private String formatearProducto(Producto p) {
        return p.getCodigo() + "|" + p.getNombre() + "|" + p.getPrecio();
    }

    // Helper: convierte línea texto a Producto
    private Producto parsearProducto(String linea) {
        if (linea == null || linea.isEmpty()) return null;
        String[] partes = linea.split("\\|");
        if (partes.length != 3) return null;

        try {
            int codigo = Integer.parseInt(partes[0].trim());
            String nombre = partes[1].trim();
            double precio = Double.parseDouble(partes[2].trim());
            return new Producto(codigo, nombre, precio);
        } catch (NumberFormatException e) {
            System.out.println("Error al parsear producto: " + e.getMessage());
            return null;
        }
    }
}
