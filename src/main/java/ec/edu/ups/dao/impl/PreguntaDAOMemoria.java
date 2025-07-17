package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaDAO;
import ec.edu.ups.modelo.Pregunta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PreguntaDAOMemoria implements PreguntaDAO {

    private final List<Pregunta> listaPreguntas;

    public PreguntaDAOMemoria() {
        this.listaPreguntas = new ArrayList<>();
        inicializarPreguntas();
    }

    private void inicializarPreguntas() {
        for (int i = 1; i <= 13; i++) {
            crear(i, "pregunta" + i);
        }
    }

    @Override
    public void crear(int id, String texto) {
        if (buscarPorCodigo(id) == null) {
            listaPreguntas.add(new Pregunta(id, texto));
        }
    }

    @Override
    public Pregunta buscarPorCodigo(int id) {
        Optional<Pregunta> resultado = listaPreguntas.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        return resultado.orElse(null);
    }

    @Override
    public List<Pregunta> listar() {
        return Collections.unmodifiableList(listaPreguntas);
    }
}
