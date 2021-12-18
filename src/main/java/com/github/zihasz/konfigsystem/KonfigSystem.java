package com.github.zihasz.konfigsystem;

import com.github.zihasz.konfigsystem.encryption.DES;
import com.github.zihasz.konfigsystem.files.BinaryReader;
import com.github.zihasz.konfigsystem.files.BinaryWriter;
import com.github.zihasz.konfigsystem.konfig.Konfig;
import com.github.zihasz.konfigsystem.util.Serialization;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

public class KonfigSystem {

	public final Logger LOGGER;

	private final Map<String, Konfig> konfigs = new HashMap<>();

	private final String configFolder;
	private final SecretKey configKey;

	public KonfigSystem(String configFolder, SecretKey configKey) {
		LOGGER = LogManager.getLogger("KonfigSystem");
		BasicConfigurator.configure();

		if (configFolder.endsWith("/"))
			this.configFolder = configFolder;
		else
			this.configFolder = configFolder + "/";

		this.configKey = configKey;
	}

	public void addKonfig(String name, Konfig konfig) {
		konfigs.put(name, konfig);
		save(name);
	}
	public Konfig getKonfig(String name) {
		return load(name);
	}

	public void saveAll() {
		for (String name : konfigs.keySet()) {
			save(name);
		}
	}
	public void loadAll() {
		for (String name : konfigs.keySet()) {
			load(name);
		}
	}

	public void save(String name) {
		try (BinaryWriter writer = new BinaryWriter(configFolder + name + ".dat")) {

			byte[] raw = Serialization.serialize(konfigs.get(name).dataMap);
			byte[] enc = DES.encrypt(raw, configKey);

			writer.write(enc);

		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
	public Konfig load(String name) {
		try (BinaryReader reader = new BinaryReader(configFolder + name + ".dat")) {

			byte[] enc = reader.readAll();
			byte[] raw = DES.decrypt(enc, configKey);
			konfigs.get(name).dataMap = (HashMap<String, Object>) Serialization.deserialize(raw);
			return konfigs.get(name);

		} catch (Exception e) {
			LOGGER.error(e);
		}
		return null;
	}

}
