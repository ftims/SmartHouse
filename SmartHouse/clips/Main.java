public class Main {
	public static void main(String[] args) {
		clipsEngine();
	}

	public static void clipsEngine() {
		double temperatura = 26.5;
		ClipsEngine eng = new ClipsEngine();
		System.out.println("clipse");
		Akcja akcja = eng.getAction(temperatura,
				"/SmartHouse/WebContent/WEB-INF/lib/temp.clp");
		System.out.println(akcja.getKomunikat());
	}
}
