package ejercicios;

import java.math.BigInteger;

public class Ejercicio4 {
	
	public static Double funcRecDouble(Integer a) {
		Double r = null;
		if(a<10) {
			r = 5.;
		} else {
			r = (Math.sqrt(3*a) * funcItDouble(a-2));
		}
		return r;
	}
	
	public static Double funcRecFinalDouble(Integer a) {
		Double acumulador = 1.;
		return recFinal(acumulador, a);
	}
	
	private static Double recFinal(Double acumulador, Integer a) {
		if(a<10) {
			return acumulador*5.;
		} else {
			Double nuevoAcumulador = acumulador * Math.sqrt(3*a);
			return recFinal(nuevoAcumulador, a-2);
		}
	}
	
	public static Double funcItDouble(Integer a) {
		Double acumulador = 1.;
		while(!(a<10)) {
			acumulador = acumulador * Math.sqrt(3*a);
			a-=2;
		}
		return acumulador * 5.;
	}
	
	//BigInteger bigNum1 = new BigInteger("123456789012345678901234567890");
	//long smallNum = 100L;
	//BigInteger bigNum2 = BigInteger.valueOf(smallNum);
	//BigInteger bigNum3 = BigInteger.ONE;

	public static BigInteger funcRecBig(Integer a) {
		BigInteger r = BigInteger.valueOf(1L);
		if(a<10) {
			r = BigInteger.valueOf(5L);   // Representa el número 1;
		} else {
			r = BigInteger.valueOf((long) Math.sqrt(3*a)).multiply(funcRecBig(a-2));
		}
		return r;
	}
	
	public static BigInteger funcRecFinalBigInteger(Integer a) {
		BigInteger acumulador = BigInteger.valueOf(1L);
		return recFinal(acumulador, a);
	}
	
	private static BigInteger recFinal(BigInteger acumulador, Integer a) {
		if(a<10) {
			return acumulador.multiply(BigInteger.valueOf(5L));
		} else {
			BigInteger nuevoAcumulador = acumulador.multiply(BigInteger.valueOf((long) Math.sqrt(3*a)));
			return recFinal(nuevoAcumulador, a-2);
		}
	}
	
	public static BigInteger funcItBig(Integer a) {
		long smallNum = 1L;
		BigInteger bigNum = BigInteger.valueOf(smallNum);
		while(!(a<10)) {
			bigNum = bigNum.multiply(BigInteger.valueOf((long) Math.sqrt(3*a)));
			a-=2;
		}
		return bigNum.multiply(BigInteger.valueOf(5L));
	}

}