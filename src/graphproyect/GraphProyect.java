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

        String contenido = leerArchivo("C:\\Users\\lolsg\\OneDrive\\Documentos\\NetBeansProjects\\GraphProyect\\src\\graphproyect\\example.json");

        if (contenido != null && !contenido.isEmpty()) {
            Grafo grafo = construirGrafoDesdeJSON(contenido);

            if (grafo != null) {
                Map<Integer,List< Arista>> arbolExpansionMinima = Grafo.obtenerArbolExpansionMinima(grafo);

    
                // Mostrar el árbol de expansión mínima
                System.out.println("Árbol de expansión mínima: ");
                for (Map.Entry<Integer, List<Arista>> entry : arbolExpansionMinima.entrySet()) {
                    int vertice = entry.getKey();
                    List<Arista> vecinos = entry.getValue();
                    for (Arista arista : vecinos) {
                        System.out.println(vertice + " -> " + arista.getDestino() + " (" + arista.getPeso() + ")");
                    }
                }
                
            } else {
                System.out.println("No se pudo construir el grafo desde el archivo JSON.");
            }

        } else {
            System.out.println("El archivo JSON está vacío o no se pudo leer.");
        }
    }

    // Función para leer el contenido del archivo JSON
    public static String leerArchivo(String rutaArchivo) {
        String contenido = "";
        try {
            contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contenido;
    }

    // Función para construir el grafo desde el archivo JSON
    public static Grafo construirGrafoDesdeJSON(String contenido) {
        Grafo grafo = null;
        try {
            JSONObject jsonObject = new JSONObject(contenido);
            JSONArray verticesJSON = jsonObject.getJSONArray("vertices");
            JSONArray aristasJSON = jsonObject.getJSONArray("aristas");

            // Crear el grafo con la cantidad de vértices
            grafo = new Grafo(verticesJSON.length());

            // Agregar las aristas al grafo
            for (int i = 0; i < aristasJSON.length(); i++) {
                JSONObject aristaJSON = aristasJSON.getJSONObject(i);
                int origen = aristaJSON.getInt("origen");
                int destino = aristaJSON.getInt("destino");
                int peso = aristaJSON.getInt("peso");

                grafo.agregarArista(origen,destino, peso);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grafo;
    }

}