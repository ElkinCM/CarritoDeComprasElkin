package ec.edu.ups.modelo;

import java.util.Objects;

/**
 * Clase que representa una pregunta utilizada en formularios, encuestas u otros contextos.
 *
 * El atributo {@code texto} generalmente almacena una clave de internacionalización (i18n),
 * como "pregunta1", que se utilizará para obtener el texto localizado desde archivos de recursos.
 *
 * Esta clase incluye métodos estándar como {@code equals}, {@code hashCode} y {@code toString}.
 *
 * @author
 */
public class Pregunta {

    /** Identificador único de la pregunta. */
    private int id;

    /**
     * Clave de internacionalización asociada a la pregunta.
     * Esta clave puede utilizarse para cargar el texto desde archivos de propiedades o recursos multilingües.
     */
    private String texto;

    /**
     * Constructor para crear una pregunta con su identificador y texto (clave i18n).
     *
     * @param id    Identificador único de la pregunta.
     * @param texto Clave de internacionalización de la pregunta.
     */
    public Pregunta(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    /**
     * Obtiene el identificador de la pregunta.
     *
     * @return ID de la pregunta.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la pregunta.
     *
     * @param id Nuevo identificador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la clave de internacionalización del texto de la pregunta.
     *
     * @return Clave de texto (ej. "pregunta1").
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Establece una nueva clave de texto para la pregunta.
     *
     * @param texto Clave de texto (i18n).
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Devuelve una representación en cadena de la pregunta.
     *
     * @return Cadena representando el objeto {@code Pregunta}.
     */
    @Override
    public String toString() {
        return "Pregunta{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                '}';
    }

    /**
     * Compara esta pregunta con otro objeto para determinar igualdad.
     * Se considera igual si tienen el mismo ID y texto.
     *
     * @param o Objeto a comparar.
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pregunta pregunta = (Pregunta) o;
        return id == pregunta.id && Objects.equals(texto, pregunta.texto);
    }

    /**
     * Genera el código hash de la pregunta basado en su ID.
     *
     * @return Código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
