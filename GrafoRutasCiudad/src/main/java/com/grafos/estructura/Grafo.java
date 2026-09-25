package com.grafos.estructura;

import com.grafos.modelo.Arista;
import com.grafos.modelo.Vertice;
import java.util.ArrayList;

/**
 * Grafo no dirigido que almacena sus vértices y aristas en ArrayList.
 */
public class Grafo {

    private ArrayList<Vertice> vertices;
    private ArrayList<Arista> aristas;

    public Grafo() {
        vertices = new ArrayList<>();
        aristas = new ArrayList<>();
    }

    /**
     * Agrega un vértice nuevo evitando nombres repetidos.
     */
    public boolean agregarVertice(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Error: el nombre del vértice no puede estar vacío.");
            return false;
        }
        if (buscarVertice(nombre) != null) {
            System.out.println("Error: el vértice '" + nombre + "' ya existe.");
            return false;
        }
        vertices.add(new Vertice(nombre.trim()));
        System.out.println("Vértice '" + nombre.trim() + "' agregado.");
        return true;
    }

    /**
     * Busca un vértice por su nombre (recorrido con for-each).
     * Devuelve null si no existe.
     */
    public Vertice buscarVertice(String nombre) {
        if (nombre == null) {
            return null;
        }
        for (Vertice v : vertices) {
            if (v.getNombre().equalsIgnoreCase(nombre.trim())) {
                return v;
            }
        }
        return null;
    }

    /**
     * Agrega una arista entre dos vértices existentes.
     */
    public boolean agregarArista(String nombreOrigen, String nombreDestino, double distanciaKm) {
        Vertice origen = buscarVertice(nombreOrigen);
        Vertice destino = buscarVertice(nombreDestino);

        if (origen == null) {
            System.out.println("Error: no se puede conectar, el vértice '" + nombreOrigen + "' no existe.");
            return false;
        }
        if (destino == null) {
            System.out.println("Error: no se puede conectar, el vértice '" + nombreDestino + "' no existe.");
            return false;
        }
        if (origen.equals(destino)) {
            System.out.println("Error: no se puede conectar un vértice consigo mismo (" + nombreOrigen + ").");
            return false;
        }
        if (distanciaKm <= 0) {
            System.out.println("Error: la distancia debe ser mayor que 0 km.");
            return false;
        }
        if (existeArista(origen, destino)) {
            System.out.println("Error: la conexión " + origen + " - " + destino + " ya existe.");
            return false;
        }
        aristas.add(new Arista(origen, destino, distanciaKm));
        System.out.println("Conexión " + origen + " - " + destino + " (" + distanciaKm + " km) agregada.");
        return true;
    }

    /**
     * Verifica si ya existe una arista entre a y b (recorrido con for clásico).
     */
    private boolean existeArista(Vertice a, Vertice b) {
        for (int i = 0; i < aristas.size(); i++) {
            if (aristas.get(i).conecta(a, b)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve la lista de vecinos de un vértice.
     * Si el vértice no existe, devuelve una lista vacía.
     */
    public ArrayList<Vertice> obtenerVecinos(String nombre) {
        ArrayList<Vertice> vecinos = new ArrayList<>();
        Vertice v = buscarVertice(nombre);
        if (v == null) {
            return vecinos;
        }
        for (Arista a : aristas) {
            if (a.contiene(v)) {
                vecinos.add(a.obtenerOtroExtremo(v));
            }
        }
        return vecinos;
    }

    /**
     * Muestra en consola los vecinos de un vértice.
     */
    public void mostrarVecinos(String nombre) {
        Vertice v = buscarVertice(nombre);
        if (v == null) {
            System.out.println("Error: el vértice '" + nombre + "' no existe.");
            return;
        }
        ArrayList<Vertice> vecinos = obtenerVecinos(nombre);
        if (vecinos.isEmpty()) {
            System.out.println(v + " no tiene vecinos.");
        } else {
            System.out.println("Vecinos de " + v + ": " + vecinos);
        }
    }

    /**
     * Muestra la lista de adyacencia del grafo.
     */
    public void mostrarListaAdyacencia() {
        System.out.println("\n===== LISTA DE ADYACENCIA =====");
        for (Vertice v : vertices) {
            ArrayList<Vertice> vecinos = obtenerVecinos(v.getNombre());
            StringBuilder linea = new StringBuilder(v.getNombre() + " -> ");
            for (int i = 0; i < vecinos.size(); i++) {
                linea.append(vecinos.get(i).getNombre());
                if (i < vecinos.size() - 1) {
                    linea.append(", ");
                }
            }
            System.out.println(linea);
        }
    }

    /**
     * Muestra todas las rutas con origen, destino y distancia (recorrido con forEach).
     */
    public void mostrarRutas() {
        System.out.println("\n===== RUTAS DISPONIBLES =====");
        if (aristas.isEmpty()) {
            System.out.println("No hay rutas registradas.");
            return;
        }
        aristas.forEach(a -> System.out.println(
                "Origen: " + a.getOrigen()
                + " | Destino: " + a.getDestino()
                + " | Distancia: " + a.getDistanciaKm() + " km"));
    }

    /**
     * Muestra todos los vértices registrados.
     */
    public void mostrarVertices() {
        System.out.println("\n===== ZONAS (VÉRTICES) =====");
        vertices.forEach(v -> System.out.println("- " + v));
    }

    // Se devuelven copias para proteger las listas internas (encapsulamiento).
    public ArrayList<Vertice> getVertices() {
        return new ArrayList<>(vertices);
    }

    public ArrayList<Arista> getAristas() {
        return new ArrayList<>(aristas);
    }
}
