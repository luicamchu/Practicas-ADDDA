package ejercicio3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.LargestDegreeFirstColoring;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultUndirectedGraph;

import us.lsi.common.Pair;
import us.lsi.graphs.views.SubGraphView;


public class Ejercicio3 {

	//	3. Se tiene un conjunto de investigadores de prestigio que forman parte de una red, de los
	//	que se conoce su identificador numérico único, su año de nacimiento, y su universidad de
	//	origen. De cada par de investigadores se conoce el número de artículos que tienen en
	//	común (puede ser 0). Considere que el grafo que representa a los autores y sus
	//	colaboraciones es conexo.

	//	a) Obtenga una vista del grafo que sólo incluya:
	//	• Los investigadores nacidos antes de 1982 o que tengan más de 5 artículos con
	//	alguno de sus coautores.
	//	• Las colaboraciones más relevantes (número de artículos mayor que 5).
	//	Muestre el grafo configurando su apariencia de forma que se resalten los vértices y
	//	las aristas de la vista.

	public static Graph<Investigador,Colaboracion> getSubgraph_EJ3A(Graph <Investigador, Colaboracion> g ) {
		// 1. Predicado de Aristas (Colaboraciones)
		Predicate<Colaboracion> filtroAristas =  c -> c.getNColaboraciones()>5 ;
		// 2. Predicado de Nodos (Investigadores)
		Predicate<Investigador> filtroNodos =  i -> i.getFNacimiento()<1982 || g.edgesOf(i).stream().anyMatch(filtroAristas);
		Graph<Investigador, Colaboracion> vista = SubGraphView.of(g, filtroNodos, filtroAristas);
		return vista;
	}

	//	b) Implemente un método que devuelva un conjunto con los 5 investigadores que
	//	tienen un mayor número de investigadores colaboradores. Muestre el grafo
	//	configurando su apariencia de forma que se resalten dichos investigadores.

	public static List<Investigador> getMayoresColaboradores_E3B (Graph<Investigador,Colaboracion> g) {
		// 1. Calcular el grado (número de colaboradores) de cada investigador.
		// Se crea un mapa: Investigador -> Grado
		Map<Investigador, Integer> grados = g.vertexSet().stream()
				.collect(Collectors.toMap(
						i -> i, 
						g::degreeOf 
						));
		// 2. Ordenar los investigadores por su grado (descendente)
		List<Investigador> listaOrdenada = grados.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.map(Map.Entry::getKey) // Extraer solo el Investigador (la clave)
				.collect(Collectors.toList());
		// 3. Devolver la sublista con los 5 primeros (o menos, si el grafo es pequeño)
		int limite = Math.min(5, listaOrdenada.size());
		return listaOrdenada.subList(0, limite);
	}

	//	c) Implemente un método que devuelva un map que almacene para cada investigador
	//	una lista de investigadores colaboradores ordenados de mayor a menor número de
	//	artículos conjuntos con él. Muestre el grafo configurando su apariencia de forma
	//	que se resalte para cada investigador su colaboración con mayor número de
	//	artículos.

	public static Map<Investigador,List<Investigador>> getMapListaColabroradores_E3C (Graph<Investigador,Colaboracion> g) {
		Map<Investigador, List<Investigador>> resultado = new HashMap<>();
		// 1. Iterar sobre todos los investigadores (v)
		for (Investigador v : g.vertexSet()) {
			// Mapa temporal: Colaborador -> Número de artículos (Double, según tu clase Colaboracion)
			Map<Investigador, Double> mapaColaboraciones = new HashMap<>();
			// 2. Encontrar todos los colaboradores y sus artículos
			for (Colaboracion e : g.edgesOf(v)) {
				// Determinar el colaborador 'u' que no es 'v'
				Investigador u = g.getEdgeSource(e).equals(v) ? 
					(Investigador)g.getEdgeTarget(e) : 
					(Investigador)g.getEdgeSource(e);
				// USANDO EL DOUBLE DEVUELTO POR TU MÉTODO
				double articulos = e.getNColaboraciones(); 
				mapaColaboraciones.put(u, articulos);
			}
			// 3. Obtener la lista de colaboradores (keys del mapa) y ordenarla
			List<Investigador> colaboradoresOrdenados = mapaColaboraciones.keySet().stream()
					// Ordenar usando el valor del mapa (artículos) de forma descendente
					.sorted(Comparator.comparingDouble(mapaColaboraciones::get).reversed())
					.collect(Collectors.toList());
			resultado.put(v, colaboradoresOrdenados);
		}
		return resultado;
	}

