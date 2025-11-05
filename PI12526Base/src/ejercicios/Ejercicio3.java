package ejercicios;

import java.util.HashMap;
import java.util.Map;

import us.lsi.common.IntTrio;

public class Ejercicio3 {
	
	public static void main(String[] args) {
		System.out.println(iterativo(4, 6, 7));
		System.out.println(recursivo_sin_memoria(4, 6, 7));
		System.out.println(recursivo_con_memoria(4, 6, 7));
	}

	// Solución ITERATIVA (Esquema General: Iterativo con while/for)
	// Implementa Programación Dinámica mediante TABULACIÓN (Bottom-up).
	public static Long iterativo(Integer a, Integer b, Integer c) {
		// Inicialización: El Map actúa como la 'Tabla' o 'Matriz' de DP.
		Map<IntTrio, Integer> mapABC = new HashMap<IntTrio, Integer>();
		Long valor = null;
		
		// Bucle: Controla la secuencia de ejecución de estado inicial (0,0,0) hasta (a,b,c).
		// Se calcula cada estado una única vez en orden creciente.
		for (int x = 0; x <= a; x++) {
			for (int y = 0; y <= b; y++) {
				for (int z = 0; z<= c; z++) {
					
					// CASO BASE (Condición de continuación en la recurrencia)
					if(x<3 || y<3 || z<3) {
						valor = (long) (x + Math.sqrt(y) + 2*z);
					} 
					// PASO RECURSIVO (Combinación de resultados previamente calculados en el Map)
					else if (x%y==0) {
						// Accede a subproblemas (que ya están garantizados en el Map)
						valor = (long) (mapABC.get(IntTrio.of(x-1, y/2, z/2)) + mapABC.get(IntTrio.of(x-3, y/3, z/3)));
					} else {
						// Accede a subproblemas
						valor = (long) (mapABC.get(IntTrio.of(x/3, y-3, z-3)) + mapABC.get(IntTrio.of(x/2, y-2, z-2)));
					}
					// Almacenamiento: El resultado de este estado se guarda para futuros usos.
					mapABC.put(IntTrio.of(x, y, z), valor.intValue());
				}
			}
		}
		// Retorno: El último 'valor' calculado corresponde al estado (a, b, c).
		return valor;
	}

	// Solución RECURSIVA SIN MEMORIA (Esquema General: Recursivo No Final)
	// La llamada recursiva NO es la última instrucción, y hay una operación de suma pendiente.
	// Sufre de complejidad exponencial por el recálculo de subproblemas.
	public static Long recursivo_sin_memoria(Integer a, Integer b, Integer c) {
		// 1. Caso Base: Condición de parada y retorno de valor simple.
		if(a<3 || b<3 || c<3) {
			return (long) (a + Math.sqrt(b) + 2*c);
		} 
		// 2. Paso Recursivo: La LLAMADA NO es la última operación.
		else if (a%b==0) {
			// El resultado se suma (Operación Pendiente)
			return recursivo_sin_memoria(a-1, b/2, c/2) + recursivo_sin_memoria(a-3, b/3, c/3);
		} else {
			// El resultado se suma (Operación Pendiente)
			return recursivo_sin_memoria(a/3, b-3, c-3) + recursivo_sin_memoria(a/2, b-2, c-2);
		}
	}

	// Solución RECURSIVA CON MEMORIA (Patrón: Memoization / Top-Down DP)
	// Utiliza el esquema Recursivo No Final pero añade un Map Acumulador.
	public static Long recursivo_con_memoria(Integer a, Integer b, Integer c) {
		// Inicialización: El Map de Memoization se inicializa fuera de la recursión.
		Map<IntTrio, Integer> mapABC = new HashMap<IntTrio, Integer>();
		// Llamada al auxiliar con el Map Acumulador.
		return recursivo_con_memoria(mapABC, a, b, c);
	}
	
	private static Long recursivo_con_memoria(Map<IntTrio, Integer> mapABC, Integer a, Integer b, Integer c) {
		IntTrio i = IntTrio.of(a, b, c);
		
		// CHECKPOINT (Patrón Memoization): Si el estado ya está calculado, se retorna inmediatamente.
		if(mapABC.containsKey(i)) {
			return mapABC.get(i).longValue();
		}
		
		Long valor;
		// 1. Caso Base
		if(a<3 || b<3 || c<3) {
			valor = (long) (a + Math.sqrt(b) + 2*c);
		} 
		// 2. Paso Recursivo No Final (Las llamadas se hacen, pero el resultado se guarda antes de retornar)
		else if(a%b==0) {
			// Las llamadas recursivas se ejecutan, y el resultado final se asigna a 'valor'.
			valor = recursivo_con_memoria(mapABC, a-1, b/2, c/2) + recursivo_con_memoria(mapABC, a-3, b/3, c/3);
		} else {
			// Las llamadas recursivas se ejecutan, y el resultado final se asigna a 'valor'.
			valor = recursivo_con_memoria(mapABC, a/3, b-3, c-3) + recursivo_con_memoria(mapABC, a/2, b-2, c-2);
		}
		
		// ALMACENAMIENTO (Patrón Memoization): Se guarda el resultado antes de que la función retorne.
		mapABC.put(i, valor.intValue());
		return valor;
	}

}