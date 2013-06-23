public enum StanRolet {

	ODSLONIETE('o'), ZASLONIETE('z');

	private Character stan;

	private StanRolet(Character stan) {
		this.stan = stan;
	}

	public Character getStan() {
		return this.stan;
	}

	public String toString() {
		return getStan().toString();
	}
}