	//	d) La distancia entre 2 investigadores es el número mínimo de investigadores
	//	intermedios que los relacionan, o 0 en caso de que estén relacionados directamente
	//	entre sí. Determine cuál es el par de investigadores que están más alejados, o uno
	//	de los pares en caso de que haya más de uno.

	public static Pair<Investigador,Investigador> getParMasLejano_E3D (Graph<Investigador,Colaboracion> g) {
		Investigador investigadorA = null;
		Investigador investigadorB = null;
		double maxPathLength = -1; // Máxima longitud del camino (número de aristas)
		List<Investigador> vertices = new LinkedList<>(g.vertexSet());
		// 1. Iterar sobre cada investigador 'u' para usarlo como fuente
		// Este enfoque repite el algoritmo de Fuente Única para lograr Todos los Pares (APSP).
		for (Investigador u : vertices) {
			// 2. Ejecutar Dijkstra para el nodo fuente 'u'
			var dsp = new DijkstraShortestPath<>(g);
			// Obtener el objeto que contiene todos los caminos más cortos desde 'u'
			ShortestPathAlgorithm.SingleSourcePaths<Investigador, Colaboracion> pathsFromU = dsp.getPaths(u); 
			// 3. Comparar 'u' con todos los demás investigadores 'v'
			for (Investigador v : vertices) {
				if (u.equals(v)) continue;
				// 4. Obtener la longitud del camino (número de aristas)
				Double length = pathsFromU.getWeight(v);
				// Si el camino existe y es mayor que el máximo actual
				if (length != null && !Double.isInfinite(length) && length > maxPathLength) {
					maxPathLength = length;
					investigadorA = u;
					investigadorB = v;
				}
			}
		}
		// La distancia solicitada es L-1 (número de intermedios). 
		// Devolvemos el par que maximiza L.
		if (investigadorA != null) {
			return Pair.of(investigadorA, investigadorB); 
		} else {
			return Pair.of(null, null); 
		}
	}

	//	e) Se desea fomentar nuevas colaboraciones entre investigadores que aún no han
	//	colaborado, para lo cual se van a organizar una serie de reuniones online (sin
	//	límite de asistentes). Minimice el número de reuniones que deben organizarse de
	//	forma que 2 investigadores colaboradores o que tengan la misma universidad de
	//	origen no pueden acudir a la misma reunión.

	public static List<Set<Investigador>> getReuniones_E3E (Graph<Investigador,Colaboracion> g) {
		// 1. Construir el Grafo de Conflicto G_conflict
		Graph<Investigador, Colaboracion> gConflict = new DefaultUndirectedGraph<>(Colaboracion.class);
		g.vertexSet().forEach(gConflict::addVertex);
		List<Investigador> vertices = new LinkedList<>(g.vertexSet());
		for (int i = 0; i < vertices.size(); i++) {
			Investigador u = vertices.get(i);
			for (int j = i + 1; j < vertices.size(); j++) {
				Investigador v = vertices.get(j);
				boolean sonColaboradores = g.containsEdge(u, v);
				boolean mismaUniversidad = u.getUniversidad().equals(v.getUniversidad());
				if (sonColaboradores || mismaUniversidad) {
					if (!gConflict.containsEdge(u, v)) {
						gConflict.addEdge(u, v, Colaboracion.of()); 
					}
				}
			}
		}
		// 2. Calcular la Coloración (Heurística)
		var coloracionAlg = new LargestDegreeFirstColoring<>(gConflict);
		// Obtener el objeto de coloración
		VertexColoringAlgorithm.Coloring<Investigador> resultadoColoracion = coloracionAlg.getColoring();
		// Obtener el mapa que asocia cada investigador con su número de color (reunión)
		Map<Investigador, Integer> asignacionColores = resultadoColoracion.getColors();
		// 3. Mapear los colores a las reuniones (grupos)
		Map<Integer, Set<Investigador>> reunionesMap = new HashMap<>();
		// Iteramos sobre el mapa de asignación de colores
		for (Map.Entry<Investigador, Integer> entry : asignacionColores.entrySet()) {
			Investigador investigador = entry.getKey();
			int colorId = entry.getValue(); // El número de color/reunión
			reunionesMap.computeIfAbsent(colorId, k -> new HashSet<>()).add(investigador);
		}
		// 4. Devolver la lista de sets (reuniones)
		return new ArrayList<>(reunionesMap.values());
	}

}
