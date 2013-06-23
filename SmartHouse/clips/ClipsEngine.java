import CLIPSJNI.Environment;
import CLIPSJNI.PrimitiveValue;

public class ClipsEngine {

	private Environment env;
	private Stan stan;
	private String path;

	public ClipsEngine() {
		env = new Environment();
		stan = new Stan(StanRolet.ZASLONIETE, 24.5);
	}

	public Akcja getAction(Double temperatura, String path) {
		this.path = path;
		stan.setTemperatura(temperatura);
		initClips();
		makeDecision();
		return this.stan.getAkcja();
	}

	public void initClips() {
		env.load(this.path);
		env.reset();
		assertFacts();
		env.run();
	}

	public void assertFacts() {
		env.assertString("(decyzja (stan-rolet " + stan.getStanRolet()
				+ " ) (temperatura " + stan.getTemperatura() + "))");
	}

	public void makeDecision() {
		try {
			prepareAction();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	public void prepareAction() throws Exception {
		String eval = "(find-all-facts ((?f	akcja)) TRUE)";
		PrimitiveValue pv = env.eval(eval);
		for (int i = 0; i < pv.size(); i++) {
			String wartosc = pv.get(i).getFactSlot("wartosc").toString();
			setAkcja(wartosc);
		}
	}

	public void setAkcja(String wartosc) {
		for (Akcja a : Akcja.values()) {
			if (a.getWartosc().equals(wartosc)) {
				stan.setAkcja(a);
			}
		}
	}

}
