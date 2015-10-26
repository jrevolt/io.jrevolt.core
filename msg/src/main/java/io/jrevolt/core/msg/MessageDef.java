package io.jrevolt.core.msg;

import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MessageDef {

	// Example:
	// Hello, World! This is ${firstName} ${surname} from ${city}, ${country}.
	// Current time is ${date}, that is ${date:Date('dd.MM.yyyy HH:mm')}
	// Parameter references are enclosed in \${} and general syntax is:
	// \${parameterName:TypeDef} where
	// TypeDef := typeDefName(properties)
	// where
	// properties :=
	String template;

	List<MessageToken> tokens;

	protected MessageDef(String template, List<MessageToken> tokens) {
		this.template = template;
		this.tokens = tokens;
	}

	public List<MessageToken.ParamRef> getParams() {
		List<MessageToken.ParamRef> prefs = new LinkedList<>();
		for (MessageToken token : tokens) {
			if (token instanceof MessageToken.ParamRef) {
				prefs.add((MessageToken.ParamRef) token);
			}
		}
		return prefs;
	}

}
