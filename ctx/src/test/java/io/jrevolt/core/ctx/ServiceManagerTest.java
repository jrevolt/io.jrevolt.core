package io.jrevolt.core.ctx;

import static io.jrevolt.core.ctx.ContextManager.*;
import static io.jrevolt.core.ctx.ServiceManager.service;
import org.junit.Test;

/**
 * @author Patrik Beno
 */
public class ServiceManagerTest {

    @Test
	public void loadService() {
		newContext();
		try {
			service(TestService.class).test();
		} finally {
			closeContext();
		}
	}

}
