package ejercicio2;

import us.lsi.tiposrecursivos.BEmpty;
import us.lsi.tiposrecursivos.BLeaf;
import us.lsi.tiposrecursivos.BTree;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.TEmpty;
import us.lsi.tiposrecursivos.TLeaf;
import us.lsi.tiposrecursivos.TNary;
import us.lsi.tiposrecursivos.Tree;



public class Ejercicio2 {

	//	2. Dado un árbol binario de cadena de caracteres, diseñe un algoritmo que devuelva cierto
	//	si se cumple que, para todo nodo, el número total de vocales (incluyendo las repetidas)
	//	contenidas en el subárbol izquierdo es igual al del subárbol derecho.
	//	Proporcione una solución también para árboles n-arios, considerando que para todo nodo,
	//	el número total de vocales (incluyendo las repetidas) contenidas en todos sus subárboles
	//	debe coincidir.


	private static boolean resultadoGlobal1; 

	public static Boolean solucion_recursiva(BinaryTree<String> tree) {
		resultadoGlobal1 = true;
		Integer nivel = 0;
		recursivo1(nivel, tree);
		return resultadoGlobal1;

	}
	/**
	 * Método privado recursivo: Su única misión es DEVOLVER el conteo total de vocales
	 * en el subárbol actual.
	 * @param tree El subárbol actual.
	 * @return El número total de vocales en el subárbol.
	 */
	private static Long recursivo1(Integer nivel, BinaryTree<String> tree) {
		return switch (tree) {
		case BEmpty() -> 0L;
		case BLeaf(var value) -> {
			yield contarVocales(value);
		}
		case BTree(String value, var left, var right) -> {
			// 1. Obtener los conteos de los subárboles hijos
			Long vocalesIzquierda = recursivo1(nivel+1, left);
			Long VocalesDerecha = recursivo1(nivel+1, right);
			// 2. VERIFICACIÓN (Solo se hace si el resultado global sigue siendo 'true')
			if(vocalesIzquierda!=VocalesDerecha) {
				resultadoGlobal1 = false;
			}
			// 3. Devolver el conteo total del subárbol actual
			yield contarVocales(value) + vocalesIzquierda + VocalesDerecha;

		}
		};
	}

	private static Long contarVocales(String s) {
		// TODO Auto-generated method stub
		return s.toLowerCase().chars().filter(v->v == 'a'|| v=='e'|| v=='i'|| v=='o'|| v=='u').count();
	}

	private static boolean resultadoGlobal2;

	public static Boolean solucion_recursiva(Tree<String> tree) {
		resultadoGlobal2 = true;
		recursivoNario(tree);
		return resultadoGlobal2;
	}

	private static Long recursivoNario(Tree<String> tree) {
		return switch (tree) {
		case TEmpty() -> 0L;			
		case TLeaf(var value) -> {
			// Caso Base: Es una hoja, devuelve el conteo de su etiqueta.
			yield contarVocales(value);
		}
		case TNary(String value, var children) -> {
			Long conteoTotalSubarbol = 0L;
			Long primerConteo = null; // Usado para almacenar el conteo de referencia.
			// 1. Iterar sobre los hijos y obtener sus conteos
			for (Tree<String> child : children) {
				// Llamada recursiva para obtener el conteo de vocales del hijo
				Long conteoHijo = recursivoNario(child);
				// Acumular el conteo total para devolver al padre
				conteoTotalSubarbol += conteoHijo;

				// 2. VERIFICACIÓN N-ARIA
				// Si el hijo no está vacío (ya que TEmpty siempre devuelve 0L)
				if (!(child instanceof TEmpty)) {
					if (primerConteo == null) {
						// Si es el primer hijo no vacío, establece el conteo de referencia.
						primerConteo = conteoHijo;
					} 
					// Si el conteo del hijo actual es diferente al de referencia, la condición falla.
					else if (conteoHijo.longValue() != primerConteo.longValue()) {
						resultadoGlobal2 = false;
					}
				}
			}

			// 3. Devolver el conteo total del subárbol actual
			// Conteo del nodo actual + conteo total de todos sus subárboles.
			yield contarVocales(value) + conteoTotalSubarbol;
		}
		};
	}
}