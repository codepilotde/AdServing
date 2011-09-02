package net.mad.ads.base.api.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.mad.ads.base.api.BaseObject;

public class BaseObjectProxyHandler extends BaseObject implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		String methodName = method.getName();
		if (methodName.startsWith("get")) {
			String name = methodName.substring(methodName.indexOf("get") + 3);
			return get(name);
		} else if (methodName.startsWith("set")) {
			String name = methodName.substring(methodName.indexOf("set") + 3);
			put(name, args[0]);
			return null;
		} else if (methodName.startsWith("is")) {
			String name = methodName.substring(methodName.indexOf("is") + 2);
			return (get(name));
		}
		return null;
	}

}
