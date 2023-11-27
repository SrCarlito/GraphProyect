/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package graphproyect;
import java.util.*;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author lolsg
 */
public class GraphProyect {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
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
