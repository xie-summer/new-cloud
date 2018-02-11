
package com.cloud.serialize.support.hessian;

import com.caucho.hessian.io.Hessian2Output;
import com.cloud.serialize.ObjectOutput;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author summer
 */
public class Hessian2ObjectOutput implements ObjectOutput
{
	private final Hessian2Output mH2o;

	public Hessian2ObjectOutput(OutputStream os)
	{
		mH2o = new Hessian2Output(os);
		mH2o.setSerializerFactory(Hessian2SerializerFactory.SERIALIZER_FACTORY);
	}

	@Override
    public void writeBool(boolean v) throws IOException
	{
		mH2o.writeBoolean(v);
	}

	@Override
    public void writeByte(byte v) throws IOException
	{
		mH2o.writeInt(v);
	}

	@Override
    public void writeShort(short v) throws IOException
	{
		mH2o.writeInt(v);
	}

	@Override
    public void writeInt(int v) throws IOException
	{
		mH2o.writeInt(v);
	}

	@Override
    public void writeLong(long v) throws IOException
	{
		mH2o.writeLong(v);
	}

	@Override
    public void writeFloat(float v) throws IOException
	{
		mH2o.writeDouble(v);
	}

	@Override
    public void writeDouble(double v) throws IOException
	{
		mH2o.writeDouble(v);
	}

	@Override
    public void writeBytes(byte[] b) throws IOException
	{
		mH2o.writeBytes(b);
	}

	@Override
    public void writeBytes(byte[] b, int off, int len) throws IOException
	{
		mH2o.writeBytes(b, off, len);
	}

	@Override
    public void writeUTF(String v) throws IOException
	{
		/**
		 * xxx Writes a string value to the stream using UTF-8 encoding.
		 */
		mH2o.writeString(v);
	}

	@Override
    public void writeObject(Object obj) throws IOException
	{
		mH2o.writeObject(obj);
	}

	@Override
    public void flushBuffer() throws IOException
	{
		mH2o.flushBuffer();
	}
}