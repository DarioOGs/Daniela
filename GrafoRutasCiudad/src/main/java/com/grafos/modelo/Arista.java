package com.grafos.modelo;

/**
 * Representa una conexión (ruta) entre dos zonas de la ciudad.
 * Como el grafo es NO dirigido, la ruta sirve en ambos sentidos.
 */
public class Arista {

    private Vertice origen;
    private Vertice destino;
    private double distanciaKm;

    public Arista(Vertice origen, Vertice destino, double distanciaKm) {
        this.origen = origen;
        this.destino = destino;
        this.distanciaKm = distanciaKm;
    }

    public Vertice getOrigen() {
        return origen;
    }

    public Vertice getDestino() {
        return destino;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    /**
     * Indica si esta arista une a los vértices a y b (en cualquier sentido).
     */
    public boolean conecta(Vertice a, Vertice b) {
        return (origen.equals(a) && destino.equals(b))
                || (origen.equals(b) && destino.equals(a));
    }

    /**
     * Indica si el vértice v es uno de los extremos de esta arista.
     */
    public boolean contiene(Vertice v) {
        return origen.equals(v) || destino.equals(v);
    }

    /**
     * Devuelve el vértice del otro extremo (el vecino de v).
     * Si v no pertenece a la arista, devuelve null.
     */
    public Vertice obtenerOtroExtremo(Vertice v) {
        if (origen.equals(v)) {
            return destino;
        }
        if (destino.equals(v)) {
            return origen;
        }
        return null;
    }

    @Override
    public String toString() {
        return origen.getNombre() + " - " + destino.getNombre()
                + " : " + distanciaKm + " km";
    }
}
