/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package graphproyect;

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
        
     try{
         //
         String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
         
         JSONObject json = new JSONObject(contenido);
         
         JSONObject graph = json.getJSONObject("graph");
         JSONArray nodes = graph.getJSONArray("nodes");
         
         Scanner ZZZ = new Scanner(System.in);
         
         System.out.print("Ingresa ID del nodo");
         int NodeId = Scanner.nextInt();
         
         
         
         
         
         
         
         
     }catch(IOException z){
         z.printStackTrace(); // error
     }
     
    }
    
}
