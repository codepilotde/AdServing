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
package net.mad.ads.base.api.utils;

import java.lang.reflect.Proxy;

import net.mad.ads.base.api.model.user.Message;

public class BaseObjectProxyFactory {
	public static <T> T newInstance (Class<T>... clazz) {
		return (T) Proxy.newProxyInstance(BaseObjectProxyFactory.class.getClassLoader(), clazz, new BaseObjectProxyHandler());
	}
	
	public static void main (String [] args) {
		
		Message m = BaseObjectProxyFactory.newInstance(Message.class);
		
		m.setMessage("hallo");
		
		System.out.println(m.getMessage());
	}
}
