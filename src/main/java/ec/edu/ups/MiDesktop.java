package ec.edu.ups;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

/**
 * Fondo visual inspirado en música electrónica: colores neón, ondas de sonido,
 * sintetizadores y partículas digitales.
 */
public class MiDesktop extends JDesktopPane {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


        int width = getWidth();
        int height = getHeight();

        // Fondo degradado púrpura a azul oscuro
        GradientPaint fondo = new GradientPaint(0, 0, new Color(70, 0, 100), 0, height, new Color(0, 0, 60));
        g2.setPaint(fondo);
        g2.fillRect(0, 0, width, height);

        // Dibujar partículas flotantes (círculos pequeños)
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int px = rand.nextInt(width);
            int py = rand.nextInt(height);
            int size = rand.nextInt(4) + 2;
            g2.setColor(new Color(255, 255, 255, rand.nextInt(100) + 50));
            g2.fillOval(px, py, size, size);
        }

        // Ondas de sonido horizontales
        g2.setStroke(new BasicStroke(2));
        for (int y = height / 3; y <= 2 * height / 3; y += 40) {
            g2.setColor(new Color(0, 255, 200, 120));
            for (int x = 0; x < width; x += 20) {
                int waveHeight = (int) (10 * Math.sin(x * 0.05 + y * 0.1));
                g2.drawLine(x, y, x, y + waveHeight);
            }
        }

        // Líneas de energía diagonales
        g2.setColor(new Color(0, 255, 255, 60));
        g2.setStroke(new BasicStroke(1));
        for (int i = -width; i < width; i += 40) {
            g2.drawLine(i, 0, i + width, height);
        }

        // Dibujar sintetizador abstracto en el centro
        int synthX = width / 2 - 100;
        int synthY = height / 2 - 50;
        g2.setColor(new Color(20, 20, 20));
        g2.fillRoundRect(synthX, synthY, 200, 100, 20, 20);

        // Teclas o pads iluminados
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                int padX = synthX + 20 + i * 40;
                int padY = synthY + 20 + j * 30;
                g2.setColor(new Color(rand.nextInt(100) + 155, rand.nextInt(255), 255));
                g2.fillRect(padX, padY, 30, 20);
            }
        }

        // Texto centrado
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        g2.setColor(Color.CYAN);
        String texto = "ELECTRONIC PULSE";
        int textWidth = g2.getFontMetrics().stringWidth(texto);
        g2.drawString(texto, (width - textWidth) / 2, 60);

        g2.dispose();
    }
}
