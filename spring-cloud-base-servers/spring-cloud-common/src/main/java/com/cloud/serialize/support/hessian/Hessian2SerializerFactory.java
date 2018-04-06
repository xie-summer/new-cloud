
package com.cloud.serialize.support.hessian;

import com.caucho.hessian.io.*;

import java.math.BigDecimal;

/**
 * lazy-load
 * @author Johnson Yang
 *
 */
public class Hessian2SerializerFactory extends SerializerFactory {

	public static final SerializerFactory SERIALIZER_FACTORY = new Hessian2SerializerFactory();

	static{
		AbstractSerializerFactory forBigDecimal = new AbstractSerializerFactory(){

			@Override
			public Serializer getSerializer(Class paramClass) throws HessianProtocolException {
				if (paramClass == BigDecimal.class) {
					return  new StringValueSerializer();
				}
				return null;
			}

			@Override
			public Deserializer getDeserializer(Class paramClass) throws HessianProtocolException {
				if (paramClass == BigDecimal.class) {
					return  new BigDecimalDeserializer();
				} /*else if (paramClass == char[].class) {
					return  new 
				}*/
				return null;
			}
			
		};
		
		SERIALIZER_FACTORY.addFactory(forBigDecimal);
	}
	private Hessian2SerializerFactory() {
		
	}

	@Override
	/**
	 */
	public ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

}
