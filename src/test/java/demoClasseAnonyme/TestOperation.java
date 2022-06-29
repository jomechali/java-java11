package demoClasseAnonyme;

public class TestOperation {

	public static void main(String[] args) {

		Operation ope = (a, b) -> a + b;
		
		System.out.println(String.valueOf(ope.calculer(5, 6)) + ope.test());

	}

}
