/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package graphproyect;
import java.util.*;

/**
 *
 * @author lolsg
 */
public class GraphProyect {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Grafo miGrafo = new Grafo(5);
        
        miGrafo.agregarArista(4, 3, 2);
        miGrafo.agregarArista(4, 6, 2);
        miGrafo.agregarArista(4, 1, 2);
        miGrafo.agregarArista(4, 9, 2);
        
        List<Arista> vecinos4 = miGrafo.obtenerVecinos(4);
        
        for(Arista vecino:vecinos4){
            System.out.println(vecino.getDestino());
            
        }
    }
    
}
