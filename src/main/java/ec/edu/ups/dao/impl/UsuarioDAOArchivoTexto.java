package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.UsuarioDAO;
import ec.edu.ups.modelo.*;

import java.io.*;
import java.util.*;

public class UsuarioDAOArchivoTexto implements UsuarioDAO {

    private final String rutaArchivo;

    public UsuarioDAOArchivoTexto(String rutaBase) {
        File directorio = new File(rutaBase, "CarritoCompras");
        if (!directorio.exists() && !directorio.mkdirs()) {
            System.out.println("Error al crear el directorio: " + directorio.getAbsolutePath());
        }
        this.rutaArchivo = new File(directorio, "usuarios.txt").getAbsolutePath();

        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                Usuario adminEjemplo = new Usuario("0107113474", Rol.ADMINISTRADOR, "M@ura1", "Elkin Chamba",
                        "elkinmaura@gmail.com", "0998610456",
                        new GregorianCalendar(2006, GregorianCalendar.NOVEMBER, 15));
                crear(adminEjemplo);
            } catch (IOException e) {
                System.out.println("Error al crear el archivo de usuarios: " + e.getMessage());
            }
        }
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
    public void crear(Usuario usuario) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            bw.write(usuarioToLinea(usuario));
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
        }
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
    public List<Usuario> listarAdministradores() {
        return listarRol(Rol.ADMINISTRADOR);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return listarRol(Rol.USUARIO);
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
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null && !linea.trim().isEmpty()) {
                usuarios.add(lineaToUsuario(linea));
            }
        } catch (IOException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    private void guardarTodos(List<Usuario> usuarios) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, false))) {
            for (Usuario u : usuarios) {
                bw.write(usuarioToLinea(u));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    private String usuarioToLinea(Usuario usuario) {
        String linea = usuario.getUsername() + "," +
                usuario.getContrasenia() + "," +
                usuario.getRol().name() + "," +
                usuario.getNombre() + "," +
                usuario.getTelefono() + "," +
                usuario.getCorreo() + "," +
                usuario.getFechaNacimientoCalendar().getTimeInMillis();

        // Carritos
        linea += "[";
        List<Carrito> carritos = usuario.getCarritos();
        for (int i = 0; i < carritos.size(); i++) {
            linea += carritoToString(carritos.get(i));
            if (i < carritos.size() - 1) linea += ",";
        }
        linea += "],";

        // Preguntas de seguridad
        linea += "[";
        List<RespuestaSegu> preguntas = usuario.getRespuestaSegu();
        for (int i = 0; i < preguntas.size(); i++) {
            RespuestaSegu pr = preguntas.get(i);
            linea += pr.getPregunta().getId() + ":" +
                    pr.getPregunta().getTexto() + ":" +
                    pr.getRespuesta();
            if (i < preguntas.size() - 1) linea += ",";
        }
        linea += "]";
        return linea;
    }

    private Usuario lineaToUsuario(String linea) {
        String[] partes = separarUsuarioLinea(linea);
        String[] atributos = partes[0].split(",");

        String username = atributos[0];
        String password = atributos[1];
        Rol rol = Rol.valueOf(atributos[2]);
        String nombre = atributos[3];
        String telefono = atributos[4];
        String correo = atributos[5];
        GregorianCalendar fechaNacimiento = new GregorianCalendar();
        fechaNacimiento.setTimeInMillis(Long.parseLong(atributos[6]));

        Usuario usuario = new Usuario(username, rol, password, nombre, correo, telefono, fechaNacimiento);

        // Carritos
        String carritosRaw = partes[1];
        if (carritosRaw != null && !carritosRaw.equals("[]")) {
            carritosRaw = carritosRaw.substring(1, carritosRaw.length() - 1);
            if (!carritosRaw.isEmpty()) {
                String[] carritosArr = carritosRaw.split(",(?=\\d+_)");
                for (String cStr : carritosArr) {
                    Carrito c = carritoDeString(cStr.trim(), usuario);
                    if (c != null) usuario.getCarritos().add(c);
                }
            }
        }

        // Preguntas seguridad
        String preguntasRaw = partes[2];
        if (preguntasRaw != null && !preguntasRaw.equals("[]")) {
            preguntasRaw = preguntasRaw.substring(1, preguntasRaw.length() - 1);
            if (!preguntasRaw.isEmpty()) {
                String[] preguntasArr = preguntasRaw.split(",");
                for (String pStr : preguntasArr) {
                    String[] pPartes = pStr.split(":");
                    if (pPartes.length >= 3) {
                        try {
                            int codigo = Integer.parseInt(pPartes[0].trim());
                            String texto = pPartes[1].trim();
                            for (int i = 2; i < pPartes.length - 1; i++) {
                                texto += ":" + pPartes[i].trim();
                            }
                            String respuesta = pPartes[pPartes.length - 1].trim();
                            Pregunta pregunta = new Pregunta(codigo, texto);
                            RespuestaSegu pr = new RespuestaSegu(pregunta, respuesta);
                            usuario.getRespuestaSegu().add(pr);
                        } catch (NumberFormatException e) {
                            System.out.println("Error parseando pregunta respondida: " + e.getMessage());
                        }
                    }
                }
            }
        }

        return usuario;
    }

    private String carritoToString(Carrito carrito) {
        String linea = carrito.getCodigo() + "_" +
                carrito.getUsuario().getUsername() + "_" +
                carrito.getFechaCrear().getTime() + "_[";

        List<ItemCarrito> items = carrito.obtenerItems();
        for (int i = 0; i < items.size(); i++) {
            ItemCarrito item = items.get(i);
            Producto p = item.getProducto();
            linea += p.getCodigo() + ";" + p.getNombre() + ";" + p.getPrecio() + ";" + item.getCantidad();
            if (i < items.size() - 1) linea += ",";
        }

        linea += "]";
        return linea;
    }

    private Carrito carritoDeString(String s, Usuario usuario) {
        String[] partes = s.split("_", 4);
        if (partes.length < 4) return null;

        int codigo = Integer.parseInt(partes[0]);
        String usernameCarrito = partes[1];
        long fechaMillis = Long.parseLong(partes[2]);
        String itemsStr = partes[3];

        Carrito carrito = new Carrito(usuario);
        carrito.setCodigo(codigo);
        carrito.setUsuario(usuario != null ? usuario : new Usuario(usernameCarrito));
        GregorianCalendar fecha = new GregorianCalendar();
        fecha.setTimeInMillis(fechaMillis);
        carrito.setFecha(fecha);

        if (itemsStr.startsWith("[") && itemsStr.endsWith("]")) {
            itemsStr = itemsStr.substring(1, itemsStr.length() - 1);
        }

        if (!itemsStr.isEmpty()) {
            String[] items = itemsStr.split(",");
            for (String item : items) {
                String[] itemParts = item.split(";");
                if (itemParts.length == 4) {
                    try {
                        int prodCodigo = Integer.parseInt(itemParts[0].trim());
                        String prodNombre = itemParts[1].trim();
                        double prodPrecio = Double.parseDouble(itemParts[2].trim());
                        int cantidad = Integer.parseInt(itemParts[3].trim());
                        Producto prod = new Producto(prodCodigo, prodNombre, prodPrecio);
                        carrito.obtenerItems().add(new ItemCarrito(prod, cantidad));
                    } catch (NumberFormatException e) {
                        System.out.println("Error parseando producto en carrito: " + e.getMessage());
                    }
                }
            }
        }

        return carrito;
    }

    private String[] separarUsuarioLinea(String linea) {
        int primerCorchete = linea.indexOf('[');
        int segundoCorchete = -1;
        if (primerCorchete != -1) {
            segundoCorchete = linea.indexOf('[', primerCorchete + 1);
        }

        String atributosStr = primerCorchete != -1 ? linea.substring(0, primerCorchete) : linea;
        String carritosStr = "[]";
        String preguntasStr = "[]";

        if (primerCorchete != -1) {
            if (segundoCorchete != -1) {
                int finCarritos = linea.indexOf("],", primerCorchete);
                if (finCarritos == -1) finCarritos = linea.indexOf("]", primerCorchete);
                else finCarritos += 1;

                carritosStr = linea.substring(primerCorchete, finCarritos + 1);
                preguntasStr = linea.substring(segundoCorchete);
            } else {
                carritosStr = linea.substring(primerCorchete);
            }
        }

        return new String[]{atributosStr, carritosStr, preguntasStr};
    }
}
