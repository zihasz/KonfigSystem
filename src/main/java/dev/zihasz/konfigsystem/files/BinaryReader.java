package dev.zihasz.konfigsystem.files;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class BinaryReader implements Closeable {

	private DataInputStream stream;

	public BinaryReader(String path) throws IOException {
		this.stream = new DataInputStream(new FileInputStream(path));
	}

	public byte[] readAll() throws IOException {
		return stream.readAllBytes();
	}

	public void close() throws IOException {
		stream.close();
	}

}
