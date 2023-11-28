package graphproyect;
import java.util.*;

public class GraphProyect {

    public static void main(String[] args) {
        Grafo miGrafo = new Grafo(5);
        
        miGrafo.agregarArista(0, 3, 2);
        miGrafo.agregarArista(2, 6, 2);
        miGrafo.agregarArista(3, 1, 2);
        miGrafo.agregarArista(0, 5, 2);
        miGrafo.agregarArista(5, 1, 2);
        miGrafo.agregarArista(4, 3, 2);
        
        List<Arista> vecinos4 = miGrafo.obtenerVecinos(4);
        
        for(Arista vecino:vecinos4){
            System.out.println(vecino.getDestino());
            
        }

        System.out.println("BFS -- Desde el vertice 0: ");
        BFS(miGrafo, 0);

        System.out.println();

        System.out.println("DFS -- Desde el vertice 0: ");
        DFS(miGrafo, 0);
    }

    //  Funcion para BFS
    private static void BFS(Grafo grafo, int inicio) {
        boolean[] visitado = new boolean[grafo.getNumVertices()];
        Queue<Integer> queue = new LinkedList<>();

        visitado[inicio] = true;
        queue.offer(inicio);

        while (!queue.isEmpty()) {
            int actual = queue.poll();
            System.out.print(actual + " ");

            for (Arista vecino : grafo.obtenerVecinos(actual)) {
                int destino = vecino.getDestino();
                if (!visitado[destino]) {
                    visitado[destino] = true;
                    queue.offer(destino);
                }
            }
        }
    }

    //  Funcion para DFS
    private static void DFS(Grafo grafo, int inicio) {
        boolean[] visitado = new boolean[grafo.getNumVertices()];
        dfsRecursivo(grafo, inicio, visitado);
    }

    private static void dfsRecursivo(Grafo grafo, int actual, boolean[] visitado) {
        visitado[actual] = true;
        System.out.println(actual + "");

        for (Arista vecino : grafo.obtenerVecinos(actual)) {
            int destino = vecino.getDestino();
            if (!visitado[destino]) {
                dfsRecursivo(grafo, destino, visitado);
            }
        }
    }

    
}