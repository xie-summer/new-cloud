package com.itopener.cache.redis.caffeine.spring.boot.autoconfigure;

import java.util.Random;

/**  
 * @author fuwei.deng
 * @date 2018年2月11日 上午9:59:30
 * @version 1.0.0
 */
public class SyncTest {
	
	public static void main(String[] args) throws InterruptedException {
		new SyncTest().test();
		Thread.sleep(50000);
	}

	public void test() throws InterruptedException {
		for(int i=0; i<10; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						new SyncTest().exec("123-" + new Random().nextInt());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
			
		}
	}
	
	public void exec(Object key) throws InterruptedException {
		synchronized (key) {
			System.out.println(key);
			Thread.sleep(5000);
		}
	}
}
