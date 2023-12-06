/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package graphproyect;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author javic
 */
public class ProbandoMetodos {
    
    public void ejecutar(){
        String currentPath = "C:/Users/javic/Desktop/trabajos/2023 2 semestre/Teoria de Grafo/GraphProyect";
        String rutaArchivo = currentPath + "/archivoJson/example.json";
        
        String contenido = leerArchivo(rutaArchivo);
        
        if(contenido != null ){
        
            // contenido del archivo json
            JSONObject grafoJson = new JSONObject(contenido);
            
            // obtencion de vertices y aristas
            JSONArray verticesJson = grafoJson.getJSONArray("vertices");
            JSONArray aristasJson = grafoJson.getJSONArray("aristas");
            
            Grafo grafo = new Grafo(verticesJson.length());
            
            // se agregan los vertices al grafo
            for(int i=0;i < verticesJson.length(); i++ ){
                grafo.agregarVertice(verticesJson.getInt(i));
            }
            
            // se agregan las aristas al grafo
            for(int i=0;i < aristasJson.length(); i++ ){
                JSONObject aristaJSON = aristasJson.getJSONObject(i);
                int origen = aristaJSON.getInt("origen");
                int destino = aristaJSON.getInt("destino");
                int peso = aristaJSON.getInt("peso");
                grafo.agregarArista(origen, destino, peso);
            }
            
 // --------------- Prueba de "existeArista" -------------------
              
            int verticeOrigenzzz = 0;
            int verticeDestinozzz = 0;  
              
        boolean existeArista = grafo.existeArista(verticeOrigenzzz, verticeDestinozzz);
        
            System.out.println("****************** Existencia de aristas ******************");

            if (existeArista) {
 
                System.out.println("Existe una arista entre el vértice " + verticeOrigenzzz + " y el vértice " + verticeDestinozzz);
                System.out.println("-----------------------------------------------");
            } else {

                System.out.println("No existe una arista entre el vértice " + verticeOrigenzzz + " y el vértice " + verticeDestinozzz);
                System.out.println("-----------------------------------------------");
            }
// -----------------------------------------------------------------
          
            
// ------------------ Prueba de obtener los vecinos de un vértice ----------------------------
            int verticeParaVecinos = 0;
            List<Arista> vecinos = grafo.obtenerVecinos(verticeParaVecinos);
            System.out.println("****************** Vecinos de un vertice ******************");
            System.out.println("Vecinos del vértice " + verticeParaVecinos + ":");
            for (Arista arista : vecinos) {
                System.out.println("Destino: " + arista.getDestino() + ", Peso: " + arista.getPeso());
            }
            System.out.println("-----------------------------------------------");
            
// ------------------------------------------------------------------------------------            
        


// ----------------- Prueba Grado de vertice -------------------------------

        int verticeGrado = 4;

        int grado = grafo.gradoVertice(verticeGrado);
        
        System.out.println("****************** Grado de vertice ******************");
       
        if (grado != -1) {
            System.out.println("El grado del vértice " + verticeGrado + " es: " + grado);
            System.out.println("-----------------------------------------------");
        } else {
            System.out.println("El vértice " + verticeGrado + " no existe en el grafo.");
            System.out.println("-----------------------------------------------");
        }

// -------------------------------------------------------------------------


// ----------------- Prueba añadir vertices al grafo -------------------------------

        // Agregar un nuevo vértice al grafo
        int nuevoVertice = 4;
        boolean verticeAgregado = grafo.agregarVertice(nuevoVertice);

        if (verticeAgregado) {
            System.out.println("Vértice " + nuevoVertice + " agregado correctamente.");
        } else {
            System.out.println("No se pudo agregar el vértice " + nuevoVertice + ". Probablemente ya exista en el grafo.");
        }

        // Verificar si el vértice ha sido agregado consultando los vértices del grafo
        int[] verticesActualizados = grafo.obtenerVertices();
        System.out.println("Vértices actuales del grafo:");
        for (int vertice : verticesActualizados) {
            System.out.print(vertice + " ");
        }
        System.out.println();
// -------------------------------------------------------------------------



// ----------------- Prueba obtener los Vertices -------------------------------
        // Obtener la lista de vértices del grafo
        int[] vertices = grafo.obtenerVertices();

        // Imprimir los vértices obtenidos
        System.out.println("Vértices del grafo:");
        for (int vertice : vertices) {
            System.out.print(vertice + " ");
        }
        System.out.println();

// ----------------------------------------------------------------------------



// ------------------------------------------   ---------------------------------
        // Agregar aristas al vértice 4
        int verticeCreado = 4;

        // Agregar aristas que tengan al vértice creado como origen
        grafo.agregarArista(verticeCreado, 0, 5);
        grafo.agregarArista(verticeCreado, 1, 8);

        // Agregar aristas que tengan al vértice creado como destino
        grafo.agregarArista(2, verticeCreado, 3);
        grafo.agregarArista(3, verticeCreado, 7);

            int verticeParaVecinos2 = 4;
            List<Arista> vecinos2 = grafo.obtenerVecinos(verticeParaVecinos2);
            System.out.println("****************** Vecinos de un vertice ******************");
            System.out.println("Vecinos del vértice " + verticeParaVecinos2 + ":");
            for (Arista arista : vecinos2) {
                System.out.println("Destino: " + arista.getDestino() + ", Peso: " + arista.getPeso());
            }
            System.out.println("-----------------------------------------------");
        
        



// -----------------------------------------------------------------------


// ------------------------------- eliminar vertice ----------------------------------


        // Eliminar el vértice 4 del grafo
        int verticeAEliminar = 4;
        boolean verticeEliminado = grafo.eliminarVertice(verticeAEliminar);

        if (verticeEliminado) {
            System.out.println("Vértice " + verticeAEliminar + " eliminado correctamente del grafo.");
        } else {
            System.out.println("No se pudo eliminar el vértice " + verticeAEliminar + ". Comprueba si el vértice existe en el grafo.");
        }

        // Obtener los vértices actualizados después de eliminar el vértice 4
        int[] verticesActualizadosRemove = grafo.obtenerVertices();
        System.out.println("Vértices actuales del grafo después de eliminar el vértice " + verticeAEliminar + ":");
        for (int vertice : verticesActualizadosRemove) {
            System.out.print(vertice + " ");
        }
        System.out.println();



// ----------------------------------------------------------------------------------

        }
    
    }
    
    // Función para leer el contenido del archivo JSON
    public static String leerArchivo(String rutaArchivo) {
        try {
            return new String(Files.readAllBytes(Paths.get(rutaArchivo)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
