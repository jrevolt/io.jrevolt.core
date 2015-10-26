package org.jrevolt.utils;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;

/**
 * @author Patrik Beno
 */
public class DataInputWrapper implements XDataInput {

	DataInput in;

	public DataInputWrapper(DataInput in) {
		this.in = in;
	}

	public DataInputWrapper(InputStream in) {
		this.in = new DataInputStream(in); 
	}

	public void readFully(byte[] b) {
		try {
			in.readFully(b);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public void readFully(byte[] b, int off, int len) {
		try {
			in.readFully(b, off, len);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public int skipBytes(int n) {
		try {
			return in.skipBytes(n);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public boolean readBoolean() {
		try {
			return in.readBoolean();
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public byte readByte() {
		try {
			return in.readByte();
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public int readUnsignedByte() {
		try {
			return in.readUnsignedByte();
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public short readShort() {
		try {
			return in.readShort();
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public int readUnsignedShort() {
		try {
			return in.readUnsignedShort();
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public char readChar() {
		try {
			return in.readChar();
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public int readInt() {
		try {
			return in.readInt();
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public long readLong() {
		try {
			return in.readLong();
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public float readFloat() {
		try {
			return in.readFloat();
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public double readDouble() {
		try {
			return in.readDouble();
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public String readLine() {
		try {
			return in.readLine();
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public String readUTF() {
		try {
			return in.readUTF();
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}
}
