package io.jrevolt.core.util;

import io.jrevolt.core.util.Assert;
import io.jrevolt.core.util.FlagSupport;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author Patrik Beno
 */
public class UtilsTest {

	/**
	 * not really a unit test: needs visual verification (programatic is too great an effort for this)
	 */
	@Test
	public void logNotYetImplemented() {
		Assert.logNotYetImplemented();
	}

	@Test
	public void flagSupport() {

		class OptionFlags extends FlagSupport<Option> {
			protected Class<Option> type() {
				return Option.class;
			}
		}

		OptionFlags options = new OptionFlags();
		options.reset();
		assert options.all().isEmpty();

		options.set(Option.A);
		assert options.isSet(Option.A);
		assert options.all().contains(Option.A);

		options.reset();
		options.set(Option.A).set(Option.B).set(Option.C);
		assert options.isSet(Option.A);
		assert options.isSet(Option.B);
		assert options.isSet(Option.C);
		assert options.isAllSet(Option.A, Option.B, Option.C);
		assert options.all().containsAll(Arrays.asList(Option.values()));

		options.reset().setAll(Option.values());
		assert options.all().containsAll(Arrays.asList(Option.values()));

		options.clear(Option.A);
		assert !options.isSet(Option.A);
		assert options.all().containsAll(Arrays.asList(Option.B, Option.C));

		options.clearAll(Option.A, Option.B, Option.C);
		assert options.all().isEmpty();
	}

	enum Option {A,B,C}
}
