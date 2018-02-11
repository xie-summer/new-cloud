package com.itopener.sequence.spring.boot.autoconfigure.support;

import java.util.Calendar;

/**
 * @description 分布式序列号生成器，twitter snowflake算法的实现
 * 	1位标志位（固定为0） + 41位时间位（当前时间减去固定时间） + 10位应用节点位（创建对象时传入） + 12位序列位（同一毫秒内自增，下一毫秒重置为0） = 64位long类型数据
 * @author fuwei.deng
 * @date 2018年1月25日 上午9:40:32
 * @version 1.0.0
 */
public class Sequence {

	/**
	 * 序列号的开始时间戳，建议固定某个时间。此时间不能变大，因为时间位（41位）是使用的当前时间和此时间之差<br/>
	 * 序列号可使用的时间就是此时间加上41位毫秒之后的时间 <br/>
	 * 		比如此时间是2018-01-01 00:00:00 000，即：1514736000000<br/>
	 * 		41位毫秒是Math.pow(2, 41)，即：2199023255552 得到时间：3713759255552，换算成时间是：2087-09-07<br/>
	 * 		15:47:35:552 也就是可以41位毫秒可以使用69年多<br/>
	 */
	static final long EPOCH;

	// 应用节点占10位，即最多支持1023个节点
	final long workerIdBits = 10;

	// 序列占12位，当同一节点需要在同一毫秒内产生序列号时，此序列自增，到下一毫秒时会重置为0
	final long sequenceBits = 12;

	//标志位，始终为0，保证产生的数字是正数
	final long sequenceMask = (1 << sequenceBits) - 1;

	// 应用节点位需要左移的位数，即是序列位数
	final long workerIdLeftShiftBits = sequenceBits;

	// 时间左移的位数，即是应用节点位数+序列位数
	final long timestampLeftShiftBits = workerIdLeftShiftBits + workerIdBits;

	// 应用节点的最大值，初始化此对象时需要判断是否在此范围内
	final long maxWorkerId = 1 << workerIdBits;

	// 应用节点id
	long workerId;

	// 同一毫秒内的序列号
	long sequence;
	
	// 产生序列号的最后时间，用于判断是否在同一毫秒内产生
	long lastTime;

	static {
		// 初始化起始时间，这里是 2018-01-01 00:00:00 000
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, Calendar.JANUARY, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		EPOCH = calendar.getTimeInMillis();
	}

	
	public Sequence(IWorker worker) {
		this.workerId = worker.getId();
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(
					String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
		}
	}

	/**
	 * @description 生成序列号，为了保证多线程时对序列（sequence）的操作，此方法保证同步操作
	 * @author fuwei.deng
	 * @date 2018年1月25日 下午3:45:11
	 * @version 1.0.0
	 * @return 序列号
	 */
	public synchronized long get() {
		long currentMillis = System.currentTimeMillis();

		if (currentMillis < lastTime) {
			throw new IllegalArgumentException(String.format(
					"Clock moved backwards, Refusing to generate id for %d milliseconds", lastTime - currentMillis));
		}

		if (lastTime == currentMillis) {
			if (0 == (sequence = ++sequence & sequenceMask)) {
				currentMillis = waitUntilNextTime(currentMillis);
			}
		} else {
			sequence = 0;
		}
		lastTime = currentMillis;
		return ((currentMillis - EPOCH) << timestampLeftShiftBits) | (workerId << workerIdLeftShiftBits)
				| sequence;
	}

	/**
	 * @description 当产生序列号时间相同并且同一毫秒内的序列已用完，则等待下一毫秒
	 * @author fuwei.deng
	 * @date 2018年1月25日 下午3:47:13
	 * @version 1.0.0
	 * @param lastTime
	 * @return
	 */
	private long waitUntilNextTime(final long lastTime) {
		long time = System.currentTimeMillis();
		while (time <= lastTime) {
			time = System.currentTimeMillis();
		}
		return time;
	}
}
