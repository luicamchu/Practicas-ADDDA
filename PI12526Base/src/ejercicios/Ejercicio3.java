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

	public static Long iterativo(Integer a, Integer b, Integer c) {
		Map<IntTrio, Integer> mapABC = new HashMap<IntTrio, Integer>();
		Long valor = null;
		for (int x = 0; x <= a; x++) {
			for (int y = 0; y <= b; y++) {
				for (int z = 0; z<= c; z++) {
					if(x<3 || y<3 || z<3) {
						valor = (long) (x + Math.sqrt(y) + 2*z);
					} else if (x%y==0) {
						valor = (long) (mapABC.get(IntTrio.of(x-1, y/2, z/2)) + mapABC.get(IntTrio.of(x-3, y/3, z/3)));
					} else {
						valor = (long) (mapABC.get(IntTrio.of(x/3, y-3, z-3)) + mapABC.get(IntTrio.of(x/2, y-2, z-2)));
					}
					mapABC.put(IntTrio.of(x, y, z), valor.intValue());
				}
			}
		}
		return valor;
	}

	public static Long recursivo_sin_memoria(Integer a, Integer b, Integer c) {
		if(a<3 || b<3 || c<3) {
			return (long) (a + Math.sqrt(b) + 2*c);
		} else if (a%b==0) {
			return recursivo_sin_memoria(a-1, b/2, c/2) + recursivo_sin_memoria(a-3, b/3, c/3);
		} else {
			return recursivo_sin_memoria(a/3, b-3, c-3) + recursivo_sin_memoria(a/2, b-2, c-2);
		}
	}

	public static Long recursivo_con_memoria(Integer a, Integer b, Integer c) {
		Map<IntTrio, Integer> mapABC = new HashMap<IntTrio, Integer>();
		return recursivo_con_memoria(mapABC, a, b, c);
	}
	
	private static Long recursivo_con_memoria(Map<IntTrio, Integer> mapABC, Integer a, Integer b, Integer c) {
		Long valor = null;
		IntTrio i = IntTrio.of(a, b, c);
		if(mapABC.containsKey(i)) {
			valor = mapABC.get(i).longValue();
		} else {
			if(a<3 || b<3 || c<3) {
				valor = (long) (a + Math.sqrt(b) + 2*c);
			} else if(a%b==0) {
				return recursivo_con_memoria(mapABC, a-1, b/2, c/2) + recursivo_con_memoria(mapABC, a-3, b/3, c/3);
			} else {
				return recursivo_con_memoria(mapABC, a/3, b-3, c-3) + recursivo_con_memoria(mapABC, a/2, b-2, c-2);
			}
			mapABC.put(i, valor.intValue());
		}
		return valor;
	}

}
