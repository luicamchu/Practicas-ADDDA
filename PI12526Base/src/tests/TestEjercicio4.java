package tests;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.math3.fitting.WeightedObservedPoint;

import ejercicios.Ejercicio4;
import us.lsi.common.Pair;
import us.lsi.common.String2;
import us.lsi.curvefitting.DataFile;
import us.lsi.curvefitting.Fit;
import us.lsi.curvefitting.GenData;
import us.lsi.curvefitting.PowerLog;
import us.lsi.graphics.MatPlotLib;

public class TestEjercicio4 {
	private static Integer nMin  = 100;
	private static Integer nMax  = 1000;
	private static Integer razon  = 3330;
	private static Integer nIter  = 50;
	private static Integer nIterWarmup  = 10000;

	private static Double a = 3.;
	
	public static void genData (Consumer<Integer> algorith, String file) {
		Function<Integer, Long> f1 = GenData.time(algorith);
		GenData.tiemposEjecucionAritmetica(f1, file, nMin, nMax, nMax, nIter, nIterWarmup);
	}
	
	
	
	public static void main(String[] args) {
		genData(t -> Ejemplo4.potenciaR(a, t), "ficheros_generados/p1/porRecursiva.txt");
		}

	
}