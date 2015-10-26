package org.jrevolt.core.message;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MessageParser {

	// Example:
	// Hello, World!
	// This is ${firstName} ${surname} from ${address.country}.
	// I live on ${address.street} ${address.number}, ${address.city}
	// Current time is ${date}, that is ${date:Date('dd.MM.yyyy HH:mm')}
	// Parameter references are enclosed in \${} and general syntax is:
	// ParamRef := \${parameterName:TypeDef} where
	// TypeDef  := typeDefName(properties)
	// where
	// properties :=

	String source;
    Method method;

	List<MessageToken> tokens = new LinkedList<>();

	int paramIndex;
	int propertyIndex;

	public MessageParser(String source) {
		this.source = source;
	}

    public MessageParser(Method method) {
        Message message = method.getAnnotation(Message.class);
        this.source = message.value();
        this.method = method;
    }
    

	MessageDef parse() {
		visit(new Substring(source));
		return new MessageDef(source, tokens);
	}


	void visit(Substring source) {
		Substring s = source.copy();
		while (s.length() > 0) {
			MessageToken token;
			if (s.startsWith("${")) {
				token = visitParamRef(s);
			} else if (s.startsWith("\\")) {
				token = visitEscape(s);
			} else {
				token = visitLiteral(s);
			}

			// save
			tokens.add(token);
		}
	}

	MessageToken.Escape visitEscape(Substring s) {
		MessageToken.Escape escape = new MessageToken.Escape();
		escape.source = s.substring(0, 1);
		return escape;
	}

	MessageToken visitLiteral(Substring source) {
		Substring s = source.copy();
		MessageToken.Literal literal = new MessageToken.Literal();
		literal.value = s.seek(pattern("^[^\\\\\\$]+")); // todo up to first Escape:"\?" or ParamRef:"${"
		literal.source = source.forward(s);
		return literal;
	}


	MessageToken.ParamRef visitParamRef(Substring source) {
		Substring s = source.copy();
		s.skip("${");

		MessageToken.ParamRef ref = new MessageToken.ParamRef();
		ref.index = paramIndex++;
		ref.expression = visitExpression(s);
		if (s.startsWith(":")) {
			s.skip(":");
			ref.type = visitTypeDef(s);
		}

		s.skip("}");
		ref.source = source.forward(s);

        if (method != null) {
            MessageToken.Identifier main = ref.expression.path.get(0);
            Parameter[] mparams = method.getParameters();
            for (int i = 0; i < mparams.length; i++) {
                Parameter p = mparams[i];
                if (p.getName().equals(main.value.toString())) {
                    ref.methodParamRef = p;
                }
            }
        }


        return ref;
	}

	MessageToken.Expression visitExpression(Substring source) {
		Substring s = source.copy();
		MessageToken.Expression expression = new MessageToken.Expression();
		while (!s.matches(pattern("^[:}].*"))) {
			MessageToken.Identifier identifier = visitIdentifier(s);
			expression.path.add(identifier);
			if (s.startsWith(".")) {
				s.skip(".");
			}
		}
		expression.source = source.forward(s);
		return expression;
	}


	MessageToken.Identifier visitIdentifier(Substring source) {
		Substring s = source.copy();

		MessageToken.Identifier identifier = new MessageToken.Identifier();
		identifier.value = s.seek(pattern("^\\p{Alpha}\\p{Alnum}+"));
		identifier.source = source.forward(s);

		return identifier;
	}

	MessageToken.TypeDef visitTypeDef(Substring source) {
		Substring s = source.copy();
		MessageToken.TypeDef tdef = new MessageToken.TypeDef();
		tdef.identifier = visitIdentifier(s);
		tdef.properties = visitPropertyList(s);
		tdef.source = source.forward(s);
		return tdef;
	}

	// (property, property, ...)
	MessageToken.PropertyList visitPropertyList(Substring source) {
		if (!source.startsWith("(")) {
			return null;
		}

		Substring s = source.copy();
		s.skip("(");

		MessageToken.PropertyList list = new MessageToken.PropertyList();
		propertyIndex = 0;

		while (!s.startsWith(")")) {
			MessageToken.Property p = visitProperty(s);
			if (list.properties == null) {
				list.properties = new LinkedList<>();
			}
			list.properties.add(p);
			if (s.startsWith(",")) {
				s.skip(",");
			}
		}

		s.skip(")");
		list.source = source.forward(s);

		return list;
	}

	// name=value
	MessageToken.Property visitProperty(Substring source) {
		Substring s = source.copy();
		MessageToken.Property property = new MessageToken.Property();
		property.index = propertyIndex++;
		property.value = s.seek(pattern("[,)]"), false);
		property.source = source.forward(s);
		return property;
	}

	///

	static Map<String, Pattern> PATTERNS = new ConcurrentHashMap<>();

	Pattern pattern(String pattern) {
		Pattern p = PATTERNS.get(pattern);
		if (p == null) {
			p = Pattern.compile(pattern);
			PATTERNS.put(pattern, p);
		}
		return p;
	}


}
