package ec.edu.ups.vista.Registrarse;

import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.modelo.RespuestaSegu;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreguntaModificarView extends JFrame {

    private JLabel lblTitulo;
    private JScrollPane scrollPane;
    private JButton btnVerificar;
    private JPanel panelPrincipal;
    private JPanel panelPreguntas;

    private final Map<Pregunta, JTextField> respuestasTextoUsuario;
    private List<RespuestaSegu> conjuntoRespuestasGuardadas;
    private final MensajeInternacionalizacionHandler mensaje;

    public PreguntaModificarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        this.respuestasTextoUsuario = new HashMap<>();
        this.conjuntoRespuestasGuardadas = null;

        panelPreguntas = new JPanel();
        scrollPane.setViewportView(panelPreguntas);

        setContentPane(panelPrincipal);
        setSize(600, 400);

        actualizarTexto();
    }

    public void construirFormularioPreguntas(List<RespuestaSegu> respuestasUsuario) {
        this.conjuntoRespuestasGuardadas = respuestasUsuario;

        panelPreguntas.removeAll();
        respuestasTextoUsuario.clear();

        if (respuestasUsuario == null || respuestasUsuario.isEmpty()) {
            panelPreguntas.revalidate();
            panelPreguntas.repaint();
            return;
        }

        panelPreguntas.setLayout(new GridLayout(respuestasUsuario.size(), 2, 10, 10));

        for (RespuestaSegu respuesta : respuestasUsuario) {
            Pregunta pregunta = respuesta.getPre();
            JLabel etiqueta = new JLabel(pregunta.getTexto());
            JTextField campoTexto = new JTextField();

            respuestasTextoUsuario.put(pregunta, campoTexto);

            panelPreguntas.add(etiqueta);
            panelPreguntas.add(campoTexto);
        }

        panelPreguntas.revalidate();
        panelPreguntas.repaint();
    }

    public void actualizarTexto() {
        setTitle(mensaje.get("pregunta.titulo"));
        lblTitulo.setText(mensaje.get("preg.recuperar.titulo"));
        btnVerificar.setText(mensaje.get("btn.verificar"));

        if (conjuntoRespuestasGuardadas != null && !conjuntoRespuestasGuardadas.isEmpty()) {
            for (Map.Entry<Pregunta, JTextField> entry : respuestasTextoUsuario.entrySet()) {
                Pregunta pregunta = entry.getKey();
                for (Component comp : panelPreguntas.getComponents()) {
                    if (comp instanceof JLabel) {
                        JLabel etiqueta = (JLabel) comp;
                        String clave = "preg.seguridad." + pregunta.getId();
                        pregunta.setTexto(mensaje.get(clave));
                        etiqueta.setText(pregunta.getTexto());
                    }
                }
            }
        }
    }

    public Map<Pregunta, String> obtenerRespuestasDigitadas() {
        Map<Pregunta, String> respuestas = new HashMap<>();
        for (Map.Entry<Pregunta, JTextField> entry : respuestasTextoUsuario.entrySet()) {
            respuestas.put(entry.getKey(), entry.getValue().getText());
        }
        return respuestas;
    }

    public JButton getBtnVerificar() {
        return btnVerificar;
    }

    public void limpiarCampos() {
        for (JTextField campo : respuestasTextoUsuario.values()) {
            campo.setText("");
        }
    }
}
