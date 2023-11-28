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

    public int getNumVertices(){
        return numVertices;
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

    public boolean agregarArista(int origen, int destino, int peso) {
        if (existeVertice(destino) && existeVertice(origen)) {
            Arista arista = new Arista(destino, peso);
            listaAdyacencia.get(origen).add(arista);
            return true;
        }
        return false;
    }

    public boolean eliminarArista(int origen, int destino) {
        if (existeArista(origen, destino)) {
            listaAdyacencia.get(origen).remove(destino);
            return true;
        }
        return false;
    }

    public boolean agregarVertice(int vertice) {
        if (!existeVertice(vertice)) {
            listaAdyacencia.put(vertice, new LinkedList<>());
            numVertices++;
            return true;
        }
        return false;
    }

    public boolean eliminarVertice(int vertice) {
        if (existeVertice(vertice)) {
            listaAdyacencia.remove(vertice);
            for (Integer destino : listaAdyacencia.keySet()) {
                eliminarArista(vertice, destino);
            }
            numVertices--;
            return true;
        }
        return false;

    }

    public int gradoVertice(int vertice) {
        if (existeVertice(vertice)) {
            return listaAdyacencia.get(vertice).size();
        }
        return -1;
    }

    public int[] obtenerVertices() {
        int[] vertices = new int[numVertices];

        int i = 0;
        for (Integer vertice : listaAdyacencia.keySet()) {
            vertices[i] = vertice;
            i++;
        }
        return vertices;
    }
    
    public List<Arista> obtenerVecinos(int vertice) {
        return listaAdyacencia.get(vertice);
    }

}
