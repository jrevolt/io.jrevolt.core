package io.jrevolt.core.util;

import java.io.ByteArrayInputStream;

/**
 * @author Patrik Beno
 */
public class ByteArrayDataInput extends DataInputWrapper {

	public ByteArrayDataInput(byte[] bytes) {
		super(new ByteArrayInputStream(bytes));
	}
}
