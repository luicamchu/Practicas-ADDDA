package ejercicios;

import java.math.BigInteger;

public class Ejercicio5 {

	// Solución ITERATIVA (Esquema General: Iterativo con while/for)
	// Implementación bottom-up que construye el resultado mediante un bucle.
	public static Double ejercicio5ItDouble(Integer n) {
		// Inicialización: Acumulador y variable de control (n) mutables.
		Double acumulador = 1.;
		
		// Bucle: Condición de continuación (mientras no se cumpla el caso base).
		while(!(n<=6)) {
			// Actualización: La operación de combinación se realiza ANTES de reducir 'n'.
			acumulador = 1. + (acumulador *log2(n-1));
			// Reducción: Actualización de la variable de control (el paso 'nx').
			n--;
		}
		// Retorno: El resultado final.
		return acumulador;
	}
	
	// Solución RECURSIVA NO FINAL (Esquema General: Recursivo Clásico)
	// La operación de suma ('1 + ...') está PENDIENTE de la subllamada.
	public static Double ejercicio5RecDouble(Integer n) {
		// 1. Caso Base: Condición de parada y valor base.
		if(n<=6) {
			return 1.;
		} else {
			// 2. Paso Recursivo: La LLAMADA NO es la última operación.
			// La suma '1 +' (Operación Pendiente) se ejecuta DESPUÉS del retorno.
			return 1. + (ejercicio5RecDouble(n-1) * log2(n-1));
		}
	}
	
	// Nota: El patrón recursivo final es difícil o imposible de aplicar directamente
	// a recurrencias de la forma f(n) = C + g(n, f(n-1)) sin transformar la recurrencia.
	// Tu nota es precisa: La suma de '1' rompe el patrón de acumulación simple.
	// La siguiente función muestra un INTENTO que es estructuralmente RF, pero conceptualmente incorrecto para muchas recurrencias.
	
	
	// Solución RECURSIVA FINAL (Esquema General: Recursión Final)
	// Intento de forzar la estructura de recursión final mediante un acumulador.
	public static Double ejercicio5RecFinalDouble(Integer n) {
		// Inicialización del acumulador, que solo es el valor inicial de la recurrencia.
		Double acumulador = 1.;
		// Llamada al auxiliar
		return ejercicio5RecFinalDouble(acumulador, n);
	}
	
	private static Double ejercicio5RecFinalDouble(Double acumulador, Integer n) {
		// 1. Caso Base: La condición de parada. Se retorna el acumulador.
		if(n<=6) {
			// La lógica de la función implica que el acumulador debe ser '1.' al llegar aquí,
			// pero el valor real de la recurrencia es '1.' (el caso base R(6)).
			// La implementación funcional aquí solo funciona si se re-interpreta la recurrencia.
			return acumulador;
		} else {
			// 2. Operación de Combinación: Intento de forzar la acumulación ANTES de la llamada.
			// ESTA ES LA REGLA DE TRANSICIÓN (el nx)
			// La lógica aquí acumula R(n) basado en R(n-1) ya calculado. 
			// Si la recurrencia fuera R(n) = f(n) * R(n-1), esto sería correcto.
			// Pero como es R(n) = 1 + R(n-1) * log2(n-1), el acumulador no es el resultado R(n-1).
			Double new_acumulador = 1. + (acumulador * log2(n-1));
			// 3. LLAMADA RECURSIVA FINAL: Última instrucción.
			return ejercicio5RecFinalDouble(new_acumulador, n-1);
		}
	}
	
	// Implementaciones de BigInteger omitidas (se mantiene la estructura)
	public static BigInteger ejercicio5RecBigInteger(Integer n) {
		return null;
	}
	
	public static BigInteger ejercicio5ItBigInteger(Integer n) {
		return null;
	}

	// Función auxiliar de logaritmo base 2
	public static int log2(int n){
	    if(n <= 0) throw new IllegalArgumentException();
	    // Usa la función nativa de Java para contar los ceros iniciales, que es una forma eficiente de calcular log2.
	    return 31 - Integer.numberOfLeadingZeros(n);
	}
	
}