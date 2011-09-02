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
