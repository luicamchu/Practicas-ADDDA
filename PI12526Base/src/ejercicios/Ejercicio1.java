package ejercicios;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.geometria.Cuadrante;
import us.lsi.geometria.Punto2D;


public class Ejercicio1 {

	// Del enunciado:
	public static Map<Integer,List<String>> solucionFuncional(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		UnaryOperator<EnteroCadena> nx = elem -> {
			return EnteroCadena.of(elem.a()+2, elem.a()%3==0 ? elem.s()+elem.a().toString() : elem.s().substring(elem.a()%elem.s().length()));
		};					
		return Stream.iterate(EnteroCadena.of(varA,varB), elem -> elem.a() < varC, nx)
					.map(elem -> elem.s() + varD)
					.filter(nom -> nom.length() < varE)
					.collect(Collectors.groupingBy(String::length));
	}
	
	public static Map<Integer,List<String>> solucionIterativa(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		Integer elemA = varA;
		String elemS = varB;
		EnteroCadena e = EnteroCadena.of(elemA, elemS);
		Map<Integer, List<String>> m = new HashMap<Integer, List<String>>();
		
		while(!(elemA >= varC)) {
			Integer newElemA = elemA + 2;
			String newElemS = elemA%3==0 ? elemS+elemA.toString() : elemS.substring(elemA%elemS.length());
			
			
			String nom = elemS + varD;
			
			if(nom.length() < varE) {
				if(m.containsKey(nom.length())) {
					List<String> ls = m.get(nom.length());
					ls.add(nom);
					m.put(nom.length(), ls);
				} else {
					List<String> ls = new LinkedList<String>();
					ls.add(nom);
					m.put(nom.length(), ls);
				}
			}
			
			e=EnteroCadena.of(newElemA,newElemS);
			elemA = e.a();
			elemS = e.s();
		}
		return m;
	}
	
	public static Map<Integer,List<String>> solucionIterativa2(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		EnteroCadena e = EnteroCadena.of(varA, varB);
		Map<Integer, List<String>> m = new HashMap<Integer, List<String>>();
		
		while((e.a() < varC)) {
			String nom = e.s() + varD;
			if(nom.length() < varE) {
				if(m.containsKey(nom.length())) {
					List<String> ls = m.get(nom.length());
					ls.add(nom);
					m.put(nom.length(), ls);
				} else {
					List<String> ls = new LinkedList<String>();
					ls.add(nom);
					m.put(nom.length(), ls);
				}
			}
			Integer elemA = e.a() + 2;
			String elemS = e.a()%3==0 ? e.s()+e.a().toString() : e.s().substring(e.a()%e.s().length());
			e=EnteroCadena.of(elemA,elemS);
		}
		
		return m;
	}
	
	public static Map<Integer,List<String>> solucionRecursivaFinal(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		EnteroCadena e = EnteroCadena.of(varA, varB);
		Map<Integer, List<String>> m = new HashMap<Integer, List<String>>();
		
		return recursivaFinal(e, m, varC, varD, varE);
	}
	
	private static Map<Integer,List<String>> recursivaFinal(EnteroCadena e, Map<Integer, List<String>> m, Integer varC, String varD, Integer varE) {
		// 1. CASO BASE (Predicado de parada del Stream.iterate)
		if (e.a() >= varC) {
	        return m; // Retorna el mapa si la condición de continuación es falsa
		}
		// 2. PASO RECURSIVO: PROCESAR EL ESTADO ACTUAL (e)
		String nom = e.s() + varD;
		if(nom.length() < varE) {
			if(m.containsKey(nom.length())) {
				List<String> ls = m.get(nom.length());
				ls.add(nom);
				m.put(nom.length(), ls);
			} else {
				List<String> ls = new LinkedList<String>();
				ls.add(nom);
				m.put(nom.length(), ls);
			}
		}
		Integer elemA = e.a() + 2;
		String elemS = e.a()%3==0 ? e.s()+e.a().toString() : e.s().substring(e.a()%e.s().length());
		e=EnteroCadena.of(elemA,elemS);
		return recursivaFinal(e, m, varC, varD, varE);
	}

}
