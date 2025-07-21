package ec.edu.ups.dao.impl.DAOPregunta;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaDAOArchivoTexto implements PreguntaDAO {

    private final File archivo;

    public PreguntaDAOArchivoTexto(String rutaBase) {
        File directorio = new File(rutaBase, "CarritoCompras");
        if (!directorio.exists() && !directorio.mkdirs()) {
            System.out.println("Error al crear directorio: " + directorio.getAbsolutePath());
        }
        this.archivo = new File(directorio, "preguntas.txt");
        if (!archivo.exists()) {
            inicializarArchivoConPreguntasEjemplo();
        }
    }

    private void inicializarArchivoConPreguntasEjemplo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (int i = 1; i <= 12; i++) {
                bw.write(i + "|" + "pregunta" + i);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al crear archivo de preguntas con ejemplos: " + e.getMessage());
        }
    }

    @Override
    public void crear(int codigo, String texto) {
        // Evitar duplicados?
        if (buscarPorCodigo(codigo) != null) {
            System.out.println("Pregunta con código " + codigo + " ya existe.");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(codigo + "|" + texto);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al crear pregunta: " + e.getMessage());
        }
    }

    @Override
    public Pregunta buscarPorCodigo(int codigo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 2) {
                    int id = Integer.parseInt(partes[0]);
                    if (id == codigo) {
                        return new Pregunta(id, partes[1]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al buscar pregunta: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Pregunta> listar() {
        List<Pregunta> preguntas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                if (partes.length == 2) {
                    int id = Integer.parseInt(partes[0]);
                    preguntas.add(new Pregunta(id, partes[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al listar preguntas: " + e.getMessage());
        }
        return preguntas;
    }
}
