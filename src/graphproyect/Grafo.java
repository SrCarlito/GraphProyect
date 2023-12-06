/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphproyect;

import java.util.*;

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

    public Map<Integer, List<Arista>> getListaAdyacencia() {
        return listaAdyacencia;
    }

    public boolean existeArista(int origen, int destino) {
        if (listaAdyacencia.containsKey(origen)) {
            for (Arista a : listaAdyacencia.get(origen)) {
                if (a.getDestino() == destino) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean existeVertice(int vertice) {
        return listaAdyacencia.containsKey(vertice);
    }

    public boolean agregarArista(int origen, int destino, int peso) {
        if (existeVertice(destino) && existeVertice(origen) && (origen != destino)) {
            Arista arista = new Arista(destino, peso);
            listaAdyacencia.get(origen).add(arista);
            return true;
        }
        return false;
    }

    public boolean eliminarArista(int origen, int destino) {
        if (existeArista(origen, destino)) {
            List<Arista> vecinos = listaAdyacencia.get(origen);
            for (int i = 0; i < vecinos.size(); i++) {
                Arista v = vecinos.get(i);
                if (v.getDestino() == destino) {
                    listaAdyacencia.get(origen).remove(i);
                    return true;
                }
            }
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
        List<Arista> res = listaAdyacencia.get(vertice);

        if (res == null) {
            res = new LinkedList<>();
        }

        return res;
    }

    public List<Arista> obtenerVecinosNoDirigido(int vertice) {
        List<Arista> res = listaAdyacencia.get(vertice);

        for (int i : obtenerVertices()) {
            for (Arista a : obtenerVecinos(i)) {
                if (a.getDestino() == vertice && !existeArista(vertice, i)) {
                    Arista cur = new Arista(i, 1);
                    res.add(cur);
                }
            }
        }

        if (res == null) {
            res = new LinkedList<>();
        }

        return res;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void encontrarEmparejamientos() {
        ArrayList<int[]> parejas = encontrarParejas();
        ArrayList<int[]> emparejamiento = new ArrayList<>();

        int major = 0;
        int cont = 0;
        while (cont < parejas.size()) {
            ArrayList<int[]> res = encontrarEmparejamiento(parejas);

            if (res.size() > major) {
                emparejamiento = res;
                major = res.size();
            }

            parejas.add(parejas.remove(0));
            cont++;
        }

        mostrarEmparejamiento(emparejamiento);
    }

    public void mostrarEmparejamiento(ArrayList<int[]> lista) {
        for (int[] p : lista) {
            System.out.println(p[0] + " <-> " + p[1]);
        }
    }

    private ArrayList<int[]> encontrarEmparejamiento(ArrayList<int[]> lista) {
        ArrayList<int[]> res = new ArrayList<>();
        ArrayList<Integer> usados = new ArrayList<>();

        for (int[] p : lista) {
            int va = p[0];
            int vb = p[1];

            if (!usados.contains(va) && !usados.contains(vb)) {
                res.add(p);
                usados.add(va);
                usados.add(vb);
            }
        }

        return res;

    }

    private ArrayList<int[]> encontrarParejas() {
        ArrayList<int[]> res = new ArrayList<>();

        for (int origen : obtenerVertices()) {
            for (Arista destino : obtenerVecinosNoDirigido(origen)) {
                int[] pareja = { origen, destino.getDestino() };
                res.add(pareja);
            }
        }
        return res;
    }

    public void colorearVertices() {
        Map<Integer, Integer> colores = new HashMap<>();

        Random random = new Random();
        // Conjunto de números ya usados
        Set<Integer> coloresPosibles = new HashSet<>();

        while (coloresPosibles.size() < numVertices) {
            int randomNumber = random.nextInt(8000000);
            if (!coloresPosibles.contains(randomNumber)) {
                coloresPosibles.add(randomNumber);
            }
        }

        for (int v : obtenerVertices()) {
            colores.put(v, asignarColor(v, colores, coloresPosibles));
        }

        for (int k : colores.keySet()) {
            System.out.println(k + " : " + colores.get(k));
        }
        ArrayList<Integer> coloresDiff = new ArrayList<>();
        for (int color : colores.values()) {
            if (!coloresDiff.contains(color)) {
                coloresDiff.add(color);
            }
        }
        System.out.println("El numero cromático del grafo es: " + coloresDiff.size());

    }

    private int asignarColor(int v, Map<Integer, Integer> colores, Set<Integer> coloresPosibles) {

        int res = 0;
        List<Arista> vecinos = obtenerVecinosNoDirigido(v);
        Set<Integer> coloresp = coloresPosibles;
        for (Arista vecino : vecinos) {
            if (colores.containsKey(vecino.getDestino())) {
                coloresp.remove(colores.get(vecino.getDestino()));
            }
        }

        for (int c : coloresp) {
            return c;
        }

        return res;
    }

    public static Map<Integer, List<Arista>> obtenerArbolExpansionMinima(Grafo grafo) {
        Map<Integer, List<Arista>> aristas = grafo.getListaAdyacencia();

        Map<Integer, List<Arista>> resultado = new HashMap<>();

        // Ordenar aristas
        for (int i : aristas.keySet()) {
            aristas.get(i).sort(Comparator.comparingInt(Arista::getPeso));
        }

        // Verificar si hay aristas con pesos negativos
        boolean tienePesosNegativos = false;
        for (int i = 0; i < grafo.getNumVertices(); i++) {
            for (Arista arista : grafo.obtenerVecinos(i)) {
                if (arista.getPeso() < 0) {
                    tienePesosNegativos = true;
                    break;
                }
            }
        }

        if (tienePesosNegativos) {
            System.out.println(
                    "El grafo contiene aristas con pesos negativos, el algoritmo de Kruskal no puede ejecutarse correctamente.");
            return null; // Retorna null para indicar que el grafo no es apto para Kruskal
        }


        ConjuntoDisjunto conjuntoDisjunto = new ConjuntoDisjunto(grafo.getNumVertices());

        for (int i : aristas.keySet()) {
            List<Arista> aristasV = new ArrayList<>();

            int origen = i;
            for (Arista arista : aristas.get(i)) {
                int destino = arista.getDestino();

                int raizOrigen = conjuntoDisjunto.encontrar(origen);
                int raizDestino = conjuntoDisjunto.encontrar(destino);

                if (raizOrigen != raizDestino) {
                    aristasV.add(arista);
                    conjuntoDisjunto.unir(raizOrigen, raizDestino);
                }
            }
            resultado.put(origen, aristasV);

        }

        return resultado;
    }
}

class ConjuntoDisjunto {

    private int[] parent;
    private int[] rank;

    public ConjuntoDisjunto(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int encontrar(int x) {
        if (parent[x] != x) {
            parent[x] = encontrar(parent[x]);
        }
        return parent[x];
    }

    public void unir(int x, int y) {
        int raizX = encontrar(x);
        int raizY = encontrar(y);

        if (raizX != raizY) {
            if (rank[raizX] < rank[raizY]) {
                parent[raizX] = raizY;
            } else if (rank[raizX] > rank[raizY]) {
                parent[raizY] = raizX;
            } else {
                parent[raizY] = raizX;
                rank[raizX]++;
            }
        }
    }
}
