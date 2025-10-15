package ejercicios;

import java.util.List;

public class Ejercicio2 {
	
	//En este ejercicio 2 no se usar addAll porque añade complejidad lineal. 
	//Tampoco darle la vuelta a la lista, tambien tiene un coste añadido. 
	//lo que hay que hacer es añadir bien los datos.

	public static List<Integer> f_RNF (Integer a, Integer b) {
		
		return null;
	}
	
	public static List<Integer> f_it (Integer a, Integer b) {
		return null;
	}
	
	public static List<Integer> f_RF (Integer a, Integer b) {
		return null;
	}
	
	public static List<Integer> f_funcional (Integer a, Integer b) {
		return null;	
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
