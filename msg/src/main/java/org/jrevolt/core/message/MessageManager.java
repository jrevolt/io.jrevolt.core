package org.jrevolt.core.message;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MessageManager {

	static public final MessageManager INSTANCE = new MessageManager();

	static public MessageManager messageManager() {
		return INSTANCE;
	}

	Map<String, SoftReference<MessageRenderer>> messageRenderers = new ConcurrentHashMap<>();

	Map<String, TypeRenderer<?>> typeRenderers = new ConcurrentHashMap<>();

	{
		registerDefaultTypeRenderers();
	}

	void registerTypeRenderer(TypeRenderer<?> typeRenderer) {
		typeRenderers.put(typeRenderer.getName(), typeRenderer);
	}

	void unregisterTypeRenderer(TypeRenderer<?> typeRenderer) {
		typeRenderers.remove(typeRenderer.getName());
	}

    public MessageDef getMessageDef(String template) {
        MessageParser parser = new MessageParser(template);
        return parser.parse();
    }
    
    public MessageDef getMessageDef(Method messageMethod) {
        MessageParser parser = new MessageParser(messageMethod);
        return parser.parse();
    }
    
	MessageRenderer getMessageRenderer(String messageTemplate) {

		// cache lookup
		SoftReference<MessageRenderer> ref = messageRenderers.get(messageTemplate);
		MessageRenderer messageRenderer = (ref != null) ? ref.get() : null;

		// cache hit: done!
		if (messageRenderer != null) {
			return messageRenderer;
		}

		// lazy initialization
		MessageParser parser = new MessageParser(messageTemplate);
		MessageDef mdef = parser.parse();
		messageRenderer = new MessageRenderer(mdef, typeRenderers);

		// cache it
		messageRenderers.put(messageTemplate, new SoftReference<>(messageRenderer));

		// done!
		return messageRenderer;
	}

    MessageRenderer getMessageRenderer(MessageDef messageDef) {
        MessageRenderer messageRenderer = new MessageRenderer(messageDef, typeRenderers);
        return messageRenderer;
    }


    protected void registerDefaultTypeRenderers() {
		registerTypeRenderer(new DefaultTypeRenderers.DateTypeRenderer());
	}


}
