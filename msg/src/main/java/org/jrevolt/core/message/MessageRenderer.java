package org.jrevolt.core.message;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MessageRenderer {

	MessageDef messageDef;

	MessageToken[] tokens;
	TypeRenderer[] typeRenderers;

	public MessageRenderer(MessageDef messageDef, Map<String, TypeRenderer<?>> typeRenderers) {
		this.messageDef = messageDef;
		List<MessageToken.ParamRef> params = messageDef.getParams();
		for (MessageToken.ParamRef pref : params) {
			if (pref.type != null) {
				TypeRenderer tr = typeRenderers.get(pref.type.identifier.asString());
				if (tr != null) {
					tr = tr.create(asStringList(pref.type.properties));
					if (this.typeRenderers == null) {
						this.typeRenderers = new TypeRenderer[params.size()];
					}
					this.typeRenderers[pref.index] = tr;
				}
			}
		}
	}

	private List<String> asStringList(MessageToken.PropertyList proplist) {
		if (proplist == null) {
			return null;
		}
		List<String> result = new ArrayList<>(proplist.properties.size());
		for (MessageToken.Property property : proplist.properties) {
			result.add(property.asString());
		}
		return result;
	}

	String render(MessageImpl message) {
		StringMessageOutput out = new StringMessageOutput();
		render(message, out);
		return out.asString();
	}

	void render(MessageImpl message, MessageOutput out) {
		if (tokens == null) {
			tokens = messageDef.tokens.toArray(new MessageToken[messageDef.tokens.size()]);
		}
		for (MessageToken token : tokens) {
			if (token.getClass() == MessageToken.Literal.class) {
				renderLiteral(out, (MessageToken.Literal) token);
			} else if (token.getClass() == MessageToken.ParamRef.class) {
				MessageToken.ParamRef pref = (MessageToken.ParamRef) token;
				Object param = resolveParameter(message, pref);
				renderParam(out, pref, param);
			}
		}


	}

	protected void renderLiteral(MessageOutput out, MessageToken.Literal literal) {
		out.write(literal.value.toChars());
	}

    @SuppressWarnings("unchecked")
	protected <T> void renderParam(MessageOutput out, MessageToken.ParamRef pref, T obj) {
        TypeRenderer<T> tr = typeRenderers != null ? typeRenderers[pref.index] : null;
		if (tr == null) {
			tr = DefaultTypeRenderers.defaultTypeRenderer();
		}

		tr.render(out, obj);
	}

	protected Object resolveParameter(MessageImpl message, MessageToken.ParamRef pref) {
        if (pref.methodParamRef != null) {
            try {
                // fixme QDH cannot access Parameter.index
                Field f = pref.methodParamRef.getClass().getDeclaredField("index");
                f.setAccessible(true);
                int i = (int) f.get(pref.methodParamRef);
                return message.args[i];
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new UnsupportedOperationException(e);
            }
        } else {
            return message.args[pref.index]; // fixme: this is temporary hack (positional parameters, need support for named params)
        }
	}


}
