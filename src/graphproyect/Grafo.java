/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphproyect;

import java.util.*;

/**
 *
 * @author lolsg
 */
public class Grafo {

    private int numVertices;
    private Map<Integer, List<Arista>> listaAdyacencia;

    public Grafo(int numVertices) {
        this.numVertices = numVertices;
        this.listaAdyacencia = new HashMap<>();
        for (int i = 0; i < numVertices; i++) {
            listaAdyacencia.put(i, new LinkedList<>());
        }
    }

    public boolean existeArista(int origen, int destino) {
        if (listaAdyacencia.containsKey(origen)) {
            return listaAdyacencia.get(origen).contains(destino);
        }
        return false;
    }

    public boolean existeVertice(int vertice) {
        return listaAdyacencia.containsKey(vertice);
    }

    public void agregarArista(int origen, int destino, int peso) {
        if (existeVertice(destino) && existeVertice(origen)) {
            Arista arista = new Arista(destino, peso);
            listaAdyacencia.get(origen).add(arista);
        }
    }

    public void eliminarArista(int origen, int destino) {
        if (existeArista(origen, destino)) {
            listaAdyacencia.get(origen).remove(destino);
        }
    }

    public void agregarVertice(int vertice) {
        if (!existeVertice(vertice)) {
            listaAdyacencia.put(vertice, new LinkedList<>());
            numVertices++;
        }
    }

    public void eliminarVertice(int vertice) {
        if (existeVertice(vertice)) {
            listaAdyacencia.remove(vertice);
            for (Integer destino : listaAdyacencia.keySet()) {
                eliminarArista(vertice, destino);
            }
            numVertices--;
        }

    }

    public int gradoVertice(int vertice) {
        if (existeVertice(vertice)) {
            return listaAdyacencia.get(vertice).size();
        }
        return -1;
    }

    public List<Arista> obtenerVecinos(int vertice) {
        return listaAdyacencia.get(vertice);
    }

}

