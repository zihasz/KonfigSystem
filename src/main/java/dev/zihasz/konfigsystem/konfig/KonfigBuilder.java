package dev.zihasz.konfigsystem.konfig;

public class KonfigBuilder {

	private Konfig konfig;

	public KonfigBuilder() {
		konfig = new Konfig();
	}

	public KonfigBuilder add(String key, Object data) {
		this.konfig.dataMap.put(key, data);
		return this;
	}

	public Konfig construct() {
		return konfig;
	}

}
