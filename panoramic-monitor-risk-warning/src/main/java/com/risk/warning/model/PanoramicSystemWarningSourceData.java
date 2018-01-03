package com.risk.warning.model;

import com.cloud.model.BaseObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author summer
 */
@Table(name = "PanoramicSystemWarningSourceData")
public class PanoramicSystemWarningSourceData extends BaseObject {
	/**
    *
    */
   private static final long serialVersionUID = 1L;
   

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   private String eventname;
   private String strevent;
   private Double eventvalue;
   private Integer status;
   private Date ctime;
   private Date utime;
   private Integer sourceid;
   /**
    * WarnConfigurationID
    */
   private Integer warnconfigurationid;
   
   /**
    * 获取ID
    * 
    * @return id -ID
    */
   public Integer getId() {
	   return id;
   }
   
   
   public void setId(Integer id) {
	   this.id = id;
   }
   
   public String getEventName() {
	   return eventname;
   }
   
   public void setEventName(String eventname) {
	   this.eventname = eventname;
   }
   
   public String getStrEvent() {
	   return strevent;
   }

   public void setStrEvent(String strevent) {
	   this.strevent = strevent;
   }
   
   public Double getEventValue() {
	   return eventvalue;
   }
   
   public void strEventValue(Double eventvalue) {
	   this.eventvalue = eventvalue;
   }
   
   public Integer getStatus() {
	   return status;
   }
   
   public void SetStatus(Integer status) {
	   this.status = status;
   }
   
   public Date getCtime() {
	   return ctime;
   }
   
   public void setCtime(Date ctime) {
	   this.ctime = ctime;
   }
   
   public Date getUtime() {
	   return utime;
   }
   
   public void setUtime(Date utime) {
	   this.utime = utime;
   }
   
   public Integer getSourceId() {
	   return sourceid;
   }
   
   
   public void setSourceId(Integer sourceid) {
	   this.sourceid = sourceid;
   }
   

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
   
    @Override
    public Serializable realId() {
        return null;
    }
}