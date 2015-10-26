package org.jrevolt.core.message;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MessageToken {

	Substring source;

	public String asString() {
		return source.toString();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": \"" + source.toString() + "\"";
	}

	static class Literal extends MessageToken {
		Substring value;
	}

	static class Escape extends MessageToken {
	}

	static class Identifier extends MessageToken {
		Substring value;
	}

	static class ParamRef extends MessageToken {
		int index;
		Expression expression;
		TypeDef type;
        Parameter methodParamRef;
	}

	static class Expression extends MessageToken {
		List<Identifier> path = new LinkedList<>();
	}

	static class TypeDef extends MessageToken {
		Identifier identifier;
		PropertyList properties;
	}

	static class PropertyList extends MessageToken {
		List<Property> properties;
	}

	static class Property extends MessageToken {
		int index;
		Substring value;
	}
}
