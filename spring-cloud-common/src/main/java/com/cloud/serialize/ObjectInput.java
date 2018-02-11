package com.cloud.serialize;

import java.io.IOException;
import java.lang.reflect.Type;
/**
 * @author summer
 */
public interface ObjectInput extends DataInput {

	/**
	 * read object.
	 * 
	 * @return object.
	 */
	Object readObject() throws IOException, ClassNotFoundException;

	/**
	 * read object.
	 * 
	 * @param cls object type.
	 * @return object.
	 */
	<T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException;

	/**
	 * read object.
	 * 
	 * @param cls object type.
	 * @return object.
	 */
	<T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException;

}