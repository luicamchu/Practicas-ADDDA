package ejercicios;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Ejercicio2 {
	
	// Nota de Diseño:
	// La lista debe construirse de atrás hacia adelante o de adelante hacia atrás, dependiendo de dónde se añade el valor en cada paso. 
	// Los métodos aquí añaden al inicio (índice 0 o addFirst) para evitar el coste de 'reverse'.
		// El uso de LinkedList optimiza el 'add(0, E)'/addFirst() a O(1), mientras que ArrayList sería O(n).
	
	// Nota de Diseño Crucial:
	// La construcción de la lista requiere añadir elementos al principio (orden inverso).
	// Los métodos Iterativo y Recursivo Final usan LinkedList y add(0, E)/addFirst() para lograr un coste O(1).
		// Si se usara ArrayList.add(0, E), el coste del algoritmo sería O(n²).
	

	// --- Solución RECURSIVA NO FINAL (Esquema 1: Clásico) ---
	// La lista se construye en la fase de RETORNO, consumiendo espacio en la pila de llamadas.
	public static List<Integer> f_RNF (Integer a, Integer b) {
		// Se usa LinkedList para garantizar que la adición en el retorno sea O(1).
		List<Integer> lista = new LinkedList<Integer>(); 
		
		// 1. Caso Base: La condición de parada.
		if(a<2 || b<2) {
			// El producto se añade al inicio (O(1)).
			lista.add(0, a*b); 
			return lista; // La recursión comienza a deshacerse.
		} 
		
		// 2. Paso Recursivo: La LLAMADA NO es la última operación.
		if(a>b) {
			// Llamada recursiva (va a la pila).
			lista = f_RNF(a%b, b-1); 
			
			// Operación pendiente: Adición al inicio DESPUÉS de que la llamada retorne (O(1)).
			lista.add(0, a); 
			return lista;

		} else {
			// Llamada recursiva (va a la pila).
			lista = f_RNF(a-2, b/2);
			
			// Operación pendiente: Adición al inicio DESPUÉS de que la llamada retorne (O(1)).
			lista.add(0, b);
			return lista;
		}
	}
	
	// --- Solución ITERATIVA (Esquema 3: Iterativo con while) ---
	// Construcción de lista eficiente O(n) utilizando la propiedad O(1) de LinkedList al inicio.
	public static List<Integer> f_it (Integer a, Integer b) {
		// Inicialización del acumulador (LinkedList para O(1) en add(0))
		List<Integer> lista = new LinkedList<Integer>();
		
		// Bucle: Mientras no se cumpla el caso base.
		while(!(a<2 || b<2)) { 
			// 1. Procesamiento y Añadido al acumulador al INICIO (O(1))
			if(a>b) {
				lista.add(0, a); // Adición O(1)
				// 2. Actualización de las variables de control (el paso 'nx')
				a=a%b;
				b=b-1;
			} else {
				lista.add(0, b); // Adición O(1)
				// 2. Actualización de las variables de control (el paso 'nx')
				a=a-2; 
				b=b/2;
			}
		}
		// Caso Base: El valor final se añade al inicio de la lista (O(1))
		lista.add(0, a*b); 
		return lista;
	}
	
	// --- Solución RECURSIVA FINAL (Esquema 2: Recursión Final) ---
	// Utiliza un acumulador mutable pasado como argumento (listaAcum).
	public static List<Integer> f_RF (Integer a, Integer b) {
		// Inicialización del acumulador
		List<Integer> lista = new LinkedList<Integer>();
		return f_RF_Privada(lista, a, b); // Llamada al auxiliar con el acumulador
	}
	
	private static List<Integer> f_RF_Privada(List<Integer> listaAcum, Integer a, Integer b) {
		// 1. Caso Base: La condición de parada. Se retorna el acumulador.
		if(a<2 || b<2) {
			// Operación O(1): El producto se añade al INICIO de la lista.
			listaAcum.add(0, a*b); 
			return listaAcum; // Retorna el acumulador
		} 
		
		// 2. Paso Recursivo: La operación se realiza ANTES de la llamada.
		if (a>b) {
			// Operación O(1): Se añade el elemento al INICIO, modificando el acumulador.
			listaAcum.add(0, a); 
			// 3. LLAMADA RECURSIVA FINAL: Última instrucción, optimizable a bucle.
			return f_RF_Privada(listaAcum, a%b, b-1);
		} else {
			// Operación O(1): Se añade el elemento al INICIO.
			listaAcum.add(0, b); 
			// 3. LLAMADA RECURSIVA FINAL: Última instrucción, optimizable a bucle.
			return f_RF_Privada(listaAcum, a-2, b/2);
		}
	}
	
	// --- Solución FUNCIONAL (Esquema 4: Basado en Streams) ---
	// Implementación INEFICIENTE O(n²) debido al coste de la inmutabilidad.
	public static List<Integer> f_funcional (Integer a, Integer b) {
		
		// 1. Generación del Stream:
		Tupla2 t = Stream.iterate(Tupla2.casoBase(a, b), e->e.otroCaso()) 
				// 2. Filtro y Finalización: Busca la primera tupla que cumple la condición de parada (Caso Base).
				.filter(e->(e.a()<2||e.b()<2)).findFirst().get();
		
		// 3. Post-procesamiento:
	    List<Integer> final_lista = new LinkedList<>(t.lista());
	    // addFirst en LinkedList es O(1)
	    final_lista.addFirst(t.a() * t.b()); 
		
		return final_lista;
	}
	
	// --- Solución FUNCIONAL V2 (Esquema 4: Usando Reduce para tomar el último elemento) ---
	public static List<Integer> f_funcional_v2 (Integer a, Integer b) {
	    
	    Tupla2 t = Stream.iterate(
	        Tupla2.casoBase(a, b),           // 1. Semilla
	        e -> !(e.a() < 2 || e.b() < 2),  // 2. HasNext: Condición de continuación.
	        e -> e.otroCaso()                // 3. Next: Función de transición.
	    )
	    // Operación Terminal: Reduce para obtener el ÚLTIMO elemento generado.
	    .reduce((first, second) -> second) 
	    .get();

	    // El post-procesamiento es O(1)
	    List<Integer> final_lista = new LinkedList<>(t.lista());
	    final_lista.addFirst(t.a() * t.b()); 
	    
	    return final_lista;
	}
	
	// --- RECORD que almacena el estado (Inmutabilidad) ---
	public static record Tupla2 (List<Integer> lista, Integer a, Integer b) {
		
		// Constructor que garantiza la inmutabilidad de la lista.
		public Tupla2 (List<Integer> lista, Integer a, Integer b) {
			// Advertencia: Esta copia defensiva es O(n) y es la causa de la complejidad total O(n²) del método funcional.
			this.lista = List.copyOf(lista); 
			this.a = a;
	        this.b = b;
		}
		
		public static Tupla2 casoBase(Integer a, Integer b) {
			List<Integer> aux = new LinkedList<Integer>();
			return new Tupla2(aux, a, b);
		}
		
		// La FUNCIÓN DE TRANSICIÓN (el nx)
		public Tupla2 otroCaso() {
			Integer new_a;
			Integer new_b;
			Integer element_to_add; 
			if(a>b) {
				element_to_add = a;
				new_a=a%b;
				new_b=b-1;
			} else {
				element_to_add = b;
				new_a=a-2; 
				new_b=b/2;
			}
			
			// ** EL PUNTO DE COSTO O(n²) **: Se construye una NUEVA lista en cada paso.
	        List<Integer> new_lista_mutable = new ArrayList<>();
	        
	        // 1. Añade el nuevo elemento al inicio (O(1)).
	        new_lista_mutable.add(0, element_to_add); 
	        
	        // 2. Añade TODOS los elementos de la lista ANTERIOR (O(n) - ¡Coste lineal!)
	        new_lista_mutable.addAll(this.lista);
	        
			// Devuelve una nueva instancia inmutable (Tupla2)
			return new Tupla2(new_lista_mutable, new_a, new_b);
		}
	}

	// [Se omiten los otros ejemplos para mantener el enfoque en Ejercicio2]
}