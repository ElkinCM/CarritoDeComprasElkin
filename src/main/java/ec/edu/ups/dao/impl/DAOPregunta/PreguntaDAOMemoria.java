package ec.edu.ups.dao.impl.DAOPregunta;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implementación en memoria del DAO para la entidad {@link Pregunta}.
 * Almacena las preguntas en una lista local simulando una base de datos.
 * Al iniciar, genera automáticamente 13 preguntas por defecto.
 *
 * @author
 */
public class PreguntaDAOMemoria implements PreguntaDAO {

    private final List<Pregunta> listaPreguntas;

    /**
     * Constructor por defecto que inicializa la lista de preguntas y
     * carga 13 preguntas iniciales con identificadores del 1 al 13.
     */
    public PreguntaDAOMemoria() {
        this.listaPreguntas = new ArrayList<>();
        inicializarPreguntas();
    }

    /**
     * Método privado para inicializar la lista con 13 preguntas de ejemplo.
     * Cada pregunta tiene un ID del 1 al 13 y un texto tipo "preguntaX".
     */
    private void inicializarPreguntas() {
        for (int i = 1; i <= 13; i++) {
            crear(i, "pregunta" + i);
        }
    }

    /**
     * Crea y agrega una nueva pregunta si no existe ya una con el mismo ID.
     *
     * @param id    Identificador único de la pregunta.
     * @param texto Contenido de la pregunta.
     */
    @Override
    public void crear(int id, String texto) {
        if (buscarPorCodigo(id) == null) {
            listaPreguntas.add(new Pregunta(id, texto));
        }
    }

    /**
     * Busca una pregunta en la lista por su identificador.
     *
     * @param id El ID de la pregunta que se desea buscar.
     * @return La pregunta correspondiente o {@code null} si no se encuentra.
     */
    @Override
    public Pregunta buscarPorCodigo(int id) {
        Optional<Pregunta> resultado = listaPreguntas.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        return resultado.orElse(null);
    }

    /**
     * Retorna una lista inmutable con todas las preguntas almacenadas.
     *
     * @return Lista de preguntas.
     */
    @Override
    public List<Pregunta> listar() {
        return Collections.unmodifiableList(listaPreguntas);
    }
}
