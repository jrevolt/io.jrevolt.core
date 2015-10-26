package io.jrevolt.core.util;

import java.io.IOException;
import static java.lang.Math.min;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Fast cyclic buffer.
 * <p/>
 * (Regular NIO buffers have expensive read/write switching: they need compact() on every switch
 * between read and write modes.)
 *
 * @author Patrik Beno
 * @version $Id: CyclicBuffer.java 163 2007-06-26 20:03:40Z patrik $
 */
public final class CyclicBuffer {

	int head; // read from
	int tail; // write to (append)
	int capacity;
	boolean flipped;

	private ByteBuffer bb;

	static public CyclicBuffer newDirectBuffer(int capacity) {
		return new CyclicBuffer(capacity, true);
	}

	public CyclicBuffer() {
		this(1024);
	}

	public CyclicBuffer(int capacity) {
		this(capacity, true);
	}

	public CyclicBuffer(int capacity, boolean direct) {
		this.capacity = capacity;
		bb = direct ? ByteBuffer.allocateDirect(capacity) : ByteBuffer.allocate(capacity);
	}

	public CyclicBuffer(byte[] buf) {
		this.bb = ByteBuffer.wrap(buf);
	}

	public void reset() {
		head = tail = 0;
	}

	public int capacity() {
		return capacity;
	}

	public int free() {
		return capacity() - size();
	}

	public int size() {
		int size = !flipped ? tail - head : capacity - (head - tail);
		return size;
	}

	public boolean isEmpty() {
		return head == tail && !flipped;
	}

	public boolean isFull() {
		return head == tail && flipped;
	}

	public ByteBuffer getByteBuffer() {
		bb.position(head);
		bb.limit(!flipped ? tail : capacity);
		return bb.asReadOnlyBuffer();
	}

	public int readFrom(ReadableByteChannel channel) throws IOException {
		if (isFull()) {
			return 0;
		}

		bb.position(tail);
		bb.limit(!flipped ? capacity : head);
		int read = channel.read(bb);

		if (read == -1) {
			return -1;
		}

		incTail(read);

//      if (read > 0 && bb.limit()==bb.capacity() && !isFull()) {
//         read += readFrom(channel);
//      }

		return read;
	}

	public int readFrom(ByteBuffer in) {
		int available = in.limit() - in.position();
		int canread = !flipped ? capacity - tail : head - tail;
		int read = min(available, canread);

		bb.clear().position(tail).limit(tail + read);
		bb.put(in);

		incTail(read);

		if (in.hasRemaining()) {
			read += readFrom(in);
		}
		return read;
	}

	public int readFrom(CyclicBuffer in) {
		return readFrom(in.getByteBuffer());
	}

	public int writeTo(WritableByteChannel channel) throws IOException {
		if (isEmpty()) {
			return 0;
		}

		bb.clear().limit(!flipped ? tail : capacity).position(head);
		int written = channel.write(bb);

		if (written == 0) {
			return 0;
		}

		incHead(written);

		if (written > 0 && head == capacity && !isEmpty()) {
			written += writeTo(channel);
		}

		return written;
	}


	private void incHead(int count) {
		head += count;
		if (head >= capacity) {
			head -= capacity;
			flipped = false;
		}
	}

	private void incTail(int count) {
		tail += count;
		if (tail >= capacity) {
			tail -= capacity;
			flipped = true;
		}
	}
}
