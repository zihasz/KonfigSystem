package com.github.zihasz.konfigsystem.tests;

import com.github.zihasz.konfigsystem.KonfigSystem;
import com.github.zihasz.konfigsystem.konfig.Konfig;
import com.github.zihasz.konfigsystem.konfig.KonfigBuilder;
import org.junit.jupiter.api.Test;

import javax.crypto.KeyGenerator;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

	@Test
	public void saveLoadTest() throws Exception {
		String path = "configs/";

		if (!Files.exists(Paths.get(path)))
			Files.createDirectory(Paths.get(path));

		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		KonfigSystem system = new KonfigSystem(path, keyGenerator.generateKey());

		Konfig konfig1 = new KonfigBuilder().add("testString", "string").add("testBool", false).add("testInt", 5).add("testLong", 10000L).add("testFloat", 10.677F).add("testDouble", 1.00000000000000008D).construct();
		Konfig konfig2 = new KonfigBuilder().add("test2String", "not a string").add("test2Bool", true).add("test2Int", 1000).add("test2Long", 60000L).add("test2Float", 10.676F).add("test2Double", 1.00000000600000008D).construct();

		system.addKonfig("test1", konfig1);
		system.addKonfig("test2", konfig2);

		Konfig loaded1 = system.load("test1");
		Konfig loaded2 = system.load("test2");

		system.LOGGER.info("Original1: ");
		system.LOGGER.info(konfig1.dataMap);

		system.LOGGER.info("Loaded1: ");
		system.LOGGER.info(loaded1.dataMap);

		system.LOGGER.info("Original2: ");
		system.LOGGER.info(konfig2.dataMap);

		system.LOGGER.info("Loaded2: ");
		system.LOGGER.info(loaded2.dataMap);
		
		assertEquals(konfig1, loaded1, "Loaded1 konfig should equal the original1 one.");
		assertEquals(konfig2, loaded2, "Loaded2 konfig should equal the original2 one.");
	}

	@Test
	public void modifyTest() throws Exception {
		String path = "configs/";

		if (!Files.exists(Paths.get(path)))
			Files.createDirectory(Paths.get(path));

		KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
		KonfigSystem system = new KonfigSystem(path, keyGenerator.generateKey());

		Konfig konfig = new KonfigBuilder()
				.add("testString", "string")
				.add("testBool", false)
				.add("testInt", 5)
				.add("testLong", 10000L)
				.add("testFloat", 10.677F)
				.add("testDouble", 1.00000000000000008D)
				.construct();

		system.addKonfig("modifyTest", konfig);
		system.getKonfig("modifyTest").dataMap.put("testString", "niggaBallz");
		system.save("modifyTest");

		Konfig loaded = system.load("modifyTest");

		system.LOGGER.info("Original: ");
		system.LOGGER.info(konfig.dataMap);

		system.LOGGER.info("Loaded: ");
		system.LOGGER.info(loaded.dataMap);

		assertEquals("niggaBallz", loaded.dataMap.get("testString"), "Loaded testString should be \"niggaBallz\"");
	}

}
