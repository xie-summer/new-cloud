package com.itopener.stock.spring.boot.autoconfigure.support;

/**  
 * @author fuwei.deng
 * @date 2018年2月7日 下午3:57:14
 * @version 1.0.0
 */
public enum StockCodeEnum {
	
	/** 库存初始化失败*/
	INIT_FAILED(-4, "库存初始化失败"),
	/** 库存未初始化*/
	NOT_INIT(-3, "库存未初始化"),
	/** 库存不足*/
	NOT_ENOUGH(-2, "库存不足"),
	/** 库存不限*/
	NOT_LIMITED(-1, "库存不限");

	private int code;
	
	private String desc;

	private StockCodeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	/**
	 * @description 库存操作是否成功
	 * @author fuwei.deng
	 * @date 2018年2月7日 下午4:02:53
	 * @version 1.0.0
	 * @return
	 */
	public boolean success(int code) {
		return code > -2;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
}
