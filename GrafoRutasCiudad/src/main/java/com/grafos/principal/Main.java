package com.grafos.principal;

import com.grafos.estructura.Grafo;
import com.grafos.modelo.Vertice;
import com.grafos.vista.VentanaGrafo;
import java.awt.GraphicsEnvironment;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        // 1. Registrar vértices
        System.out.println("===== REGISTRO DE ZONAS =====");
        grafo.agregarVertice("Centro");
        grafo.agregarVertice("Norte");
        grafo.agregarVertice("Sur");
        grafo.agregarVertice("Este");
        grafo.agregarVertice("Oeste");

        // 2. Crear conexiones (con distancia en km)
        System.out.println("\n===== REGISTRO DE CONEXIONES =====");
        grafo.agregarArista("Centro", "Norte", 5.2);
        grafo.agregarArista("Centro", "Sur", 4.8);
        grafo.agregarArista("Centro", "Este", 6.1);
        grafo.agregarArista("Norte", "Este", 7.3);
        grafo.agregarArista("Este", "Oeste", 12.5);
        grafo.agregarArista("Sur", "Oeste", 8.0);

        // 3. Pruebas de validaciones (mensajes de error)
        System.out.println("\n===== PRUEBAS DE VALIDACIÓN =====");
        grafo.agregarVertice("Centro");              // nombre repetido
        grafo.agregarArista("Centro", "Aeropuerto", 15); // vértice inexistente
        grafo.agregarArista("Norte", "Centro", 5.2); // conexión repetida (no dirigido)
        grafo.agregarArista("Sur", "Sur", 1);        // vértice consigo mismo

        // 4. Búsqueda de vértices
        System.out.println("\n===== BÚSQUEDA DE VÉRTICES =====");
        buscar(grafo, "Este");
        buscar(grafo, "Aeropuerto");

        // 5. Vecinos de un vértice
        System.out.println("\n===== VECINOS =====");
        grafo.mostrarVecinos("Centro");
        grafo.mostrarVecinos("Oeste");
        grafo.mostrarVecinos("Aeropuerto");

        // 6. Lista de adyacencia y rutas
        grafo.mostrarVertices();
        grafo.mostrarListaAdyacencia();
        grafo.mostrarRutas();

        // 7. Representación visual (reto opcional)
        if (!GraphicsEnvironment.isHeadless()) {
            SwingUtilities.invokeLater(() -> new VentanaGrafo(grafo).setVisible(true));
        }
    }

    private static void buscar(Grafo grafo, String nombre) {
        Vertice v = grafo.buscarVertice(nombre);
        if (v != null) {
            System.out.println("Vértice encontrado: " + v);
        } else {
            System.out.println("El vértice '" + nombre + "' no existe.");
        }
    }
}
