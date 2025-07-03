package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;

import java.util.ArrayList;
import java.util.List;

public class PreguntaDAOMemoria implements PreguntaDAO {
    private final List<Pregunta> listarPreguntas;

    public PreguntaDAOMemoria() {
        this.listarPreguntas = new ArrayList<>();
    }

    @Override
    public List<Pregunta> listar() {
        return  new ArrayList<>(this.listarPreguntas);
    }
}
