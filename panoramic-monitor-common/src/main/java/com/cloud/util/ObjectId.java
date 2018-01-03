package com.cloud.util;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @author summer
 */
public class ObjectId implements Serializable {
	private static final long serialVersionUID = -4415279469780082174L;
	final int _time;
	final int _machine;
	final int _inc;
	private static AtomicInteger _nextInc = new AtomicInteger(new Random().nextInt());
	private static final int _genmachine;

	public static ObjectId get() {
		return new ObjectId();
	}
	/**
	 * return String type 
	 * */
	public static String uuid(){
		return get().toString();
	}
	public ObjectId() {
		this._time = (int) (System.currentTimeMillis() / 1000L);
		this._machine = _genmachine;
		this._inc = _nextInc.getAndIncrement();
	}
	public ObjectId(Long timestamp, Integer num1){
		this._time = (int) (timestamp / 1000L);
		this._machine = num1;
		this._inc = _nextInc.getAndIncrement();
	}
	public ObjectId(Long timestamp, Integer num1, Integer num2){
		this._time = (int) (timestamp / 1000L);
		this._machine = num1;
		this._inc = num2;
	}
	public ObjectId(Long timestamp) {
		this._time = (int) (timestamp / 1000L);
		this._machine = _genmachine;
		this._inc = _nextInc.getAndIncrement();
	}
	/**
	 * second
	 * @return
	 */
	public Long getTimestamp(){
		return this._time*1000L;
	}
	public byte[] toByteArray() {
		byte[] b = new byte[12];
		ByteBuffer bb = ByteBuffer.wrap(b);
		bb.putInt(this._time);
		bb.putInt(this._machine);
		bb.putInt(this._inc);
		return b;
	}
	@Override
	public String toString() {
		byte[] b = toByteArray();
		StringBuilder buf = new StringBuilder(24);
		for (int i = 0; i < b.length; ++i) {
			int x = b[i] & 0xFF;
			String s = Integer.toHexString(x);
			if (s.length() == 1) {
                buf.append("0");
            }
			buf.append(s);
		}
		return buf.toString();
	}
	static {
		try {
			int machinePiece;
			try {
				StringBuilder sb = new StringBuilder();
				Enumeration e = NetworkInterface.getNetworkInterfaces();
				while (e.hasMoreElements()) {
					NetworkInterface ni = (NetworkInterface) e.nextElement();
					sb.append(ni.toString());
				}
				machinePiece = sb.toString().hashCode() << 16;
			} catch (Exception e) {
				machinePiece = new Random().nextInt() << 16;
			}
			int processId = new Random().nextInt();
			try {
				processId = ManagementFactory.getRuntimeMXBean().getName().hashCode();
			} catch (Throwable t) {
			}
			ClassLoader loader = ObjectId.class.getClassLoader();
			int loaderId = (loader != null) ? System.identityHashCode(loader) : 0;

			StringBuilder sb = new StringBuilder();
			sb.append(Integer.toHexString(processId));
			sb.append(Integer.toHexString(loaderId));
			int processPiece = sb.toString().hashCode() & 0xFFFF;

			_genmachine = machinePiece | processPiece;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}