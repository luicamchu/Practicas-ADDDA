package ejercicios;

public record EnteroCadena(Integer a, String s) {
	// Java genera este código automáticamente:
    public  EnteroCadena(Integer a, String s) {
		// TODO Auto-generated constructor stub
        this.a = a;
        this.s = s;
    }
    
    public static EnteroCadena of(Integer a, String s) {
    	return new EnteroCadena(a, s);
    }
    
	// Java genera este código automáticamente:
    public Integer a() {
        return a; // Este es el método que llamas con p.a()
    }
    
    public String s( ) {
    	return s; // Este es el método que llamas con p.s()
    }
}

