package com.cloud.code.util;

import java.util.regex.Pattern;
/**
 * @author summer
 */
public abstract class AbstractLineFilter {
	public abstract boolean accept(String line);
	public static AbstractLineFilter ACCEPT_ALL_FILTER = new AbstractLineFilter(){
		@Override
		public boolean accept(String line) {
			return true;
		}
	};
	public static class RegFilter extends AbstractLineFilter{
		private Pattern pattern = null;
		
		public RegFilter(String regexp){
			pattern = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
		}
		@Override
		public boolean accept(String line) {
			return  pattern.matcher(line).find();
		}
		
	}
}
