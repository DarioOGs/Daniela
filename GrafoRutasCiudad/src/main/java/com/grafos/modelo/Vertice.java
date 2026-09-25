package com.grafos.modelo;

/**
 * Representa una zona de la ciudad (un vértice del grafo).
 */
public class Vertice {

    private String nombre;

    public Vertice(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Dos vértices son iguales si tienen el mismo nombre (sin importar mayúsculas).
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Vertice)) {
            return false;
        }
        Vertice otro = (Vertice) obj;
        return nombre.equalsIgnoreCase(otro.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return nombre;
    }
}
