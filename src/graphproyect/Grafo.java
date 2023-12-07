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

    public void emparejamiento() {
        if (esBipartito()) {
            emparejamientoEstable();
        } else {
            encontrarEmparejamientos();
            ;
        }
    }
    
    private boolean esBipartito() {
        int[] colores = new int[numVertices + 1];
        Arrays.fill(colores, -1);

        for (int i = 1; i <= numVertices; i++) {
            if (colores[i] == -1) {
                if (!esBipartitoUtil(i, colores)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean esBipartitoUtil(int vertice, int[] colores) {
        colores[vertice] = 1;

        Queue<Integer> cola = new LinkedList<>();
        cola.add(vertice);

        while (!cola.isEmpty()) {
            int actual = cola.poll();

            List<Arista> vecinos = obtenerVecinos(actual);
            for (Arista arista : vecinos) {
                int destino = arista.getDestino();

                if (colores[destino] == -1) {
                    colores[destino] = 1 - colores[actual];
                    cola.add(destino);
                } else if (colores[destino] == colores[actual]) {
                    return false;
                }
            }
        }

        return true;
    }

    private void emparejamientoEstable() {
        System.out.println("Emparejamiento estable: ");
    }
    private void encontrarEmparejamientos() {
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
        ArrayList<Integer> usados = new ArrayList<>();//[1,2],[1,3]
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

    public void colorearVertices() {
        Map<Integer, Integer> colores = new HashMap<>();

        //inicializar colores
        for(int j =0; j < numVertices; j++){
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

        //Ordenar vertices por grado
        List<Integer> vertices = new ArrayList<>();
        for (int v : obtenerVertices()) {
            vertices.add(v);
        }

        //Asignar colores
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
        //Mostrar colores
        for (int k : colores.keySet()) {
            System.out.println(k + " : " + colores.get(k));
        }
 
        //Mostrar numero cromatico
        Set<Integer> coloresDiff = new HashSet<>(colores.values());
        System.out.println("El numero cromático estimado del grafo es: " + coloresDiff.size());

    }

    private int asignarColor(int v, Map<Integer, Integer> colores, Set<Integer> coloresPosibles) {
        List<Arista> vecinos = obtenerVecinosNoDirigido(v);

        //Copiar colores posibles
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
            aristas.get(i).sort(Comparator.comparingInt(Arista::getPeso));//0rdenar de menor a mayor
        }

        // Verificar si hay aristas con pesos negativos
        boolean tienePesosNegativos = false;
        for (int i = 0; i < getNumVertices(); i++) {
            for (Arista arista : obtenerVecinos(i)) {
                if (arista.getPeso() < 0) {
                    tienePesosNegativos = true;
                    break;
                }
            }
        }

        if (tienePesosNegativos) {
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
        for (int i : resultado.keySet()) {
            System.out.print(i + ": ");
            for (Arista arista : resultado.get(i)) {
                System.out.print(arista.getDestino() + " ");
            }
            System.out.println();
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
        int numVertices = getNumVertices();//numero de vertices
        boolean[] visitado = new boolean[numVertices]; //arreglo de booleanos para saber si ya se visito un nodo

        int[] distancias = new int[numVertices]; //se crea un arreglo de distancias
        Arrays.fill(distancias, Integer.MAX_VALUE); //llenar el arreglo de distancias con valores maximos
        
        distancias[nodoOrigen] = 0;//la distancia del nodo origen a el mismo es 0 (distancia minima)
    
        for (int i : obtenerVertices()) { //recorrer todos los nodos
            int nodoActual = obtenerNodoMinimo(distancias, visitado);//obtener el nodo con la distancia minima
            visitado[nodoActual] = true;//marcar el nodo como visitado
    
            for (Arista arista : obtenerVecinos(nodoActual)) {//recorrer los vecinos del nodo actual
                int nodoDestino = arista.getDestino();//obtener el nodo destino
                int peso = arista.getPeso();//obtener el peso de la arista
                if (!visitado[nodoDestino] && distancias[nodoActual] != Integer.MAX_VALUE && //si el nodo no ha sido visitado y la distancia del nodo actual es diferente de infinito
                        distancias[nodoActual] + peso < distancias[nodoDestino]) //y la distancia del nodo actual mas el peso de la arista es menor a la distancia del nodo destino
                        { 
                            distancias[nodoDestino] = distancias[nodoActual] + peso; //la distancia del nodo destino es igual a la distancia del nodo actual mas el peso de la arista
                }
            }
        }
    
        // Imprimir las distancias mínimas desde el nodo origen a todos los demás nodos válidos.
        for (int i : obtenerVertices()) { //recorrer todos los nodos
            if (distancias[i] != Integer.MAX_VALUE) { //si la distancia del nodo es diferente de infinito
                System.out.println("Distancia desde " + nodoOrigen + " hasta " + i + ": " + distancias[i]); //imprimir la distancia desde el nodo origen hasta el nodo actual
            }
        }
    }

    private static int obtenerNodoMinimo(int[] distancias, boolean[] visitado) {

        int minimo = Integer.MAX_VALUE;//valor maximo 
        int nodoMinimo = -1;//nodo minimo 
        for (int i = 0; i < distancias.length; i++) {//recorrer todos los nodos
            if (!visitado[i] && distancias[i] <= minimo) {//Si el nodo no ha sido visitado y la distancia del nodo es menor o igual al minimo
                minimo = distancias[i];//el minimo es igual a la distancia del nodo
                nodoMinimo = i; //el nodo minimo es igual al nodo actual
            }
        }
        if (nodoMinimo == -1) {// si el nodo minimo es igual a -1
            throw new RuntimeException("No se puede encontrar un nodo minimo");//lanzar una excepcion
        }
        return nodoMinimo;
    }

    public void floydWarshall() {
        int numVertices = getNumVertices();
        int[][] distancias = new int[numVertices][numVertices];
    
        // Inicialización de la matriz de distancias
        // i representa el vertice inicial
        // j representa el vertice final
        for (int i : obtenerVertices()) {
          for (int j : obtenerVertices()) {
              final int finalJ = j; // Marcar como final
              if (i == j) {
                  distancias[i][j] = 0;
              } else if (existeArista(i, j)) { //conexion directa entre i j
                  int pesoArista = obtenerVecinos(i).stream()
                          .filter(a -> a.getDestino() == finalJ)// Filtrar aristas que van a finalJ
                          .findFirst()//regresa solo los vecinos
                          .map(Arista::getPeso)//toma el peso de la arista
                          .orElse(Integer.MAX_VALUE);
                  distancias[i][j] = pesoArista; 
              } else {
                  distancias[i][j] = Integer.MAX_VALUE;
              }
          }
      }
    
        // Algoritmo de Floyd-Warshall
        for (int k : obtenerVertices()) { //itera sobre todo los vertices como posible candidato
            for (int i : obtenerVertices()) {
                for (int j : obtenerVertices()) {
                    //Si existe  una distancia menor a la actual 
                    if (distancias[i][k] != Integer.MAX_VALUE && distancias[k][j] != Integer.MAX_VALUE) {
                        int nuevoPeso = distancias[i][k] + distancias[k][j];//Nuevo peso

                        if (nuevoPeso < distancias[i][j]) {//Actualiza el peso si este es menor al anterior
                            distancias[i][j] = nuevoPeso;
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
                //si la distancia es MAX_VALUE imprime INF de infinito
                //de lo contrario, imprime la distancia
                System.out.print(distancias[i][j] == Integer.MAX_VALUE ? "INF" : distancias[i][j]);
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    public void BFS(int inicio) {
        boolean[] visitado = new boolean[getNumVertices()];
        Queue<Integer> queue = new LinkedList<>();

        visitado[inicio] = true;
        queue.offer(inicio);
        
        while (!queue.isEmpty()) {
            int actual = queue.poll();
            System.out.print(actual + " ");

            for (Arista vecino : obtenerVecinos(actual)) {
                int destino = vecino.getDestino();
                if (!visitado[destino]) {
                    visitado[destino] = true;
                    queue.offer(destino);
                }
            }
        }
    }

    public void DFS(int inicio) {
        boolean[] visitado = new boolean[getNumVertices()];
        dfsRecursivo(inicio, visitado);
    }
    private void dfsRecursivo(int actual, boolean[] visitado) {
        visitado[actual] = true;
        System.out.println(actual + "");

        for (Arista vecino : obtenerVecinos(actual)) {
            int destino = vecino.getDestino();
            if (!visitado[destino]) {
                dfsRecursivo(destino, visitado);
            }
        }
    }
}

class ConjuntoDisjunto {

    private Map<Integer,Integer> parent;
    private Map<Integer,Integer> rank;

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
