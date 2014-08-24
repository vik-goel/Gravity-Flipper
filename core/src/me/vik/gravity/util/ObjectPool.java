package me.vik.gravity.util;

import java.util.ArrayList;

public class ObjectPool<T> {

	private ArrayList<T> recycled = new ArrayList<T>(250);
	
	public void recycle(T t) {
		recycled.add(t);
	}
	
	public T get() {
		if (recycled.isEmpty())
			return null;
		
		return recycled.remove(recycled.size() - 1);
	}
	
}
