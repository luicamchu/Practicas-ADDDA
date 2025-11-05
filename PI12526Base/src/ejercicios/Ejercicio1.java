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

	// Solución FUNCIONAL (Esquema 4: Basado en Streams)
	// Implementación declarativa que evita variables mutables explícitas.
	public static Map<Integer,List<String>> solucionFuncional(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		// UNARYOPERATOR: Define la FUNCIÓN DE TRANSICIÓN DE ESTADO (el 'nx')
		// Calcula el siguiente elemento (a', s') a partir del elemento actual (a, s).
		UnaryOperator<EnteroCadena> nx = elem -> {
			return EnteroCadena.of(elem.a()+2, elem.a()%3==0 ? elem.s()+elem.a().toString() : elem.s().substring(elem.a()%elem.s().length()));
		};					
		
		return Stream.iterate(
					EnteroCadena.of(varA,varB), // 1. Elemento semilla/inicial (Estado inicial)
					elem -> elem.a() < varC,    // 2. Predicado de parada/continuación (La condición del while)
					nx)                         // 3. Función de sucesión (Cómo calculo el siguiente estado)
					// Operaciones Intermedias
					.map(elem -> elem.s() + varD) // Aplica la transformación requerida a cada elemento
					.filter(nom -> nom.length() < varE) // Aplica el filtro
					// Operación Terminal (GROUPINGBY): Realiza la reducción y colección final
					.collect(Collectors.groupingBy(String::length));
	}
	
	// Solución ITERATIVA 1 (Esquema 3: Iterativo con while)
	// Usa variables mutables (elemA, elemS) y bucles para controlar el flujo.
	public static Map<Integer,List<String>> solucionIterativa(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		// Inicialización de las variables de control y el acumulador (Map)
		Integer elemA = varA;
		String elemS = varB;
		EnteroCadena e = EnteroCadena.of(elemA, elemS);
		Map<Integer, List<String>> m = new HashMap<Integer, List<String>>(); // Acumulador
		
		// Bucle: Condición de continuación (el negado del predicado de parada)
		while(!(elemA >= varC)) { 
			// 1. Cálculo del siguiente estado (Función de Transición)
			Integer newElemA = elemA + 2;
			String newElemS = elemA%3==0 ? elemS+elemA.toString() : elemS.substring(elemA%elemS.length());
			
			// 2. Procesamiento y Acumulación del estado actual (Dentro del bucle)
			String nom = elemS + varD;
			
			if(nom.length() < varE) {
				// Lógica de agrupación y acumulación manual en el Map
				if(m.containsKey(nom.length())) {
					List<String> ls = m.get(nom.length());
					ls.add(nom);
					m.put(nom.length(), ls); // Se actualiza la lista
				} else {
					List<String> ls = new LinkedList<String>();
					ls.add(nom);
					m.put(nom.length(), ls); // Se crea un nuevo grupo
				}
			}
			
			// 3. Actualización de las variables de control para la siguiente iteración
			e=EnteroCadena.of(newElemA,newElemS);
			elemA = e.a();
			elemS = e.s();
		}
		// Retorno del acumulador final
		return m;
	}
	
	// Solución ITERATIVA 2 (Esquema 3: Iterativo con while - Ligeramente refactorizado)
	// Es funcionalmente idéntica a la anterior, solo varía el orden de actualización.
	public static Map<Integer,List<String>> solucionIterativa2(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		EnteroCadena e = EnteroCadena.of(varA, varB);
		Map<Integer, List<String>> m = new HashMap<Integer, List<String>>();
		
		while((e.a() < varC)) { // Condición de continuación (el mismo predicado del Stream.iterate)
			// Procesamiento del estado actual (e)
			String nom = e.s() + varD;
			if(nom.length() < varE) {
				// Lógica de acumulación
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
			// Cálculo y actualización del siguiente estado
			Integer elemA = e.a() + 2;
			String elemS = e.a()%3==0 ? e.s()+e.a().toString() : e.s().substring(e.a()%e.s().length());
			e=EnteroCadena.of(elemA,elemS); // Actualización de la variable de control 'e'
		}
		
		return m;
	}
	
	// Solución RECURSIVA FINAL (Esquema 2: Recursión Final)
	// La función pública inicia la recursión y la función auxiliar contiene el cuerpo.
	public static Map<Integer,List<String>> solucionRecursivaFinal(Integer varA, String varB, Integer varC, String varD, Integer varE) {
		// Inicialización del estado inicial (e) y el acumulador (m)
		EnteroCadena e = EnteroCadena.of(varA, varB);
		Map<Integer, List<String>> m = new HashMap<Integer, List<String>>();
		
		// Llamada inicial a la función auxiliar
		return recursivaFinal(e, m, varC, varD, varE);
	}
	
	private static Map<Integer,List<String>> recursivaFinal(EnteroCadena e, Map<Integer, List<String>> m, Integer varC, String varD, Integer varE) {
		// 1. CASO BASE: Predicado de parada (Si la condición de continuación es FALSA)
		if (e.a() >= varC) {
	        return m; // Se retorna el ACUMULADOR (el Map)
		}
		
		// 2. PASO RECURSIVO: Se procesa el estado actual y se calcula el nuevo acumulador.
		String nom = e.s() + varD;
		if(nom.length() < varE) {
			// Lógica de acumulación (Aquí 'm' se MUTARÍA en Java, pero conceptualmente es el nuevo acumulador)
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
		
		// Cálculo del nuevo estado (la FUNCIÓN DE SUCESIÓN/TRANSICIÓN)
		Integer elemA = e.a() + 2;
		String elemS = e.a()%3==0 ? e.s()+e.a().toString() : e.s().substring(e.a()%e.s().length());
		e=EnteroCadena.of(elemA,elemS); // Nuevo estado 'e'
		
		// 3. LLAMADA RECURSIVA FINAL: Es la ÚLTIMA acción ejecutada, optimizable a un bucle.
		return recursivaFinal(e, m, varC, varD, varE);
	}

}