package ec.edu.ups.dao;

import ec.edu.ups.modelo.Pregunta;

import java.util.List;

/**
 * Interfaz para las operaciones de acceso a datos relacionadas con la entidad {@link Pregunta}.
 * Define los métodos básicos para la gestión de preguntas de seguridad.
 */
public interface PreguntaDAO {

    /**
     * Crea una nueva pregunta de seguridad con el código y texto proporcionados.
     *
     * @param codigo         el código único de la pregunta.
     * @param preguntaTexto  el texto de la pregunta.
     */
    void crear(int codigo, String preguntaTexto);

    /**
     * Busca una pregunta de seguridad por su código único.
     *
     * @param codigo el código identificador de la pregunta.
     * @return la pregunta correspondiente al código, o {@code null} si no se encuentra.
     */
    Pregunta buscarPorCodigo(int codigo);

    /**
     * Lista todas las preguntas de seguridad almacenadas.
     *
     * @return una lista de todas las preguntas registradas.
     */
    List<Pregunta> listar();
}
