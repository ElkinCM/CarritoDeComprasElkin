package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class PreguntaDAOArchivoBinario implements PreguntaDAO {
    private final String rutaArchivo;
    private static final int LONGITUD_PREGUNTA = 11;
    private static final int BYTES_POR_PREGUNTA = Integer.BYTES + (LONGITUD_PREGUNTA * Character.BYTES);

    public PreguntaDAOArchivoBinario(String rutaBase) {
        File directorio = new File(rutaBase, "CarritoCompras");
        if (!directorio.exists() && !directorio.mkdirs()) {
            System.out.println("Error al crear directorio: " + directorio.getAbsolutePath());
        }
        this.rutaArchivo = new File(directorio, "preguntas.dat").getAbsolutePath();

        inicializarArchivoConPreguntasEjemplo();
    }

    private void inicializarArchivoConPreguntasEjemplo() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
                for (int i = 1; i <= 12; i++) {
                    escribirPregunta(raf, i, "pregunta" + i);
                }
            } catch (IOException e) {
                System.out.println("Error al crear archivo de preguntas con ejemplos: " + e.getMessage());
            }
        }
    }

    @Override
    public void crear(int codigo, String pregunta) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            raf.seek(raf.length());
            escribirPregunta(raf, codigo, pregunta);
        } catch (IOException e) {
            System.out.println("Error al crear pregunta: " + e.getMessage());
        }
    }

    private void escribirPregunta(RandomAccessFile raf, int codigo, String pregunta) throws IOException {
        raf.writeInt(codigo);

        if (pregunta.length() > LONGITUD_PREGUNTA) {
            pregunta = pregunta.substring(0, LONGITUD_PREGUNTA);
        }
        pregunta = String.format("%-" + LONGITUD_PREGUNTA + "s", pregunta); // rellena con espacios

        raf.writeChars(pregunta);
    }

    @Override
    public Pregunta buscarPorCodigo(int codigo) {
        if (codigo <= 0) return null;

        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            long totalPreguntas = raf.length() / BYTES_POR_PREGUNTA;
            if (codigo > totalPreguntas) return null;

            raf.seek((codigo - 1) * BYTES_POR_PREGUNTA);

            int codigoLeido = raf.readInt();
            String preguntaTexto = leerPreguntaTexto(raf);

            if (codigoLeido == codigo) {
                return new Pregunta(codigoLeido, preguntaTexto);
            }
        } catch (IOException e) {
            System.out.println("Error al buscar pregunta: " + e.getMessage());
        }
        return null;
    }

    private String leerPreguntaTexto(RandomAccessFile raf) throws IOException {
        StringBuilder sb = new StringBuilder(LONGITUD_PREGUNTA);
        for (int i = 0; i < LONGITUD_PREGUNTA; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }

    @Override
    public List<Pregunta> listar() {
        List<Pregunta> preguntas = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            long totalPreguntas = raf.length() / BYTES_POR_PREGUNTA;

            for (int i = 0; i < totalPreguntas; i++) {
                raf.seek(i * BYTES_POR_PREGUNTA);
                int codigo = raf.readInt();
                String texto = leerPreguntaTexto(raf);

                if (codigo != 0) {
                    preguntas.add(new Pregunta(codigo, texto));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al listar preguntas: " + e.getMessage());
        }
        return preguntas;
    }
}
