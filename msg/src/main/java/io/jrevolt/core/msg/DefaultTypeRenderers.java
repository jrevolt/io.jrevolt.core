package io.jrevolt.core.msg;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:patrikbeno@gmail.com">Patrik Beno</a>
 * @version $Id$
 */
public class DefaultTypeRenderers {

	static public final TypeRenderer<?> DEFAULT = new TypeRenderer<Object>() {
		@Override
		public String getName() {
			return "object";
		}

		@Override
		public TypeRenderer<Object> create(List<String> properties) {
			return this;
		}
	};

    @SuppressWarnings("unchecked")
    static public <T> TypeRenderer<T> defaultTypeRenderer() {
        return (TypeRenderer<T>) DEFAULT;
    }

	static public class DateTypeRenderer extends TypeRenderer<Date> {

		SimpleDateFormat sdf;
		MessageFormat format;


		@Override
		public String getName() {
			return "date";
		}

		@Override
		public TypeRenderer<Date> create(List<String> properties) {
			DateTypeRenderer renderer = new DateTypeRenderer();
			if (properties != null && properties.size() > 0) {
				renderer.format = new MessageFormat("{0,date," + properties.get(0) + "}");
				renderer.sdf = new SimpleDateFormat(properties.get(0));
			}
			return renderer;
		}

		Calendar cal = Calendar.getInstance();

		@Override
		public void render(MessageOutput out, Date value) {
			if (format != null) {
//                String s = format.format(new Object[] {value});
//                buf.put(toChars(s));
//                buf.put(s);

//                buf.put(sdf.format(value));

				cal.setTime(value);
				StringBuilder sb = new StringBuilder()
						.append(cal.get(Calendar.DAY_OF_MONTH)).append('.')
						.append(cal.get(Calendar.MONTH)).append('.')
						.append(cal.get(Calendar.YEAR));
				out.write(sb);
			} else {
				super.render(out, value);
			}
		}
	}

}


