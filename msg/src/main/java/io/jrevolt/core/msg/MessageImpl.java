package io.jrevolt.core.msg;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class MessageImpl {

	protected MessageDef mdef;
	protected Object[] args;

	public MessageImpl(String template, Object... args) {
		this.mdef = MessageManager.messageManager().getMessageDef(template);
		this.args = args;
	}

	public MessageImpl(Method messageMethod, Object... args) {
		this.mdef = MessageManager.messageManager().getMessageDef(messageMethod);
		this.args = args;
	}

    public String getMessageTemplate() {
        return mdef.template;
    }

	public String render() {
		MessageRenderer renderer = MessageManager.messageManager().getMessageRenderer(mdef);
		StringMessageOutput out = new StringMessageOutput();
		renderer.render(this, out);
		return out.asString();
	}

}
