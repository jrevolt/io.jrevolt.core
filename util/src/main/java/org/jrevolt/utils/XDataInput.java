package org.jrevolt.utils;

import java.io.DataInput;

/**
 * @author Patrik Beno
 */
public interface XDataInput extends DataInput {

	void readFully(byte b[]);

	void readFully(byte b[], int off, int len);

	int skipBytes(int n);

	boolean readBoolean();

	byte readByte();

	int readUnsignedByte();

	short readShort();

	int readUnsignedShort();

	char readChar();

	int readInt();

	long readLong();

	float readFloat();

	double readDouble();

	String readLine();

	String readUTF();
}
