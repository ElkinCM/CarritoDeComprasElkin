package ec.edu.ups.modelo;

import java.util.Objects;

public class Pregunta {
    private int id;
    private String texto; // Aquí se guarda la clave para internacionalización, ej. "pregunta1"

    public Pregunta(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    /**
     * Devuelve la clave de la pregunta (para buscar texto localizado).
     */
    public String getTexto() {
        return texto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "id=" + id +
                ", texto='" + texto + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pregunta pregunta = (Pregunta) o;
        return id == pregunta.id && Objects.equals(texto, pregunta.texto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
