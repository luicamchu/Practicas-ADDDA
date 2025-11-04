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
	private static Integer nMin = 100; // n mínimo para el cálculo de potencia
	private static Integer nMax = 10000; // n máximo para el cálculo de potencia
	private static Integer razon = 3330; // incremento en los valores de n del cálculo de potencia
	private static Integer nIter = 50; // número de iteraciones para cada medición de tiempo
	private static Integer nIterWarmup = 10000; // número de iteraciones para warmup

	private static Double a = 3.;
	
	public static void genData (Consumer<Integer> algorithm, String file) {
		Function<Integer,Long> f1 = GenData.time(algorithm);
		GenData.tiemposEjecucionAritmetica(f1,file,nMin,nMax,razon,nIter,nIterWarmup);
	}
	
	public static void show(Fit pl, String file, String label) {
		List<WeightedObservedPoint> data = DataFile.points(file);
		pl.fit(data);
		MatPlotLib.show(file, pl.getFunction(), String.format("%s = %s",label,pl.getExpression()));
	}
	
	public static void showCombined() {
		MatPlotLib.showCombined("Tiempos",
				List.of("ficheros_generados/funcRecDouble.txt",
						//"ficheros_generados/funcRecFinalDouble.txt",
						"ficheros_generados/funcItDouble.txt",
						"ficheros_generados/funcRecBig.txt",
						//"ficheros_generados/funcRecFinalBigInteger.txt",
						"ficheros_generados/funcItBig.txt"), 
				List.of("Recursiva",
						//"RecursivaFinal",
						"Iterativa",
						"RecursivaBI",
						//"RecursivaFinalBI",
						"IterativaBI","Log"));
	}

	public static void main(String[] args) {
		String nombreArchivo1 = "ficheros_generados/funcRecDouble.txt";
		//String nombreArchivo2 = "ficheros_generados/funcRecFinalDouble.txt";
		String nombreArchivo3 = "ficheros_generados/funcItDouble.txt";
		
		String nombreArchivo4 = "ficheros_generados/funcRecBig.txt";
		//String nombreArchivo5 = "ficheros_generados/funcRecFinalBigInteger.txt";
		String nombreArchivo6 = "ficheros_generados/funcItBig.txt";
		
		genData(t -> Ejercicio4.funcRecDouble(t),nombreArchivo1);
		//genData(t -> Ejercicio4.funcRecFinalDouble(t),nombreArchivo2);
		genData(t -> Ejercicio4.funcItDouble(t),nombreArchivo3);
		
		genData(t -> Ejercicio4.funcRecBig(t),nombreArchivo4);
		//genData(t -> Ejercicio4.funcRecFinalBigInteger(t),nombreArchivo5);
		genData(t -> Ejercicio4.funcItBig(t),nombreArchivo6);
		

		show(PowerLog.of(List.of(Pair.of(2, 0.),Pair.of(3, 0.))), nombreArchivo1,"Recursiva");
		//show(PowerLog.of(List.of(Pair.of(2, 0.),Pair.of(3, 0.))), nombreArchivo2,"RecursivaFinal");
		show(PowerLog.of(List.of(Pair.of(2, 0.),Pair.of(3, 0.))), nombreArchivo3,"Iterativa");
		
		show(PowerLog.of(List.of(Pair.of(2, 0.),Pair.of(3, 0.))), nombreArchivo1,"RecursivaBI");
		//show(PowerLog.of(List.of(Pair.of(2, 0.),Pair.of(3, 0.))), nombreArchivo2,"RecursivaFinalBI");
		show(PowerLog.of(List.of(Pair.of(2, 0.),Pair.of(3, 0.))), nombreArchivo3,"IterativaBI");
		
		//show(PowerLog.of(List.of(Pair.of(1, 0.),Pair.of(2, 1.),Pair.of(3, 0.))), nombreArchivo3,"Log");
		
		
		
		showCombined();
	}

	
}