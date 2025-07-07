package ec.edu.ups;

import javax.swing.*;
import java.awt.*;

public class MiDesktop extends JDesktopPane {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Fondo dividido: azul oscuro arriba, púrpura abajo
        g2.setColor(new Color(40, 40, 80));
        g2.fillRect(0, 0, width, height / 2);
        g2.setColor(new Color(100, 60, 140));
        g2.fillRect(0, height / 2, width, height / 2);

        // Texto principal
        String texto = "JAVA MUSIC STUDIO";
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 32));
        int textWidth = g2.getFontMetrics().stringWidth(texto);
        g2.drawString(texto, (width - textWidth) / 2, height - 30);

        // 🎹 Dibujo de piano en la parte inferior
        int pianoX = width / 2 - 200;
        int pianoY = height - 150;
        int pianoWidth = 400;
        int pianoHeight = 80;

        // Base del piano
        g2.setColor(new Color(30, 30, 30));
        g2.fillRoundRect(pianoX, pianoY, pianoWidth, pianoHeight, 20, 20);

        // Teclas blancas
        g2.setColor(Color.WHITE);
        int numKeys = 10;
        int keyWidth = pianoWidth / numKeys;
        for (int i = 0; i < numKeys; i++) {
            g2.fillRect(pianoX + i * keyWidth, pianoY, keyWidth - 2, pianoHeight);
            g2.setColor(Color.BLACK);
            g2.drawRect(pianoX + i * keyWidth, pianoY, keyWidth - 2, pianoHeight);
            g2.setColor(Color.WHITE);
        }

        // Teclas negras (simplificadas)
        g2.setColor(Color.BLACK);
        for (int i = 0; i < numKeys - 1; i++) {
            if (i % 7 != 2 && i % 7 != 6) {
                g2.fillRect(pianoX + i * keyWidth + keyWidth / 2, pianoY, keyWidth / 2, pianoHeight / 2);
            }
        }

        // 🎧 Auriculares en la izquierda
        int earX = width / 4 - 50;
        int earY = height / 3;
        g2.setColor(new Color(60, 60, 60));
        g2.fillOval(earX, earY, 50, 100);
        g2.fillOval(earX + 100, earY, 50, 100);
        g2.setStroke(new BasicStroke(10));
        g2.drawArc(earX + 20, earY - 40, 110, 100, 0, 180);

        // 🎵 Nota musical central
        int noteX = width / 2 - 30;
        int noteY = height / 2 - 100;
        g2.setColor(Color.YELLOW);
        g2.fillOval(noteX, noteY, 40, 40); // Cabeza
        g2.fillRect(noteX + 25, noteY - 80, 10, 90); // Palo
        g2.fillArc(noteX + 20, noteY - 100, 40, 40, 0, 180); // Bandera

        g2.dispose();
    }
}
