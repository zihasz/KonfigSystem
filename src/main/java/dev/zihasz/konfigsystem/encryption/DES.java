package dev.zihasz.konfigsystem.encryption;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

public class DES {

	public static byte[] encrypt(byte[] data, SecretKey key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(ENCRYPT_MODE, key);

		return Base64.getEncoder().encode(cipher.doFinal(data));
	}

	public static byte[] decrypt(byte[] data, SecretKey key)
			throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(DECRYPT_MODE, key);

		return cipher.doFinal(Base64.getDecoder().decode(data));
	}

}
