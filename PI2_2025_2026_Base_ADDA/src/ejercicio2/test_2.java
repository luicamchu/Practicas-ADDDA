package ejercicio2;

import java.util.ArrayList;
import java.util.List;

public class test_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Integer a = 0;
			for (int i = 0; i < 10; i++) {
				fun(a);
			}
			List<Integer> b = new ArrayList<Integer>();
			for (int i = 0; i < 10; i++) {
				fun2(b);
			}
	}
	public static void fun(Integer b) {
		b=b+1;
	}
	public static void fun2(List<Integer> a) {
		a.add(0);
	}

	//a ->[
	
}
