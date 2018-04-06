package com.cloud.serialize;

import java.io.IOException;
/**
 * @author summer
 */
public interface ObjectOutput extends DataOutput {

	/**
	 * write object.
	 * 
	 * @param obj object.
	 */
	void writeObject(Object obj) throws IOException;

}