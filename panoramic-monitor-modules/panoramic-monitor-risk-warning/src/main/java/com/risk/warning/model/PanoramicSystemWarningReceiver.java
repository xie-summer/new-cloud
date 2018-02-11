package com.risk.warning.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cloud.model.BaseObject;

@Table(name = "panoramic_system_WarningReceiver")
public class PanoramicSystemWarningReceiver extends BaseObject {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private Integer warningconfigurationid;
    
    private Integer userid;
    
    private String username;
    
    private String email;
    
    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    } 
    
    
    public Integer getWarningConfigurationID() {
    	return warningconfigurationid;
    }
    
    public void setWarningConfigurationID(Integer warningconfigurationid) {
    	this.warningconfigurationid = warningconfigurationid;
    }
    
    
    public Integer getUserID() {
    	return userid;
    }
    
    public void setUserID(Integer userid) {
    	this.userid = userid;
    }
    
    
    public String getUserName() {
    	return username;
    }
    
    public void setUserName(String username) {
    	this.username = username;
    }


    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    @Override
    public Serializable realId() {
        return null;
    }
}