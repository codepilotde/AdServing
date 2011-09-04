
package net.mad.ads.base.api.utils.logging;
/*
 * Mad-Advertisement 
 * Copyright (C) 2011 Thorsten Marx
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
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
