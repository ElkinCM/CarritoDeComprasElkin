package ec.edu.ups;

import ec.edu.ups.util.MensajeInternacionalizacionHandler;

import javax.swing.*;
import java.awt.*;

public class MiDesktop extends JDesktopPane {
    private MensajeInternacionalizacionHandler mensaje;

    public MiDesktop(MensajeInternacionalizacionHandler mensaje) {
        super();
        this.mensaje = mensaje;
    }

    public void actualizarTextos() {
        repaint();
    }
}
