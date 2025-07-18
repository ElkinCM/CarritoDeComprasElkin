package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.CarritoDAO;
import ec.edu.ups.modelo.Carrito;
import ec.edu.ups.modelo.Producto;
import ec.edu.ups.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Implementación de {@link CarritoDAO} que almacena datos en un archivo de texto plano.
 * Cada carrito se guarda como una línea en el archivo, serializado en un formato específico.
 */
public class CarritoDAOArchivoTexto implements CarritoDAO {
    private String rutaArchivo;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    /**
     * Constructor que inicializa la ruta y crea los archivos necesarios si no existen.
     *
     * @param rutaInicial Ruta base donde se creará el archivo "CarritoCompras/carrito.txt".
     */
    public CarritoDAOArchivoTexto(String rutaInicial) {
        File archivo = new File(rutaInicial + "\\CarritoCompras");
        if (!archivo.exists()) {
            archivo.mkdirs();
        }
        this.rutaArchivo = archivo.getAbsolutePath() + "\\carrito.txt";
        File archivo2 = new File(this.rutaArchivo);
        if (!archivo2.exists()) {
            try {
                archivo2.createNewFile();
            } catch (IOException e) {
                System.out.println("No se pudo crear el archivo " + e.getMessage());
            }
        }
    }
    /**
     * Guarda un nuevo carrito en el archivo.
     *
     * @param carrito El carrito que se va a guardar.
     */
    @Override
    public void crear(Carrito carrito) {
        try {
            abrirWriter(true);
            bufferedWriter.write(carrito.toString());
            bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.println("No se pudo crear el carrito " + e.getMessage());
        } finally {
            cerrarWriter();
        }
    }
    /**
     * Busca un carrito por su código.
     *
     * @param codigo Código del carrito.
     * @return El carrito si se encuentra, o null si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        try {
            abrirReader();
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                Carrito carrito = CarritoString(linea, null);
                if (carrito != null && carrito.getCodigo() == codigo) {
                    return carrito;
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo buscar el carrito " + e.getMessage());
        } finally {
            cerrarReader();
        }
        return null;
    }
    /**
     * Actualiza los datos de un carrito existente.
     *
     * @param carrito El carrito actualizado.
     */
    @Override
    public void actualizar(Carrito carrito) {
        List<Carrito> carritos = listarTodos();
        try {
            abrirWriter(false);
            for (Carrito c : carritos) {
                if (c.getCodigo() == carrito.getCodigo()) {
                    c = carrito;
                }
                bufferedWriter.write(c.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("No se pudo actualizar el carrito " + e.getMessage());
        } finally {
            cerrarWriter();
        }
    }
    /**
     * Elimina un carrito según su código.
     *
     * @param codigo Código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        List<Carrito> carritos = listarTodos();
        try {
            abrirWriter(false);
            for (Carrito c : carritos) {
                if (c.getCodigo() != codigo) {
                    bufferedWriter.write(c.toString());
                    bufferedWriter.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo eliminar el carrito " + e.getMessage());
        } finally {
            cerrarWriter();
        }
    }
    /**
     * Lista todos los carritos almacenados en el archivo.
     *
     * @return Lista de todos los carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        List<Carrito> carritos = new ArrayList<>();
        try {
            abrirReader();
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                Carrito carrito = CarritoString(linea, null);
                if (carrito != null) {
                    carritos.add(carrito);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo listar los carritos " + e.getMessage());
        } finally {
            cerrarReader();
        }
        return carritos;
    }
    /**
     * Busca todos los carritos pertenecientes a un usuario específico.
     *
     * @param usuario Usuario del cual se desea obtener los carritos.
     * @return Lista de carritos asociados al usuario.
     */
    @Override
    public List<Carrito> buscarPorUsuario(Usuario usuario) {
        List<Carrito> carritos = new ArrayList<>();
        try {
            abrirReader();
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                Carrito carrito = CarritoString(linea, usuario);
                if (carrito != null && carrito.getUsuario() != null &&
                        carrito.getUsuario().getUsername().equals(usuario.getUsername())) {
                    carritos.add(carrito);
                }
            }
        } catch (IOException e) {
            System.out.println("No se pudo buscar carritos por usuario " + e.getMessage());
        } finally {
            cerrarReader();
        }
        return carritos;
    }
    /**
     * Convierte una línea de texto del archivo a un objeto Carrito.
     *
     * @param s       Línea del archivo.
     * @param usuario Usuario opcional para asociar directamente al carrito.
     * @return Objeto Carrito representado por la línea.
     */
    private Carrito CarritoString(String s, Usuario usuario) {
        String[] partes = s.split("_");
        if (partes.length < 4) {
            return null;
        }
        Carrito carrito;
        if (usuario != null) {
            carrito = new Carrito(usuario);
        } else {
            carrito = new Carrito();
            carrito.setUsuario(new Usuario(partes[1]));
        }
        int codigo = Integer.parseInt(partes[0]);
        carrito.setCodigo(codigo);
        carrito.setUsuario(new Usuario(partes[1]));
        long fechaMillis = Long.parseLong(partes[2]);
        String itemsStr = partes[3];

        if (itemsStr.startsWith("[") && itemsStr.endsWith("]")) {
            itemsStr = itemsStr.substring(1, itemsStr.length() - 1);
        }

        if (!itemsStr.isEmpty()) {
            String[] itemArr = itemsStr.split(",");
            for (String item : itemArr) {
                if (!item.isEmpty()) {
                    String[] itemPartes = item.split(";");
                    if (itemPartes.length == 4) {
                        int prodCodigo = Integer.parseInt(itemPartes[0].trim());
                        String prodNombre = itemPartes[1];
                        double prodPrecio = Double.parseDouble(itemPartes[2].trim());
                        int cantidad = Integer.parseInt(itemPartes[3].trim());
                        Producto producto = new Producto(prodCodigo, prodNombre, prodPrecio);
                        carrito.agregarProducto(producto, cantidad);
                    }
                }
            }
        }

        GregorianCalendar fechaCreacion = new GregorianCalendar();
        fechaCreacion.setTimeInMillis(fechaMillis);
        carrito.setFecha(fechaCreacion);
        return carrito;
    }
    /**
     * Abre el archivo para escritura.
     *
     * @param append true para añadir al final del archivo, false para sobrescribir.
     */
    private void abrirWriter(boolean append) {
        try {
            fileWriter = new FileWriter(rutaArchivo, append);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            System.out.println("Error al abrir el archivo para escritura: " + e.getMessage());
            bufferedWriter = null;
            fileWriter = null;
        }
    }
    /**
     * Cierra los flujos de escritura del archivo.
     */
    private void cerrarWriter() {
        try {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar el archivo de escritura: " + e.getMessage());
        }
    }
    /**
     * Abre el archivo para lectura.
     */
    private void abrirReader() {
        try {
            fileReader = new FileReader(rutaArchivo);
            bufferedReader = new BufferedReader(fileReader);
        } catch (IOException e) {
            System.out.println("Error al abrir el archivo para lectura: " + e.getMessage());
            bufferedReader = null;
            fileReader = null;
        }
    }
    /**
     * Cierra los flujos de lectura del archivo.
     */
    private void cerrarReader() {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileReader != null) {
                fileReader.close();
            }
        } catch (IOException e) {
            System.out.println("Error al cerrar el archivo de lectura: " + e.getMessage());
        }
    }
}
