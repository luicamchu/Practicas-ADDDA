package ejercicio1;

import java.util.LinkedList;
import java.util.List;

import us.lsi.tiposrecursivos.BEmpty;
import us.lsi.tiposrecursivos.BLeaf;
import us.lsi.tiposrecursivos.BTree;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.TEmpty;
import us.lsi.tiposrecursivos.TLeaf;
import us.lsi.tiposrecursivos.TNary;
import us.lsi.tiposrecursivos.Tree;

public class Ejercicio1 {
	/**
     * Determina el camino desde la raíz a una hoja con el producto de etiquetas máximo.
     * Restricción: No usar métodos auxiliares, todo en el método principal.
     */
	public static List<Integer> caminoMaximo (BinaryTree<Integer> tree) {
		return switch (tree) {
		
			case BEmpty() -> new LinkedList<Integer>();
			
			case BLeaf(var value) -> {
				List<Integer> aux = new LinkedList<Integer>();
				aux.add(value);
				yield aux;
			}
			
			case BTree(Integer value, var left, var right) -> {
                // 1. Llamadas recursivas: Obtenemos el camino de producto máximo de los hijos.
				List<Integer> caminoMaxIzquierda = caminoMaximo(left);
				List<Integer> caminoMaxDerecha = caminoMaximo(right);
				
				// 2. Cálculo del producto (Ineficiente, pero necesario por la restricción)
                // Usamos 1 para inicializar el producto y multiplicar por todos los elementos de la lista.
				Integer productoCaminoMaxIzq = caminoMaxIzquierda.stream().reduce(1, (a,b)->a*b);
				Integer productoCaminoMaxDch = caminoMaxDerecha.stream().reduce(1, (a,b)->a*b);
						//.mapToInt(v -> v.intValue()).sum();
				List<Integer> caminoDevuelto = new LinkedList<Integer>();

				// 3. Comparación y selección del mejor camino del subárbol
                if (productoCaminoMaxIzq == 1L && caminoMaxIzquierda.isEmpty() && 
                		productoCaminoMaxDch == 1L && caminoMaxDerecha.isEmpty()) {
                    
                    // Caso en que ambos subárboles son vacíos (BEmpty)
                    caminoDevuelto = new LinkedList<>();

                } else if (productoCaminoMaxDch > productoCaminoMaxIzq) {
					caminoDevuelto = caminoMaxDerecha;
				} else {
					caminoDevuelto = caminoMaxIzquierda;
				}
                
                // 4. Se añade la etiqueta del nodo actual (la raíz de este subárbol)
                caminoDevuelto.add(0, value); // Añadir al principio de la lista
				yield caminoDevuelto;
			}
		
		};
	}
	
	// --- SOLUCIÓN PARA ÁRBOLES N-ARIOS ---
    
    /**
     * Determina el camino desde la raíz a una hoja con el producto de etiquetas máximo.
     * Restricción: No usar métodos auxiliares, todo en el método principal.
     */
    public static List<Integer> caminoMaximo (Tree<Integer> tree) {
        return switch (tree) {
        
            case TEmpty() -> new LinkedList<Integer>();
            
            case TLeaf(var value) -> {
                List<Integer> aux = new LinkedList<>();
                aux.add(value);
                yield aux;
            }
            
            case TNary(var value, var child) -> {
                
                List<List<Integer>> caminosHijos = new LinkedList<>();
                
                // 1. Llamadas recursivas a todos los hijos
                for(Tree<Integer> c : child) {
                    caminosHijos.add(caminoMaximo(c));
                }
                
                long productoMaximoHijo = Long.MIN_VALUE;
                List<Integer> mejorCaminoHijo = new LinkedList<>();
                
                // 2. Iterar sobre todos los caminos devueltos por los hijos
                for (List<Integer> camino : caminosHijos) {
                    if (camino.isEmpty()) continue; // Ignorar caminos vacíos (TEmpty)
                    
                    // 3. Recálculo del producto del camino (Ineficiente)
                    long productoActual = camino.stream()
                                              .mapToLong(v -> v.longValue())
                                              .reduce(1L, (a, b) -> a * b);
                    
                    // 4. Comparación y selección
                    if (productoActual > productoMaximoHijo) {
                        productoMaximoHijo = productoActual;
                        mejorCaminoHijo = camino;
                    }
                }
                
                // 5. Añadir la etiqueta del nodo actual a la lista seleccionada
                mejorCaminoHijo.add(0, value); // Añadir al principio
                
                yield mejorCaminoHijo;
            }
        };
    }
}
