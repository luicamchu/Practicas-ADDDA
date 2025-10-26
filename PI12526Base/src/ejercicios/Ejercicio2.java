package ejercicios;

import java.util.LinkedList;
import java.util.List;

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
		return null;
	}
	
	public static record Tupla2 (List<Integer> listaAcum, Integer a, Integer b) {
		public static Tupla2 constructor (List<Integer> l, Integer a, Integer b) {
			return new Tupla2(l, a, b);
		}
		public List<Integer> casoBase(Integer a, Integer b) {
			listaAcum.add(a*b);
			return listaAcum;
		}
		
		public List<Integer> otroCaso(Integer a, Integer b) {
			return null;
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
