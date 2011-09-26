/**
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
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
package net.mad.ads.common.template.impl.velocity;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import net.mad.ads.common.template.TemplateManager;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;



public class VMTemplateManager implements TemplateManager {

	private VelocityEngine ve = null;
	
	private Map<String, Template> templates = null;
	
	public void init(String templatePath) throws IOException {
		ve = new VelocityEngine();
		ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templatePath);
		try {
			ve.init();
		} catch (Exception e) {
			throw new IOException(e);
		}
		templates = new HashMap<String, Template>();
	}

	public void registerTemplate(String name, String file) throws IOException {
		try {
			Template t = ve.getTemplate(file);
			
			templates.put(name, t);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	public String processTemplate(String name, Map<String, Object> parameters)
			throws IOException {
		
		try {
			VelocityContext context = new VelocityContext();
			if (parameters != null) {
				for (String key : parameters.keySet()) {
					context.put(key, parameters.get(key));
				}
			}
			
			Template t = templates.get(name);
			
			StringWriter sw = new StringWriter();
			BufferedWriter bw = new BufferedWriter(sw);
			
			t.merge(context, bw);
			bw.flush();
			return sw.toString();
		} catch (Exception e) {
			throw new IOException(e);
		}

	}

}
