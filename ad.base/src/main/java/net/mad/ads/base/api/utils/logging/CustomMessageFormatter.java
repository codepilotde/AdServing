package net.mad.ads.base.api.utils.logging;

import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
public class CustomMessageFormatter extends Formatter {

	private MessageFormat messageFormat = new MessageFormat("{0}\t{1}\t{2,date,dd:MM:yy:h:mm:ss.SSS}:\t\t{3}\n");

	@Override
	public String format(LogRecord record) {
		Object[] arguments = new Object[4];
		arguments[0] = record.getLevel();
		arguments[1] = Thread.currentThread().getName();
		arguments[2] = new Date(record.getMillis());
		arguments[3] = record.getMessage();
		return messageFormat.format(arguments);
	}

	public CustomMessageFormatter( MessageFormat mf) {
		super();
		messageFormat = mf;
	}

}
