package com.grafos.vista;

import com.grafos.estructura.Grafo;
import javax.swing.JFrame;

/**
 * Ventana principal que contiene el panel de dibujo del grafo.
 */
public class VentanaGrafo extends JFrame {

    public VentanaGrafo(Grafo grafo) {
        super("Rutas entre zonas de la ciudad");
        add(new PanelGrafo(grafo));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
