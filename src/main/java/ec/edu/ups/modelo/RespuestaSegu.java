package ec.edu.ups.modelo;

/**
 * Clase que representa una respuesta a una pregunta de seguridad.
 *
 * Esta clase se utiliza comúnmente en procesos de recuperación de cuentas o validaciones
 * de identidad, donde se hacen preguntas de seguridad al usuario.
 *
 * Contiene una referencia a una {@link Pregunta} y la respuesta dada por el usuario.
 *
 * @author
 */
public class RespuestaSegu {

    /** Pregunta de seguridad asociada. */
    private Pregunta pregunta;

    /** Respuesta proporcionada por el usuario. */
    private String respuesta;

    /**
     * Constructor para crear una respuesta de seguridad con su pregunta asociada.
     *
     * @param pregunta  Objeto {@link Pregunta} asociado a la respuesta.
     * @param respuesta Respuesta escrita por el usuario.
     */
    public RespuestaSegu(Pregunta pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    /**
     * Obtiene la pregunta asociada a esta respuesta.
     *
     * @return Objeto {@link Pregunta}.
     */
    public Pregunta getPregunta() {
        return pregunta;
    }

    /**
     * Asigna una nueva pregunta a esta respuesta.
     *
     * @param pregunta Nueva pregunta de seguridad.
     */
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    /**
     * Obtiene la respuesta escrita por el usuario.
     *
     * @return Respuesta como cadena de texto.
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * Establece la respuesta proporcionada por el usuario.
     *
     * @param respuesta Nueva respuesta.
     */
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    /**
     * Devuelve una representación en cadena de esta respuesta de seguridad.
     *
     * @return Cadena con los datos de la pregunta y la respuesta.
     */
    @Override
    public String toString() {
        return "RespuestaSegu{" +
                "pregunta=" + pregunta +
                ", respuesta='" + respuesta + '\'' +
                '}';
    }
}
