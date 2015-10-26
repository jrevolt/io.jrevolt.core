package org.jrevolt.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/**
 * @author Patrik Beno
 */
public class ByteArrayDataOutput extends XDataOutputWrapper {

	ByteArrayOutputStream baos;

	public ByteArrayDataOutput() {
		this(new ByteArrayOutputStream());
	}

	public ByteArrayDataOutput(ByteArrayOutputStream baos) {
		super(new DataOutputStream(baos));
		this.baos = baos;
	}

	public byte[] toByteArray() {
		return baos.toByteArray();
	}




}
