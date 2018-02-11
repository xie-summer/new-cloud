package com.itopener.redisson.spring.boot.autoconfigure.config;

/**  
 * @author fuwei.deng
 * @date 2018年1月5日 下午2:31:09
 * @version 1.0.0
 */
public class ConfigFile {

	/** json格式配置文件*/
	private String json;
	
	/** yaml格式配置文件*/
	private String yaml;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getYaml() {
		return yaml;
	}

	public void setYaml(String yaml) {
		this.yaml = yaml;
	}
}
