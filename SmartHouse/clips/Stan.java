public class Stan {

	private StanRolet stanRolet;
	private Double temperatura;
	private Akcja akcja = Akcja.NIE_ROB_NIC;

	public Stan() {
	}

	public Stan(StanRolet stanRolet, Double temperatura) {
		this.stanRolet = stanRolet;
		this.temperatura = temperatura;
	}

	public Character getStanRoletChar() {
		return this.stanRolet.getStan();
	}

	public StanRolet getStanRolet() {
		return stanRolet;
	}

	public void setStanRolet(StanRolet stanRolet) {
		this.stanRolet = stanRolet;
	}

	public Double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}

	public Akcja getAkcja() {
		return akcja;
	}

	public void setAkcja(Akcja akcja) {
		this.akcja = akcja;
	}
}
