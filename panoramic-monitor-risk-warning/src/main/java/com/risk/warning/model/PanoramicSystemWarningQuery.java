package com.risk.warning.model;

import com.cloud.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author summer
 */
@Table(name = "PanoramicSystemWarningQuery")
public class PanoramicSystemWarningQuery extends BaseObject {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * WarnConfigurationID
     */
    private Integer warnconfigurationid;
    
    private String querysql;
    
    private Integer intervaltime;
    
    private Date executetime;

    /**
     * 获取WarnConfigurationID
     *
     * @return WarnConfigurationID - ID
     */
    public Integer getWarnConfigurationID() {
        return warnconfigurationid;
    }

    /**
     * 设置WarnConfigurationID
     *
     * @param WarnConfigurationID ID
     */
    public void setWarnConfigurationID(Integer warnconfigurationid) {
        this.warnconfigurationid = warnconfigurationid;
    } 
    
    public String getQuerySql() {
    	return querysql;
    	
    }
    
    public void setQuerySql(String querysql) {
    	this.querysql = querysql;
    }
    
  
    public Integer getIntervalTime() {
    	return intervaltime;
    }
    
    public void setIntervalTime(Integer intervaltime) {
    	this.intervaltime = intervaltime;
    }
    
    public Date getExecuteTime() {
    	return executetime;
    }
    
    public void setExecuteTime(Date executetime) {
    	this.executetime = executetime;
    } 
    
    @Override
    public Serializable realId() {
        return null;
    }
}