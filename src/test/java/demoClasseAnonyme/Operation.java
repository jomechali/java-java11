package demoClasseAnonyme;

@FunctionalInterface
public interface Operation {

	int calculer(int a, int b);
	
	default boolean test() {
		return false;
	}
}
