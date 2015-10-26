package org.jrevolt.utils;

import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Patrik Beno
 */
public class XDataOutputWrapper implements XDataOutput {

	DataOutput out;

	public XDataOutputWrapper(DataOutput out) {
		this.out = out;
	}

	public void write(int b) {
		try {
			out.write(b);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public void write(byte[] b) {
		try {
			out.write(b);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public void write(byte[] b, int off, int len) {
		try {
			out.write(b, off, len);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public void writeBoolean(boolean v) {
		try {
			out.writeBoolean(v);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public void writeByte(int v) {
		try {
			out.writeByte(v);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public void writeShort(int v) {
		try {
			out.writeShort(v);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public void writeChar(int v) {
		try {
			out.writeChar(v);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public void writeInt(int v) {
		try {
			out.writeInt(v);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public void writeLong(long v) {
		try {
			out.writeLong(v);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public void writeFloat(float v) {
		try {
			out.writeFloat(v);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public void writeDouble(double v) {
		try {
			out.writeDouble(v);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public void writeBytes(String s) {
		try {
			out.writeBytes(s);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public void writeChars(String s) {
		try {
			out.writeChars(s);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}

	public void writeUTF(String s) {
		try {
			out.writeUTF(s);
		} catch (IOException e) {
			throw new JackieException(e);
		}
	}
}
