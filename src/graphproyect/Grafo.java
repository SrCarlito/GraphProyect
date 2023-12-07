/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphproyect;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;
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
                    Arista cur = new Arista(i, a.getPeso());
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

    public void emparejamiento_Coloreo() {

        Map<Integer, Integer> coloreo = colorearVertices();;

        Set<Integer> solicitantes = new HashSet<>();
        Set<Integer> requeridos = new HashSet<>();
        Set<Integer> coloresDiff = new HashSet<>(coloreo.values());
        

        if (coloresDiff.size() !=2 ) {
            encontrarEmparejamientos();
        } else {
            int[] c1 = new int[2];

            int j = 0;
            for(int i : coloresDiff){
                c1[j] = i;
                j++;
            }

            for (Map.Entry<Integer, Integer> entry : coloreo.entrySet()) {
                int vertice = entry.getKey();
                int color = entry.getValue();
                if (color == c1[0]) {
                    solicitantes.add(vertice);
                } else {
                    requeridos.add(vertice);
                }
            }
            emparejamientoEstable(solicitantes, requeridos);
            ;
        }
    }

    private void emparejamientoEstable(Set<Integer> solicitantes, Set<Integer> requeridos) {
        System.out.println("--------------------");
        System.out.println("Emparejamiento estable");
        Map<Integer, Integer> emparejamiento = new HashMap<>();
        Set<Integer> solicitantesA = new HashSet<>(solicitantes);
        Set<Integer> usados = new HashSet<>();

        while (!solicitantesA.isEmpty()) { // Mientras existan solicitantes
            for (int s : solicitantes) { // Recorrer solicitantes
                if (solicitantesA.contains(s)) { // Si el solicitante no tiene pareja
                    // Obtener vecinos ordenados por prioridad
                    List<Arista> requeridosActual = obtenerVecinosNoDirigido(s);
                    // ordenar de mayor a menor peso
                    Collections.sort(requeridosActual, Comparator.comparingInt(Arista::getPeso).reversed());
                
                    for (Arista r : requeridosActual) { // Recorrer requeridos
                        // obtener vecinos de r
                        List<Arista> solicitantesR = obtenerVecinosNoDirigido(r.getDestino());
                        // ordenar de mayor a menor peso
                        solicitantesR.sort(Comparator.comparingInt(Arista::getPeso).reversed());

                        //obtener solo los solicitantes
                        List<Integer> solicitantesRD = new ArrayList<>();
                        for (Arista a : solicitantesR) {
                            solicitantesRD.add(a.getDestino());
                        }

                        if (!emparejamiento.containsValue(r.getDestino())) { // Si el requerido no ha sido usado
                            emparejamiento.put(s, r.getDestino()); // Emparejar
                            usados.add(r.getDestino()); // Marcar como usado
                            solicitantesA.remove(s); // Eliminar solicitante de la lista
                            break; // Salir del ciclo
                        } else {

                            int s2 = 0;
                            for (Map.Entry<Integer, Integer> entry : emparejamiento.entrySet()) {
                                if (entry.getValue() == r.getDestino()) {
                                    s2 = entry.getKey();
                                }
                            } // Obtener solicitante emparejado
                            if (solicitantesRD.indexOf(s) < solicitantesRD.indexOf(s2)) { // Si el solicitante actual tiene mayor prioridad
                                emparejamiento.put(s, r.getDestino()); // Emparxejar
                                emparejamiento.remove(s2); // Eliminar emparejamiento anterior
                                solicitantesA.add(s2); // Agregar solicitante anterior a la lista
                                usados.add(r.getDestino());
                                break; // Salir del ciclo
                            }
                        }
                    }

                    solicitantesA.remove(s); // Eliminar solicitante de la lista
                }
            }

        }
        
        // Mostrar emparejamiento
        for (Map.Entry<Integer, Integer> entry : emparejamiento.entrySet()) {
            int solicitante = entry.getKey();
            int requerido = entry.getValue();
            System.out.println(solicitante + " <-> " + requerido);
        }

    }


    // Resto del código de la clase Grafo

    private void encontrarEmparejamientos() {
        System.out.println("--------------------");
        System.out.println("Emparejamiento");
        ArrayList<int[]> parejas = encontrarParejas();
        ArrayList<int[]> emparejamiento = new ArrayList<>();

        int major = 0;
        int count = 0;

        while (count < parejas.size()) {
            ArrayList<int[]> res = encontrarEmparejamiento(parejas);

            if (res.size() > major) {
                emparejamiento = res;
                major = res.size();
            }

            parejas.add(parejas.remove(count)); // [1,2,3] -> [3,1,2]
            count++;
        }
        mostrarEmparejamiento(emparejamiento);
    }

    private void mostrarEmparejamiento(ArrayList<int[]> lista) {
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
        ArrayList<Integer> usados = new ArrayList<>();// [1,2],[1,3]
        ArrayList<int[]> res = new ArrayList<>();

        for (int origen : obtenerVertices()) {
            for (Arista destino : obtenerVecinosNoDirigido(origen)) {
                if (!usados.contains(origen) && !usados.contains(destino.getDestino())) {
                    int[] pareja = { origen, destino.getDestino(), destino.getPeso() };
                    res.add(pareja);
                    usados.add(origen);
                    usados.add(destino.getDestino());
                }
            }
        }
        return res;
    }

    private Map<Integer,Integer> colorearVertices() {
        Map<Integer, Integer> colores = new HashMap<>();

        // inicializar colores
        for (int j = 0; j < numVertices; j++) {
            colores.put(j, j);
        }
        // Conjunto de números ya usados
        Set<Integer> coloresPosibles = new HashSet<>();

        Random random = new Random();
        while (coloresPosibles.size() < numVertices) {
            int randomNumber = random.nextInt(8000000);
            if (!coloresPosibles.contains(randomNumber)) {
                coloresPosibles.add(randomNumber);
            }
        }

        // Ordenar vertices por grado
        List<Integer> vertices = new ArrayList<>();
        for (int v : obtenerVertices()) {
            vertices.add(v);
        }

        // Asignar colores
        for (int c = 0; c < numVertices; c++) {
            Map<Integer, Integer> colorestemp = new HashMap<>();

            for (int v : vertices) {
                colorestemp.put(v, asignarColor(v, colorestemp, coloresPosibles));
            }

            Set<Integer> coloresDiff = new HashSet<>(colorestemp.values());
            Set<Integer> coloresDiff2 = new HashSet<>(colores.values());

            if (coloresDiff.size() < coloresDiff2.size()) {
                colores = new HashMap<>(colorestemp);
            }
            vertices.add(vertices.remove(0));
        }
        // Mostrar colores
        for (int k : colores.keySet()) {
            System.out.println(k + " : " + colores.get(k));
        }

        // Mostrar numero cromatico
        Set<Integer> coloresDiff = new HashSet<>(colores.values());
        System.out.println("El numero cromático estimado del grafo es: " + coloresDiff.size());

        return colores;

    }

    private int asignarColor(int v, Map<Integer, Integer> colores, Set<Integer> coloresPosibles) {
        List<Arista> vecinos = obtenerVecinosNoDirigido(v);

        // Copiar colores posibles
        Set<Integer> coloresp = new HashSet<>(coloresPosibles);

        for (Arista vecino : vecinos) {
            if (colores.containsKey(vecino.getDestino())) {
                coloresp.remove(colores.get(vecino.getDestino()));
            }
        }

        for (int c : coloresp) {
            return c;
        }
        return 0;
    }

    public void obtenerArbolExpansionMinima() {

        Map<Integer, List<Arista>> aristas = getListaAdyacencia();
        Map<Integer, List<Arista>> resultado = new HashMap<>();

        // Ordenar aristas
        for (int i : aristas.keySet()) {
            aristas.get(i).sort(Comparator.comparingInt(Arista::getPeso));// 0rdenar de menor a mayor
        }

        if (tienePesosNegativos()) {
            System.out.println(
                    "El grafo contiene aristas con pesos negativos, el algoritmo de Kruskal no puede ejecutarse correctamente.");
            return; // Retorna null para indicar que el grafo no es apto para Kruskal
        }

        ConjuntoDisjunto conjuntoDisjunto = new ConjuntoDisjunto(obtenerVertices());

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

        // Imprimir resultado
        for (Map.Entry<Integer, List<Arista>> entry : resultado.entrySet()) {
            int vertice = entry.getKey();
            List<Arista> vecinos = entry.getValue();
            for (Arista arista : vecinos) {
                System.out.println(vertice + " <-> " + arista.getDestino() + " (" + arista.getPeso() + ")");
            }
        }

    }

    public boolean tienePesosNegativos() {
        for (int i : obtenerVertices()) {
            for (Arista arista : obtenerVecinos(i)) {
                if (arista.getPeso() < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public void dijkstra(int nodoOrigen) {
        Map<Integer, Boolean> visitado = new HashMap<>(); // arreglo de booleanos para saber si ya se visito un nodo
        Map<Integer, Integer> distancias = new HashMap<>(); // arreglo de distancias

        for (int i : obtenerVertices()) { // recorrer todos los nodos
            visitado.put(i, false); // inicializar todos los nodos como no visitados
            distancias.put(i, Integer.MAX_VALUE); // inicializar todas las distancias como infinito

        }
        distancias.put(nodoOrigen, 0);// la distancia del nodo origen a el mismo es 0 (distancia minima)

        for (int i : obtenerVertices()) { // recorrer todos los nodos

            int nodoActual = obtenerNodoMinimo(distancias, visitado);// obtener el nodo con la distancia minima
            visitado.put(nodoActual, true);// marcar el nodo como visitado

            for (Arista arista : obtenerVecinos(nodoActual)) {// recorrer los vecinos del nodo actual
                int nodoDestino = arista.getDestino();// obtener el nodo destino
                int peso = arista.getPeso();// obtener el peso de la arista
                if (!visitado.get(nodoDestino) && distancias.get(nodoActual) != Integer.MAX_VALUE && // si el nodo no ha                              // infinito
                        distancias.get(nodoActual) + peso < distancias.get(nodoDestino)) // y la distancia del nodo

                {
                    distancias.put(nodoDestino, distancias.get(nodoActual) + peso); // la distancia del nodo destino es
                                                                                    // igual a
                                                                                    // la distancia del nodo actual mas
                                                                                    // el peso de la arista
                }
            }
        }

        // Imprimir las distancias mínimas desde el nodo origen a todos los demás nodos
        // válidos.
        for (int i : obtenerVertices()) { // recorrer todos los nodos
            if (distancias.get(i) != Integer.MAX_VALUE) { // si la distancia del nodo es diferente de infinito
                System.out.println("Distancia desde " + nodoOrigen + " hasta " + i + ": " + distancias.get(i)); // imprimir
                // actual
            }
        }
    }

    private int obtenerNodoMinimo(Map<Integer, Integer> distancias, Map<Integer, Boolean> visitado) {

        int minimo = Integer.MAX_VALUE;// valor maximo
        int nodoMinimo = -1;// nodo minimo

        for (int i : obtenerVertices()) {// recorrer todos los nodos
            if (!visitado.get(i) && distancias.get(i) <= minimo) {// Si el nodo no ha sido visitado y la distancia del
                                                                  // nodo es
                // menor o igual al minimo
                minimo = distancias.get(i);// el minimo es igual a la distancia del nodo
                nodoMinimo = i; // el nodo minimo es igual al nodo actual
            }
        }
        if (nodoMinimo == -1) {// si el nodo minimo es igual a -1
            throw new RuntimeException("No se puede encontrar un nodo minimo");// lanzar una excepcion
        }
      
        return nodoMinimo;
    }

    public void floydWarshall() {
        Map<Integer, Map<Integer, Integer>> distancias = new HashMap<>();

        // Inicialización de la matriz de distancias
        // i representa el vertice inicial
        // j representa el vertice final
        for (int i : obtenerVertices()) {
            Map<Integer, Integer> distanciasI = new HashMap<>();
            for (int j : obtenerVertices()) {
                if (i == j) {
                    distanciasI.put(j, 0);
                } else if (existeArista(i, j)) {
                    distanciasI.put(j, obtenerVecinos(i).stream()
                            .filter(a -> a.getDestino() == j)// Filtrar aristas que van a j
                            .findFirst()// regresa solo los vecinos
                            .map(Arista::getPeso)// toma el peso de la arista
                            .orElse(Integer.MAX_VALUE));
                } else {
                    distanciasI.put(j, Integer.MAX_VALUE);
                }
            }
            distancias.put(i, distanciasI);
        }

        // Algoritmo de Floyd-Warshall
        for (int k : obtenerVertices()) { // itera sobre todo los vertices como posible candidato
            for (int i : obtenerVertices()) {
                for (int j : obtenerVertices()) {
                    // Si existe una distancia menor a la actual
                    if (distancias.get(i).get(k) != Integer.MAX_VALUE
                            && distancias.get(k).get(j) != Integer.MAX_VALUE) {
                        int nuevoPeso = distancias.get(i).get(k) + distancias.get(k).get(j);// Nuevo peso

                        if (nuevoPeso < distancias.get(i).get(j)) {// Actualiza el peso si este es menor al anterior
                            distancias.get(i).put(j, nuevoPeso);
                        }
                    }
                }
            }
        }

        System.out.print("\t");
        for (int i : obtenerVertices()) {
            System.out.print(i + "\t");
        }
        System.out.println();
        // Imprimir resultados
        for (int i : obtenerVertices()) {
            System.out.print(i + "\t");
            for (int j : obtenerVertices()) {
                if (distancias.get(i).get(j) == Integer.MAX_VALUE) {
                    System.out.print("INF\t");
                } else {
                    System.out.print(distancias.get(i).get(j) + "\t");
                }
            }
            System.out.println();
        }
    }

    public void BFS(int inicio) {
        Map<Integer, Boolean> visitado = new HashMap<>();
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(inicio);
        System.out.print(inicio + " -> ");
        for (int i : obtenerVertices()) {
            visitado.put(i, false);
        }

        visitado.put(inicio, true);
        while (!queue.isEmpty()) {
            int actual = queue.poll();

            for (Arista vecino : obtenerVecinos(actual)) {
                int destino = vecino.getDestino();
                if (!visitado.get(destino)) {
                    visitado.put(destino, true);
                    System.out.print(destino + " -> ");
                    queue.offer(destino);
                }
            }
        }
        System.out.println();
    }

    public void DFS(int inicio) {
        Map<Integer, Boolean> visitado = new HashMap<>();

        for (int i : obtenerVertices()) {
            visitado.put(i, false);
        }

        dfsRecursivo(inicio, visitado);
        System.out.println();
    }

    private void dfsRecursivo(int actual, Map<Integer, Boolean> visitado) {
        visitado.put(actual, true);
        System.out.print(actual + " -> ");

        for (Arista vecino : obtenerVecinos(actual)) {
            int destino = vecino.getDestino();
            if (!visitado.get(destino)) {
                dfsRecursivo(destino, visitado);
            }
        }
    }
}

class ConjuntoDisjunto {

    private Map<Integer, Integer> parent;
    private Map<Integer, Integer> rank;

    public ConjuntoDisjunto(int[] vertices) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        for (int i : vertices) {
            parent.put(i, i);
            rank.put(i, 0);
        }
    }

    public int encontrar(int x) {
        if (parent.get(x) != x) {
            parent.put(x, encontrar(parent.get(x)));
        }
        return parent.get(x);
    }

    public void unir(int x, int y) {
        int raizX = encontrar(x);
        int raizY = encontrar(y);

        if (raizX != raizY) {
            if (rank.get(raizX) < rank.get(raizY)) {
                parent.put(raizX, raizY);
            } else if (rank.get(raizX) > rank.get(raizY)) {
                parent.put(raizY, raizX);
            } else {
                parent.put(raizY, raizX);
                rank.put(raizX, rank.get(raizX) + 1);
            }
        }
    }
}
