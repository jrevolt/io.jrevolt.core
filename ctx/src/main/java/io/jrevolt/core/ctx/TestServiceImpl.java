package io.jrevolt.core.ctx;

import io.jrevolt.core.util.Log;

/**
 * @author Patrik Beno
 */
public class TestServiceImpl implements TestService {
	public void test() {
		Log.debug("TestService.test()"); 
	}
}
