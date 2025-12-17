package ejercicio4;


import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.tour.HeldKarpTSP;


public class Ejercicio4 {
	/**
     * Calcula el camino de menor duración entre dos monumentos dados.
     * @param mIn Nombre del monumento de inicio.
     * @param mOut Nombre del monumento de destino.
     * @param g Grafo de Intersecciones (vértices) y Calles (aristas).
     * @param ftest Parámetro no utilizado para el cálculo.
     * @return El camino más corto (menor duración) como un objeto GraphPath, o null si no existe.
     */
	
	public static GraphPath<Interseccion,Calle> getSubgraph_EJ4A(String mIn, String mOut,Graph <Interseccion, Calle> g, String ftest) {
		// 1. Validar y Buscar Vértices de Origen y Destino
        Interseccion origen = g.vertexSet().stream().filter(v->v.hasMonumento() && v.getNombre().equals(mIn))
        		.findFirst() // <-- Devuelve un Optional<Interseccion>
        		.orElse(null); // <-- Si no se encuentra, devuelve null
        
        Interseccion destino = g.vertexSet().stream().filter(v->v.hasMonumento() && v.getNombre().equals(mOut))
        		.findFirst()
        		.orElse(null);
        
        // 2. Crear el Algoritmo de Dijkstra 
        var dijkstraAlg = new DijkstraShortestPath<>(g);
        
		// 3. Ejecutar el Algoritmo
        // El método getPath calcula el camino más corto entre los dos vértices.
        GraphPath<Interseccion, Calle> shortestPath = dijkstraAlg.getPath(origen, destino);
		
        // 4. Devolver el resultado
        if (origen == null || destino == null) {
            System.err.println("❌ Error: Uno o ambos monumentos no se encontraron o no existen.");
            return null;
        }
        
        if (shortestPath != null) {
            System.out.println("✅ Camino de menor duración (" + shortestPath.getWeight() + " min) encontrado.");
        } else {
            System.out.println("❌ No existe camino entre " + mIn + " y " + mOut);
        }
        
        return shortestPath;
	}
	/*
	 * b) Determine la ruta de menor esfuerzo que pase por todas las intersecciones 
	exactamente una vez y vuelva al origen. Muestre el grafo configurando su 
	apariencia de forma que se resalte dicha ruta.  
	 * */
	public static GraphPath<Interseccion,Calle> getRecorrido_E4B(Graph<Interseccion, Calle> g) {
		// 1. Crear el Algoritmo del Viajante (Held-Karp)
	    // El algoritmo de Held-Karp calcula la solución óptima del TSP.
	    var tspAlg = new HeldKarpTSP<Interseccion, Calle>(); 
	    
	    // 2. Calcular la ruta (Ciclo Hamiltoniano/Tour)
	    // El método getTour(Graph g) ejecuta el algoritmo y devuelve el ciclo como un GraphPath.
	    GraphPath<Interseccion, Calle> cicloTSP = tspAlg.getTour(g);

	 // 3. Devolver el resultado y la impresión
	    if (cicloTSP != null) {
	        double esfuerzoTotal = cicloTSP.getWeight();
	        List<Interseccion> ruta = cicloTSP.getVertexList();
	        
	        System.out.println("✅ Ciclo TSP (Held-Karp, Óptimo) de menor esfuerzo total (" + esfuerzoTotal + " esf) encontrado.");
	        System.out.println("Ruta de intersecciones: " + ruta);
	        
	        // --- Configuración de Apariencia (Resaltado) ---
	        // Se resalta la ruta de menor esfuerzo encontrada por el algoritmo.
	        System.out.println("\n--- Configurando Apariencia (Resaltado de la Ruta) ---");
	        System.out.println("Vértices del ciclo: " + ruta);
	        System.out.println("Aristas del ciclo: " + cicloTSP.getEdgeList());
	        
	    } else {
	        System.out.println("❌ No se pudo encontrar un ciclo Hamiltoniano que incluya todas las intersecciones.");
	    }
        
        return cicloTSP;
    }
	
	public static Graph<Interseccion,Calle> getRecorridoMaxRelevante_E4C(Set<Calle> cs,Graph <Interseccion, Calle> g, String ftest) {
		
		return null;
	}
		
}
