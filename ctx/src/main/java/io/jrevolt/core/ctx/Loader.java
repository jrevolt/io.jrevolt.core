package io.jrevolt.core.ctx;

import java.io.InputStream;
import io.jrevolt.core.util.Assert;
import io.jrevolt.core.util.IOHelper;
import io.jrevolt.core.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Patrik Beno
 */
public class Loader {

	protected String resource;

	protected Map<Class,Class> classByInterface;

	public Loader(String resource) {
		this.resource = resource;
		this.classByInterface = new HashMap<Class, Class>();
	}

	protected void loadResources() {
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			Enumeration<URL> e = cl.getResources(resource);
			while (e.hasMoreElements()) {
				InputStream in = null;
				try {
					URL url = e.nextElement();
					Log.debug("Loading type:implementation mapping from %s", url);
					in = url.openStream();
					Properties props = new Properties();
					props.load(in);
					load(props);
				} finally {
					IOHelper.close(in);
				}
			}
		} catch (IOException e) {
			throw Assert.notYetHandled(e);
		}
	}

	protected void load(Properties props) {
		Enumeration<?> names = props.propertyNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String implname = props.getProperty(name);
			try {
				Class iface = load(name);
				Class impl = load(implname);
				classByInterface.put(iface, impl);
			} catch (Exception e) {
				Assert.logNotYetImplemented();
			}
		}
	}

	protected Class load(String clsname) {
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			Class<?> cls = Class.forName(clsname, false, cl);
			return cls;
		} catch (ClassNotFoundException e) {
			throw Assert.notYetHandled(e);
		}
	}

}
