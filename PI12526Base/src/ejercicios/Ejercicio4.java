package ejercicios;

import java.math.BigInteger;

public class Ejercicio4 {
	
	// --- IMPLEMENTACIONES DOUBLE (Aritmética de punto flotante nativa) ---
	
	// Solución RECURSIVA NO FINAL (Esquema 1: Recursivo Clásico)
	// La operación de multiplicación está PENDIENTE hasta que la subllamada retorne, consumiendo la pila.
	public static Double funcRecDouble(Integer a) {
		// 1. Caso Base: Condición de parada y retorno del valor inicial.
		if(a<10) {
			return 5.;
		} else {
			// 2. Paso Recursivo: La LLAMADA NO es la última operación.
			// La multiplicación (Operación Pendiente) se ejecuta después de la llamada.
			return (Math.sqrt(3*a) * funcRecDouble(a-2));
		}
	}
	
	// Solución RECURSIVA FINAL (Esquema 2: Tail Recursion)
	// Utiliza un acumulador explícito para que la llamada recursiva sea la última acción.
	public static Double funcRecFinalDouble(Integer a) {
		Double acumulador = 1.; // Inicialización del acumulador
		return recFinal(acumulador, a);
	}
	
	// Función auxiliar privada para la Recursión Final
	private static Double recFinal(Double acumulador, Integer a) {
		// 1. Caso Base: Se retorna el valor del ACUMULADOR.
		if(a<10) {
			return acumulador*5.; 
		} else {
			// 2. Operación de Combinación: El cálculo se hace ANTES de la llamada.
			Double nuevoAcumulador = acumulador * Math.sqrt(3*a);
			// 3. LLAMADA RECURSIVA FINAL: Es la ÚLTIMA instrucción, optimizable a bucle.
			return recFinal(nuevoAcumulador, a-2);
		}
	}
	
	// Solución ITERATIVA (Esquema 3: Iterativo con while/for)
	// Usa variables mutables (a, acumulador) y un bucle para controlar el flujo.
	public static Double funcItDouble(Integer a) {
		// Inicialización: Variables mutables (acumulador y variable de control 'a')
		Double acumulador = 1.;
		
		// Bucle: Condición de continuación (mientras NO sea el caso base)
		while(!(a<10)) {
			// Actualización: El acumulador se modifica en cada paso.
			acumulador = acumulador * Math.sqrt(3*a);
			// Actualización: La variable de control se reduce (nx).
			a-=2;
		}
		// Retorno: Resultado final
		return acumulador * 5.;
	}
	
	// --- IMPLEMENTACIONES BIGINTEGER (Aritmética de precisión arbitraria) ---

	// Solución RECURSIVA NO FINAL (Esquema 1: Recursivo Clásico)
	// Idéntica estructura al RNF de Double, pero con operaciones BigInteger.
	public static BigInteger funcRecBig(Integer a) {
		// Inicialización del resultado local.
		BigInteger r = BigInteger.valueOf(1L); 
		
		// 1. Caso Base: Condición de parada.
		if(a<10) {
			r = BigInteger.valueOf(5L);
		} else {
			// 2. Paso Recursivo: La LLAMADA NO es la última operación.
			// La multiplicación (Operación Pendiente) se ejecuta después de la llamada.
			r = BigInteger.valueOf((long) Math.sqrt(3*a)).multiply(funcRecBig(a-2));
		}
		return r;
	}
	
	// Solución RECURSIVA FINAL (Esquema 2: Tail Recursion)
	// Idéntica estructura al RF de Double, usando BigInteger como acumulador.
	public static BigInteger funcRecFinalBigInteger(Integer a) {
		BigInteger acumulador = BigInteger.valueOf(1L); // Inicialización del acumulador
		return recFinal(acumulador, a);
	}
	
	// Función auxiliar privada para la Recursión Final (BigInteger)
	private static BigInteger recFinal(BigInteger acumulador, Integer a) {
		// 1. Caso Base: Se retorna el acumulador.
		if(a<10) {
			return acumulador.multiply(BigInteger.valueOf(5L));
		} else {
			// 2. Operación de Combinación: El cálculo se hace ANTES de la llamada.
			BigInteger nuevoAcumulador = acumulador.multiply(BigInteger.valueOf((long) Math.sqrt(3*a)));
			// 3. LLAMADA RECURSIVA FINAL: Última instrucción.
			return recFinal(nuevoAcumulador, a-2);
		}
	}
	
	// Solución ITERATIVA (Esquema 3: Iterativo con while/for)
	// Idéntica estructura al Iterativo de Double, usando BigInteger como acumulador.
	public static BigInteger funcItBig(Integer a) {
		// Inicialización: Variable mutable 'bigNum' (acumulador) y variable de control 'a'.
		long smallNum = 1L;
		BigInteger bigNum = BigInteger.valueOf(smallNum);
		
		// Bucle: Condición de continuación
		while(!(a<10)) {
			// Actualización: El acumulador se modifica en cada paso.
			bigNum = bigNum.multiply(BigInteger.valueOf((long) Math.sqrt(3*a)));
			// Actualización: La variable de control se reduce (nx).
			a-=2;
		}
		// Retorno: Resultado final
		return bigNum.multiply(BigInteger.valueOf(5L));
	}

}