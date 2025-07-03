package ec.edu.ups.vista.Registrarse;

import ec.edu.ups.modelo.Pregunta;
import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaRegistrarView extends JFrame {
    private JPanel panelPrincipal;
    private JButton BtnGuardar;
    private JScrollPane JScrollPane;
    private JLabel lblTitulo;
    private MensajeInternacionalizacionHandler mensaje;
    private final List<JLabel> Pregunta;
    private final List<JTextField> Respuesta;

    public PreguntaRegistrarView(MensajeInternacionalizacionHandler mensaje) {
        this.mensaje = mensaje;
        this.Pregunta = new ArrayList<>();
        this.Respuesta = new ArrayList<>();

        setContentPane(panelPrincipal);
        setSize(800, 600);

        CampPreguntas();
        actualizarTexto();
    }

    private void CampPreguntas() {
        JPanel panelPregunta = new JPanel();
        panelPregunta.setLayout(new GridLayout(10, 2, 10, 5));

        int contador = 1;
        while (contador <= 10) {
            String clave = "preg.seguridad." + contador;
            String textoPregunta = mensaje.get(clave);

            JLabel etiqueta = new JLabel(textoPregunta);
            JTextField campoTexto = new JTextField();

            Pregunta.add(etiqueta);
            Respuesta.add(campoTexto);

            panelPregunta.add(etiqueta);
            panelPregunta.add(campoTexto);

            contador++;
        }

        JScrollPane.setViewportView(panelPregunta);
    }

    public void Mostrar(List<Pregunta> preguntas){
        for (int i = 0; i < preguntas.size(); i++) {
            if (i < Pregunta.size()) {
                Pregunta.get(i).setText(preguntas.get(i).getTexto());
            }
        }
    }

    public List<String> getRespuestas() {
        List<String> listaRespuestas = new ArrayList<>();
        for (JTextField campo : Respuesta) {
            listaRespuestas.add(campo.getText());
        }
        return listaRespuestas;
    }

    public void actualizarTexto() {
        setTitle(mensaje.get("login.btn.reg"));
        lblTitulo.setText(mensaje.get("pregunta.titulo"));
        BtnGuardar.setText(mensaje.get("btn.guardar"));
    }

    public JButton getBtnGuardar() {
        return BtnGuardar;
    }
}
