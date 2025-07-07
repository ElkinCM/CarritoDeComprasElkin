package ec.edu.ups.dao;

import ec.edu.ups.modelo.Pregunta;

import java.util.List;

public interface PreguntaDAO {
    void crear(int codigo, String preguntaTexto);

    Pregunta buscarPorCodigo(int codigo);

    List<Pregunta> listar();
}
