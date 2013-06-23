public enum Akcja {

	NIE_ROB_NIC("nie-rob-nic", "Nie wykonuj �adnej czynno�ci"), ODSLON(
			"odslon-rolety", "Ods�o� rolety"), ZASLON("zaslon-rolety",
			"Zas�o� rolety");

	private String wartosc;
	private String komunikat;

	private Akcja(String wartosc, String komunikat) {
		this.wartosc = wartosc;
		this.komunikat = komunikat;
	}

	public String getWartosc() {
		return this.wartosc;
	}

	public String getKomunikat() {
		return this.komunikat;
	}
}
