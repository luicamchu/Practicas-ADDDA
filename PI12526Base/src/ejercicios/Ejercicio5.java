package ejercicios;

import java.math.BigInteger;

public class Ejercicio5 {

	public static Double ejercicio5ItDouble(Integer n) {
		Double acumulador = 1.;
		while(!(n<=6)) {
			acumulador = 1. + (acumulador *log2(n-1));
			n--;
		}
		return acumulador;
	}
	
	public static Double ejercicio5RecDouble(Integer n) {
		if(n<=6) {
			return 1.;
		} else {
			return 1. + (ejercicio5RecDouble(n-1) * log2(n-1));
		}
	}
	
	//La suma de 1 rompe el patrón de acumulación simple requerido para esta técnica.
	
	
	public static Double ejercicio5RecFinalDouble(Integer n) {
		Double acumulador = 1.;
		return ejercicio5RecFinalDouble(acumulador, n);
	}
	
	private static Double ejercicio5RecFinalDouble(Double acumulador, Integer n) {
		if(n<=6) {
			return acumulador;
		} else {
			Double new_acumulador = 1. + (acumulador *log2(n-1));
			return ejercicio5RecFinalDouble(new_acumulador, n-1);
		}
	}
	
	public static BigInteger ejercicio5RecBigInteger(Integer n) {
		return null;
	}
	
	public static BigInteger ejercicio5ItBigInteger(Integer n) {
		return null;
	}

	public static int log2(int n){
	    if(n <= 0) throw new IllegalArgumentException();
	    return 31 - Integer.numberOfLeadingZeros(n);
	}
	
	
}