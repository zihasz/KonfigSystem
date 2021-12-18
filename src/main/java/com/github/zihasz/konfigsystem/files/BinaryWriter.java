package com.github.zihasz.konfigsystem.files;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class BinaryWriter implements Closeable {

	private DataOutputStream stream;

	public BinaryWriter(String path) throws IOException {
		this.stream = new DataOutputStream(new FileOutputStream(path));
	}

	public void write(byte[] bytes) throws IOException {
		stream.write(bytes);
	}

	public void flush() throws IOException {
		stream.flush();
	}

	public void close() throws IOException {
		stream.close();
	}

}
