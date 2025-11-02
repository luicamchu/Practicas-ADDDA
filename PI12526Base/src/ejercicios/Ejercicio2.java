package ejercicios;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Ejercicio2 {
	
	//En este ejercicio 2 no se usar addAll porque añade complejidad lineal. 
	//Tampoco darle la vuelta a la lista, tambien tiene un coste añadido. 
	//lo que hay que hacer es añadir bien los datos.

	public static List<Integer> f_RNF (Integer a, Integer b) {
		List<Integer> lista = new LinkedList<Integer>();
		if(a<2 || b<2) {
			lista.add(a*b);
			return lista;

		} else if(a>b) {
			lista = f_RNF(a%b, b-1); 
			lista.add(a);
			return lista;

		} else {
			lista = f_RNF(a-2, b/2);
			lista.add(b);
			return lista;

		}
	}
	
	public static List<Integer> f_it (Integer a, Integer b) {
		List<Integer> lista = new LinkedList<Integer>();
		while(!(a<2 || b<2)) {
			if(a>b) {
				lista.add(0, a);
				a=a%b;
				b=b-1;
			} else {
				lista.add(0, b);
				a=a-2; 
				b=b/2;
			}
		}
		lista.add(0, a*b);
		return lista;
	}
	
	public static List<Integer> f_RF (Integer a, Integer b) {
		List<Integer> lista = new LinkedList<Integer>();
		return f_RF_Privada(lista, a, b);
	}
	
	private static List<Integer> f_RF_Privada(List<Integer> listaAcum, Integer a, Integer b) {
		if(a<2 || b<2) {
			listaAcum.addFirst(a*b);
			return listaAcum;
		} else if (a>b) {
			listaAcum.addFirst(a);
			return f_RF_Privada(listaAcum, a%b, b-1);
		} else {
			listaAcum.addFirst(b);
			return f_RF_Privada(listaAcum, a-2, b/2);
		}
	}
	
	public static List<Integer> f_funcional (Integer a, Integer b) {
		Tupla2 t = Stream.iterate(Tupla2.casoBase(a, b), e->e.otroCaso())
				.filter(e->(e.a()<2||e.b()<2)).findFirst().get();
		
		// Una vez encontrada la tupla final, preparamos la lista de resultado
	    // (el producto del caso base va al principio, como en f_RF).
	    List<Integer> final_lista = new LinkedList<>(t.lista());
	    final_lista.addFirst(t.a() * t.b()); // 👈 Añade el producto de la tupla final
		
		return final_lista;
	}
	public static List<Integer> f_funcional_v2 (Integer a, Integer b) {
	    
	    Tupla2 t = Stream.iterate(
	        Tupla2.casoBase(a, b),           // 1. Initial: La tupla inicial.
	        e -> !(e.a() < 2 || e.b() < 2),  // 2. HasNext (Predicado de parada): Continúa MIENTRAS esta condición sea VERDADERA.
	        e -> e.otroCaso()                // 3. Next (Función de transición): Genera la siguiente tupla.
	    )
	    //es un patrón común para tomar el último elemento de un stream finito.
	    .reduce((first, second) -> second) // Toma el ÚLTIMO elemento generado antes de la parada.
	    .get();

	    // El post-procesamiento para añadir el valor final es el mismo
	    List<Integer> final_lista = new LinkedList<>(t.lista());
	    final_lista.addFirst(t.a() * t.b()); 
	    
	    return final_lista;
	}
	
	public static record Tupla2 (List<Integer> lista, Integer a, Integer b) {
		
		public Tupla2 (List<Integer> lista, Integer a, Integer b) {
			this.lista = List.copyOf(lista);
			this.a = a;
	        this.b = b;
		}
		
		public static Tupla2 casoBase(Integer a, Integer b) {
			List<Integer> aux = new LinkedList<Integer>();
			//aux.add(0, a*b);
			return new Tupla2(aux, a, b);
		}
		
		public Tupla2 otroCaso() {
			Integer new_a;
			Integer new_b;
			Integer element_to_add; // Elemento a añadir en este paso
			if(a>b) {
				element_to_add = a;
				new_a=a%b;
				new_b=b-1;
			} else {
				element_to_add = b;
				new_a=a-2; 
				new_b=b/2;
			}
			// 🔑 PASO CLAVE: Construir una NUEVA lista inmutable para la siguiente tupla
	        // 1. Creamos una lista mutable temporal (ArrayList) con el nuevo elemento al inicio.
	        List<Integer> new_lista_mutable = new ArrayList<>();
	        new_lista_mutable.add(0, element_to_add); 
	        
	        // 2. Añadimos TODOS los elementos de la lista ANTERIOR
	        new_lista_mutable.addAll(this.lista);
			return new Tupla2(new_lista_mutable, new_a, new_b);
		}
	}

	//Ejemplo: 
	
	//rnf
	public static String solRNF(Integer a, Integer b) {
		String r = null;
		if (a < 5 || b < 5) {
			Integer t = a * b;
			r = "(" + t + ")";
			//r = String.format("(%d)", a * b);
		}else {
			r = String.format("%d", a + b) + solRNF(a/2,  b-2);
		}
		return r;
	}
	
	//rf
	//metodo publico
	public static String solRF(Integer a, Integer b) {
		// TODO Auto-generated method stub
		return recFinal("", a, b);
	}
	//metodo privado
	private static String recFinal(String ac, Integer a, Integer b) {
		String r = null;
		if (a < 5 || b < 5) {
			Integer t = a * b;
			r = ac + "(" + t + ")";
			//r = String.format("(%d)", a * b);
		}else {
			r = recFinal(String.format("%d", a + b), a/2, b-2);
		}
		return r;
	}
	
	//iterativo
	private static String solucionIterativa(Integer a, Integer b) {
		String ac = "";
		while(!(a < 5 || b < 5)) {
			ac = String.format("%s%d", ac, a + b);
			a /= 2;
			b -= 2;
		}
		return ac + String.format("%d", a * b);
	}
	
	//solFuncional
	public static record Tupla(String acumulador, Integer a, Integer b) {
		public static Tupla constructor(String ac, Integer a, Integer b) {
			return new Tupla(ac, a, b);
		}
		
		public static Tupla casoBase(Integer a, Integer b) {
			return constructor("", a, b);
		}
		
		public Tupla otroCaso() {
			return constructor(acumulador + String.format("%d", a+b), a, b);
		}
	}
	
}
