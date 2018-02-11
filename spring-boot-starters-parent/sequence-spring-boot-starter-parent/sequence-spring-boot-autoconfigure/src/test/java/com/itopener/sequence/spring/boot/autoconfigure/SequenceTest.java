package com.itopener.sequence.spring.boot.autoconfigure;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import com.itopener.sequence.spring.boot.autoconfigure.support.IWorker;
import com.itopener.sequence.spring.boot.autoconfigure.support.Sequence;

/**  
 * @author fuwei.deng
 * @date 2018年1月11日 上午9:32:50
 * @version 1.0.0
 */
public class SequenceTest {
	
	private AtomicLong atomicLong = new AtomicLong();

	private Set<Long> set = Collections.synchronizedSet(new HashSet<>());
	
	@Test
	public void test1() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, Calendar.JANUARY, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		System.out.println(calendar.getTimeInMillis());
		
		System.out.println(new BigDecimal(Math.pow(2, 41)).toString());
		
		System.out.println(new BigDecimal(calendar.getTimeInMillis() + Math.pow(2, 41)).toString());
		
		calendar.setTimeInMillis(2199023255552L);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(calendar.getTime()));
	}
	
	
	@Test
	public void test() {
		Sequence sequence = new Sequence(new IWorker() {
			@Override
			public long getId() {
				return 1;
			}
		});
		for(int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					long start = System.currentTimeMillis();
					while ((System.currentTimeMillis() - start) < 10 * 1000) {
//						long id = sequence.get();
//						set.add(id);
						sequence.get();
						atomicLong.incrementAndGet();
					}
				}
			}).start();
		}
		
		try {
			Thread.sleep(1000 * 15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(atomicLong.get() + "===" + set.size());
	}
}
