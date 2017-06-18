package it.uniroma3.pacman.collision.handlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import it.uniroma3.pacman.collision.CollisionHandler;

public class AutoCollisionHandler implements CollisionHandler {
	
	CollisionHandlers handlers = new CollisionHandlers();

	@Override
	public void handle(Object o1, Object o2) {
		Class<?> o1Class = o1.getClass();
		Class<?> o2Class = o2.getClass();
		
		Method method = null;
		
		try {
			method = handlers.getClass().getMethod("handle", o1Class, o2Class);
		} catch (NoSuchMethodException | SecurityException e) {
			/* Non e' prevista questa collisione */
		}
		
		try {
			method.invoke(handlers, o1, o2);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
