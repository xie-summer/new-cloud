package com.cloud.code.util;
/**
 * @author summer
 */
public interface ClassFilter {
	boolean accept(Class clazz);
	public ClassFilter ACCEPT_ALL = new ClassFilter(){
		@Override
		public boolean accept(Class clazz) {
			return true;
		}
		
	};
}
