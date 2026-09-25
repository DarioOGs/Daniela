package com.grafos.vista;

import com.grafos.estructura.Grafo;
import com.grafos.modelo.Arista;
import com.grafos.modelo.Vertice;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Panel que dibuja el grafo: cada vértice es un círculo y cada arista una línea.
 */
public class PanelGrafo extends JPanel {

    private static final int RADIO = 30;
    private static final int CURVATURA = 90;

    private Grafo grafo;
    // Posición de cada vértice en pantalla (misma posición que en getVertices()).
    private ArrayList<Point> posiciones;

    public PanelGrafo(Grafo grafo) {
        this.grafo = grafo;
        this.posiciones = new ArrayList<>();
        setPreferredSize(new Dimension(600, 500));
        setBackground(Color.WHITE);
    }

    /**
     * Calcula las posiciones: zonas conocidas en su punto cardinal,
     * cualquier otra zona se ubica en círculo alrededor del centro.
     */
    private void calcularPosiciones(ArrayList<Vertice> vertices) {
        posiciones.clear();
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int d = Math.min(getWidth(), getHeight()) / 2 - RADIO - 20;

        for (int i = 0; i < vertices.size(); i++) {
            String nombre = vertices.get(i).getNombre().toLowerCase();
            switch (nombre) {
                case "centro":
                    posiciones.add(new Point(cx, cy));
                    break;
                case "norte":
                    posiciones.add(new Point(cx, cy - d));
                    break;
                case "sur":
                    posiciones.add(new Point(cx, cy + d));
                    break;
                case "este":
                    posiciones.add(new Point(cx + d, cy));
                    break;
                case "oeste":
                    posiciones.add(new Point(cx - d, cy));
                    break;
                default:
                    double angulo = 2 * Math.PI * i / vertices.size() + Math.PI / 4;
                    posiciones.add(new Point(
                            cx + (int) (d * Math.cos(angulo)),
                            cy + (int) (d * Math.sin(angulo))));
            }
        }
    }

    /**
     * Indica si el punto medio de una arista cae sobre algún vértice.
     */
    private boolean pasaPorOtroVertice(int x, int y) {
        for (Point p : posiciones) {
            if (p.distance(x, y) < RADIO) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ArrayList<Vertice> vertices = grafo.getVertices();
        calcularPosiciones(vertices);

        // 1. Dibujar aristas (líneas + distancia)
        g2.setStroke(new BasicStroke(2));
        g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        FontMetrics fmAristas = g2.getFontMetrics();
        for (Arista a : grafo.getAristas()) {
            Point p1 = posiciones.get(vertices.indexOf(a.getOrigen()));
            Point p2 = posiciones.get(vertices.indexOf(a.getDestino()));
            int mx = (p1.x + p2.x) / 2;
            int my = (p1.y + p2.y) / 2;
            String etiqueta = a.getDistanciaKm() + " km";
            int ex = mx + 5;

            g2.setColor(Color.GRAY);
            if (pasaPorOtroVertice(mx, my)) {
                // La línea recta taparía otro vértice: se dibuja como curva.
                double largo = Math.hypot(p2.x - p1.x, p2.y - p1.y);
                int cx = mx + (int) (-(p2.y - p1.y) / largo * 2 * CURVATURA);
                int cy = my + (int) ((p2.x - p1.x) / largo * 2 * CURVATURA);
                g2.draw(new QuadCurve2D.Double(p1.x, p1.y, cx, cy, p2.x, p2.y));
                // Punto más alto de la curva (donde va la etiqueta)
                mx = (mx + cx) / 2;
                my = (my + cy) / 2;
                // Etiqueta a la izquierda para no chocar con otras líneas
                ex = mx - fmAristas.stringWidth(etiqueta) - 8;
            } else {
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }

            g2.setColor(new Color(180, 40, 40));
            g2.drawString(etiqueta, ex, my - 5);
        }

        // 2. Dibujar vértices (círculos + nombre)
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        FontMetrics fm = g2.getFontMetrics();
        for (int i = 0; i < vertices.size(); i++) {
            Point p = posiciones.get(i);
            g2.setColor(new Color(30, 90, 160));
            g2.fillOval(p.x - RADIO, p.y - RADIO, RADIO * 2, RADIO * 2);
            g2.setColor(Color.WHITE);
            String nombre = vertices.get(i).getNombre();
            g2.drawString(nombre, p.x - fm.stringWidth(nombre) / 2, p.y + fm.getAscent() / 2 - 2);
        }
    }
}
