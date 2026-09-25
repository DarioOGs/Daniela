package com.grafos.principal;

import com.grafos.estructura.Grafo;
import com.grafos.modelo.Vertice;
import com.grafos.vista.VentanaGrafo;
import java.awt.GraphicsEnvironment;
import java.util.Scanner;
import javax.swing.SwingUtilities;

public class Main {

    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        Grafo grafo = new Grafo();
        cargarDatosIniciales(grafo);

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Elija una opción: ");

            switch (opcion) {
                case 1:
                    grafo.mostrarVertices();
                    break;
                case 2:
                    buscar(grafo, leerTexto("Nombre de la zona a buscar: "));
                    break;
                case 3:
                    grafo.mostrarVecinos(leerTexto("Nombre de la zona: "));
                    break;
                case 4:
                    grafo.mostrarListaAdyacencia();
                    break;
                case 5:
                    grafo.mostrarRutas();
                    break;
                case 6:
                    grafo.agregarVertice(leerTexto("Nombre de la nueva zona: "));
                    break;
                case 7:
                    String origen = leerTexto("Zona de origen: ");
                    String destino = leerTexto("Zona de destino: ");
                    double km = leerDecimal("Distancia en km: ");
                    grafo.agregarArista(origen, destino, km);
                    break;
                case 8:
                    abrirVentana(grafo);
                    break;
                case 0:
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Error: opción no válida.");
            }
        } while (opcion != 0);

        System.exit(0); // Cierra también las ventanas Swing que sigan abiertas
    }

    /**
     * Registra las 5 zonas y las 6 conexiones pedidas en la actividad.
     */
    private static void cargarDatosIniciales(Grafo grafo) {
        System.out.println("===== REGISTRO DE ZONAS =====");
        grafo.agregarVertice("Centro");
        grafo.agregarVertice("Norte");
        grafo.agregarVertice("Sur");
        grafo.agregarVertice("Este");
        grafo.agregarVertice("Oeste");

        System.out.println("\n===== REGISTRO DE CONEXIONES =====");
        grafo.agregarArista("Centro", "Norte", 5.2);
        grafo.agregarArista("Centro", "Sur", 4.8);
        grafo.agregarArista("Centro", "Este", 6.1);
        grafo.agregarArista("Norte", "Este", 7.3);
        grafo.agregarArista("Este", "Oeste", 12.5);
        grafo.agregarArista("Sur", "Oeste", 8.0);
    }

    private static void mostrarMenu() {
        System.out.println("\n========== MENÚ ==========");
        System.out.println("1. Mostrar zonas");
        System.out.println("2. Buscar una zona");
        System.out.println("3. Ver vecinos de una zona");
        System.out.println("4. Mostrar lista de adyacencia");
        System.out.println("5. Mostrar rutas (origen, destino, distancia)");
        System.out.println("6. Agregar una zona");
        System.out.println("7. Agregar una conexión");
        System.out.println("8. Ver el grafo (ventana gráfica)");
        System.out.println("0. Salir");
    }

    private static void buscar(Grafo grafo, String nombre) {
        Vertice v = grafo.buscarVertice(nombre);
        if (v != null) {
            System.out.println("Zona encontrada: " + v);
        } else {
            System.out.println("Error: la zona '" + nombre + "' no existe.");
        }
    }

    private static void abrirVentana(Grafo grafo) {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Error: no hay entorno gráfico disponible.");
            return;
        }
        SwingUtilities.invokeLater(() -> new VentanaGrafo(grafo).setVisible(true));
        System.out.println("Ventana abierta. Puede seguir usando el menú.");
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine().trim();
    }

    /**
     * Lee un número entero; si el usuario escribe otra cosa devuelve -1.
     */
    private static int leerEntero(String mensaje) {
        try {
            return Integer.parseInt(leerTexto(mensaje));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Lee un número decimal (acepta punto o coma); si no es válido devuelve -1.
     */
    private static double leerDecimal(String mensaje) {
        try {
            return Double.parseDouble(leerTexto(mensaje).replace(',', '.'));
        } catch (NumberFormatException e) {
            System.out.println("Error: debe escribir un número.");
            return -1;
        }
    }
}
