package org.jrevolt.core.message;

import org.junit.Test;

import java.text.MessageFormat;
import java.util.Date;

import static org.jrevolt.core.message.MessageToken.*;
import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MessageTest {

	@Test
	public void literal() {
		MessageImpl msg = new MessageImpl("Hello, World!");
		MessageParser p = new MessageParser(msg.getMessageTemplate());
		p.parse();
		assertEquals(1, p.tokens.size());
		assertEquals("Hello, World!", ((Literal) p.tokens.get(0)).value.toString());
		assertEquals("Hello, World!", msg.render());
	}

	@Test
	public void simple() {
		MessageImpl msg = new MessageImpl("My name is ${name} ${surname}.", "John", "Doe");
		MessageParser p = new MessageParser(msg.getMessageTemplate());
		p.parse();
		assertEquals(5, p.tokens.size());
		assertEquals("My name is ", ((Literal) p.tokens.get(0)).value.toString());
		assertEquals("${name}", ((ParamRef) p.tokens.get(1)).source.toString());
		assertEquals("name", ((ParamRef) p.tokens.get(1)).expression.path.get(0).value.toString());
		assertEquals(" ", ((Literal) p.tokens.get(2)).value.toString());
		assertEquals("${surname}", ((ParamRef) p.tokens.get(3)).source.toString());
		assertEquals("surname", ((ParamRef) p.tokens.get(3)).expression.path.get(0).value.toString());
		assertEquals(".", ((Literal) p.tokens.get(4)).value.toString());
		assertEquals("My name is John Doe.", msg.render());
	}

	@Test
	public void types() {
		MessageImpl msg = new MessageImpl("Local date: ${now:date}", new Date());
		System.out.println(msg.render());
	}

	@Test
	public void properties() {
		MessageImpl msg = new MessageImpl("Local date: ${now:date(dd.MM.yyyy HH:mm)}", new Date());
		System.out.println(msg.render());
	}

	int count = 30 * 1000;// * 1000;

	@Test
	public void perf() {
		long started = System.currentTimeMillis();
		MessageImpl msg = new MessageImpl("Local date: ${now:date(dd.MM.yyyy HH:mm)}", new Date());
		String s = null;
		for (int i = 0; i < count; i++) {
			s = msg.render();
		}
		long elapsed = System.currentTimeMillis() - started;
		System.out.println("perf() " + elapsed);
		System.out.println(msg);
		System.out.println(s);
	}

	@Test
	public void perf3() {
		long started = System.currentTimeMillis();
		MessageFormat mf = new MessageFormat("Local date: {0,date,dd.MM.yyyy}");
		String s = null;
		for (int i = 0; i < count; i++) {
			s = mf.format(new Object[]{new Date()});
		}
		long elapsed = System.currentTimeMillis() - started;
		System.out.println("perf3()" + elapsed);
		System.out.println(mf);
		System.out.println(s);
	}


}
