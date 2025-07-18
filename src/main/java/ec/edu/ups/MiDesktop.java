package ec.edu.ups;

import javax.swing.*;
import java.awt.*;

/**
 * Clase MiDesktop que extiende JDesktopPane para personalizar el fondo
 * dibujando un diseño temático musical con gradientes de color, un piano,
 * auriculares y una nota musical.
 */
public class MiDesktop extends JDesktopPane {

    /**
     * Método sobrescrito para pintar el componente con gráficos personalizados.
     * @param g El objeto Graphics para dibujar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Crear una copia del contexto gráfico como Graphics2D para mejores efectos
        Graphics2D g2 = (Graphics2D) g.create();

        // Activar antialiasing para suavizar bordes y texto
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();   // Ancho del componente
        int height = getHeight(); // Alto del componente

        // Dibuja el fondo dividido en dos colores:
        // La mitad superior con un color azul oscuro
        g2.setColor(new Color(40, 40, 80));
        g2.fillRect(0, 0, width, height / 2);

        // La mitad inferior con un color púrpura
        g2.setColor(new Color(100, 60, 140));
        g2.fillRect(0, height / 2, width, height / 2);

        // Texto principal centrado en la parte inferior
        String texto = "JAVA MUSIC STUDIO";
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 32));
        int textWidth = g2.getFontMetrics().stringWidth(texto);
        g2.drawString(texto, (width - textWidth) / 2, height - 30);

        // Coordenadas y dimensiones para el dibujo del piano
        int pianoX = width / 2 - 200;  // Posición horizontal centrada
        int pianoY = height - 150;     // Posición vertical cerca de la parte inferior
        int pianoWidth = 400;          // Ancho total del piano
        int pianoHeight = 80;          // Alto del piano

        // Dibujo de la base del piano con rectángulo redondeado en color gris oscuro
        g2.setColor(new Color(30, 30, 30));
        g2.fillRoundRect(pianoX, pianoY, pianoWidth, pianoHeight, 20, 20);

        // Dibujo de las teclas blancas del piano
        g2.setColor(Color.WHITE);
        int numKeys = 10;              // Número total de teclas blancas
        int keyWidth = pianoWidth / numKeys; // Ancho de cada tecla

        // Bucle para dibujar las teclas blancas con borde negro
        for (int i = 0; i < numKeys; i++) {
            g2.fillRect(pianoX + i * keyWidth, pianoY, keyWidth - 2, pianoHeight);
            g2.setColor(Color.BLACK);
            g2.drawRect(pianoX + i * keyWidth, pianoY, keyWidth - 2, pianoHeight);
            g2.setColor(Color.WHITE);
        }

        // Dibujo de las teclas negras simplificadas
        g2.setColor(Color.BLACK);
        for (int i = 0; i < numKeys - 1; i++) {
            // Condición para omitir algunas teclas negras que no existen en un piano real
            if (i % 7 != 2 && i % 7 != 6) {
                g2.fillRect(pianoX + i * keyWidth + keyWidth / 2, pianoY, keyWidth / 2, pianoHeight / 2);
            }
        }

        // Dibujo de auriculares en el lado izquierdo
        int earX = width / 4 - 50;    // Posición horizontal
        int earY = height / 3;        // Posición vertical
        g2.setColor(new Color(60, 60, 60));

        // Dos óvalos grandes que representan las almohadillas de los auriculares
        g2.fillOval(earX, earY, 50, 100);
        g2.fillOval(earX + 100, earY, 50, 100);

        // Arco que conecta las almohadillas simulando la diadema
        g2.setStroke(new BasicStroke(10));
        g2.drawArc(earX + 20, earY - 40, 110, 100, 0, 180);

        // Dibujo de una nota musical amarilla en el centro
        int noteX = width / 2 - 30;   // Posición horizontal centrada
        int noteY = height / 2 - 100; // Posición vertical

        g2.setColor(Color.YELLOW);
        g2.fillOval(noteX, noteY, 40, 40);           // Cabeza de la nota (círculo)
        g2.fillRect(noteX + 25, noteY - 80, 10, 90); // Palo vertical de la nota
        g2.fillArc(noteX + 20, noteY - 100, 40, 40, 0, 180); // Bandera de la nota

        // Liberar recursos del objeto Graphics2D
        g2.dispose();
    }
}
