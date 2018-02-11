package com.itopener.stock.spring.boot.autoconfigure.support;

/**  
 * @author fuwei.deng
 * @date 2018年2月6日 下午5:39:49
 * @version 1.0.0
 */
public interface IStockCallback {

	/**
	 * @description 回源数据库获取最新库存
	 * @author fuwei.deng
	 * @date 2018年2月7日 上午9:26:01
	 * @version 1.0.0
	 * @param stock 库存名称，可以利用此名称，统一一个IStockCallback的实现类，根据名称去调用对应的库存查询的实现
	 * @return
	 */
	public long getStock(String stock);
}
